package burlap.behavior.stochasticgame.agents.naiveq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import burlap.behavior.statehashing.StateHashFactory;
import burlap.behavior.statehashing.StateHashTuple;
import burlap.behavior.stochasticgame.agents.naiveq.operators.BackupOp;
import burlap.behavior.stochasticgame.agents.naiveq.operators.CoCoQ;
import burlap.behavior.stochasticgame.agents.naiveq.operators.MaxOp;
import burlap.behavior.stochasticgame.agents.naiveq.operators.MaxTotalVal;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.Agent;
import burlap.oomdp.stochasticgames.AgentType;
import burlap.oomdp.stochasticgames.GroundedSingleAction;
import burlap.oomdp.stochasticgames.JointAction;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.SingleAction;

public class SGQLOppAwareAgent extends SGQLAgent {
	protected SGQLOppAwareAgent opponent;
	protected BackupOp operator;
	protected GroundedSingleAction myLastAction;
	protected Map <StateHashTuple, List<SGQJAValue>> qJAMap;
	protected Map <String, Integer>   jaIndex;
	protected boolean initJa;
	
	public SGQLOppAwareAgent(SGDomain d, double discount, double learningRate, StateHashFactory hashFactory) {
		super(d, discount, learningRate, hashFactory);
		this.setOperator(new MaxOp());
		this.strategy = new SGEQOppAwareGreedy(this, EPSILON);
		this.qJAMap = new HashMap<StateHashTuple, List<SGQJAValue>>();
		initJa = false;
	}
	
	public SGQLOppAwareAgent(SGDomain d, double discount, double learningRate, double defaultQ, StateHashFactory hashFactory) {
		super(d, discount, learningRate, defaultQ, hashFactory);
		this.setOperator(new MaxOp());
		this.strategy = new SGEQOppAwareGreedy(this, EPSILON);
		this.qJAMap = new HashMap<StateHashTuple, List<SGQJAValue>>();
		initJa = false;
	}
	
	public void initJAIndex(List<JointAction> jas)
	{
		Collections.sort(jas);
		this.jaIndex = new HashMap<String, Integer>();
		for(int i = 0; i < jas.size(); i++)
		{
			jaIndex.put(jas.get(i).toString(), i);
			System.out.println(jas.get(i).toString());
		}
	}
	
	public void setOpponent(SGQLOppAwareAgent a)
	{
		opponent = a;
		((SGEQOppAwareGreedy)strategy).setOpponent(a);
	}
	
	public void setOperator(BackupOp o)
	{
		operator = o;
	}
	
	public BackupOp getOperator()
	{
		return operator;
	}
	
	public Agent getOpponent()
	{
		return opponent;
	}
	
	public GroundedSingleAction getLastAction()
	{
		return myLastAction;
	}
	
	public void setLastAction(GroundedSingleAction gsa)
	{
		myLastAction = gsa;
	}
	
	public JointAction getJointAction(State s)
	{
		return ((SGEQOppAwareGreedy)strategy).getJointAction(s);
	}
	
	/**
	 * Returns all Q-values for the input state
	 * @param s the state for which all Q-values should be returned
	 * @return all Q-values for the input state
	 */
	public List <SGQJAValue> getAllJAQsFor(State s){
		
		List<JointAction> jas = null;
		
		jas = JointAction.getAllPossibleJointActions(s, this, opponent);
		
		if(!initJa)
		{
			initJAIndex(jas);
			initJa = true;
		}
		
		return this.getAllJAQsFor(s, jas);
	}
	
