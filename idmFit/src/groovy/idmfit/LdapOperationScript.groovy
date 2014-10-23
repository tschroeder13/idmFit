package idmfit

import javax.naming.NamingException
import javax.naming.ldap.InitialLdapContext

import org.apache.commons.lang3.NotImplementedException

import com.novell.ldapchai.ChaiEntry
import com.novell.ldapchai.ChaiFactory
import com.novell.ldapchai.exception.ChaiOperationException
import com.novell.ldapchai.exception.ChaiUnavailableException
import com.novell.ldapchai.provider.ChaiConfiguration
import com.novell.ldapchai.provider.ChaiProvider
import com.novell.ldapchai.provider.ChaiProviderFactory
import com.novell.ldapchai.provider.ChaiSetting
import com.novell.ldapchai.util.SearchHelper

/**
 * @author tschroet
 *
 */
class LdapOperationScript {
	
//	public JNDIProviderImpl con
	public ChaiProvider con
	public String id	
	
	LdapOperationScript(String connection){
		this.id = connection
		con = LdapConnection.getProvider(connection)
	}
	
	def createObjectWithClassAndAttributes(String fqdn, String objectClass, Map attributes){
		con.createEntry(fqdn, objectClass, attributes)
		return ChaiFactory.createChaiEntry(fqdn, con).isValid()
	}

	def deleteObject(String fqdn){
		con.deleteEntry(fqdn)
		return !ChaiFactory.createChaiEntry(fqdn, con).isValid()
	}

	def getEntryDnForRdnInSearchBase(String rdn, String searchbase){
		if(!rdn.matches(/.*=.*/)){
			throw new NamingException("${rdn} is not a valid relative distinguished name")
		}
		return getObjectDN(rdn, searchbase).empty ? null : getObjectDN(rdn, searchbase)
	}
	
	private Map getObjectDN(String searchbase, String filter){
		def Map res = con.search(searchbase, new SearchHelper(filter))()
//		if(res.size() != 1) throw new Exception("The result isn't unique")
//		return res.siz()==1 ? res.keySet().asList()[0] : false
		return res
	}
	
	def objectExists(String fqdn){
		def rdn = fqdn.takeWhile {it!=','}
		def parent = fqdn.substring(rdn.length()+1)
		def sr
		try {
			sr = con.search(parent, new SearchHelper("(${rdn})"))
		} catch (ChaiOperationException e) {
			return false
		}
		return sr.containsKey(fqdn)
	}

	def addAttributeValueMapToObject(Map attributes,String fqdn){
		def ChaiEntry entry = ChaiFactory.createChaiEntry(fqdn, con)
		attributes.each {k,v->
			entry.addAttribute(k, v)
		}
	}
	def addAttributeWithValueToObject(String attrName, String value, String fqdn){
		def user = ChaiFactory.createChaiUser(fqdn, con)
		user.addAttribute(attrName, value)
	}

	def replaceAttributesOldValueWithNewValueOnObject(String attrName, String oldValue, String newValue, String fqdn){
		def entry = ChaiFactory.createChaiEntry(fqdn, con)
		try {
			entry.deleteAttribute(attrName, oldValue)
		} catch (ChaiOperationException e) {
			
		}
		entry.addAttribute(attrName, newValue)
		return true
	}
	
	public deleteAttributeValueMapFromObject(Map<String,String> attr, String fqdn) {
		attr.each {k,v->
			ChaiFactory.createChaiEntry(fqdn, con).deleteAttribute(k, v)
		}
	}

	
	public Object deleteAttributeOnObject(String attrName, String fqdn) {
		ChaiFactory.createChaiEntry(fqdn, con).deleteAttribute(attrName)
	}

	
	public Object replaceAttributeValue(String fqdn,
			String attrName, String oldValue, String newValue) {
		ChaiFactory.createChaiEntry(fqdn, con).replaceAttribute(attrName, oldValue, newValue)
	}

	
	public Object getAttributeForObject( String attrName,String fqdn) {
		return ChaiFactory.createChaiEntry(fqdn, con).readStringAttribute(attrName)
	}

	
	public Object attributeExistsForObject( String attrName, String fqdn) {
		return !ChaiFactory.createChaiEntry(fqdn, con).readStringAttribute(attrName).empty
	}

	
	public Object attributeContainsValueForObject(String attrName, String containingValue, String fqdn) {
		return ChaiFactory.createChaiUser(fqdn, con).readStringAttribute(attrName).equals(containingValue)
	}

