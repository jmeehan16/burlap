package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.List;

import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;

public abstract class BackupOp {
	
	public static class Tuple<T> {
		public T a;
		public T b;
		
		public Tuple(T a, T b)
		{
			this.a = a;
			this.b = b;
		}
	}
	
	public abstract Tuple<Double> performOp(List <SGQValue> thisAgentQVal, List <SGQValue> otherAgentQVal);

}
