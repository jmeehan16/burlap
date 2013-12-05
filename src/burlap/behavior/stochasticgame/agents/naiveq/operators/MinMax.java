package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.Iterator;
import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;
import burlap.nash.TwoPersonZeroSumGameNash;


public class MinMax extends BackupOp {
	
	public Tuple<Double> performOp(List <SGQValue> thisAgentQVal, List <SGQValue> otherAgentQVal)
	{
		Tuple<Double> bestVals = null;
		assert(thisAgentQVal.size() == otherAgentQVal.size());
		double[][] payout = new double[thisAgentQVal.size()][otherAgentQVal.size()];
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
				payout[i][j] = (double)(val1.q - val2.q)/2.0;
				j++;
			}
			i++;
			j = 0;
			itr2 = otherAgentQVal.iterator();
		}
		
		double[] optVals;
		try{
			 optVals = TwoPersonZeroSumGameNash.getNash(payout);
			 bestVals = new Tuple<Double>(optVals[0], optVals[1]);
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		
		return bestVals;
	}
	
}
