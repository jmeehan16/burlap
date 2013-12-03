package burlap.domain.stochasticgames.gridgame.cocoqpaper;

import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.domain.stochasticgames.gridgame.GridGameStandardMechanics;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.JointActionModel;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.explorers.SGVisualExplorer;
import burlap.oomdp.visualizer.Visualizer;
import burlap.behavior.statehashing.DiscreteStateHashFactory;
import burlap.behavior.stochasticgame.agents.naiveq.*;
import burlap.behavior.stochasticgame.agents.naiveq.coopq.*;

/**
 * The GridGame domain is much like the GridWorld domain, except for arbitrarily many agents in
 * a stochastic game. Each agent in the world has an OO-MDO object instance of OO-MDP class "agent"
 * which is defined by an x position, a y position, and a player number. Agents can either move north, south, east,
 * west, or do nothing. There is also an OO-MDP object class for 1-dimensional walls (both for horizontal
 * walls or vertical walls). Each wall can take on a different type; a solid wall that can never be passed,
 * and a semi-wall, can be passed with some stochastic probability. Finally, there is also an OO-MDP
 * class for goal locations, which also have different types. There is a type that can be indicated
 * as a universal goal/reward location for all agents, and type that is only useful to each individual
 * agent.
 * @author James MacGlashan
 *
 */
public class PrisonerGridGame extends  GridGame {

	
	public static void main(String [] args){
		
		GridGame gg = new PrisonerGridGame();
		DiscreteStateHashFactory hashingFactory = new DiscreteStateHashFactory();
		
		int gameWidth = 9;
		int gameHeight = 1;
		int numGoals = 3;
		SGDomain d = (SGDomain)gg.generateDomain();
		
		State s = getCleanState(d, 2, numGoals, 3, 2, gameWidth, gameHeight);
		
		setAgent(s, 0, 5, 0, 1);
		setAgent(s, 1, 3, 0, 4);
		
		setGoal(s, 0, 0, 0, 5);
		setGoal(s, 1, 4, 0, 0); // neutral
		setGoal(s, 2, 8, 0, 2);
		
		int numOfHorizontalCells = 0; //cells
		setHorizontalWall(s, 2, 4, 1, numOfHorizontalCells, 1);
		
		
		SGQLAgent p1 = new SGQLAgent(d, 1, 0.2, hashingFactory);
		SGQLAgent p2 = new SGQLAgent(d, 1, 0.2, hashingFactory);
		
		p1.setInternalRewardFunction(new CoopQJointReward(d));
		p2.setInternalRewardFunction(new CoopQJointReward(d));
		
		
		//System.out.println(s.getCompleteStateDescription());
		
		
		JointActionModel jam = new GridGameStandardMechanics(d);
		
		Visualizer v = GGVisualizer.getVisualizer(gameWidth, gameHeight);
		int visWidth = 800*gameWidth/(gameWidth+gameHeight);
		int visHeight = 800*gameHeight/(gameWidth+gameHeight);
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
