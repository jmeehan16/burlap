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

public class Turkey extends GridGameRevisited {
	
	public Turkey(){
		this.width = 3;
		this.height = 4;
		this.numGoals = 3;
		this.numAgents = 2;
	}
	
	public State generateState(List<Agent> agents,Domain domain) {
		
		//create a state that is the same as the one in the GridGame main method
		
		//this method will create object instances for the number of agents, but they will have arbirary names
		//and are not necessarily the same as the names of the agents in the world.
		State s = GridGame.getCleanState(domain, this.numAgents, this.numGoals, 3, 2, this.width, this.height);
		
		setAgent(s, 0, 2, 0, 1);
		setAgent(s, 1, 0, 0, 4);
		
		setGoal(s, 0, 2, 3, 2);
		setGoal(s, 1, 1, 2, 0); 
		setGoal(s, 2, 0, 3, 5); 
		
		setHorizontalWall(s, 1, 1, 0, 0, 1);
		setHorizontalWall(s, 2, 1, 3, 1, 1);
		
		
		//rename the agent class object instances to match the name of the corresponding agents in the world 
		List<ObjectInstance> agentObs = s.getObjectsOfTrueClass(GridGame.CLASSAGENT);
		s.renameObject(agentObs.get(0), agents.get(0).getAgentName());
		
		if(agents.size() == 2){
			s.renameObject(agentObs.get(1), agents.get(1).getAgentName());
		}
		
		
		
		return s;
		
	}
	
	
//	public static void main(String [] args){
//		
//		GridGame gg = new Turkey();
//		
//		int gameWidth = 3;
//		int gameHeight = 4;
//		int numGoals = 3;
//		SGDomain d = (SGDomain)gg.generateDomain();
//		
//		State s = getCleanState(d, 2, numGoals, 3, 2, gameWidth, gameHeight);
//		
//		
//		
//		
//		//System.out.println(s.getCompleteStateDescription());
//		
//		
//		JointActionModel jam = new GridGameStandardMechanics(d);
//		
//		Visualizer v = GGVisualizer.getVisualizer(gameWidth, gameHeight);
//		int visWidth = 1200*gameWidth/(gameWidth+gameHeight);
//		int visHeight = 1200*gameHeight/(gameWidth+gameHeight);
//		SGVisualExplorer exp = new SGVisualExplorer(d, v, s, jam,visWidth,visHeight);
//		
//		exp.setJAC("c"); //press c to execute the constructed joint action
//		
//		exp.addKeyAction("w", CLASSAGENT+"0:"+ACTIONNORTH);
//		exp.addKeyAction("s", CLASSAGENT+"0:"+ACTIONSOUTH);
//		exp.addKeyAction("d", CLASSAGENT+"0:"+ACTIONEAST);
//		exp.addKeyAction("a", CLASSAGENT+"0:"+ACTIONWEST);
//		exp.addKeyAction("q", CLASSAGENT+"0:"+ACTIONNOOP);
//		
//		exp.addKeyAction("i", CLASSAGENT+"1:"+ACTIONNORTH);
//		exp.addKeyAction("k", CLASSAGENT+"1:"+ACTIONSOUTH);
//		exp.addKeyAction("l", CLASSAGENT+"1:"+ACTIONEAST);
//		exp.addKeyAction("j", CLASSAGENT+"1:"+ACTIONWEST);
//		exp.addKeyAction("u", CLASSAGENT+"1:"+ACTIONNOOP);
//		
//		exp.initGUI();
//		
//
//		
//		
//	}
}
