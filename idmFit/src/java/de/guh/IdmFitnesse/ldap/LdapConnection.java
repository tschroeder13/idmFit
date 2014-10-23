package de.guh.IdmFitnesse.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.novell.ldapchai.exception.ChaiUnavailableException;
import com.novell.ldapchai.provider.ChaiProvider;
import com.novell.ldapchai.provider.ChaiProviderFactory;

import de.guh.IdmFitnesse.ldap.LdapConnection.ConnectionStatus;

public class LdapConnection {
	private String bindUsername; // = "cn=admin,o=org";
	private String bindPassword;// = "password";
	private String serverURL;
	private ChaiProvider provider;
	private static Map<String, LdapConnection> connections = new HashMap<String, LdapConnection>();

	
	
	public static class ConnectionStatus {
		String name;
		String status;

		public ConnectionStatus(String name, String status) {
			super();
			this.name = name;
			this.status = status;
		}

		static List<ConnectionStatus> stati = new ArrayList<ConnectionStatus>();

		public static boolean add(ConnectionStatus arg0) {
			return stati.add(arg0);
		}

		public static boolean remove(Object arg0) {
			return stati.remove(arg0);
		}

		public static Object[] getStati() {
			return stati.toArray();
		}
	}

	public ChaiProvider getProvider() {
		return provider;
	}

	public static Map<String, LdapConnection> getConnections() {
		return connections;
	}

	public static void setConnections(Map<String, LdapConnection> connections) {
		LdapConnection.connections = connections;
	}

	public LdapConnection(String bindUsername, String bindPassword,
			String serverURL) throws ChaiUnavailableException {
		super();
		this.bindUsername = bindUsername;
		this.bindPassword = bindPassword;
		this.serverURL = serverURL;
		provider = ChaiProviderFactory.createProvider(serverURL, bindUsername,
				bindPassword);
	}

	public static void addConnection(String id, LdapConnection connection) {
		connections.put(id, connection);
	}

	public static void addConnection(String id, String bindUsername,
			String bindPassword, String serverURL)
			throws ChaiUnavailableException {
		LdapConnection ldapConnection = new LdapConnection(bindUsername, bindPassword, serverURL);
		connections.put(id, ldapConnection);
		
	}

}
