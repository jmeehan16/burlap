package burlap.tests.stochasticgames;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.GroundedProp;
import burlap.oomdp.core.ObjectInstance;
import burlap.oomdp.core.PropositionalFunction;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.JointAction;
import burlap.oomdp.stochasticgames.JointReward;


/**
 * Returns a reward of 1 to any agent that reaches a goal location; 0 otherwise
 * @author James MacGlashan
 *
 */
public class GGCoCoJointRewardFunction implements JointReward {

	PropositionalFunction agentInPersonalGoal;
	PropositionalFunction agentInUniversalGoal;
	
	double defaultReward = -1.;
	double goalReward = 100.;
	
	public GGCoCoJointRewardFunction(Domain ggDomain){
		agentInPersonalGoal = ggDomain.getPropFunction(GridGame.PFINPGOAL);
		agentInUniversalGoal = ggDomain.getPropFunction(GridGame.PFINUGOAL);
	}
	
	@Override
	public Map<String, Double> reward(State s, JointAction ja, State sp) {
		
		Map <String, Double> rewards = new HashMap<String, Double>();
		double totalReward = 0.0;
		
		//get all agents and initialize reward to default
		List <ObjectInstance> obs = sp.getObjectsOfTrueClass(GridGame.CLASSAGENT);
		for(ObjectInstance o : obs){
			rewards.put(o.getName(), defaultReward);
			totalReward += defaultReward;
		}
		
		
		//check for any agents that reached a personal goal location and give them a goal reward if they did
		List<GroundedProp> ipgps = sp.getAllGroundedPropsFor(agentInPersonalGoal);
		for(GroundedProp gp : ipgps){
			String agentName = gp.params[0];
			if(gp.isTrue(sp)){
				totalReward += goalReward;
				rewards.put(agentName, rewards.get(agentName) + goalReward);
			}
		}
		
		
		//check for any agents that reached a universal goal location and give them a goal reward if they did
		List<GroundedProp> upgps = sp.getAllGroundedPropsFor(agentInUniversalGoal);
		for(GroundedProp gp : upgps){
			String agentName = gp.params[0];
			if(gp.isTrue(sp)){
				totalReward += goalReward;
				rewards.put(agentName, rewards.get(agentName) + goalReward);
			}
		}
		
		for(ObjectInstance o : obs){
			rewards.put(o.getName(), rewards.get(o.getName()) + totalReward/obs.size());
		}
		
		return rewards;
		
	}

}
