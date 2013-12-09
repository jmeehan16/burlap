package burlap.behavior.stochasticgame.agents.naiveq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.AgentType;
import burlap.oomdp.stochasticgames.GroundedSingleAction;
import burlap.oomdp.stochasticgames.JointAction;
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
	
	public JointAction getJointAction(State s) {
		
		
		List<JointAction> allJAs = JointAction.getAllPossibleJointActions(s, agent, opponent);
		
		double roll = rand.nextDouble();
		if(roll > e || stopExploring){
			//choose randomly among max satisfying actions
			
			List <Integer> maxCands = this.getMaxJointActions(s, allJAs);
			
			if(maxCands.size() == 1){
				return allJAs.get(maxCands.get(0));
			}
			else{
				int ind = maxCands.get(rand.nextInt(maxCands.size()));
				return allJAs.get(ind);
			}
			
			
		}
		
		
		//otherwise select randomly
		return allJAs.get(rand.nextInt(allJAs.size()));
		
	}
	
	public List<Integer> getMaxJointActions(State s, List<JointAction> allJAs) {

		List<SGQJAValue> aVal1 = ((SGQLOppAwareAgent)agent).getAllJAQsFor(s);
		List<SGQJAValue> aVal2 = opponent.getAllJAQsFor(s);
		JointAction ja;
		List<Integer> maxCands = new ArrayList<Integer>(allJAs.size());
		
		double maxQ = Double.NEGATIVE_INFINITY;
		double nowQ = Double.NEGATIVE_INFINITY;
		
		Collections.sort(aVal1);
		Collections.sort(aVal2);
		
		for(int i = 0; i < allJAs.size(); i++) {
			ja = allJAs.get(i);
			nowQ = aVal1.get(i).q;
			nowQ += aVal2.get(i).q;
			
			//nowQ = aVal1.get(aVal1.lastIndexOf(ja.action(agent.getAgentName()))).q;
			//nowQ += aVal2.get(aVal2.lastIndexOf(ja.action(opponent.getAgentName()))).q;
			if(nowQ > maxQ) {
				maxCands.clear();
				maxCands.add(i);
				maxQ = nowQ;
			}
			else if(nowQ == maxQ){
				maxCands.add(i);
			}
		}
		return maxCands;
	}
	
	public List<Integer> getMaxJointActionsCoop(State s, List<JointAction> allJAs) {

		List<SGQJAValue> aVal1 = ((SGQLOppAwareAgent)agent).getAllJAQsFor(s);
		List<SGQJAValue> aVal2 = opponent.getAllJAQsFor(s);
		JointAction ja;
		List<Integer> maxCands = new ArrayList<Integer>(allJAs.size());
		
		double maxQ = Double.NEGATIVE_INFINITY;
		double nowQ = Double.NEGATIVE_INFINITY;
		
		Collections.sort(aVal1);
		Collections.sort(aVal2);
		
		for(int i = 0; i < allJAs.size(); i++) {
			ja = allJAs.get(i);
			nowQ = aVal1.get(i).q;
			nowQ += aVal2.get(i).q;
			
			//nowQ = aVal1.get(aVal1.lastIndexOf(ja.action(agent.getAgentName()))).q;
			//nowQ += aVal2.get(aVal2.lastIndexOf(ja.action(opponent.getAgentName()))).q;
			if(nowQ > maxQ) {
				maxCands.clear();
				maxCands.add(i);
				maxQ = nowQ;
			}
			else if(nowQ == maxQ){
				maxCands.add(i);
			}
		}
		return maxCands;
	}
	
	public List<Integer> getMaxJointActionsCompetitive(State s, List<JointAction> allJAs) {

		List<SGQJAValue> aVal1 = ((SGQLOppAwareAgent)agent).getAllJAQsFor(s);
		List<SGQJAValue> aVal2 = opponent.getAllJAQsFor(s);
		JointAction ja;
		List<Integer> maxCands = new ArrayList<Integer>(allJAs.size());
		
		double maxQ = Double.NEGATIVE_INFINITY;
		double nowQ = Double.NEGATIVE_INFINITY;
		
		Collections.sort(aVal1);
		Collections.sort(aVal2);
		
		for(int i = 0; i < allJAs.size(); i++) {
			ja = allJAs.get(i);
			nowQ = aVal1.get(i).q;
			nowQ += aVal2.get(i).q;
			
			//nowQ = aVal1.get(aVal1.lastIndexOf(ja.action(agent.getAgentName()))).q;
			//nowQ += aVal2.get(aVal2.lastIndexOf(ja.action(opponent.getAgentName()))).q;
			if(nowQ > maxQ) {
				maxCands.clear();
				maxCands.add(i);
				maxQ = nowQ;
			}
			else if(nowQ == maxQ){
				maxCands.add(i);
			}
		}
		return maxCands;
	}
	
	
	/**
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
	*/
	/**
	 * Returns a list of the index of actions with the maximum Q-value. Typically this list will be of size 1, but 
	 * if there are ties for the max action (which is typically in an unvisited state) it will return all of the tied actions.
	 * @param s the state in which to get Q-values
	 * @param srcGSAs the actions the agent can take and which is used to define the index of actions
	 * @return a list of the index actions with the maximum Q-value.
	 */
	/**
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
	*/

}
