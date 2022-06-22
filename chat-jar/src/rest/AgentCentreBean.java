package rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agentmanager.AgentType;
import agentmanager.AID;
import agentmanager.AgentManagerRemote;
import messagemanager.MessageManagerRemote;
import rest.AgentCentre;

@Stateless
@LocalBean
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Remote(AgentCentre.class)
@Path("/managers")
public class AgentCentreBean implements AgentCentre{
	@EJB
	AgentManagerRemote agm;

	@EJB
	MessageManagerRemote msm;

	@Override
	public List<AgentType> getAvailableAgentClasses() {
		return agm.getAvailableAgentClasses();
	}

	@Override
	public List<AID> getRunningAgents() {
		return agm.getRunningAgentsAID();
	}

	@Override
	public AID startAgent(AgentType type, String name) {
		msm.post("AGENT_STARTED");
		return agm.startServerAgent(type, name);
	}
}
