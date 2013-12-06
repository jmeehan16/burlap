package burlap.behavior.stochasticgame.agents.naiveq;

import java.util.Map;

import burlap.behavior.statehashing.StateHashFactory;
import burlap.behavior.stochasticgame.agents.naiveq.operators.BackupOp;
import burlap.behavior.stochasticgame.agents.naiveq.operators.CoCoQ;
import burlap.behavior.stochasticgame.agents.naiveq.operators.MaxOp;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.Agent;
import burlap.oomdp.stochasticgames.GroundedSingleAction;
import burlap.oomdp.stochasticgames.JointAction;
import burlap.oomdp.stochasticgames.SGDomain;

public class SGQLOppAwareAgent extends SGQLAgent {
	protected SGQLOppAwareAgent opponent;
	protected BackupOp operator;
	
	public SGQLOppAwareAgent(SGDomain d, double discount, double learningRate, StateHashFactory hashFactory) {
		super(d, discount, learningRate, hashFactory);
		this.setOperator(new MaxOp());
	}
	
	public SGQLOppAwareAgent(SGDomain d, double discount, double learningRate, double defaultQ, StateHashFactory hashFactory) {
		super(d, discount, learningRate, defaultQ, hashFactory);
		this.setOperator(new MaxOp());
	}
	
	public void setOpponent(SGQLOppAwareAgent a)
	{
		opponent = a;
	}
	
	public void setOperator(BackupOp o)
	{
		operator = o;
	}
	
	public Agent getOpponent()
	{
		return opponent;
	}
	
	public void observeOutcome(State s, JointAction jointAction, Map<String, Double> jointReward, State sprime, boolean isTerminal) {
		
		if(internalRewardFunction != null){
			jointReward = internalRewardFunction.reward(s, jointAction, sprime);
		}
		
		GroundedSingleAction myAction = jointAction.action(worldAgentName);

		double r = jointReward.get(worldAgentName);
		SGQValue qe = this.getSGQValue(s, myAction);
		double maxQ = 0.;
		/**
		if(!isTerminal){
			maxQ = this.getMaxQValue(sprime);
		}
		*/
		if(!isTerminal){
			maxQ = operator.performOp(this, opponent, sprime);
		}
		
		if(operator instanceof CoCoQ)
			r -= maxQ;
			
		qe.q = qe.q + this.learningRate * (r + (this.discount * maxQ) - qe.q);

	}

}