	/**
	 * Returns all the Q-values for the given state and actions
	 * @param s the state for which Q-values should be returned
	 * @param gsas the actions for which Q-values should be returned
	 * @return all the Q-values for the given state and actions
	 */
	public List <SGQJAValue> getAllJAQsFor(State s, List <JointAction> jas){
		
		StateHashTuple shq = this.stateHash(s);
		
		State storedRep = stateRepresentations.get(shq);
		if(storedRep == null){
			//no existing entry so we can create it
			stateRepresentations.put(shq, shq.s);
			List <SGQJAValue> entries = new ArrayList<SGQJAValue>();
			for(JointAction ja : jas){
				SGQJAValue q = new SGQJAValue(ja, defaultQ);
				entries.add(q);
			}
			qJAMap.put(shq, entries);
			return entries;
			
		}
		
		//otherwise an entry exists and we need to do the matching
		
		List <SGQJAValue> entries = qJAMap.get(shq);
		List <SGQJAValue> returnedEntries = new ArrayList<SGQJAValue>(jas.size());
		Map <String, String> matching = null;
		
		Collections.sort(entries);
		Collections.sort(jas);
		
		if(entries.size() != jas.size())
		{
			System.out.println(entries.size() + " " + jas.size());
			System.out.println("PANIC IN THE STREETS");
			//System.exit(-1);
			
			for(JointAction ja :jas){
				JointAction transja = ja;
				
				//find matching action in entry list
				boolean foundMatch = false;
				for(SGQJAValue qe : entries){
					if(qe.ja.equals(transja)){
						returnedEntries.add(qe);
						foundMatch = true;
						break;
					}
				}
				
				if(!foundMatch){
					SGQJAValue qe = new SGQJAValue(transja, defaultQ);
					entries.add(qe);
					returnedEntries.add(qe);
				}
			}
			Collections.sort(returnedEntries);
			
			return returnedEntries;
		}
		return entries;
		/** TODO In case the new code doesn't work
		for(JointAction ja :jas){
			JointAction transja = ja;
			
			//find matching action in entry list
			boolean foundMatch = false;
			for(SGQJAValue qe : entries){
				if(qe.ja.equals(transja)){
					returnedEntries.add(qe);
					foundMatch = true;
					break;
				}
			}
			
			if(!foundMatch){
				SGQJAValue qe = new SGQJAValue(transja, defaultQ);
				entries.add(qe);
				returnedEntries.add(qe);
			}
			
			
		}
		
		if(returnedEntries.size() == 0){
			throw new RuntimeException();
		}
		Collections.sort(returnedEntries);
		
		return returnedEntries;
		*/
	}
	
	
	/**
	 * Returns the Q-value for a given state-action pair
	 * @param s the state for which the Q-value should be returned
	 * @param gsa the action for which the Q-value should be returned.
	 * @return the Q-value for the given state-action pair.
	 */
	public SGQJAValue getSGQJAValue(State s, JointAction ja){
		StateHashTuple shq = this.stateHash(s);
		
		State storedRep = stateRepresentations.get(shq);
		if(storedRep == null){
			//no existing entry so we can create it
			stateRepresentations.put(shq, shq.s);
			SGQJAValue q = new SGQJAValue(ja, defaultQ);
			List <SGQJAValue> entries = new ArrayList<SGQJAValue>();
			entries.add(q);
			qJAMap.put(shq, entries);
			return q;
		}
		
		List <SGQJAValue> entries = qJAMap.get(shq);
		Collections.sort(entries);
		return entries.get(jaIndex.get(ja.toString()));
		
		/** TODO
		for(SGQJAValue qe : entries){
			if(qe.ja.equals(ja)){
				return qe;
			}
		}
		
		//if we got here then there are no entries for this action
		//System.out.println("NO ENTRIES");
		SGQJAValue qe = new SGQJAValue(ja, defaultQ);
		entries.add(qe);
		
		return qe;
		*/
	}
	
	//This is for when the Q values are associated with joint actions rather than single actions
	public void observeOutcomeJAQ(State s, JointAction jointAction, Map<String, Double> jointReward, State sprime, boolean isTerminal) {
		
		if(internalRewardFunction != null){
			jointReward = internalRewardFunction.reward(s, jointAction, sprime);
		}
		
		
		GroundedSingleAction myAction = jointAction.action(worldAgentName);

		double r = jointReward.get(worldAgentName);
		
		SGQJAValue qe = this.getSGQJAValue(s, jointAction);
		
		double qValue = 0.;

		if(!isTerminal){

			qValue = operator.performOp(this, opponent, sprime);
			
		}
			
		qe.q = qe.q + this.learningRate * (r + (this.discount * qValue) - qe.q);
		myLastAction = null;
	}
	
	
	public void observeOutcome(State s, JointAction jointAction, Map<String, Double> jointReward, State sprime, boolean isTerminal) {
		
		if(internalRewardFunction != null){
			jointReward = internalRewardFunction.reward(s, jointAction, sprime);
		}
		
		
		GroundedSingleAction myAction = jointAction.action(worldAgentName);

		double r = jointReward.get(worldAgentName);
		
		SGQValue qe = this.getSGQValue(s, myAction);
		
		double qValue = 0.;

		if(!isTerminal){

			qValue = operator.performOp(this, opponent, sprime);
			
		}
			
		qe.q = qe.q + this.learningRate * (r + (this.discount * qValue) - qe.q);
		myLastAction = null;
		//System.out.println(this.getAgentName() + ": " + qe.q);
	}

}
