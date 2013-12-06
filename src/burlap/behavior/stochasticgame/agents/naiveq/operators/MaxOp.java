package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.Iterator;
import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;
import burlap.behavior.stochasticgame.agents.naiveq.operators.BackupOp.Tuple;
import burlap.nash.TwoPersonZeroSumGameNash;
import burlap.oomdp.core.State;
import burlap.behavior.stochasticgame.agents.naiveq.SGQLAgent;
import burlap.oomdp.stochasticgames.Agent;
import burlap.oomdp.stochasticgames.GroundedSingleAction;
import burlap.oomdp.stochasticgames.SingleAction;


public class MaxOp extends BackupOp {
	
	public double performOp(SGQLAgent a1, SGQLAgent a2, State s)
	{
			
		List<GroundedSingleAction> gas = SingleAction.getAllPossibleGroundedSingleActions(s, a1.getAgentName(), a1.getAgentType().actions);
		List <SGQValue> entries = a1.getAllQsFor(s, gas);
		
		double maxQ = Double.NEGATIVE_INFINITY;
		for(SGQValue qe : entries){
			if(qe.q > maxQ){
				maxQ = qe.q;
			}
		}
		System.out.println(a1.getAgentName() + ": " + maxQ);
		return maxQ;
		
	}

}
