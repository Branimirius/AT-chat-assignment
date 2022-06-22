package connectionmanager;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ObjectField implements Serializable{
	private String name;
	private Class<?> type;
	private Object value;
	
	public ObjectField() {
		super();
	}

	public ObjectField(String name, Class<?> type, Object value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
}