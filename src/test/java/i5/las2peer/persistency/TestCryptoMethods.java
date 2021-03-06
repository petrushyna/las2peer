package i5.las2peer.persistency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class TestCryptoMethods {

	@Test
	public void testSymmetric() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {

	    KeyGenerator kg = KeyGenerator.getInstance( "AES" ); 
	    kg.init( 256 );
	    SecretKey secKey  = kg.generateKey(); 
	    SecretKey secKey2  = kg.generateKey(); 

		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secKey);

	    String plainString = "eine Plaintext Test-Nachricht";	    
	    byte[] plain = plainString.getBytes("UTF-8");
	    byte[] crypted =  cipher.doFinal( plain );
	    	    
	    Cipher decipher = Cipher.getInstance("AES");
	    decipher.init( Cipher.DECRYPT_MODE,  secKey);
	    byte[] decrypted = decipher.doFinal ( crypted );
	    
	    String decString = new String ( decrypted, "UTF-8" );
	    
	    assertEquals ( plainString, decString);
	    
	    decipher.init( Cipher.DECRYPT_MODE,  secKey2);
	    try {
		    decrypted = decipher.doFinal ( crypted );
		    fail ( "wrong key should lead to a BadPaddingException" );
	    } catch ( BadPaddingException e ) {
	    	// that's correct!
	    }
	}
	
	@Test
	public void testAsymmetric () throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance( "RSA" );
		kpg.initialize( 2048 );
		KeyPair keyPair = kpg.genKeyPair();
		KeyPair keyPair2 = kpg.genKeyPair();
		
		Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic() );
	    
	    String plainString = "eine Plaintext Test-Nachricht";	    
	    byte[] plain = plainString.getBytes("UTF-8");
	    byte[] crypted =  cipher.doFinal( plain );
	    	    
	    Cipher decipher = Cipher.getInstance("RSA");
	    decipher.init( Cipher.DECRYPT_MODE,  keyPair.getPrivate() );
	    byte[] decrypted = decipher.doFinal ( crypted );
	    
	    String decString = new String ( decrypted, "UTF-8" );
	    
	    assertEquals ( plainString, decString);
	    
	    decipher.init( Cipher.DECRYPT_MODE,  keyPair2.getPrivate() );	    
	    try {
		    decrypted = decipher.doFinal ( crypted );		    	
		    fail ( "wrong key should lead to a BadPaddingException" );
	    } catch ( BadPaddingException e ) {
	    	// that's correct!
	    }
	}
	
	
	
	@Test
	public void testSignature () throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance( "RSA" );
		kpg.initialize( 2048 );
		KeyPair keyPair = kpg.genKeyPair();
		KeyPair keyPair2 = kpg.genKeyPair();
		
		Signature sig = Signature.getInstance("SHA1withRSA");
		sig.initSign(keyPair.getPrivate());
		
		String testMessage = "Dies ist der Plaintext einer Testnachricht";
		sig.update(testMessage.getBytes("UTF-8"));
		byte[] signature = sig.sign();
				
		
		sig.initVerify(keyPair.getPublic());
		
	    sig.update(testMessage.getBytes("UTF-8"));
	    assertTrue ( sig.verify(signature) );
	    
	    
		sig.initVerify(keyPair2.getPublic());
	    sig.update(testMessage.getBytes("UTF-8"));
	    
	    assertFalse ( sig.verify(signature) );   	    
	}
	
	
	
	@Test
	public void testBase64 () throws UnsupportedEncodingException {
		String testMessage = "Dies ist eine längere Nachricht, die ich gerne in Base64 encodieren möchte.";
		byte[] bytes = testMessage.getBytes( "UTF-8" );
		
		String encoded = Base64.encodeBase64String(bytes);
		
		byte[] decoded = Base64.decodeBase64(encoded);
		
		String decodedMessage = new String ( decoded, "UTF-8");
		
		assertEquals ( testMessage, decodedMessage );
	}
	
	
	
	
		
	
	
	
	
	

}
