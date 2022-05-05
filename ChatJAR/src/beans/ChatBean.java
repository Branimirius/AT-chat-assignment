package beans;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import messagemanager.AgentMessage;
import messagemanager.MessageManagerBean;
import messagemanager.MessageType;

@Stateless
@Path("/chat")
@LocalBean
public class ChatBean {
	
	@EJB private MessageManagerBean msm;

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String Test() {
		return "Ok";
	}
	
	@POST
	@Path("/login/{sender}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void logIn(User user, @PathParam("sender") String sender){
		AgentMessage message = new AgentMessage(MessageType.LOG_IN, sender);
		message.addAttribute("username", user.getUsername());
		message.addAttribute("password", user.getPassword());
		msm.post(message);
	}
	
	@POST
	@Path("/register/{sender}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void register(User user, @PathParam("sender") String sender){
		AgentMessage message = new AgentMessage(MessageType.REGISTER, sender);
		message.addAttribute("username", user.getUsername());
		message.addAttribute("password", user.getPassword());
		msm.post(message);
	}
	
}
