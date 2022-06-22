package jndi;

import javax.naming.NamingException;

import agentmanager.AgentManagerRemote;
import agentmanager.AgentManagerBean;
import rest.AgentCentreBean;
import rest.AgentCentre;
import rest.Node;
import messagemanager.JMSFactory;
import messagemanager.MessageManagerRemote;
import messagemanager.MessageManagerBean;
import agents.Agent;

public abstract class ObjectFactory {
	public static final String AgentManagerLookup = "ejb:" + Agent.CHAT_EAR + "/" + Agent.CHAT_MODULE + "//" + AgentManagerBean.class.getSimpleName() + "!" + AgentManagerRemote.class.getName();
	public static final String MessageManagerLookup = "ejb:" + Agent.CHAT_EAR + "/" + Agent.CHAT_MODULE + "//" + MessageManagerBean.class.getSimpleName() + "!" + MessageManagerRemote.class.getName();
	public static final String WebClientManagerLookup = "ejb:" + Agent.CHAT_EAR + "/" + Agent.CHAT_MODULE + "//" + AgentCentreBean.class.getSimpleName() + "!" + AgentCentre.class.getName() + "?stateful";
	public static final String JMSFactoryLookup = "java:app/" + Agent.CHAT_MODULE + "/" + JMSFactory.class.getSimpleName();
	
	public static AgentManagerRemote getAgentManager(Node remote) {
		return lookup(AgentManagerLookup, AgentManagerRemote.class, remote);
	}
	
	public static MessageManagerRemote getMessageManager(Node remote) {
		return lookup(MessageManagerLookup, MessageManagerRemote.class, remote);
	}
	
	public static AgentCentre getWebClientManager() {
		return lookup(WebClientManagerLookup, AgentCentre.class, Node.LOCAL);
	}
	
	public static JMSFactory getJMSFactory() {
		return lookup(JMSFactoryLookup, JMSFactory.class, Node.LOCAL);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T lookup(String name, Class<T> c, Node remote) {
		try {
			return (T) ContextFactory.get(remote).lookup(name);
		} catch (NamingException ne) {
			throw new IllegalStateException("Failed to lookup " + name, ne);
		}
	}
}
