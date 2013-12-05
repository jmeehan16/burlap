package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.Iterator;
import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;

public class MaxMax extends BackupOp {

	public Tuple<Double> performOp(List <SGQValue> thisAgentQVal, List <SGQValue> otherAgentQVal)
	{
		Tuple<Double> bestVals = null;
		double maxVal = Double.NEGATIVE_INFINITY;
		assert(thisAgentQVal.size() == otherAgentQVal.size());
		
		Iterator<SGQValue> itr1 = thisAgentQVal.iterator();
		Iterator<SGQValue> itr2 = thisAgentQVal.iterator();
		
		while(itr1.hasNext())
		{
			SGQValue val1 = itr1.next();
			SGQValue val2 = itr2.next();
			
			if(val1.q + val2.q > maxVal)
			{
				maxVal = val1.q + val2.q;
				bestVals = new Tuple<Double>(val1.q, val2.q);
			}
		}
		
		return bestVals;
	}
	
}
