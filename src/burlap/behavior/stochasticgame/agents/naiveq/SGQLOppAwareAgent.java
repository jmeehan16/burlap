package burlap.behavior.stochasticgame.agents.naiveq;

import java.util.Map;

import burlap.behavior.statehashing.StateHashFactory;
import burlap.behavior.stochasticgame.agents.naiveq.operators.BackupOp;
import burlap.behavior.stochasticgame.agents.naiveq.operators.CoCoQ;
import burlap.behavior.stochasticgame.agents.naiveq.operators.MaxOp;
import burlap.behavior.stochasticgame.agents.naiveq.operators.MaxTotalVal;
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
		//double opR = jointReward.get(opponent.getAgentName());
		
		//double maxmax = operator.performOp(this, opponent, sprime);
		
		SGQValue qe = this.getSGQValue(s, myAction);
		double qValue = 0.;
		//double qValueOp = 0.;
		/**
		if(!isTerminal){
			maxQ = this.getMaxQValue(sprime);
		}
		*/
		if(!isTerminal){
			System.out.println("IN TERMINAL");
			qValue = operator.performOp(this, opponent, sprime);
			//qValueOp = operator.performOp(opponent, this, sprime);
			//assert(qValue == qValueOp);
			
		}
		if(operator instanceof CoCoQ)
		{
			
			BackupOp maxUtil = new MaxTotalVal();
			double a = operator.performOp(this,opponent,s);
			double b = maxUtil.performOp(this, opponent, s);
			double sidePayment = a - b;
			//System.out.println(this.getAgentName() + ": " + a + " - " + b);
			//if(this.getAgentName().equals("default0"))
			//	System.out.println()
			//System.out.println(this.getAgentName() + " Q: " + qValue + "  side: " + sidePayment);
			//if(sidePayment >= 0.00000000001 || sidePayment <= -0.000000000001)
			//	System.out.println(this.getAgentName() + ": " + sidePayment);
			r -= sidePayment;
		}
		
		
			
		qe.q = qe.q + this.learningRate * (r + (this.discount * qValue) - qe.q);
		//System.out.println(this.getAgentName() + ": " + qe.q);
	}

}
