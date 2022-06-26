package agents;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import chatmanager.ChatManager;
import messagemanager.ACLMessage;
import messagemanager.MessageManagerRemote;


@Stateful
@Remote(Agent.class)
public class UserHelperAgent extends BaseAgent {

	private static final long serialVersionUID = 1L;
	
	@EJB ChatManager chm;
	@EJB MessageManagerRemote msm;

	@Override
	public void handleMessage(ACLMessage message) {
		switch(message.getPerformative()) {
			case ADD_MESSAGE: {
				
			}
			case ADD_REGISTERED: {
				
			}
			case ADD_LOGGED_IN: {
				
			}
			case REMOVE_LOGGED_IN: {
				
			}
			default: return;
		}
	}
	
	
	
	
	

}
