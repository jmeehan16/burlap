package burlap.behavior.stochasticgame.agents.naiveq;

import burlap.oomdp.stochasticgames.JointAction;


/**
 * A Q-value wrapper for stochastic games
 * @author James MacGlashan
 *
 */
public class SGQJAValue implements Comparable<SGQJAValue> {

	/**
	 * The action this Q-value is for
	 */
	public JointAction					ja;
	
	/**
	 * The numeric Q-value
	 */
	public double						q;
	
	
	/**
	 * Initializes as a copy and an existing Q-value
	 * @param qv the Q-value object to copy
	 */
	public SGQJAValue(SGQJAValue qv) {
		this.ja = qv.ja;
		this.q = qv.q;
	}
	
	
	/**
	 * Initializes with a given action an Q-value
	 * @param gsa the action this Q-value is for.
	 * @param q the numeric Q-value.
	 */
	public SGQJAValue(JointAction ja, double q){
		this.ja = ja;
		this.q = q;
	}


	@Override
	public int compareTo(SGQJAValue o) {
		return this.ja.compareTo(o.ja);
	}

}
