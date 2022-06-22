package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agentmanager.AgentType;
import agentmanager.AID;

public interface AgentCentre {
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgentType> getAvailableAgentClasses();
	
	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AID> getRunningAgents();
	
	@PUT
	@Path("/agents/running/{type}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AID startAgent(AgentType type, @PathParam ("name") String name);

}
