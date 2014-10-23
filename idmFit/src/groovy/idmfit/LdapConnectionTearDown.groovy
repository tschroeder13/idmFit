package idmfit

class LdapConnectionTearDown {

	def LdapConnectionTearDown(String name){
		LdapConnection.closeConnection(name)
	}

	def LdapConnectionTearDown(List names){
		names.each {name->
			LdapConnection.closeConnection(name)
		}
	}

	def LdapConnectionTearDown(){
		LdapConnection.getConnections().each {name,con->
			LdapConnection.closeConnection(name)
		}
	}
}
