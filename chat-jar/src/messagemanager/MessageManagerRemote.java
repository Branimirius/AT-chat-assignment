package messagemanager;

import java.util.List;

import javax.ejb.Remote;

import messagemanager.ACLMessage;

@Remote
public interface MessageManagerRemote {

	public void post(AgentMessage message);
	
	List<String> getPerformatives();
	
	void post(ACLMessage message);
	
	void post(String message);
}
