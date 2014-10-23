package ldapFitNesse

import dbfit.util.PropertiesLoader
import dbfit.util.crypto.CryptoFactories
import dbfit.util.crypto.CryptoKeyStoreFactory
import dbfit.util.crypto.CryptoServiceFactory

class EncryptionUtil {

	static main(args) {
//		CryptoApp.main "Password"
		def pw ="Master1!"
		def CryptoServiceFactory csf = CryptoFactories.getCryptoServiceFactory()
		def CryptoKeyStoreFactory ksf = CryptoFactories.getCryptoKeyStoreFactory()
		def encPw = "ENC(${csf.getCryptoService().encrypt(pw)})"
		
		def decPw = csf.getCryptoService().decrypt(csf.getCryptoService().encrypt(pw))
		
		println "The PW: $pw is encrypted to $encPw and decrypted to $decPw"
		
		Properties props = new Properties();
		props.put("user", "Chef");
//		props.put("password", new PropertiesLoader().parseValue("1234qwer"));
		props.put("password", new PropertiesLoader().parseValue(encPw));
		println props.getProperty("password")
	}

}
