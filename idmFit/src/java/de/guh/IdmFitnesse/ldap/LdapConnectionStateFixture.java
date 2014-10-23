package de.guh.IdmFitnesse.ldap;

import java.util.HashMap;

import de.guh.IdmFitnesse.ldap.LdapConnection.ConnectionStatus;
import fit.RowFixture;

public class LdapConnectionStateFixture extends RowFixture {

	@Override
	public Class<?> getTargetClass() {
		return LdapConnection.ConnectionStatus.class;
	}

	@Override
	public Object[] query() throws Exception {
		return LdapConnection.ConnectionStatus.getStati();
		
	}

}
