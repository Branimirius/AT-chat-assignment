package agents;

import java.io.Serializable;

public class AgentType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String node;
	private String module;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNode(String host) {
		this.node = host;
	}
	public String getNode() {
		return node;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		AgentType type = (AgentType)obj;
		return type.module.equals(module) && type.name.equals(name) && type.node.equals(node);
	}
	
	public AgentType() { }
	
	public AgentType(String name, String module, String host) {
		super();
		this.name = name;
		this.module = module;
		this.node = host;
	}
	
	
}
