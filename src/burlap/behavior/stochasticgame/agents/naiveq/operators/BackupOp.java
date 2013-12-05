package burlap.behavior.stochasticgame.agents.naiveq.operators;

import java.util.List;
import java.util.Map;

import burlap.behavior.stochasticgame.agents.naiveq.SGQValue;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.Agent;
import burlap.oomdp.stochasticgames.GroundedSingleAction;
import burlap.oomdp.stochasticgames.JointAction;
import burlap.behavior.stochasticgame.agents.naiveq.SGQLAgent;

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
	
	public abstract double performOp(SGQLAgent a1, SGQLAgent a2, State s);

}


