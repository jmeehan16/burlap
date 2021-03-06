package burlap.behavior.stochasticgame.agents.naiveq.operators;



import java.util.Iterator;
import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQJAValue;
import burlap.behavior.stochasticgame.agents.naiveq.SGQLAgent;
import burlap.behavior.stochasticgame.agents.naiveq.SGQLOppAwareAgent;
import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;
import burlap.oomdp.core.State;
import burlap.solvers.BimatrixGeneralSumSolver;
import burlap.solvers.CorrelatedQ;
import burlap.nash.TwoPersonZeroSumGameNash;

public class Correlated extends BackupOp{
	public double performOp(SGQLOppAwareAgent a1, SGQLOppAwareAgent a2, State s)
	{
		List<SGQJAValue> jaQVal1 = a1.getAllJAQsFor(s);
		List<SGQJAValue> jaQVal2 = a2.getAllJAQsFor(s);
		
		assert(jaQVal1.size() == jaQVal2.size());
		double[][] payout1 = new double[jaQVal1.size()][jaQVal2.size()];
		double[][] payout2 = new double[jaQVal1.size()][jaQVal2.size()];

		//double maxmax = Double.NEGATIVE_INFINITY;
		//double minmaxM = Double.NEGATIVE_INFINITY;
		
		
		
		
		for(int i = 0; i < jaQVal1.size(); i++)
		{
			double val1 = jaQVal1.get(i).q;
			double val2 = jaQVal2.get(i).q;
			
			payout1[i%5][i-(i%5)] = val1 ;
			payout2[i%5][i-(i%5)] = val2 ;
			
			
		}
		//double minmax = TwoPersonZeroSumGameNash.getNash(payout1);
		
		double corq = CorrelatedQ.CorrelatedQ(payout1,payout2);
		
		
		return corq;
			
	}
	

}
