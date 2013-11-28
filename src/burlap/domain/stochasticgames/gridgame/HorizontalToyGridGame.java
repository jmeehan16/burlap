package burlap.domain.stochasticgames.gridgame;

import java.util.List;

import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.ObjectInstance;
import burlap.oomdp.core.PropositionalFunction;
import burlap.oomdp.core.State;
import burlap.oomdp.stochasticgames.JointActionModel;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.SingleAction;
import burlap.oomdp.stochasticgames.common.UniversalSingleAction;
import burlap.oomdp.stochasticgames.explorers.SGVisualExplorer;
import burlap.oomdp.visualizer.Visualizer;

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
public class HorizontalToyGridGame extends  GridGame {

	
	public static void main(String [] args){
		
		GridGame gg = new HorizontalToyGridGame();
		
		int gameWidth = 7;
		int gameHeight = 1;
		int numGoals = 2;
		SGDomain d = (SGDomain)gg.generateDomain();
		
		State s = getCleanState(d, 2, numGoals, 3, 2, gameWidth, gameHeight);
		
		setAgent(s, 0, 2, 0, 1);
		setAgent(s, 1, 5, 0, 4);
		
		setGoal(s, 0, 0, 0, 5);
		setGoal(s, 1, 3, 0, 0); // neutral
		
		int numOfHorizontalCells = 0; //cells
		setHorizontalWall(s, 2, 4, 1, numOfHorizontalCells, 1);
		
		
		//System.out.println(s.getCompleteStateDescription());
		
		
		JointActionModel jam = new GridGameStandardMechanics(d);
		
		Visualizer v = GGVisualizer.getVisualizer(gameWidth, gameHeight);
		SGVisualExplorer exp = new SGVisualExplorer(d, v, s, jam,gameHeight,gameWidth);
		
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

	@Override
	public Domain generateDomain() {
		
		SGDomain domain = new SGDomain();
		
		
		Attribute xatt = new Attribute(domain, ATTX, Attribute.AttributeType.DISC);
		xatt.setDiscValuesForRange(0, maxDim, 1);
		
		Attribute yatt = new Attribute(domain, ATTY, Attribute.AttributeType.DISC);
		yatt.setDiscValuesForRange(0, maxDim, 1);
		
		Attribute e1att = new Attribute(domain, ATTE1, Attribute.AttributeType.DISC);
		e1att.setDiscValuesForRange(0, maxDim, 1);
		
		Attribute e2att = new Attribute(domain, ATTE2, Attribute.AttributeType.DISC);
		e2att.setDiscValuesForRange(0, maxDim, 1);
		
		Attribute patt = new Attribute(domain, ATTP, Attribute.AttributeType.DISC);
		patt.setDiscValuesForRange(0, maxDim, 1);
		
		Attribute pnatt = new Attribute(domain, ATTPN, Attribute.AttributeType.DISC);
		pnatt.setDiscValuesForRange(0, maxPlyrs, 1);
		
		Attribute gtatt = new Attribute(domain, ATTGT, Attribute.AttributeType.DISC);
		gtatt.setDiscValuesForRange(0, maxGT, 1);
		
		Attribute wtatt = new Attribute(domain, ATTWT, Attribute.AttributeType.DISC);
		wtatt.setDiscValuesForRange(0, maxWT, 1);
		
		
		
		ObjectClass agentClass = new ObjectClass(domain, CLASSAGENT);
		agentClass.addAttribute(xatt);
		agentClass.addAttribute(yatt);
		agentClass.addAttribute(pnatt);
		
		ObjectClass goalClass = new ObjectClass(domain, CLASSGOAL);
		goalClass.addAttribute(xatt);
		goalClass.addAttribute(yatt);
		goalClass.addAttribute(gtatt);
		
		ObjectClass horWall = new ObjectClass(domain, CLASSDIMHWALL);
		horWall.addAttribute(e1att);
		horWall.addAttribute(e2att);
		horWall.addAttribute(patt);
		horWall.addAttribute(wtatt);
		
		ObjectClass vertWall = new ObjectClass(domain, CLASSDIMVWALL);
		vertWall.addAttribute(e1att);
		vertWall.addAttribute(e2att);
		vertWall.addAttribute(patt);
		vertWall.addAttribute(wtatt);
		
		
		SingleAction actnorth = new UniversalSingleAction(domain, ACTIONNORTH);
		SingleAction actsouth = new UniversalSingleAction(domain, ACTIONSOUTH);
		SingleAction acteast = new UniversalSingleAction(domain, ACTIONEAST);
		SingleAction actwest = new UniversalSingleAction(domain, ACTIONWEST);
		SingleAction actnoop = new UniversalSingleAction(domain, ACTIONNOOP);
		
		
		PropositionalFunction aug = new AgentInUGoal(PFINUGOAL, domain);
		PropositionalFunction apg = new AgentInPGoal(PFINPGOAL, domain);
		
		
		return domain;
	}

	
	
	/**
	 * AddsN objects of a specific object class to a state object
	 * @param d the domain of the object classes
	 * @param s the state to which the objects of the specified class should be added
	 * @param className the name of the object class for which to create object instances
	 * @param n the number of object instances to create
	 */
	protected static void addNObjects(Domain d, State s, String className, int n){
		for(int i = 0; i < n; i++){
			ObjectInstance o = new ObjectInstance(d.getObjectClass(className), className+i);
			s.addObject(o);
		}
	}
	
	

	
	
	
	
	/**
	 * Defines a propositional function that evaluates to true when a given agent is in any universal goal
	 * @author James MacGlashan
	 *
	 */
	static class AgentInUGoal extends PropositionalFunction{

		
		/**
		 * Initializes with the given name and domain and is set to evaluate on agent objects
		 * @param name the name of the propositional function
		 * @param domain the domain for this propositional function
		 */
		public AgentInUGoal(String name, Domain domain) {
			super(name, domain, new String[]{CLASSAGENT});
		}

		@Override
		public boolean isTrue(State s, String[] params) {
			
			ObjectInstance agent = s.getObject(params[0]);
			int ax = agent.getDiscValForAttribute(ATTX);
			int ay = agent.getDiscValForAttribute(ATTY);
			
			
			//find all universal goals
			List <ObjectInstance> goals = s.getObjectsOfTrueClass(CLASSGOAL);
			for(ObjectInstance goal : goals){
				
				int gt = goal.getDiscValForAttribute(ATTGT);
				if(gt == 0){
				
					int gx = goal.getDiscValForAttribute(ATTX);
					int gy = goal.getDiscValForAttribute(ATTY);
					if(gx == ax && gy == ay){
						return true;
					}
					
				}
				
				
			}
			
			return false;
		}
		
		
	}
	
	
	/**
	 * Defines a propositional function that evaluates to true when a given agent is in any of its personal goals
	 * @author James MacGlashan
	 *
	 */
	static class AgentInPGoal extends PropositionalFunction{

		
		/**
		 * Initializes with the given name and domain and is set to evaluate on agent objects
		 * @param name the name of the propositional function
		 * @param domain the domain for this propositional function
		 */
		public AgentInPGoal(String name, Domain domain) {
			super(name, domain, new String[]{CLASSAGENT});
		}

		@Override
		public boolean isTrue(State s, String[] params) {
			
			ObjectInstance agent = s.getObject(params[0]);
			int ax = agent.getDiscValForAttribute(ATTX);
			int ay = agent.getDiscValForAttribute(ATTY);
			int apn = agent.getDiscValForAttribute(ATTPN);
			
			//find all universal goals
			List <ObjectInstance> goals = s.getObjectsOfTrueClass(CLASSGOAL);
			for(ObjectInstance goal : goals){
				
				int gt = goal.getDiscValForAttribute(ATTGT);
				if(gt == apn+1){
				
					int gx = goal.getDiscValForAttribute(ATTX);
					int gy = goal.getDiscValForAttribute(ATTY);
					if(gx == ax && gy == ay){
						return true;
					}
					
				}
				
				
			}
			
			return false;
		}

		
		
	}
	
	
	
}
