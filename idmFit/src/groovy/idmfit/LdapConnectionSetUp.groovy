package idmfit

import com.novell.ldapchai.provider.ChaiProvider;

class LdapConnectionSetUp {

	private String connectionName
	private String bindUsername // = "cn=admin,o=org";
	private String bindPassword// = "password";
	private String serverUrl
	
	def connect(){
		if(bindUsername==null){
			return "Username is not specified"
		}
		if(bindPassword==null){
			return "Password is not specified"
		}
		if(serverUrl==null){
			return "Server URL is not specified"
		}else{
			LdapConnection.addConnection(connectionName, bindUsername, bindPassword, serverUrl)
		}
		return LdapConnection.getConnectionState(connectionName)
	}
	
	def public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
	
	def public void setBindUsername(String bindUsername) {
		this.bindUsername = bindUsername;
	}
	
	def public void setBindPassword(String bindPassword) {
		this.bindPassword = bindPassword;
	}
	
	def public void setServerURL(String serverUrl) {
		this.serverUrl = serverUrl;
	}
}
