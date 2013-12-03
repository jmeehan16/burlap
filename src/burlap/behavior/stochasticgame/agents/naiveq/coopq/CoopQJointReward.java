package burlap.behavior.stochasticgame.agents.naiveq.coopq;

import java.util.*;

import burlap.oomdp.stochasticgames.JointAction;
import burlap.oomdp.stochasticgames.JointReward;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.oomdp.core.*;

public class CoopQJointReward implements JointReward {
	
	protected SGDomain									domain;
	
	public CoopQJointReward(SGDomain d)
	{
		this.domain = d;
	}
	
	public Map<String, Double> reward(State s, JointAction ja, State sp)
	{
		Map<String, Double> r = new HashMap<String, Double>();
		Double totalReward = 0.0;
		List<ObjectInstance> a = sp.getObjectsOfTrueClass(GridGame.CLASSAGENT);
		//List<ObjectInstance> g = sp.getObjectsOfTrueClass(GridGame.CLASSGOAL);
		
		for(ObjectInstance agent: a){
			//if the agent moves, then there is a step cost for both agents
			if(ja.action(agent.getName()).action.actionName != GridGame.ACTIONNOOP){
				totalReward -= 1;
			}
			
			//if the agent reaches a goal, both get 100 points
			for(PropositionalFunction pf: this.domain.getPropFunctions()){
				if(pf.isTrue(sp, agent.getName())){
					totalReward += 100;
				}
			}
		}
		for(ObjectInstance agent: a){
			r.put(agent.getName(), totalReward);
		}
		
		return r;
	}
}
