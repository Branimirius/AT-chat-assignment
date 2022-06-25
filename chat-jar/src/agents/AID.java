package agents;

import java.io.Serializable;

import connectionmanager.AgentCenter;

public class AID implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private AgentType type;
	private AgentCenter node;
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public AgentCenter getNode() {
		return node;
	}
	public void setNode(AgentCenter host) {
		this.node = host;
	}
	
	public AgentType getType() {
		return type;
	}
	public void setType(AgentType type) {
		this.type = type;
	}

	
	public AID() { }
	
	public AID(String name, AgentCenter node, AgentType type) {
		super();
		this.name = name;
		this.node = node;
		this.type = type;
	}
	@Override
	public boolean equals(Object obj) {
		AID aid = (AID)obj;
		return aid.name.equals(name) && aid.node.equals(node) && aid.type.equals(type);
	}
	
	
}
