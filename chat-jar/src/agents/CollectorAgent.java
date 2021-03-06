package agents;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import messagemanager.ACLMessage;
import messagemanager.MessageManager;

@Stateful
@Remote(Agent.class)
public class CollectorAgent extends BaseAgent {

	private static final long serialVersionUID = 1L;

	@EJB MessageManager msm;
	
	@Override
	public void handleMessage(ACLMessage message) {
		switch(message.getPerformative()) {
			case COLLECT: {
				
				break;
			}
			default: return;
		}
	}
	
	
}
