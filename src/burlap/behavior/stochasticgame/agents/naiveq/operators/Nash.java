package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.Iterator;
import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQLAgent;
import burlap.behavior.stochasticgame.agents.naiveq.SGQLOppAwareAgent;
import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;
import burlap.oomdp.core.State;
import burlap.solvers.BimatrixGeneralSumSolver;


public class Nash extends BackupOp {
	
	public double performOp(SGQLOppAwareAgent a1, SGQLOppAwareAgent a2, State s)
	{
		List<SGQValue> thisAgentQVal = a1.getAllQsFor(s);
		List<SGQValue> otherAgentQVal = a2.getAllQsFor(s);
		
		assert(thisAgentQVal.size() == otherAgentQVal.size());
		double[][] payout1 = new double[thisAgentQVal.size()][otherAgentQVal.size()];
		double[][] payout2 = new double[thisAgentQVal.size()][otherAgentQVal.size()];
		int i = 0;
		int j = 0;
		
		Iterator<SGQValue> itr1 = thisAgentQVal.iterator();
		Iterator<SGQValue> itr2 = otherAgentQVal.iterator();
		
		while(itr1.hasNext())
		{
			SGQValue val1 = itr1.next();
			while(itr2.hasNext())
			{
				SGQValue val2 = itr2.next();
				payout1[i][j] = val1.q;
				payout2[i][j] = val2.q;
				j++;
			}
			i++;
			j = 0;
			itr2 = otherAgentQVal.iterator();
		}
		

		return BimatrixGeneralSumSolver.generalSumNash(payout1, payout2);
	}
	
}
