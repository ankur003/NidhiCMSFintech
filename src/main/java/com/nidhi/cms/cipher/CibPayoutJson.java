package com.nidhi.cms.cipher;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class CibPayoutJson {

	private String jsonMain;
	
	private static String hex(String binStr) {

		  String newStr = new String();
		  try {
		   String hexStr = "0123456789ABCDEF";
		   byte[] p = binStr.getBytes();
		   for (int k = 0; k < p.length; k++) {
		        int j = (p[k] >> 4) & 0xF;
		        newStr = newStr + hexStr.charAt(j);
		        j = p[k] & 0xF;
		        newStr = newStr + hexStr.charAt(j) + " ";
		   }
		  } catch (Exception e) {
		   System.out.println("Failed to convert into hex values: " + e);
		  }
		  return newStr;
		 }
	
	public String runMainMethod(String message) {

		System.out.println("sdfsssfsfs:"+message);
		  //String message = "";
		  byte[] messageBytes;
		  byte[] tempPub = null;
		  String sPub = null;
		  byte[] ciphertextBytes = null;

		 // byte[] textBytes = null;

		  try {

		   // The source of randomness
		   SecureRandom secureRandom = new SecureRandom();

		   // Obtain a RSA Cipher Object
		   Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		   // Loading certificate file  
		   //String certFile = "W:/keys/publicKey.txt";
		   String certFile = "/home/nidhicms/public_html/keys/publicKey.txt";
		   InputStream inStream = new FileInputStream(certFile);
		   CertificateFactory cf = CertificateFactory.getInstance("X.509");
		   X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
		   inStream.close();


		   // Read the public key from certificate file
		   RSAPublicKey pubkey = (RSAPublicKey) cert.getPublicKey();
		   tempPub = pubkey.getEncoded();
		   sPub = new String(tempPub);
		   System.out.println("Public key from certificate file:\n" + hex(sPub) + "\n");
		   System.out.println("Public Key Algorithm = " + cert.getPublicKey().getAlgorithm() + "\n");


		   //String keyFile = "W:/keys/privateKey.txt";
		   String keyFile = "/home/nidhicms/public_html/keys/privateKey.txt";
		   inStream = new FileInputStream(keyFile);
		   byte[] encKey = new byte[inStream.available()];
		   inStream.read(encKey);
		   inStream.close();
		   String pvtKey = new String(encKey);
		   
		   
		   System.out.println(pvtKey);
		   // Read the private key from file
		   System.out.println("RSA PrivateKeyInfo: " + encKey.length + " bytes\n");
		   PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pvtKey));
		   KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		   System.out.println("KeyFactory Object Info:");
		   System.out.println("Algorithm = " + keyFactory.getAlgorithm());
		   System.out.println("Provider = " + keyFactory.getProvider());
		   PrivateKey priv = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
		   System.out.println("Loaded " + priv.getAlgorithm() + " " + priv.getFormat() + " private key.");
		   
			   messageBytes = message.getBytes();
			   System.out.println("Plain message:\n" + message + "\n");

			   // Initialize the cipher for encryption
			   cipher.init(Cipher.ENCRYPT_MODE, pubkey, secureRandom);

			   // Encrypt the message
			   ciphertextBytes = cipher.doFinal(messageBytes);
			   String responseRequest =SendThePostRequest("https://apibankingone.icicibank.com/api/Corporate/CIB/v1/Transaction", new String(org.bouncycastle.util.encoders.Base64.encode(ciphertextBytes)));
			   //System.out.println("Response:  "+responseRequest);
			   byte[] cipherByte = org.bouncycastle.util.encoders.Base64.decode(responseRequest.getBytes("UTF-8"));
			   cipher.init(Cipher.DECRYPT_MODE, priv, secureRandom);
			   jsonMain= new String(cipher.doFinal(cipherByte));
			  // System.out.println("Message decrypted with file private key:\n");
			   //System.out.println(jsonMain);
			   return jsonMain;
		   
		  } catch (IOException e) {
			  e.printStackTrace();
		   System.out.println("IOException:" + e);
		  } catch (Exception e) {
			  e.printStackTrace();
		   System.out.println("Exception:" + e);
		  }
		return jsonMain;
		
	}
	
	
	public static String SendThePostRequest(String cc, String json ) throws Exception{
		 String jsonResponse="";
		 URL url = new URL("https://apibankingone.icicibank.com/api/Corporate/CIB/v1/Transaction");
		 try {
			    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			    connection.setRequestMethod("POST");	
			    connection.setRequestProperty("apikey", "f59e8580b4a34dce87e89b121e242392");
			    connection.setRequestProperty("Content-Type", "text/plain");
			    connection.setDoOutput(true); 

			    DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
			    requestWriter.writeBytes(json);
			    requestWriter.close();
			    String responseData = "";
			    InputStream is = connection.getInputStream();
			    BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
			    String inputLine;
			    StringBuffer response=new StringBuffer();
			    while ((inputLine = responseReader.readLine()) != null) {
			    	response.append(inputLine);
			    }
			    
			    responseReader.close();
			    jsonResponse=response.toString();
			    
			} catch (Exception exception) {
			    exception.printStackTrace();
			}
		 
		 
		 	return jsonResponse;
		}
	 
	
	
}