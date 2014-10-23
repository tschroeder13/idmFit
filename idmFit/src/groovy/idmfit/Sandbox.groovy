package idmfit
import com.novell.ldapchai.ChaiFactory;


import java.beans.WeakIdentityMap.Entry;

import com.novell.ldapchai.ChaiEntry;
import com.novell.ldapchai.ChaiFactory
import com.novell.ldapchai.provider.ChaiConfiguration
import com.novell.ldapchai.provider.ChaiProvider;
import com.novell.ldapchai.provider.ChaiProviderFactory
import com.novell.ldapchai.provider.ChaiSetting

class Sandbox {
	def String bauer
	def int birnen
	def boolean wurmstichig 

	def Sandbox(){
		println "hallo welt"
	}
	
	def fasseZusammen(){
		return "Der Bauer '${bauer}' hat ${birnen} Birnen die ${wurmstichig ? 'Ausschuss':'von erlesener Qualität'} sind."
	}
	def static main(args){
//		def s = new Sandbox()
//		s.setBauer("Meyer")
//		s.setBirnen(35)
//		s.setWurmstichig(true)
//		println s.fasseZusammen()
//		s.setBauer("Huber")
//		s.setBirnen(67)
//		s.setWurmstichig(false)
//		println s.fasseZusammen()
		
//		def conf = new ChaiConfiguration("ldaps://192.168.56.11:636","cn=admin,ou=sa,o=system","Master1!").setSetting(ChaiSetting.PROMISCUOUS_SSL, "true")
//		def provider = ChaiProviderFactory.createProvider(conf)
//		println "The Provider is ${provider.isConnected() ? 'connected' : 'not connected'} "
		
//		def sr = ["derDNdesObjects":["attribut1":"wert1", "attribut2":"wert2"]]
//		println sr.containsKey("derDNdesObjects")
		
		def conf = new ChaiConfiguration("ldaps://192.168.56.22:636","2k12\\Administrator","Master1!").setSetting(ChaiSetting.PROMISCUOUS_SSL, "true")
//				def conf = new ChaiConfiguration("ldaps://192.168.56.11:636","cn=admin,ou=sa,o=system","Master1!").setSetting(ChaiSetting.PROMISCUOUS_SSL, "true")
		def ChaiProvider provider = ChaiProviderFactory.createProvider(conf)
		def ChaiEntry entry = ChaiFactory.createChaiEntry("CN=jdoe,OU=MA,OU=VM,OU=V,OU=STW,DC=2k12,DC=tsc", provider)
		def result = entry.readIntAttribute("userAccountControl")
		switch (entry.getChaiProvider().getDirectoryVendor()){
			case ChaiProvider.DIRECTORY_VENDOR.MICROSOFT_ACTIVE_DIRECTORY :
				println "Hallo Microsoft AD"
//			default:
//				println "Hallo Welt"
		}
		
		println entry.getChaiProvider().getDirectoryVendor().equals(ChaiProvider.DIRECTORY_VENDOR.MICROSOFT_ACTIVE_DIRECTORY )
//		def result = 514
		println "uac = ${result}"
		println Integer.toBinaryString(result)
//		def dn = "cn=test,o=stw"
//		provider.createEntry(dn, "inetORgPerson", ['sn':'Test', 'givenName':'Tekla'])//
//		println ChaiFactory.createChaiUser(dn, provider).isValid()
//		provider.deleteEntry(dn)
//		println ChaiFactory.createChaiUser(dn, provider).isValid()
//		println ChaiUtility.determineDirectoryVendor(user)
//		InitialLdapContext ctx = provider.getConnectionObject()
//		ctx.rename("cn=test,o=stw","cn=test2,o=stw")
		
		
//		println 'sn=test'.matches(~/.*=.*/)
		
//		provider.close()
	}
}
