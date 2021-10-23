package com.nidhi.cms.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class RSA_Encryption {
	public byte[] readFileBytes(String filename) throws IOException {
		File resource = new ClassPathResource(filename).getFile();
		return Files.readAllBytes(resource.toPath());
	}

	public PublicKey readPublicKey(String filename)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(publicSpec);
		
//		 byte [] derRsaPubBytes = readFileBytes(filename));
//	        ASN1Primitive asn1Prime = new ASN1InputStream(derRsaPubBytes).readObject();
//	        org.bouncycastle.asn1.pkcs.RSAPublicKey rsaPub = org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(asn1Prime);
//	        KeyFactory kf = KeyFactory.getInstance("RSA");
//	        return kf.generatePublic(new RSAPublicKeySpec(rsaPub.getModulus(), rsaPub.getPublicExponent()));
	}

	public PrivateKey readPrivateKey(String filename)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	public byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(plaintext);
	}

//	public byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
//	{
//	    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");   
//	    cipher.init(Cipher.DECRYPT_MODE, key);  
//	    return cipher.doFinal(ciphertext);
//	}

	public String encryptData(String data) {
		try {
			PublicKey publicKey = readPublicKey("publicKey.txt");
			byte[] message = data.getBytes("UTF8");
			String base64Encoder = Base64.getEncoder().encodeToString(encrypt(publicKey, message));
			System.out.println(base64Encoder);
			return base64Encoder;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}