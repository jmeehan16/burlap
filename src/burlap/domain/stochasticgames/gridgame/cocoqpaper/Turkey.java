package burlap.domain.stochasticgames.gridgame.cocoqpaper;

import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.domain.stochasticgames.gridgame.GridGameStandardMechanics;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.JointActionModel;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.explorers.SGVisualExplorer;
import burlap.oomdp.visualizer.Visualizer;

public class Turkey extends GridGame {
	
	public static void main(String [] args){
		
		GridGame gg = new Turkey();
		
		int gameWidth = 3;
		int gameHeight = 4;
		int numGoals = 3;
		SGDomain d = (SGDomain)gg.generateDomain();
		
		State s = getCleanState(d, 2, numGoals, 3, 2, gameWidth, gameHeight);
		
		setAgent(s, 0, 2, 0, 1);
		setAgent(s, 1, 0, 0, 4);
		
		setGoal(s, 0, 2, 3, 2);
		setGoal(s, 1, 1, 2, 0); 
		setGoal(s, 2, 0, 3, 5); 
		
		setHorizontalWall(s, 1, 1, 0, 0, 1);
		setHorizontalWall(s, 2, 1, 3, 1, 1);
		
		
		//System.out.println(s.getCompleteStateDescription());
		
		
		JointActionModel jam = new GridGameStandardMechanics(d);
		
		Visualizer v = GGVisualizer.getVisualizer(gameWidth, gameHeight);
		int visWidth = 1200*gameWidth/(gameWidth+gameHeight);
		int visHeight = 1200*gameHeight/(gameWidth+gameHeight);
		SGVisualExplorer exp = new SGVisualExplorer(d, v, s, jam,visWidth,visHeight);
		
		exp.setJAC("c"); //press c to execute the constructed joint action
		
		exp.addKeyAction("w", CLASSAGENT+"0:"+ACTIONNORTH);
		exp.addKeyAction("s", CLASSAGENT+"0:"+ACTIONSOUTH);
		exp.addKeyAction("d", CLASSAGENT+"0:"+ACTIONEAST);
		exp.addKeyAction("a", CLASSAGENT+"0:"+ACTIONWEST);
		exp.addKeyAction("q", CLASSAGENT+"0:"+ACTIONNOOP);
		
		exp.addKeyAction("i", CLASSAGENT+"1:"+ACTIONNORTH);
		exp.addKeyAction("k", CLASSAGENT+"1:"+ACTIONSOUTH);
		exp.addKeyAction("l", CLASSAGENT+"1:"+ACTIONEAST);
		exp.addKeyAction("j", CLASSAGENT+"1:"+ACTIONWEST);
		exp.addKeyAction("u", CLASSAGENT+"1:"+ACTIONNOOP);
		
		exp.initGUI();
		

		
		
	}
}
