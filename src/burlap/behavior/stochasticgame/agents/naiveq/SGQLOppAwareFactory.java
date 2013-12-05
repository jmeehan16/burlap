package burlap.behavior.stochasticgame.agents.naiveq;

import burlap.behavior.statehashing.StateHashFactory;
import burlap.oomdp.auxiliary.StateAbstraction;
import burlap.oomdp.stochasticgames.Agent;
import burlap.oomdp.stochasticgames.SGDomain;

public class SGQLOppAwareFactory extends SGQFactory {

	public SGQLOppAwareFactory(SGDomain domain, double discount,
			double learningRate, double defaultQ, StateHashFactory stateHash) {
		super(domain, discount, learningRate, defaultQ, stateHash);
	}
	
	public SGQLOppAwareFactory(SGDomain domain, double discount,
			double learningRate, double defaultQ, StateHashFactory stateHash, StateAbstraction storedAbstraction) {
		super(domain, discount, learningRate, defaultQ, stateHash, storedAbstraction);
	}

	public Agent generateAgent() {
		SGQLOppAwareAgent agent = new SGQLOppAwareAgent(domain, discount, learningRate, defaultQ, stateHash);
		if(storedAbstraction != null){
			agent.setStoredMapAbstraction(storedAbstraction);
		}
		return agent;
	}
	
}
