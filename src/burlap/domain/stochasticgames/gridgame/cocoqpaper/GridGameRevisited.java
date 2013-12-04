package burlap.domain.stochasticgames.gridgame.cocoqpaper;

import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQLAgent;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectInstance;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.Agent;
import burlap.oomdp.stochasticgames.World;

public abstract class GridGameRevisited extends GridGame {


	protected int width;
    protected	int height;
	int numAgents;
	int numGoals;
	

	public State generateState(List<Agent> agents,Domain domain) {
		
		//create a state that is the same as the one in the GridGame main method
		
		//this method will create object instances for the number of agents, but they will have arbirary names
		//and are not necessarily the same as the names of the agents in the world.
		State s = GridGame.getCleanState(domain, agents.size(), 3, 3, 2, this.width, this.height);
		
		GridGame.setAgent(s, 0, 0, 0, 0);
		
		if(agents.size() == 2){
			GridGame.setAgent(s, 1, 4, 0, 1);
		}
		
		GridGame.setGoal(s, 0, 0, 4, 1);
		GridGame.setGoal(s, 1, 2, 4, 0);
		GridGame.setGoal(s, 2, 4, 4, 2);
		
		GridGame.setHorizontalWall(s, 2, 4, 1, 3, 1);
		
		
		//rename the agent class object instances to match the name of the corresponding agents in the world 
		List<ObjectInstance> agentObs = s.getObjectsOfTrueClass(GridGame.CLASSAGENT);
		s.renameObject(agentObs.get(0), agents.get(0).getAgentName());
		
		if(agents.size() == 2){
			s.renameObject(agentObs.get(1), agents.get(1).getAgentName());
		}
		
		
		
		return s;
		
	}
	
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}



	public void setHeight(int height) {
		this.height = height;
	}

}
