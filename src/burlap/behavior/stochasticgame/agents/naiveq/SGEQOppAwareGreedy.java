package burlap.behavior.stochasticgame.agents.naiveq;

import java.util.ArrayList;
import java.util.List;

import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.AgentType;
import burlap.oomdp.stochasticgames.GroundedSingleAction;
import burlap.oomdp.stochasticgames.SingleAction;

public class SGEQOppAwareGreedy extends SGEQGreedy {
	
	protected SGQLOppAwareAgent    opponent;
	
	public SGEQOppAwareGreedy(SGQLAgent a, double e)
	{
		super(a, e);
		opponent = null;
	}
	
	public void setOpponent(SGQLOppAwareAgent o)
	{
		opponent = o;
	}
	
	@Override
	public GroundedSingleAction getAction(State s) {
		
		AgentType at = agent.getAgentType();
		AgentType ot = opponent.getAgentType();
		String aname = agent.getAgentName();
		String oname = opponent.getAgentName();
		
		List<GroundedSingleAction> gas = SingleAction.getAllPossibleGroundedSingleActions(s, aname, at.actions);
		List<GroundedSingleAction> ogas = SingleAction.getAllPossibleGroundedSingleActions(s, oname, ot.actions);
		
		double roll = rand.nextDouble();
		if(roll > e || stopExploring){
			//choose randomly among max satisfying actions
			
			List <Integer> maxCands = this.getMaxActions(s, gas, ogas);
			
			if(maxCands.size() == 1){
				((SGQLOppAwareAgent)agent).setLastAction(gas.get(maxCands.get(0)));
				return gas.get(maxCands.get(0));
			}
			else{
				int ind = maxCands.get(rand.nextInt(maxCands.size()));
				((SGQLOppAwareAgent)agent).setLastAction(gas.get(ind));
				return gas.get(ind);
			}
			
			
		}
		
		
		//otherwise select randomly
		((SGQLOppAwareAgent)agent).setLastAction(gas.get(rand.nextInt(gas.size())));
		return gas.get(rand.nextInt(gas.size()));
	}
	
	/**
	 * Returns a list of the index of actions with the maximum Q-value. Typically this list will be of size 1, but 
	 * if there are ties for the max action (which is typically in an unvisited state) it will return all of the tied actions.
	 * @param s the state in which to get Q-values
	 * @param srcGSAs the actions the agent can take and which is used to define the index of actions
	 * @return a list of the index actions with the maximum Q-value.
	 */
	protected List <Integer> getMaxActions(State s, List<GroundedSingleAction> srcGSAs, List<GroundedSingleAction> srcOGSAs){
		List <Integer> maxCands = new ArrayList<Integer>(srcGSAs.size());
		List <SGQValue> entries = agent.getAllQsFor(s, srcGSAs);
		List <SGQValue> oppEntries = opponent.getAllQsFor(s, srcOGSAs);
		double maxQ = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < entries.size(); i++){
			SGQValue qe = entries.get(i);
			if(opponent.getLastAction() == null)
			{
				for(int j = 0; j < oppEntries.size(); j++) {
					SGQValue oqe = oppEntries.get(j);
					if(qe.q + oqe.q > maxQ){
						maxCands.clear();
						maxCands.add(i);
						maxQ = qe.q + oqe.q;
					}
					else if(qe.q + oqe.q == maxQ && !maxCands.contains(i)){
						maxCands.add(i);
					}
				}
			}
			else
			{
				SGQValue oqe = opponent.getSGQValue(s, opponent.getLastAction());
				if(qe.q + oqe.q > maxQ){
					maxCands.clear();
					maxCands.add(i);
					maxQ = qe.q + oqe.q;
				}
				else if(qe.q + oqe.q == maxQ && !maxCands.contains(i)){
					maxCands.add(i);
				}
			}
			
		}
		
		return maxCands;
		
	}

}