	public Object valueOfAttributeIsEqualForObject(String equalValue, String attrName, String fqdn) {
		ChaiFactory.createChaiEntry(fqdn, con).readStringAttribute(attrName).equals(equalValue)
	}

	
	def addUserToGroup(String user_fqdn, String group_fqdn){
		ChaiFactory.createChaiUser(user_fqdn, con).addGroupMembership(ChaiFactory.createChaiGroup(group_fqdn, con))
		return true
				ChaiFactory.createChaiUser(user_fqdn, con)addGroupMembership(ChaiFactory.createChaiGroup(group_fqdn, con))
				return true
	}
	
	def removeUserFromGroup(String user_fqdn, String group_fqdn){
		ChaiFactory.createChaiUser(user_fqdn, con).removeGroupMembership(ChaiFactory.createChaiGroup(group_fqdn, con))
		return true
	}		
	
	public boolean setPasswordFor(String pwd, String fqdn){
		return setPassword(fqdn, pwd)
	}
	public boolean setPassword(String fqdn, String pwd) {
		def user = ChaiFactory.createChaiUser(fqdn, con)
		user.setPassword(pwd)
		return user.testPassword(pwd)
	}
	
	public boolean passwordIsCorrectFor(String pwd, String fqdn){
		return ChaiFactory.createChaiUser(fqdn, con).testPassword(pwd)
	}
	
	
	public Object userCanConnectToServerWithPassword(String fqdn, String server, String pwd) {
		def provider
		try {
			
			provider = ChaiProviderFactory.createProvider(new ChaiConfiguration(server, fqdn, pwd).setSetting(ChaiSetting.PROMISCUOUS_SSL, "true"))
		} catch (ChaiUnavailableException e) {
			throw e	
			return false
		}
		def result = provider.isConnected()
		provider.close()
		return result
	}
	
	def userAccountIsEnabled(String fqdn){
		def user = ChaiFactory.createChaiUser(fqdn, con)
		switch (user.getParentEntry().getChaiProvider().getDirectoryVendor()){
			case ChaiProvider.DIRECTORY_VENDOR.MICROSOFT_ACTIVE_DIRECTORY :
				return user.readIntAttribute("userAccountControl").toBigInteger().getLowestSetBit()!=1
			case ChaiProvider.DIRECTORY_VENDOR.NOVELL_EDIRECTORY :
				return user.isAccountEnabled()
			case ChaiProvider.DIRECTORY_VENDOR.OPEN_LDAP :
				return user.isAccountEnabled()
			case ChaiProvider.DIRECTORY_VENDOR.DIRECTORY_SERVER_389 :
				return user.isAccountEnabled()
			case ChaiProvider.DIRECTORY_VENDOR.GENERIC :
				return user.isAccountEnabled()
		}		
		throw new NotImplementedException("The Directory Vendor is unkown, so we don't know how to get appropriate Attribute value.")
	}
	
	def renameEntryTo(String old_fqdn, String new_fqdn){
		renameEntry(old_fqdn, new_fqdn)
	}
	def moveEntryTo(String old_fqdn, String new_fqdn){
		renameEntry(old_fqdn, new_fqdn)
	}
	def renameUserTo(String old_fqdn, String new_fqdn){
		renameEntry(old_fqdn, new_fqdn)
	}
	def moveUserTo(String old_fqdn, String new_fqdn){
		renameEntry(old_fqdn, new_fqdn)
	}
	private renameEntry(String oldname, String newname){
		InitialLdapContext ctx = con.getConnectionObject()
		ctx.rename(oldname, newname)
		return true
	}
	
	def evaluate(String command){
		Eval.me(command)
	}
}
