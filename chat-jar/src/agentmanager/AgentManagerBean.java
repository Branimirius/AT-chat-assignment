package agentmanager;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import agents.AID;
import agents.Agent;
import agents.AgentType;
import agents.CollectorAgent;
import agents.MasterAgent;
import agents.PredictorAgent;
import agents.UserAgent;
import agents.UserHelperAgent;
import rest.AgentEndpoint;
import util.AgentCenterRemote;
import util.JNDILookup;
import websocket.AgentSocket;
import websocket.AgentTypeSocket;

@Singleton
@Remote(AgentManager.class)
@LocalBean
public class AgentManagerBean implements AgentManager {
	
	Set<Agent> runningAgents = new HashSet<Agent>();
	Set<AID> otherNodeAgents = new HashSet<AID>();
	
	Set<AgentType> otherNodeTypes = new HashSet<AgentType>();
	
	@EJB AgentCenterRemote acm;
	@EJB AgentSocket agentSocket;
	@EJB AgentTypeSocket typeSocket;
	
	@Override
	public void stopAgent(AID aid) {
		boolean deleted = runningAgents.removeIf(a -> a.getAID().equals(aid));
		if(deleted) {
			updateWithSocket();
		}
		
	}

	@Override
	public void startAgent(AgentType type, String name) {
		if(getAvailableTypes().stream().anyMatch(t -> t.equals(type))) {
			Agent agent = (Agent) JNDILookup.lookUp(type.getModule() + type.getName() + "!"
									+ Agent.class.getName() + "?stateful", Agent.class);
			if(agent != null) {
				agent.init(new AID(name, acm.getHost(), type));
				if(runningAgents.stream().noneMatch(a -> a.getAID().equals(agent.getAID()))) {
					runningAgents.add(agent);
					updateWithSocket();
				}
			}
		}
		
	}
		
	@Override
	public Set<AID> getRunningAgents() {
		Set<AID> agents = runningAgents.stream().map(a -> a.getAID()).collect(Collectors.toSet());
		return agents;
	}
		
	@Override
	public Set<AgentType> getAgentTypes() {
		Set<AgentType> types = new HashSet<AgentType>(otherNodeTypes);
		types.addAll(getAvailableTypes());
		return types;
	}
		
	@Override
	public void updateAgentTypes(Set<AgentType> types, String nodeAlias) {
		otherNodeTypes.removeIf(t -> t.getNode().equals(nodeAlias));
		
		updateWithTypeSocket();
	}

	@Override
	public void updateRunningAgents(Set<AID> agents, String nodeAlias) {
		otherNodeAgents.removeIf(a -> a.getNode().getAlias().equals(nodeAlias));
		otherNodeAgents.addAll(agents);
		updateWithSocket();
	}
	
	@Override
	public void deleteRunningAgents(String nodeAlias) {
		otherNodeAgents.removeIf(a -> a.getNode().getAlias().equals(nodeAlias));
		updateWithSocket();
	}
	
	@Override
	public void deleteAgentTypes(String nodeAlias) {
		otherNodeTypes.removeIf(t -> t.getNode().equals(nodeAlias));
		updateWithTypeSocket();
	}
	
	@Override
	public Agent getRunningAgentByAID(AID aid) {
		return runningAgents.stream().filter(a -> a.getAID().equals(aid)).findFirst().orElse(null);
	}	
		
	private Set<AgentType> getAvailableTypes() {
		Set<AgentType> types = new HashSet<AgentType>();
		types.add(new AgentType(UserAgent.class.getSimpleName(), JNDILookup.JNDIPathChat, acm.getHost().getAlias()));
		types.add(new AgentType(UserHelperAgent.class.getSimpleName(), JNDILookup.JNDIPathChat, acm.getHost().getAlias()));
		types.add(new AgentType(MasterAgent.class.getSimpleName(), JNDILookup.JNDIPathChat, acm.getHost().getAlias()));
		types.add(new AgentType(CollectorAgent.class.getSimpleName(), JNDILookup.JNDIPathChat, acm.getHost().getAlias()));
		types.add(new AgentType(PredictorAgent.class.getSimpleName(), JNDILookup.JNDIPathChat, acm.getHost().getAlias()));
		return types;
	}
	private void updateWithTypeSocket() {
		try {
	    	Set<AgentType> types = getAgentTypes();
			ObjectMapper mapper = new ObjectMapper();
			String typesJSON = mapper.writeValueAsString(types);
			typeSocket.send(typesJSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	private void updateWithSocket() {
	    try {
	    	Set<AID> agents = getRunningAgents();
			ObjectMapper mapper = new ObjectMapper();
			String agentsJSON = mapper.writeValueAsString(agents);
			agentSocket.send(agentsJSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
