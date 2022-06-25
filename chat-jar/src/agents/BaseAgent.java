package agents;

public abstract class BaseAgent implements Agent {

	private static final long serialVersionUID = 1L;

	protected AID aid;
	
	
	
	@Override
	public AID getAID() {
		return aid;
	}
	
	@Override
	public void init(AID aid) {
		this.aid = aid;
	}
	
	
}
