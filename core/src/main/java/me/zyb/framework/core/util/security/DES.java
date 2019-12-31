package me.zyb.framework.core.util.security;

import me.zyb.framework.core.dict.ConstString;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * DES加密&解密
 * @author zhangyingbin
 *
 */
@SuppressWarnings("restriction")
public class DES {
	private static final String KEY_DES = "";
	
	/**
	 * 加密
	 * @author zhangyingbin
	 *
	 * @param str   明文
	 * @param key   密钥
	 * @return String
	 */
	public static String encrypt(String str, String key){
		String strEncrypt = null;
        try {
            //DES算法要求有一个可信任的随机数源
        	SecureRandom sr = new SecureRandom();
        	//创建一个DESKeySpec对象
            DESKeySpec desks = new DESKeySpec(keyLengthMakeUp(key));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ConstString.ALGORITHM_DES);
            //将DESKeySpec对象转换成SecretKey对象
            SecretKey sk = skf.generateSecret(desks);
            //加密对象
            Cipher cipher = Cipher.getInstance(ConstString.ALGORITHM_DES);
            cipher.init(Cipher.ENCRYPT_MODE, sk, sr);
            byte[] bytes = cipher.doFinal(str.getBytes());
            strEncrypt = Base64.getEncoder().encodeToString(bytes);
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
            //DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec desks = new DESKeySpec(keyLengthMakeUp(key));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ConstString.ALGORITHM_DES);
            SecretKey sk = skf.generateSecret(desks);  
            //解密对象  
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, sk, sr);
            byte[] bytes = Base64.getDecoder().decode(str);
            byte[] code = cipher.doFinal(bytes);
            strDecrypt = new String(code);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        
        return strDecrypt;
    }
	
	/**
	 * 补足密钥长度为8的整数倍（末位补0）
	 * @return byte[]
	 */
	private static byte[] keyLengthMakeUp(String key){
		byte[] keys = key.getBytes();
		//至少8位长度
		int keyLength = keys.length == 0 ? 8 : keys.length;
		if(keys.length % 8 != 0){
			keyLength = (keys.length / 8 + 1) * 8;
		}
		
		byte[] bytes = new byte[keyLength];
		for(int i = 0; i < bytes.length; i++){
			if(i < keys.length){
				bytes[i] = keys[i];
			}
			else{
				bytes[i] = 0;
			}
		}
		
		return bytes;
	}
	
	/**
	 * 加密
	 * @author zhangyingbin
	 * @param str   明文
	 * @return String
	 */
	public static String encrypt(String str){
		return encrypt(str, KEY_DES);
	}
	
	/**
	 * 解密
	 * @author zhangyingbin
	 * @param str   密文
	 * @return String
	 */
	public static String decrypt(String str) {
        return decrypt(str, KEY_DES);
    }
}
