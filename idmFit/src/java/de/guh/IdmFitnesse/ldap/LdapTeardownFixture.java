package de.guh.IdmFitnesse.ldap;

import java.util.Iterator;
import java.util.Map.Entry;

import fitlibrary.SetUpFixture;

public class LdapTeardownFixture extends SetUpFixture {

	
	
	public LdapTeardownFixture() {
		super();
	}

	public void teardown(boolean doTeardown) {
		Iterator<Entry<String, LdapConnection>> it = LdapConnection
				.getConnections().entrySet().iterator();
		while (it.hasNext()) {
			String id = it.next().getKey(); 
			LdapConnection lc = it.next().getValue();
			LdapConnection.ConnectionStatus.remove(new LdapConnection.ConnectionStatus(id, "connected"));
			lc.getProvider().close();
			LdapConnection.ConnectionStatus.add(new LdapConnection.ConnectionStatus(id, "not connected"));
		}
	}
}
