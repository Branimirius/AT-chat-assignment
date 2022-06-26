package agents;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import messagemanager.ACLMessage;

@Stateful
@Remote(Agent.class)
public class MasterAgent extends BaseAgent {

	private static final long serialVersionUID = 1L;

	
	
	@Override
	public void handleMessage(ACLMessage message) {
		switch(message.getPerformative()) {
			case DISPLAY: {
				
				break;
			}
			default: return;
		}
	}

}
