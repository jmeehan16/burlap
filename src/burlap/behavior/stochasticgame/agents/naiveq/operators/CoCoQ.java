package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.Iterator;
import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQLAgent;
import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;
import burlap.oomdp.core.State;
import burlap.solvers.BimatrixGeneralSumSolver;


public class CoCoQ extends BackupOp {
	
	public double performOp(SGQLAgent a1, SGQLAgent a2, State s)
	{
		List<SGQValue> thisAgentQVal = a1.getAllQsFor(s);
		List<SGQValue> otherAgentQVal = a2.getAllQsFor(s);
		
		assert(thisAgentQVal.size() == otherAgentQVal.size());
		double[][] payout1 = new double[thisAgentQVal.size()][otherAgentQVal.size()];
		double[][] payout2 = new double[thisAgentQVal.size()][otherAgentQVal.size()];
		int i = 0;
		int j = 0;
		double maxmax = Double.NEGATIVE_INFINITY;
		
		Iterator<SGQValue> itr1 = thisAgentQVal.iterator();
		Iterator<SGQValue> itr2 = otherAgentQVal.iterator();
		
		while(itr1.hasNext())
		{
			SGQValue val1 = itr1.next();
			while(itr2.hasNext())
			{
				SGQValue val2 = itr2.next();
				if(val1.q + val2.q > maxmax)
				{
					maxmax = val1.q + val2.q;
				}
				payout1[i][j] = (double)(val1.q - val2.q)/2.0;
				payout2[i][j] = (double)(val2.q - val1.q)/2.0;
				j++;
			}
			i++;
			j = 0;
			itr2 = otherAgentQVal.iterator();
		}
		

		double minmax = BimatrixGeneralSumSolver.generalSumNash(payout1, payout2);
		
		return maxmax/2.0 + minmax;
	}
	
}
