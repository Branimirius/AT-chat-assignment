package agentmanager;

import java.io.Serializable;

import agentmanager.Agent;
import agentmanager.AgentType;
import agentmanager.AgentInitArgs;
//import siebog.agentmanager.XjafAgent;
import util.JSON;

public class AgentType implements Serializable {
	
	private static final long serialVersionUID = 6355526464588438905L;
	private String name;
	private String module;
	
	public AgentType() {
		
	}
	
	public AgentType(String name, String module) {
		this.name = name;
		this.module = module;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
}