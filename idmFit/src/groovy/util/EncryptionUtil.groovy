package util

import java.awt.Toolkit

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection

import groovy.swing.SwingBuilder

import javax.swing.JComponent
import javax.swing.JOptionPane
import javax.swing.event.AncestorEvent
import javax.swing.event.AncestorListener

import dbfit.util.crypto.CryptoFactories
import dbfit.util.crypto.CryptoKeyStoreFactory
import dbfit.util.crypto.CryptoServiceFactory

class EncryptionUtil {
	public static final int EXIT_SUCCESS = 0;
	public static final int EXIT_INVALID_COMMAND = 1;
	public static final int EXIT_COMMAND_FAILED = 2;
	
	private def SwingBuilder swing
	private def CryptoKeyStoreFactory ksf
	private def CryptoServiceFactory csf

	public EncryptionUtil() {
		super();
		this.swing = new SwingBuilder();
		this.csf = CryptoFactories.getCryptoServiceFactory()
		this.ksf = CryptoFactories.getCryptoKeyStoreFactory()
	}
	
	private int encryptPassword(final String password, final String path)
													throws Exception {
		def encPwd = csf.getCryptoService(ksf.newInstance()).encrypt(password);
	}

	private int encryptPassword(final String password) throws Exception {
		return encryptPassword(password, ksf.newInstance());
	}
	
	def execute(){
		def cred = showInputDialog("Create with desfault keystore's cipher")
		
		def pw = cred.password
		def encPw = "ENC(${csf.getCryptoService(ksf.newInstance()).encrypt(pw)})"
		def msg = """!define ${cred.userKey} {${cred.username}}
!define ${cred.pwKey} {${encPw}}
"""
		def res = showAnswerDialog(msg)
	}
	
	
	def showAnswerDialog(String msg){
		def res
		swing.edt {
			res = optionPane().showConfirmDialog(null, msg, "IdmFitNesse credentials", JOptionPane.YES_NO_OPTION )
		}
		if (res==0){
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(msg), null)
		}
		return res
	}
	
	def showInputDialog(String dialogTitle){
		def pw
		def pwKey
		def user
		def userKey
		def i = 0
		def left = ''
		swing.edt{
			def userNameField = textField()
			def userKeyField = textField()
			def pwKeyField = textField()
			def pwField = passwordField()
			JComponent[] components = [
				label("User variable name"),
				userKeyField,
                label("Username:"),
				userNameField,
				label("Password variable Name"),
				pwKeyField,
				label("Password:"),
				pwField,
			]
			
			userKeyField.addAncestorListener( new AncestorListener(){
				def removeListener = true
				@Override
				public void ancestorAdded(AncestorEvent e)
				{
					JComponent component = e.getComponent();
					component.requestFocusInWindow();
			
//					if (removeListener)
//					component.removeAncestorListener( this );
				}
			
				@Override
				public void ancestorMoved(AncestorEvent e) {}
			
				@Override
				public void ancestorRemoved(AncestorEvent e) {}
			} )
			
			def result = optionPane().showConfirmDialog(null, components, dialogTitle, 2, 3)
			pwKey = pwKeyField.getText()
			pw = pwField.getPassword().toString()
			userKey = userKeyField.getText()
			user = userNameField.getText()
		}
		return ['userKey':userKey, 'username':user, 'pwKey':pwKey ,'password':pw]
	}
	
	
	
	static main(args) {
		new EncryptionUtil().execute()
//		new EncryptionUtil().updateStatus("Hallo Welt!", 0)
//		println new EncryptionUtil().showAnswerDialog("test")
	}

}
