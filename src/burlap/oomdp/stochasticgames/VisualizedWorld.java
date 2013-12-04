package burlap.oomdp.stochasticgames;

import burlap.debugtools.DPrint;
import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.domain.stochasticgames.gridgame.cocoqpaper.GridGameRevisited;
import burlap.oomdp.core.State;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.stochasticgames.explorers.SGVisualExplorer;
import burlap.oomdp.visualizer.Visualizer;

public class VisualizedWorld extends World {

	protected GridGameRevisited game;
	
	protected Visualizer						vis;
	protected SGVisualExplorer 					exp;
	
	
	
	
	public VisualizedWorld(SGDomain domain, JointActionModel jam,
			JointReward jr, TerminalFunction tf, SGStateGenerator sg,GridGameRevisited game) {
		super(domain, jam, jr, tf, sg);
		this.game = game;
		this.vis = GGVisualizer.getVisualizer(game.getWidth(),game.getHeight());
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * Runs a game until a terminal state is hit.
	 */
	public void runAndVisualizeGame(){
		
		for(Agent a : agents){
			a.gameStarting();
		}
		
		
		currentState = game.generateState(agents, domain);
		
		
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
