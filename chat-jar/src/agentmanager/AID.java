package agentmanager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import agentmanager.AID;
import agentmanager.AgentType;
import agentmanager.AgentCenter;
//import siebog.agentmanager.XjafAgent;
//import siebog.radigost.RadigostStub;
import util.JSON;
import util.NodeManager;

public final class AID implements Serializable {
	private static final long serialVersionUID = 1L;
	private AgentCenter agentCenter;
	private AgentType agentType;
	
	public AID() {
		
	}
	
	public AID(AgentCenter agentCenter, AgentType agentType) {
		this.agentCenter = agentCenter;
		this.agentType = agentType;
	}
	
	public AID(String json) {
		try {
			AID a = JSON.g.fromJson(json, AID.class);
			this.agentCenter = a.agentCenter;
			this.agentType = a.agentType;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public AgentCenter getAgentCenter() {
		return agentCenter;
	}
	public void setAgentCenter(AgentCenter agentCenter) {
		this.agentCenter = agentCenter;
	}
	public AgentType getAgentType() {
		return agentType;
	}
	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}
}
