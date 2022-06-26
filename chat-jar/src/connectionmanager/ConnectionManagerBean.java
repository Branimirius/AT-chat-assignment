package connectionmanager;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.Path;



import agentmanager.AgentManager;
import util.AgentCenterRemote;

@Singleton
@Startup
@Remote(ConnectionManager.class)
@Path("/connection")
public class ConnectionManagerBean implements ConnectionManager {
	
	@EJB AgentCenterRemote acm;
	@EJB AgentManager agm;
	
	private String hostAlias;
	
	@PostConstruct
	private void init() {
		setLocalNodeInfo();
		hostAlias = acm.getHost().getAlias();
		//if(!isMaster())
			//handshake();
	}
	
	private void setLocalNodeInfo() {
		acm.setLocalNodeInfo();
		acm.setMasterAlias();
	}

	

	
	
	
	
	
	

	
}
