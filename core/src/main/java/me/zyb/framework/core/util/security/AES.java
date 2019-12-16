package me.zyb.framework.core.util.security;

import me.zyb.framework.core.dict.ConstString;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/** 
 * AES加密&解密
 * @author zhangyingbin
 *
 */
@SuppressWarnings("restriction")
public class AES {
	private static final String KEY_AES = "";
	

	/**
	 * 加密
	 * @author zhangyingbin
	 * @param str   明文
	 * @param key   密钥
	 * @return String
	 */
	public static String encrypt(String str, String key){
		String strEncrypt = null;
        try {
        	KeyGenerator kg = KeyGenerator.getInstance(ConstString.ALGORITHM_AES);
            kg.init(128, new SecureRandom(key.getBytes()));
            SecretKey sk = kg.generateKey();
            byte[] enCodeFormat = sk.getEncoded();
            SecretKeySpec sks = new SecretKeySpec(enCodeFormat, ConstString.ALGORITHM_AES);
            
            //加密
            Cipher cipher = Cipher.getInstance(ConstString.ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            byte[] bytes = cipher.doFinal(str.getBytes());
            strEncrypt = new BASE64Encoder().encodeBuffer(bytes);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return strEncrypt;
	}
	
	/**
	 * 解密
	 * @author zhangyingbin
	 * @param str   密文
	 * @param key   密钥
	 * @return String
	 */
	public static String decrypt(String str, String key) {
        String strDecrypt = null;
        try {
        	KeyGenerator kg = KeyGenerator.getInstance(ConstString.ALGORITHM_AES);
        	kg.init(128, new SecureRandom(key.getBytes()));
            SecretKey sk = kg.generateKey();
            byte[] enCodeFormat = sk.getEncoded();
            SecretKeySpec sks = new SecretKeySpec(enCodeFormat, ConstString.ALGORITHM_AES);
            
            //解密
            Cipher cipher = Cipher.getInstance(ConstString.ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, sks);
            byte[] bytes = new BASE64Decoder().decodeBuffer(str);
            byte[] code = cipher.doFinal(bytes);
            strDecrypt = new String(code);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        
        return strDecrypt;
    }
	
	/**
	 * 加密
	 * @author zhangyingbin
	 *
	 * @param str   明文
	 * @return String
	 */
	public static String encrypt(String str){
		return encrypt(str, KEY_AES);
	}
	
	/**
	 * 解密
	 * @author zhangyingbin
	 * @param str   密文
	 * @return String
	 */
	public static String decrypt(String str) {
        return decrypt(str, KEY_AES);
    }
}
