package agents;

import java.io.Serializable;

import javax.ejb.Remote;

import messagemanager.ACLMessage;

@Remote
public interface Agent extends Serializable {
	
	String CHAT_MODULE = "chat-jar";
	String CHAT_EAR = "chat-ear";
	String CHAT_WAR = "chat-war";

	public void handleMessage(ACLMessage message);
	
	public void init(AID aid);
	
	public AID getAID();
}
