package messagemanager;

import java.util.Set;

import javax.ejb.Remote;

@Remote
public interface MessageManager {

	public void post(ACLMessage message);
	
	public Set<String> getPerformatives();
}
