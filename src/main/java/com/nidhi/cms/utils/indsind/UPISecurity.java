package com.nidhi.cms.utils.indsind;

import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class UPISecurity {
	private SecretKeySpec skeySpec;
	private Cipher cipher;

	/**
	 * Constructor
	 */
	public UPISecurity() {
		skeySpec = null;
		cipher = null;
	}

	/**
	 * This method is used to init encryption
	 * 
	 * @param key
	 * @throws Exception
	 */
	public void initEncrypt(String key) throws Exception {
		try {
			skeySpec = new SecretKeySpec(HexUtil.HexfromString(key), "AES");
			cipher = Cipher.getInstance("AES");
			cipher.init(1, skeySpec);
		} catch (NoSuchAlgorithmException nsae) {
			throw new Exception("Invalid Java Version");
		} catch (NoSuchPaddingException nse) {
			throw new Exception("Invalid Key");
		}
	}

	/**
	 * This method is used to init decryption
	 * 
	 * @param key
	 * @throws Exception
	 */
	public void initDecrypt(String key) throws Exception {
		try {
			skeySpec = new SecretKeySpec(HexUtil.HexfromString(key), "AES");
			cipher = Cipher.getInstance("AES");
			cipher.init(2, skeySpec);

		} catch (NoSuchAlgorithmException nsae) {
			throw new Exception("Invalid Java Version");
		} catch (NoSuchPaddingException nse) {
			throw new Exception("Invalid Key");
		}
	}

	/**
	 * This method is used to return encrypted data.
	 * 
	 * @param instr
	 * @return String
	 * @throws Exception
	 */
	public String encrypt(String message,String enc_key) throws Exception {
		try {
			initEncrypt(enc_key);
			
			byte encstr[] = cipher.doFinal(message.getBytes());
			return HexUtil.HextoString(encstr);
		} catch (BadPaddingException nse) {
			throw new Exception("Invalid input String");
		}
	}

	/**
	 * This method is used to return decrypted data.
	 * 
	 * @param instr
	 * @return String
	 * @throws Exception
	 */
	public String decrypt(String message,String dec_key) throws Exception {
		try {
			
			initDecrypt(dec_key);
			
			byte encstr[] = cipher.doFinal(HexUtil.HexfromString(message));
			return new String(encstr);
		} catch (BadPaddingException nse) {
			throw new Exception("Invalid input String");
		}
	}

}