package messagemanager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import messagemanager.ACLMessage;
import agentmanager.AID;
import messagemanager.Performative;

@Stateless
@LocalBean
public class MessageManagerBean implements MessageManagerRemote {

	@EJB private JMSFactory factory;
	private Session session;
	private MessageProducer defaultProducer;
	private MessageProducer wsProducer;

	@PostConstruct
	public void postConstruct() {
		session = factory.getSession();
		defaultProducer = factory.getProducer(session);
	}

	@PreDestroy
	public void preDestroy() {
		try {
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void post(AgentMessage message) {
		try {
			defaultProducer.send(createJMSMessage(message));
			System.out.println("Entered message manager");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private Message createJMSMessage(AgentMessage message) {
		ObjectMessage jmsMessage = null;
		try {
			jmsMessage = session.createObjectMessage(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return jmsMessage;
	}

	@Override
	public List<String> getPerformatives() {
		final Performative[] arr = Performative.values();
		List<String> list = new ArrayList<>(arr.length);
		for (Performative p : arr) {
			list.add(p.toString());
		}
		
		return list;
	}

	@Override
	public void post(ACLMessage message) {
		for (int i = 0; i < message.getRecievers().size(); i++) {
			if (message.getRecievers().get(i) == null) {
				throw new IllegalArgumentException("AID cannot be null.");
			}
			postToReciever(message, i);
		}
		
	}
	
	@Override
	public void post(String message) {
		try {
			ObjectMessage jmsMsg = session.createObjectMessage(message);
			getWsProducer().send(jmsMsg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void postToReciever(ACLMessage msg, int index) {
		AID aid = msg.getRecievers().get(index);
		try {
			ObjectMessage jmsMsg = session.createObjectMessage(msg);
			setupJmsMsg(jmsMsg, aid, index);
			getProducer(msg).send(jmsMsg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void setupJmsMsg(ObjectMessage jmsMsg, AID aid, int index) throws JMSException {
//		jmsMsg.setStringProperty("JMSXGroupID", aid.getStr());
		jmsMsg.setIntProperty("AIDIndex", index);
		jmsMsg.setStringProperty("_HQ_DUPL_ID", UUID.randomUUID().toString());
	}
	
	private MessageProducer getProducer(ACLMessage msg) {
//		if (MessageManager.REPLY_WITH_TEST.equals(msg.inReplyTo)) {
//			return getTestProducer();
//		}
		
		return defaultProducer;
	}
	
	
	private MessageProducer getWsProducer() {
//		if (wsProducer == null) {
//			wsProducer = factory.getTestProducer(session);
//		}
		return wsProducer;
	}

}
