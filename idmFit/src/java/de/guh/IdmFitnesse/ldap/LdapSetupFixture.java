package de.guh.IdmFitnesse.ldap;

import java.util.Iterator;
import java.util.Map.Entry;

import com.novell.ldapchai.exception.ChaiUnavailableException;

import fitlibrary.SetUpFixture;

public class LdapSetupFixture extends SetUpFixture {

	public LdapSetupFixture() {
	}

	public void setupLdapConnections(String id, String bindDN, String bindPW,
			String ldapUrl) throws ChaiUnavailableException {
		// LdapConnection.addConnection(id, bindDN,bindPW,ldapUrl);
		LdapConnection connection = new LdapConnection(bindDN, bindPW, ldapUrl);
		LdapConnection.addConnection(id, connection);
		LdapConnection.ConnectionStatus
				.add(new LdapConnection.ConnectionStatus(id, connection
						.getProvider().isConnected() ? "connected" : "not connected"));
	}

	public void nameBindDNBindPWLdapURL(String id, String bindDN,
			String bindPW, String ldapUrl) throws ChaiUnavailableException {
		setupLdapConnections(id, bindDN, bindPW, ldapUrl);

	}

	
}
