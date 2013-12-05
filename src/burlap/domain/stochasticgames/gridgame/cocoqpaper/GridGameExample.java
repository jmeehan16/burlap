package burlap.domain.stochasticgames.gridgame.cocoqpaper;

import java.util.List;

import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectInstance;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.Agent;

public class GridGameExample extends GridGameRevisited {
	public GridGameExample(){
		this.width = 5;
		this.height = 5;
		this.numGoals = 3;
		this.numAgents = 2;
	}
	
	@Override
	public State generateState(List<Agent> agents) {
		State s = getCleanState(this.domain, this.numAgents , this.numGoals, 3, 2, this.width, this.height);

		setAgent(s, 0, 0, 0, 0);
		setAgent(s, 1, 4, 0, 1);

		setGoal(s, 0, 0, 4, 1);
		setGoal(s, 1, 2, 4, 0);
		setGoal(s, 2, 4, 4, 2);

		setHorizontalWall(s, 2, 4, 1, 3, 1);
		//setHorizontalWall(s, 2, 4, 1, 4, 0);
		
		//rename the agent class object instances to match the name of the corresponding agents in the world 
		List<ObjectInstance> agentObs = s.getObjectsOfTrueClass(GridGame.CLASSAGENT);
		s.renameObject(agentObs.get(0), agents.get(0).getAgentName());
		
		if(agents.size() == 2){
			s.renameObject(agentObs.get(1), agents.get(1).getAgentName());
		}
	
		return s;
	}
}
