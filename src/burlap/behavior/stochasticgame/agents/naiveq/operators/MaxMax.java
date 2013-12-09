package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQLOppAwareAgent;
import burlap.behavior.stochasticgame.agents.naiveq.SGQJAValue;
import burlap.oomdp.core.State;

public class MaxMax extends BackupOp {

	public double performOp(SGQLOppAwareAgent a1, SGQLOppAwareAgent a2, State s)
	{
		List<SGQJAValue> jaQVal1 = a1.getAllJAQsFor(s);
		List<SGQJAValue> jaQVal2 = a2.getAllJAQsFor(s);
		
		double maxVal = Double.NEGATIVE_INFINITY;
		
		//Collections.sort(jaQVal1);
		//Collections.sort(jaQVal2);
		/**
		System.out.println("VALS1");
		for(int i = 0; i < jaQVal1.size(); i++)
		{
			System.out.println(i + ": " + jaQVal1.get(i).ja);
		}
		System.out.println("VALS2");
		for(int i = 0; i < jaQVal2.size(); i++)
		{
			System.out.println(i + ": " + jaQVal2.get(i).ja);
		}*/
		
		//System.exit(-1);
		
		//Iterator<SGQJAValue> itr1 = jaQVal1.iterator();
		//Iterator<SGQJAValue> itr2 = jaQVal2.iterator();
		
		for(int i = 0; i < jaQVal1.size(); i++)
		{
			SGQJAValue val1 = jaQVal1.get(i);
			SGQJAValue val2 = jaQVal2.get(i);
			if(!val1.ja.equals(val2.ja))
				System.out.println("BADBADBAD");
			
			
			//System.out.println(val1.ja + "   " + val2.ja);

			if(val1.q + val2.q > maxVal)
			{
				maxVal = val1.q + val2.q;
			}
		}
		//return the agent's own payoff
		//System.out.println(a1.getAgentName() + ": " + maxVal/2.0);
		return maxVal/2.0;
	}
	
}
