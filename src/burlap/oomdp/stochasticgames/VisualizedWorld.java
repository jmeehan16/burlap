package burlap.oomdp.stochasticgames;

import java.util.Map;

import burlap.debugtools.DPrint;
import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.GridGameRevisited;
import burlap.oomdp.core.State;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.stochasticgames.explorers.SGVisualExplorer;
import burlap.oomdp.visualizer.Visualizer;
import burlap.behavior.stochasticgame.agents.naiveq.SGQLOppAwareAgent;
import burlap.behavior.stochasticgame.agents.naiveq.operators.CoCoQJA;
import burlap.behavior.stochasticgame.agents.naiveq.operators.MaxMax;

public class VisualizedWorld extends World {

	protected GridGameRevisited game;
	
	protected Visualizer						vis;
	protected SGVisualExplorer 					exp;
	
	
	
	
	public VisualizedWorld(SGDomain domain, JointActionModel jam,
			JointReward jr, TerminalFunction tf, SGStateGenerator sg) {
		super(domain, jam, jr, tf, sg);
		this.game = (GridGameRevisited)sg;
		this.vis = GGVisualizer.getVisualizer(game.getWidth(),game.getHeight());
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Overrided: Runs a single stage of this game.
	 */
	public void runStage(){
		if(tf.isTerminal(currentState)){
			return ; //cannot continue this game
		}
		Agent agent1 = agents.get(0);	
		
		JointAction ja = new JointAction(agents.size());
		State abstractedCurrent = abstractionForAgents.abstraction(currentState);
		
		if(agent1 instanceof SGQLOppAwareAgent && (
				((SGQLOppAwareAgent)agent1).getOperator() instanceof MaxMax) || 
				((SGQLOppAwareAgent)agent1).getOperator() instanceof CoCoQJA)
		{
			ja = ((SGQLOppAwareAgent)agent1).getJointAction(abstractedCurrent);
		}
		else {
		
		
			for(Agent a : agents){
				ja.addAction(a.getAction(abstractedCurrent));
			}
		}
		this.lastJointAction = ja;
		
		DPrint.cl(debugId, ja.toString());
		
		
		//now that we have the joint action, perform it
		State sp = worldModel.performJointAction(currentState, ja);
		State abstractedPrime = this.abstractionForAgents.abstraction(sp);
		Map<String, Double> jointReward = jointRewardModel.reward(currentState, ja, sp);
		
		DPrint.cl(debugId, jointReward.toString());
		
		//index reward
		for(String aname : jointReward.keySet()){
			double curCumR = agentCumulativeReward.get(aname);
			curCumR += jointReward.get(aname);
			agentCumulativeReward.put(aname, curCumR);
		}
		
		
		
		if(agent1 instanceof SGQLOppAwareAgent && (
				((SGQLOppAwareAgent)agent1).getOperator() instanceof MaxMax) || 
				((SGQLOppAwareAgent)agent1).getOperator() instanceof CoCoQJA)
		{
			for(Agent a : agents){
				((SGQLOppAwareAgent)a).observeOutcomeJAQ(abstractedCurrent, ja, jointReward, abstractedPrime, tf.isTerminal(sp));
			}
		}
		else {
			//tell all the agents about it
			for(Agent a : agents){
				a.observeOutcome(abstractedCurrent, ja, jointReward, abstractedPrime, tf.isTerminal(sp));
			}
		}
		
		//update the state
		currentState = sp;
		
	}

	
	
	/**
	 * Runs a game until a terminal state is hit.
	 */
	public void runAndVisualizeGame(){
		
		for(Agent a : agents){
			a.gameStarting();
		}
		
		
		//currentState = game.generateState(agents);
		currentState = initialStateGenerator.generateState(agents);
		
		
		int visWidth = 1200*game.getWidth()/(game.getWidth()+game.getHeight());
		int visHeight = 1200*game.getHeight()/(game.getWidth()+game.getHeight());
		exp = new SGVisualExplorer(domain, vis, currentState, worldModel,visWidth,visHeight);
		
		
		exp.initGUI();
		
		while(!tf.isTerminal(currentState)){

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.runStage();
			vis.updateState(currentState);
		}
		
		for(Agent a : agents){
			a.gameTerminated();
		}
		
		DPrint.cl(debugId, currentState.getCompleteStateDescription());
		
	}
	

}
