package burlap.tests.stochasticgames;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import burlap.behavior.statehashing.DiscreteStateHashFactory;
import burlap.behavior.stochasticgame.agents.naiveq.SGQFactory;
import burlap.behavior.stochasticgame.agents.naiveq.SGQLAgent;
import burlap.behavior.stochasticgame.agents.naiveq.SGQLOppAwareAgent;
import burlap.behavior.stochasticgame.agents.naiveq.SGQLOppAwareFactory;
import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;
import burlap.behavior.stochasticgame.agents.naiveq.operators.MaxMax;
import burlap.behavior.stochasticgame.agents.naiveq.operators.MaxOp;
import burlap.debugtools.DPrint;
import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.domain.stochasticgames.gridgame.GridGameStandardMechanics;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.CoordinatedGridGame;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.FriendOrFoeGridGame;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.GridGameExample;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.GridGameRevisited;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.HorizontalToyGridGame;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.Incredible;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.PrisonerGridGame;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.Turkey;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.Agent;
import burlap.oomdp.stochasticgames.AgentFactory;
import burlap.oomdp.stochasticgames.AgentType;
import burlap.oomdp.stochasticgames.JointActionModel;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.VisualizedWorld;
import burlap.oomdp.stochasticgames.World;
import burlap.oomdp.stochasticgames.explorers.SGVisualExplorer;
import burlap.oomdp.visualizer.Visualizer;

public class QLGGTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new QLGGTest();
	}
	
	public QLGGTest(){
		
		//create domain
		
		
		//GridGameRevisited game = new GridGameExample();
		//GridGameRevisited game = new CoordinatedGridGame();
		//GridGameRevisited game = new Turkey();
		//GridGameRevisited game = new Incredible();
		GridGameRevisited game = new FriendOrFoeGridGame();
		
		
		SGDomain domain = (SGDomain)game.generateDomain();
		
		//create hashing factory that only hashes on the agent positions (ignores wall attributes)
		DiscreteStateHashFactory hashingFactory = new DiscreteStateHashFactory();
		hashingFactory.addAttributeForClass(GridGame.CLASSAGENT, domain.getAttribute(GridGame.ATTX));
		hashingFactory.addAttributeForClass(GridGame.CLASSAGENT, domain.getAttribute(GridGame.ATTY));
		hashingFactory.addAttributeForClass(GridGame.CLASSAGENT, domain.getAttribute(GridGame.ATTPN));
		
		//parameters for q-learning
		double discount = 0.99;
		double learningRate = 0.1;
		double defaultQ = 0.;
		
		//create a factory for Q-learning, since we're going to make both of our agents a Q-learning agent with the same algorithm parameters
		//(alternatively, we could have just used the Q-learning constructor twice for each agent)
		AgentFactory af = new SGQLOppAwareFactory(domain, discount, learningRate, defaultQ, hashingFactory);
		//AgentFactory af = new SGQFactory(domain, discount, learningRate, defaultQ, hashingFactory);
		JointActionModel jam = new GridGameStandardMechanics(domain);
		
		SimpleGGStateGen stateGen = new SimpleGGStateGen(domain);
		
		//create our world
		VisualizedWorld w = new VisualizedWorld(domain, jam, new GGJointRewardFunction(domain), new GGTerminalFunction(domain), game);
		//World w = new World(domain, jam, new GGJointRewardFunction(domain), new GGTerminalFunction(domain), 
		//		stateGen);
		
		
		//make a single agent type that can use all actions and refers to the agent class of grid game that we will use for both our agents
		AgentType at = new AgentType("default", domain.getObjectClass(GridGame.CLASSAGENT), domain.getSingleActions());
		
		
		//generate our agents using our factory
		Agent a0 = af.generateAgent();
		Agent a1 = af.generateAgent();
		
		((SGQLOppAwareAgent)a0).setOpponent((SGQLOppAwareAgent)a1);
		((SGQLOppAwareAgent)a1).setOpponent((SGQLOppAwareAgent)a0);
		
		((SGQLOppAwareAgent)a0).setOperator(new MaxOp());
		((SGQLOppAwareAgent)a1).setOperator(new MaxOp());
		
		//have the agents join the world
		a0.joinWorld(w, at);
		a1.joinWorld(w, at);
		
		LinkedList<Agent> agents = new LinkedList<Agent>();
		agents.add(a0);
		agents.add(a1);
		
		
		//don't have the world print out debug info (comment out if you want to see it!)
		DPrint.toggleCode(w.getDebugId(), false);
		
		//int x = Integer.parseInt(domain.getAttribute(GridGame.ATTX).valueConstructor().getStringVal());
		//int y = Integer.parseInt(domain.getAttribute(GridGame.ATTY).valueConstructor().getStringVal());
		//State s = GridGame.getCleanState(domain, 2, 3, 3, 2, 5, 5);
		
		System.out.println("Starting training");
		int ngames = 100000;
		for(int i = 0; i < ngames; i++){
			if(i % 10 == 0){
				System.out.println("Game: " + i);
			}
			w.runGame();
		}
		
		System.out.println("Finished training");
		/**
		List<SGQValue> a0AllQs = ((SGQLAgent)a0).getAllQsFor(stateGen.generateState(agents));
		List<SGQValue> a1AllQs = ((SGQLAgent)a1).getAllQsFor(stateGen.generateState(agents));
		
		Iterator<SGQValue> iter1 = a0AllQs.iterator();
		Iterator<SGQValue> iter2 = a1AllQs.iterator();
		System.out.println("Agent 1");
		while(iter1.hasNext()){
			SGQValue v1 = iter1.next();
			System.out.println(v1.q);
		}
		System.out.println("Agent 2");
		while(iter2.hasNext()){
			SGQValue v2 = iter2.next();
			System.out.println(v2.q);
		}
		*/
		
		//turn debug back on if we want to observe the behavior of agents after they have already learned how to behave
		DPrint.toggleCode(w.getDebugId(), true);
		
		((SGQLAgent)a0).stopExploring(true);
		((SGQLAgent)a1).stopExploring(true);
		
		//run game to observe behavior
		w.runAndVisualizeGame();
		//w.runGame();
		
	}
	
	

}
