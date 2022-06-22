package util;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import util.ContextFactory;

public abstract class ContextFactory {
	private static final Logger logger = Logger.getLogger(ContextFactory.class.getName());
	private static Context context;
	private static Context remoteContext;

	/*
	 * java.naming.factory.url.pkgs=org.jboss.ejb.client.naming
	 * java.naming.factory.initial=org.jboss.naming.remote.client.
	 * InitialContextFactory java.naming.provider.url=http-remoting://maja:8080
	 */

	static {
		try {
			Hashtable<String, Object> jndiProps = new Hashtable<>();
			jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			context = new InitialContext(jndiProps);
		} catch (NamingException ex) {
			logger.log(Level.SEVERE, "Context initialization error.", ex);
		}
	}
/*
	public static Context get(SiebogNode remote) {
		if (remote != SiebogNode.LOCAL) {
			try {
				if (remoteContext == null || !remoteContext.getEnvironment().get(Context.PROVIDER_URL).toString()
						.equals("http-remoting://" + remote.host + ":" + remote.port)) {
					Hashtable<String, Object> jndiProps = new Hashtable<>();
					jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
					jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,
							"org.jboss.naming.remote.client.InitialContextFactory");
					jndiProps.put(Context.PROVIDER_URL, "http-remoting://" + remote.host + ":" + remote.port);
					remoteContext = new InitialContext(jndiProps);
				}
			} catch (NamingException e) {
				e.printStackTrace();
				remoteContext = null;
			}
			return remoteContext;
		}
		return context;
	}
	*/
}

