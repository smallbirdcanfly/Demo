package com.fz.cdh.pcdd.util;

import org.apache.commons.net.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MD5Utils {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',  
        '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  

protected static MessageDigest messagedigest = null;  

	static {  
	    try {  
	        messagedigest = MessageDigest.getInstance("MD5");  
	    } catch (NoSuchAlgorithmException nsaex) {  
	        System.err.println(MD5Utils.class.getName()  
	                + "initial failed，MessageDigest not support MD5Util.");
	        nsaex.printStackTrace();  
	    }  
	}  

	public static String getMD5String(String str) {
	    return getMD5String(str.getBytes());  
	}  


	public static String getMD5String(byte[] bytes) {  
	    messagedigest.update(bytes);  
	    return bufferToHex(messagedigest.digest());  
	}  
	
	
	private static String bufferToHex(byte bytes[]) {  
	    return bufferToHex(bytes, 0, bytes.length);  
	}  
	
	private static String bufferToHex(byte bytes[], int m, int n) {  
	    StringBuffer stringbuffer = new StringBuffer(2 * n);  
	    int k = m + n;  
	    for (int l = m; l < k; l++) {  
	        appendHexPair(bytes[l], stringbuffer);  
	    }  
	    return stringbuffer.toString();  
	}  
	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
        char c0 = hexDigits[(bt & 0xf0) >> 4];  
        char c1 = hexDigits[bt & 0xf];  
        stringbuffer.append(c0);  
        stringbuffer.append(c1);  
    } 

	public static  byte[]  aesEncrypt(String content, String password)  {  
		if (password == null) {
            System.out.print("Key==null");
            return null;
        }
        if (password.length() != 16) {
            System.out.print("Key length != 16");
            return null;
        }
        
        try {
        	byte[] raw = password.getBytes("utf-8");
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
	 
	        return encrypted;
		} catch (Exception e) {
			e.getStackTrace();	
		}
		 
		return null;
	}

	public static String getNTimesMd5(String str, int times) {
		String result = str;
		for(int i=0; i<times; i++)
			result = getMD5String(result);
		return result;
	}
     
    public static String BASE64Encode(byte[] bstr){ 
    	return Base64.encodeBase64String(bstr);
	 }
}
