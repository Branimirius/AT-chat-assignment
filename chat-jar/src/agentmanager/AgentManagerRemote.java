package agentmanager;

import java.io.Serializable;
import java.util.List;

import agents.Agent;
import agentmanager.AID;
import agentmanager.AgentType;

public interface AgentManagerRemote extends Serializable {

	public void startAgent(String agentId, String name);
	
	public void stopAgent(String agentId);
	
	public List<Agent> getRunningAgents();
	
	public List<AgentType> getAvailableAgentClasses();
	
	public Agent getRunningAgentByid(String agentId);
	
	public List<AID> getRunningAgentsAID(); 
	
	public AID startServerAgent(AgentType type, String runtimeName);
}
