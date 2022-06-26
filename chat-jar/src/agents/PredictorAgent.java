package agents;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import messagemanager.ACLMessage;
import messagemanager.MessageManager;
import util.AgentCenterRemote;

@Stateful
@Remote(Agent.class)
public class PredictorAgent extends BaseAgent {

	private static final long serialVersionUID = 1L;
	
	
	@EJB AgentCenterRemote acm;
	@EJB MessageManager msm;

	@Override
	public void handleMessage(ACLMessage message) {
		switch(message.getPerformative()) {
			case PREDICT: {
				
				break;
			}
			default: return;
		}
	}
	
	
}
