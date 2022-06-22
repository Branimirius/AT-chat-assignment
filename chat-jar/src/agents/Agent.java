package agents;

import java.io.Serializable;

import javax.ejb.Remote;

import agentmanager.AID;
import messagemanager.ACLMessage;
import messagemanager.AgentMessage;

@Remote
public interface Agent extends Serializable {
	
	String CHAT_MODULE = "chat-jar";
	String CHAT_EAR = "chat-ear";
	String CHAT_WAR = "chat-war";
	
	public void init(String agentId);
	
	void initAID(AID aid);
	
	public void handleMessage(AgentMessage message);
	
	public void handleACLMessage(ACLMessage message);
	
	public String getAgentId();
	
	AID getAid();
	
	void stop();
}
