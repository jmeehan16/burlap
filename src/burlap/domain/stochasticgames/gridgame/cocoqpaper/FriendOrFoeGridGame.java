package burlap.domain.stochasticgames.gridgame.cocoqpaper;

import java.util.List;

import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.domain.stochasticgames.gridgame.GridGameStandardMechanics;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectInstance;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.Agent;
import burlap.oomdp.stochasticgames.JointActionModel;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.explorers.SGVisualExplorer;
import burlap.oomdp.visualizer.Visualizer;

/**
 * The GridGame domain is much like the GridWorld domain, except for arbitrarily many agents in
 * a stochastic game. Each agent in the world has an OO-MDO object instance of OO-MDP class "agent"
 * which is defined by an x position, a y position, and a player number. Agents can either move north, south, east,
 * west, or do nothing. There is also an OO-MDP object class for 1-dimensional walls (both for horizontal
 * walls or vertical walls). Each wall can take on a different type; a solid wall that can never be passed,
 * and a semi-wall, can be passed with some stochastic probability. Finally, there is also an OO-MDP
 * class for goal locations, which also have different types. There is a type that can be indicated
 * as a universal goal/reward location for all agents, and type that is only useful to each individual
 * agent.
 * @author James MacGlashan
 *
 */

public class FriendOrFoeGridGame extends  GridGameRevisited {

	public FriendOrFoeGridGame(){
		this.width = 8;
		this.height = 1;
		this.numGoals = 2;
		this.numAgents = 2;
	}
	
	
	public State generateState(List<Agent> agents) {
		
		//create a state that is the same as the one in the GridGame main method
		
		//this method will create object instances for the number of agents, but they will have arbirary names
		//and are not necessarily the same as the names of the agents in the world.
		State s = GridGame.getCleanState(this.domain, this.numAgents, this.numGoals, 3, 2, this.width, this.height);
		
		
		GridGame.setAgent(s, 0, 3, 0, 4);
		GridGame.setAgent(s, 1, 6, 0, 1);
		
		GridGame.setGoal(s, 0, 0, 0, 5);
		GridGame.setGoal(s, 1, 4, 0, 0); // neutral
		
		int numOfHorizontalCells = 0; //cells
		setHorizontalWall(s, 2, 4, 1, numOfHorizontalCells, 1);
		
		
		
		//rename the agent class object instances to match the name of the corresponding agents in the world 
		List<ObjectInstance> agentObs = s.getObjectsOfTrueClass(GridGame.CLASSAGENT);
		s.renameObject(agentObs.get(0), agents.get(0).getAgentName());
		
		if(agents.size() == 2){
			s.renameObject(agentObs.get(1), agents.get(1).getAgentName());
		}
		
		
		
		return s;
		
	}
	
	
	/*
	public static void main(String [] args){
		
		GridGame gg = new FriendOrFoeGridGame();
		
		int gameWidth = 8;
		int gameHeight = 1;
		int numGoals = 2;
		SGDomain d = (SGDomain)gg.generateDomain();
		
		State s = getCleanState(d, 2, numGoals, 3, 2, gameWidth, gameHeight);
		
		setAgent(s, 0, 3, 0, 4);
		setAgent(s, 1, 6, 0, 1);
		
		setGoal(s, 0, 0, 0, 5);
		setGoal(s, 1, 4, 0, 0); // neutral
		
		int numOfHorizontalCells = 0; //cells
		setHorizontalWall(s, 2, 4, 1, numOfHorizontalCells, 1);
		
		
		//System.out.println(s.getCompleteStateDescription());
		
		
		JointActionModel jam = new GridGameStandardMechanics(d);
		
		Visualizer v = GGVisualizer.getVisualizer(gameWidth, gameHeight);
		int visWidth = 800*gameWidth/(gameWidth+gameHeight);
		int visHeight = 800*gameHeight/(gameWidth+gameHeight);
		SGVisualExplorer exp = new SGVisualExplorer(d, v, s, jam,visWidth,visHeight);
		
		exp.setJAC("c"); //press c to execute the constructed joint action
		
		exp.addKeyAction("w", CLASSAGENT+"0:"+ACTIONNORTH);
		exp.addKeyAction("s", CLASSAGENT+"0:"+ACTIONSOUTH);
		exp.addKeyAction("d", CLASSAGENT+"0:"+ACTIONEAST);
		exp.addKeyAction("a", CLASSAGENT+"0:"+ACTIONWEST);
		exp.addKeyAction("q", CLASSAGENT+"0:"+ACTIONNOOP);
		
		exp.addKeyAction("i", CLASSAGENT+"1:"+ACTIONNORTH);
		exp.addKeyAction("k", CLASSAGENT+"1:"+ACTIONSOUTH);
		exp.addKeyAction("l", CLASSAGENT+"1:"+ACTIONEAST);
		exp.addKeyAction("j", CLASSAGENT+"1:"+ACTIONWEST);
		exp.addKeyAction("u", CLASSAGENT+"1:"+ACTIONNOOP);
		
		exp.initGUI();
		

		
		
	}*/


	
	
}
