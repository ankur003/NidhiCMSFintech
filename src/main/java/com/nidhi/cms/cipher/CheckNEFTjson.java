package com.nidhi.cms.cipher;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
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
import java.util.Map;

import javax.crypto.Cipher;

public class CheckNEFTjson {

	private static String jsonMain;

	private static String hex(String binStr) {
		String newStr = "";
		String hexStr = "0123456789ABCDEF";
		byte[] p = binStr.getBytes();
		for (int k = 0; k < p.length; k++) {
			int j = (p[k] >> 4) & 0xF;
			newStr = newStr + hexStr.charAt(j);
			j = p[k] & 0xF;
			newStr = newStr + hexStr.charAt(j) + " ";
		}
		return newStr;
	}

	public CheckNEFTjson() {
		//
	}

	public Map<Object, Object> checkNEFTTes(Map<Object, Object> map) {
		String bodyMessage = "{\"AGGRID\":\"CUST0355\", \"UTRNUMBER\":\"022694322561\",\"CORPID\":\"573759208\",\"USERID\":\"USER1\",\"URN\":\"SR188085192\"}";
		runMainMethod(bodyMessage, map);
		return map;
	}

	public static String runMainMethod(String message, Map<Object, Object> map) {

		map.put("plainMsg", message);
		byte[] messageBytes;
		byte[] tempPub = null;
		String sPub = null;
		byte[] ciphertextBytes = null;
		try {

			// The source of randomness
			SecureRandom secureRandom = new SecureRandom();

			// Obtain a RSA Cipher Object
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			String certFile = "/home/nidhicms/public_html/keys/publicKey.txt";

			InputStream inStream = new FileInputStream(certFile);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
			inStream.close();

			// Read the public key from certificate file
			RSAPublicKey pubkey = (RSAPublicKey) cert.getPublicKey();
			tempPub = pubkey.getEncoded();
			sPub = new String(tempPub);
			String tt = hex(sPub);
			map.put("Public_key_from_certificate_file", tt);
			map.put("Public_Key_Algorithm", cert.getPublicKey().getAlgorithm());

			String keyFile = "/home/nidhicms/public_html/keys/privateKey.txt";
			inStream = new FileInputStream(keyFile);
			byte[] encKey = new byte[inStream.available()];
			inStream.read(encKey);
			inStream.close();
			String pvtKey = new String(encKey);

			map.put("pvtKey", pvtKey);
			map.put("pvtKeyInfo", encKey.length + "bytes");
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pvtKey));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priv = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
			messageBytes = message.getBytes();

			// Initialize the cipher for encryption
			cipher.init(Cipher.ENCRYPT_MODE, pubkey, secureRandom);

			// Encrypt the message
			ciphertextBytes = cipher.doFinal(messageBytes);

			String responseRequest = sendThePostRequest(
					new String(org.bouncycastle.util.encoders.Base64.encode(ciphertextBytes)));
			byte[] cipherByte = org.bouncycastle.util.encoders.Base64.decode(responseRequest.getBytes("UTF-8"));
			cipher.init(Cipher.DECRYPT_MODE, priv, secureRandom);
			jsonMain = new String(cipher.doFinal(cipherByte));
			map.put("jsonMain", jsonMain);
			return jsonMain;

		} catch (Exception e) {
			e.printStackTrace();
			map.put("exception", e);
		}
		return jsonMain;

	}

	public static String sendThePostRequest(String json) throws Exception {
		String jsonResponse = "";
		URL url = new URL("https://apibankingone.icicibank.com/api/v1/CIBNEFTStatus");
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
			StringBuffer response = new StringBuffer();
			while ((inputLine = responseReader.readLine()) != null) {
				response.append(inputLine);
			}

			responseReader.close();
			jsonResponse = response.toString();

		} catch (Exception exception) {
		}

		return jsonResponse;
	}

	public static String sendThePostRequest(String encyptedJson, String url, String method) {
		try {
			URL apiUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod(method.toUpperCase());
			connection.setRequestProperty("apikey", "f59e8580b4a34dce87e89b121e242392");
			connection.setRequestProperty("Content-Type", "text/plain");
			connection.setDoOutput(true);

			DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
			requestWriter.writeBytes(encyptedJson);
			requestWriter.close();
			System.out.println(connection.getResponseCode() +"  -  " + connection.getResponseMessage());
			InputStream is = connection.getInputStream();
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = responseReader.readLine()) != null) {
				response.append(inputLine);
			}

			responseReader.close();
			return response.toString();

		} catch (Exception exception) {
			System.out.println(exception);
		}

		return null;
	}

	public static byte[] encryptJsonRequest(String jsonAsString) {
		String certFile = "/home/nidhicms/public_html/keys/publicKey.txt";
		// The source of randomness
		try (InputStream inStream = new FileInputStream(certFile)) {
			// Obtain a RSA Cipher Object
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);

			// Read the public key from certificate file
			RSAPublicKey pubkey = (RSAPublicKey) cert.getPublicKey();
			byte[] messageBytes = jsonAsString.getBytes();

			// Initialize the cipher for encryption
			SecureRandom secureRandom = new SecureRandom();
			cipher.init(Cipher.ENCRYPT_MODE, pubkey, secureRandom);

			// Encrypt the message
			return cipher.doFinal(messageBytes);

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	public static String deCryptResponse(String jsonResponse) {
		if (jsonResponse == null) {
			System.out.println("***************************************************jsonResponse is null************************************");
		}
		String keyFile = "/home/nidhicms/public_html/keys/privateKey.txt";
		try (InputStream inStream = new FileInputStream(keyFile);) {
			System.out.println("inStream ==> " +inStream);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			System.out.println("cipher ==> " +cipher);
			byte[] encKey = new byte[inStream.available()];
			inStream.read(encKey);
			String pvtKey = new String(encKey);
			System.out.println("pvtKey ==> " +pvtKey);
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pvtKey));
			System.out.println("privKeySpec ==> " +privKeySpec);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			System.out.println("keyFactory ==> " +keyFactory);
			PrivateKey priv = keyFactory.generatePrivate(privKeySpec);
			System.out.println("priv ==> " +priv);
			byte[] cipherByte = org.bouncycastle.util.encoders.Base64.decode(jsonResponse.getBytes("UTF-8"));
			System.out.println("cipherByte ==> " +cipherByte);
			SecureRandom secureRandom = new SecureRandom();
			System.out.println("secureRandom ==> " +secureRandom);
			cipher.init(Cipher.DECRYPT_MODE, priv, secureRandom);
			System.out.println("cipher ==> " +cipher);
			return new String(cipher.doFinal(cipherByte));
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}