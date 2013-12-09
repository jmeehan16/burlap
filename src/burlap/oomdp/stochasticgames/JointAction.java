package burlap.oomdp.stochasticgames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import burlap.oomdp.core.State;


/**
 * This class specifies which action each agent took in a world. The class is backed by a Map from
 * agent names to the {@link GroundedSingleAction} taken by that respective agent. 
 * The {@link GroundedSingleAction} objects of this class can also
 * be iterated over.
 * @author James MacGlashan
 *
 */
public class JointAction implements Iterable<GroundedSingleAction> , Comparable<JointAction>{

	public Map <String, GroundedSingleAction>		actions;
	
	public JointAction(){
		actions = new HashMap<String, GroundedSingleAction>();
	}
	
	/**
	 * Initializes the internal data structure to keep a keep space for capacity number of actions.
	 * @param capacity
	 */
	public JointAction(int capacity){
		actions = new HashMap<String, GroundedSingleAction>(capacity);
	}
	
	
	/**
	 * Adds all {@link GroundedSingleAction} objects in a list to this joint action.
	 * @param actions the actions to add to this joint action.
	 */
	public JointAction(List <GroundedSingleAction> actions){
		for(GroundedSingleAction gsa : actions){
			this.addAction(gsa);
		}
	}
	
	/**
	 * Adds a single {@link GroundedSingleAction} object to this joint aciton
	 * @param action the action to add
	 */
	public void addAction(GroundedSingleAction action){
		actions.put(action.actingAgent, action);
	}
	
	/**
	 * Returns the number of actions in this joint action.
	 * @return the number of actions in this joint action.
	 */
	public int size(){
		return actions.size();
	}
	
	/**
	 * Returns a list of the actions in this joint action. Modifying the returned
	 * list does not modify the structure of this joint action.
	 * @return a list of the actions in this joint action
	 */
	public List <GroundedSingleAction> getActionList(){
		return new ArrayList<GroundedSingleAction>(actions.values());
	}
	
	
	/**
	 * Returns the action taken by the agent with the given name
	 * @param agentName the name of the agent whose taken action is to be returned.
	 * @return the action taken by the agent with the given name
	 */
	public GroundedSingleAction action(String agentName){
		return actions.get(agentName);
	}
	
	
	/**
	 * Returns a list of the names of all agents who are represented in this joint action.
	 * @return a list of the names of all agents who are represented in this joint action.
	 */
	public List <String> getAgentNames(){
		List <String> anames = new ArrayList<String>(actions.size());
		
		List <GroundedSingleAction> gsas = this.getActionList();
		for(GroundedSingleAction gsa : gsas){
			anames.add(gsa.actingAgent);
		}
		
		return anames;
		
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer(100);
		List <GroundedSingleAction> gsas = this.getActionList();
		for(int i = 0; i < gsas.size(); i++){
			if(i > 0){
				buf.append(';');
			}
			buf.append(gsas.get(i).toString());
		}
		
		return buf.toString();
	}
	

	@Override
	public Iterator<GroundedSingleAction> iterator() {
		return new Iterator<GroundedSingleAction>() {
			
			List <GroundedSingleAction> gsas = getActionList();
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < gsas.size();
			}

			@Override
			public GroundedSingleAction next() {
				return gsas.get(i++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	
	
	/**
	 * Returns all possible joint actions given two agents, agent1 and agent2
	 * @param s the state in which the agent would execute this action
	 * @param agent1
	 * @param agent2
	 * @return all possible joint actions given two agents.
	 */
	public static List<JointAction> getAllPossibleJointActions(State s, Agent agent1, Agent agent2){
		
		List <JointAction> pos = new ArrayList<JointAction>();
		
		List <GroundedSingleAction> actions1 = SingleAction.getAllPossibleGroundedSingleActions(s, agent1.getAgentName(), (agent1.getAgentType()).actions);
		List <GroundedSingleAction> actions2 = SingleAction.getAllPossibleGroundedSingleActions(s, agent2.getAgentName(), (agent2.getAgentType()).actions);
		JointAction ja;
		
		for(GroundedSingleAction a1: actions1) {
			for(GroundedSingleAction a2: actions2) {
				ja = new JointAction(2);
				ja.addAction(a1);
				ja.addAction(a2);
				pos.add(ja);
			}
		}
		Collections.sort(pos);
		return pos;
		
	}
	
	public boolean equals(Object o)
	{
		JointAction jao = (JointAction)o;
		return (jao.toString().equals(this.toString()));
	}

	@Override
	public int compareTo(JointAction o) {
		ArrayList<String> s = new ArrayList<String>(this.actions.keySet());
		Collections.sort(s);
		
		Iterator<String> iter = s.iterator();
		String key = "";
		
		while(iter.hasNext())
		{
			key = iter.next();
			if(o.actions.get(key).compareTo(this.actions.get(key)) < 0)
			{
				//System.out.println(o.actions.get(key).toString() + " < " + this.actions.get(key).toString() + " (-1)");
				return -1;
			}
			else if(o.actions.get(key).compareTo(this.actions.get(key)) > 0)
			{
				//System.out.println(o.actions.get(key).toString() + " > " + this.actions.get(key).toString() + " (1)");
				return 1;
			}
		}
		System.out.println(o.actions.get(key).toString() + "==" + this.actions.get(key).toString() + " (0)");
		return 0;
	}
	
	
}

