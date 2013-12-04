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

public class CoordinatedGridGame extends  GridGameRevisited {

	public CoordinatedGridGame(){
		this.width = 3;
		this.height = 3;
		this.numGoals = 2;
		this.numAgents = 2;
	}
	
	
	public State generateState(List<Agent> agents,Domain domain) {
		
		//create a state that is the same as the one in the GridGame main method
		
		//this method will create object instances for the number of agents, but they will have arbirary names
		//and are not necessarily the same as the names of the agents in the world.
		State s = GridGame.getCleanState(domain, this.numAgents, this.numGoals, 3, 2, this.width, this.height);
		
		
		GridGame.setAgent(s, 0, 2, 0, 1);
		GridGame.setAgent(s, 1, 0, 0, 4);
		
		GridGame.setGoal(s, 0, 2, 2, 5);
		GridGame.setGoal(s, 1, 0, 2, 2); 
		
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
	
	
	/*public static void main(String [] args){
		
		GridGame gg = new CoordinatedGridGame();
		
		int gameWidth = 3;
		int gameHeight = 3;
		int numGoals = 2;
		SGDomain d = (SGDomain)gg.generateDomain();
		
		State s = getCleanState(d, 2, numGoals, 3, 2, gameWidth, gameHeight);
		
		setAgent(s, 0, 2, 0, 1);
		setAgent(s, 1, 0, 0, 4);
		
		setGoal(s, 0, 2, 2, 5);
		setGoal(s, 1, 0, 2, 2); 
		
		int numOfHorizontalCells = 0; //cells
		setHorizontalWall(s, 2, 4, 1, numOfHorizontalCells, 1);
		
		
		//System.out.println(s.getCompleteStateDescription());
		
		
		JointActionModel jam = new GridGameStandardMechanics(d);
		
		Visualizer v = GGVisualizer.getVisualizer(gameWidth, gameHeight);
		int visWidth = 1200*gameWidth/(gameWidth+gameHeight);
		int visHeight = 1200*gameHeight/(gameWidth+gameHeight);
		SGVisualExplorer exp = new SGVisualExplorer(d, v, s, jam,visWidth,visHeight);
		
		
		

		
		
	}*/

	
	
}
