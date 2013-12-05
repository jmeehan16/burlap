package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.Iterator;
import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQLAgent;
import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;
import burlap.oomdp.core.State;

public class MaxMax extends BackupOp {

	public double performOp(SGQLAgent a1, SGQLAgent a2, State s)
	{
		List<SGQValue> thisAgentQVal = a1.getAllQsFor(s);
		List<SGQValue> otherAgentQVal = a2.getAllQsFor(s);
		
		
		double maxVal = Double.NEGATIVE_INFINITY;
		double thisQ = Double.NEGATIVE_INFINITY;
		assert(thisAgentQVal.size() == otherAgentQVal.size());
		
		Iterator<SGQValue> itr1 = thisAgentQVal.iterator();
		Iterator<SGQValue> itr2 = otherAgentQVal.iterator();
		
		while(itr1.hasNext())
		{
			SGQValue val1 = itr1.next();
			while(itr2.hasNext())
			{
				SGQValue val2 = itr2.next();
				
				if(val1.q + val2.q > maxVal)
				{
					maxVal = val1.q + val2.q;
					thisQ = val1.q;
				}
			}
			itr2 = otherAgentQVal.iterator();
		}
		
		return maxVal/2;
	}
	
}
