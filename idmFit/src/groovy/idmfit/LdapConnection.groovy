package idmfit

import com.novell.ldapchai.ChaiEntry
import com.novell.ldapchai.ChaiFactory
import com.novell.ldapchai.ChaiUser
import com.novell.ldapchai.exception.ChaiUnavailableException
import com.novell.ldapchai.provider.ChaiConfiguration
import com.novell.ldapchai.provider.ChaiProvider
import com.novell.ldapchai.provider.ChaiProviderFactory
import com.novell.ldapchai.provider.ChaiSetting

import dbfit.util.PropertiesLoader

class LdapConnection {
	private String bindUsername // = "cn=admin,o=org";
	private String bindPassword// = "password";
	private String serverURL
	private ChaiProvider provider
	public static final Map<String,LdapConnection> connections = [:]
	public LdapConnection(String bindUsername, String bindPassword,
			String serverURL) {
		super();
		this.bindUsername = bindUsername;
		this.bindPassword = bindPassword;
		this.serverURL = serverURL;
		Properties props = new Properties()
		props.put("user", bindUsername);
		props.put("password", new PropertiesLoader().parseValue(bindPassword));

		provider = ChaiProviderFactory.createProvider(
				new ChaiConfiguration(
						serverURL, props.getProperty("user"),
						props.getProperty("password")
					).setSetting(ChaiSetting.PROMISCUOUS_SSL, "true")
				)
		println provider.toString()
	}
	public static Map getConnections() {
		return connections;
	}
	public static void setConnections(java.lang.Object connections) {
		LdapConnection.connections = connections;
	}
	public ChaiProvider getProvider() {
		return provider;
	}
	public static ChaiProvider getProvider(String id) {
		getConnections().getAt(id).getProvider()
	}

//	public static void addConnection(String id, LdapConnection connection) {
//		connections.put(id, connection);
//	}

	public static void addConnection(String id, String bindUsername,
			String bindPassword, String serverURL)
			throws ChaiUnavailableException {

		connections.put(id, new LdapConnection(bindUsername, bindPassword,
				serverURL));
	}
	
	def static getConnectionStates(){
		return connections.collect {
			[name:it.key, state: (it.value.getProvider().isConnected() ? "connected" : "not connected")]
		}
	}
	
	def static getConnectionState(String id){
		getConnections().getAt(id).getProvider().isConnected()
	}
	def static closeConnection(String name){
		getProvider(name).close()
	}
}
