package agentmanager;

import java.util.Set;

import agents.AID;
import agents.Agent;
import agents.AgentType;

public interface AgentManager {

	public void startAgent(AgentType type, String name);
	
	public void stopAgent(AID aid);
	
	
	
	public Set<AID> getRunningAgents();
	
	
	
	
	public Set<AgentType> getAgentTypes();
	
	
	public void updateRunningAgents(Set<AID> agents, String nodeAlias);
	
	public void updateAgentTypes(Set<AgentType> types, String nodeAlias);
	
	
	
	public void deleteRunningAgents(String nodeAlias);
	
	public void deleteAgentTypes(String nodeAlias);
	
	public Agent getRunningAgentByAID(AID aid);
	
}
