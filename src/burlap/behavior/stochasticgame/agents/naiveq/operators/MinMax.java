package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.Iterator;
import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;
import burlap.behavior.stochasticgame.agents.naiveq.operators.BackupOp.Tuple;


public class MinMax extends BackupOp {
	
	public Tuple<Double> performOp(List <SGQValue> thisAgentQVal, List <SGQValue> otherAgentQVal)
	{
		Tuple<Double> bestVals = null;
		assert(thisAgentQVal.size() == otherAgentQVal.size());
		//double[][] 
		
		
		Iterator<SGQValue> itr1 = thisAgentQVal.iterator();
		Iterator<SGQValue> itr2 = thisAgentQVal.iterator();
		
		while(itr1.hasNext())
		{
			SGQValue val1 = itr1.next();
			SGQValue val2 = itr2.next();
			
			
		}
		
		return bestVals;
	}
	
}
