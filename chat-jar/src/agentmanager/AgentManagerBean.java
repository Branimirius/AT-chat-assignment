package agentmanager;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.NamingException;

import org.infinispan.Cache;

import agents.Agent;
import appCache.GlobalCache;
import rest.Node;
import jndi.JndiTreeParser;
import jndi.ObjectFactory;
import agentmanager.AgentCenter;
import agentmanager.AgentType;
import agentmanager.AID;
import util.JNDILookup;

@Singleton
@Remote(AgentManagerRemote.class)
@LocalBean
public class AgentManagerBean implements AgentManagerRemote {

	private static final long serialVersionUID = 1L;
	
	private Cache<AID, Agent> agents;
	
	@EJB
	private JndiTreeParser jndiTreeParser;
	
	Map<String, Agent> runningAgents = new HashMap<String, Agent>();
	
	@Override
	public void startAgent(String agentId, String name) {
		Agent agent = (Agent) JNDILookup.lookUp(name, Agent.class);
		agent.init(agentId);
		runningAgents.put(agentId, agent);
	}

	@Override
	public void stopAgent(String agentId) {
		runningAgents.remove(agentId);
	}

	@Override
	public List<Agent> getRunningAgents() {
		return (List<Agent>) runningAgents.values();
	}

	@Override
	public Agent getRunningAgentByid(String agentId) {
		return runningAgents.get(agentId);
	}

	@Override
	public List<AgentType> getAvailableAgentClasses() {
		try {
			return jndiTreeParser.parse();
		} catch (NamingException ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public List<AID> getRunningAgentsAID() {
		Set<AID> set = getCache().keySet();
		
		if (set.size() > 0) {
			try {
				AID aid = set.iterator().next();
				try {
					ObjectFactory.lookup(getAgentLookup(aid.getAgentType(), true), Agent.class, Node.LOCAL);
				} catch (Exception ex) {
					ObjectFactory.lookup(getAgentLookup(aid.getAgentType(), false), Agent.class, Node.LOCAL);
				}
			} catch (Exception e) {
				getCache().clear();
				return new ArrayList<AID>();
			}
		}
		return new ArrayList<AID>(set);
	}
	
	private Cache<AID, Agent> getCache() {
		if (agents == null) {
			agents = GlobalCache.get().getRunningAgents();
		}
		
		return agents;
	}
	private String getAgentLookup(AgentType agentType, boolean stateful) {
		if (inEar(agentType)) {
			if (stateful) {
				return String.format("ejb:%s//%s!%s?stateful", agentType.getModule(), agentType.getName(), Agent.class.getName());
			} else {
				return String.format("ejb:%s//%s!%s", agentType.getModule(), agentType.getName(), Agent.class.getName());
			}
		} else {
			if (stateful) {
				return String.format("ejb:%s//%s!%s?stateful", agentType.getModule(), agentType.getName(), Agent.class.getName());
			} else {
				return String.format("ejb:%s//%s!%s", agentType.getModule(), agentType.getName(), Agent.class.getName());
			}
		}
	}
	
	private boolean inEar(AgentType agentType) {
		if (agentType.getModule().contains("/")) {
			return true;
		}
		
		return false;
	}
	
	public Agent getAgentReference(AID aid) {
		Cache<AID, Agent> cache = getCache();
		Set<AID> aids = cache.keySet();
		Agent agent = null;
		for (AID a : aids) {
			if (aid.getAgentCenter().getAlias().equals(a.getAgentCenter().getAlias()) && 
					aid.getAgentCenter().getAddress().equals(a.getAgentCenter().getAddress()) &&
					aid.getAgentType().getName().equals(a.getAgentType().getName()) /*&&
					aid.getAgentType().getModule().equals(a.getAgentType().getModule())*/) {
				agent = cache.get(a);
				break;
			}
		}
//		return getCache().get(aid);
		return agent;
	}

	@Override
	public AID startServerAgent(AgentType type, String runtimeName) {
//		String host = System.getProperty("jboss.node.name");
		String nodeAddr = "";
		try {
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName http = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");
			nodeAddr = (String) mBeanServer.getAttribute(http, "boundAddress");
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		if (host == null) {
//			host = type.
//		}
		
		String host = nodeAddr + ":8080";
		AgentCenter agentCenter = new AgentCenter(runtimeName, host);
		AID aid = new AID(agentCenter, type);
		startServerAgent(aid, true);
		
		return aid;
	}
	
	public void startServerAgent(AID aid, boolean replace) {
		if (getCache().containsKey(aid)) {
			if(!replace) {
				throw new IllegalStateException("Agent already running: " + aid);
			}
			stopAgent(aid);
//			if (args == null || args.get("noUIUpdate", "").equals("")) {
//				LoggerUtil.logAgent(aid, SocketMessageType.REMOVE);
//			}
		}
		Agent agent = null;
		try {
			agent = ObjectFactory.lookup(getAgentLookup(aid.getAgentType(), true), Agent.class, Node.LOCAL);
		} catch (IllegalStateException ise) {
			agent = ObjectFactory.lookup(getAgentLookup(aid.getAgentType(), false), Agent.class, Node.LOCAL);
		}
		initAgent(agent, aid);
		System.out.println("Agent " + aid.getAgentCenter().getAlias() + " started.");
		
	}
	public void stopAgent(AID aid) {
//		Agent agent = getCache().get(aid);
		Cache<AID, Agent> cache = getCache();
		Set<AID> aids = cache.keySet();
		Agent agent = null;
		AID agentAid = null;
		for (AID a : aids) {
			if (aid.getAgentCenter().getAlias().equals(a.getAgentCenter().getAlias()) && 
					aid.getAgentCenter().getAddress().equals(a.getAgentCenter().getAddress()) &&
					aid.getAgentType().getName().equals(a.getAgentType().getName()) &&
					aid.getAgentType().getModule().equals(a.getAgentType().getModule())) {
				agent = cache.get(a);
				agentAid = a;
				break;
			}
		}
		
		if (agent != null) {
			agent.stop();
//			getCache().remove(aid);
			getCache().remove(agentAid);
			System.out.println("Stopped agent: " + aid);
			//informOtherNodesAgentStopped(agentAid);
//			LoggerUtil.log("Stopped agent: " + aid, true);
//			LoggerUtil.logAgent(aid, SocketMessageType.REMOVE);
		}
	}
	public void initAgent(Agent agent, AID aid) {
		getCache().put(aid, agent);
		agent.initAID(aid);
	}
}
