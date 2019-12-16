package me.zyb.framework.core.util.security;

import me.zyb.framework.core.dict.ConstString;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密&解密
 * @author zhangyingbin
 *
 */
@SuppressWarnings("restriction")
public class RSA {
	private static final String KEY_PAIR_PUBLIC = "KeyPairPublic";
	private static final String KEY_PAIR_PRIVATE = "KeyPairPrivate";

	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFI2qm88WvrhWvUFHFrgSxUQYQg3aVz9UwfniIyY4QEc0QH8ApKW4C/arubt/qyDq3BW99yq+blMxZjYosyI3nuAvtMRrmZV41vBqe8vzvezdlXHouY9FUieQ7xBXfWrcR9cz91Ixkg2B2BBVw+BwjanQO0UrsJz4mMAhhOSAoTQIDAQAB";
	private static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIUjaqbzxa+uFa9QUcWuBLFRBhCDdpXP1TB+eIjJjhARzRAfwCkpbgL9qu5u3+rIOrcFb33Kr5uUzFmNiizIjee4C+0xGuZlXjW8Gp7y/O97N2Vcei5j0VSJ5DvEFd9atxH1zP3UjGSDYHYEFXD4HCNqdA7RSuwnPiYwCGE5IChNAgMBAAECgYB0uU0628O4NyaZ0hAvAFbq4j8v4SHpTBH7dxXTzul22zRua3e8Xq8tfhoK+jfkDfG0HdbWcQmunDDgXoACrT4C4eZiFSJLZNzoN5HZKsZZKA9ZjCAjU10lYO/EE8dljMO36bE6N/2SSyMk6/8Nv+gZtqSJPNg3iIzB+rrcspAtXQJBAMUfHDiyJFUyl7XS7unjl4hYe2yW0wOekJ4+ZGGQbqEDKdM1PD8MmeFKrloR/iSKaMpY3Yo/V7jeRTUbQDS2dF8CQQCs59XJ9XlUrm0WbAqRksPqy41LyxFmFhz0Wm1ppLv91c0em+QrzVoKExs7H/y4Vn6ny5mQ24nyyVIKeglyGoLTAkAj3vNtxc0iy+AbsFTVrxrmLuPW8ONZZ0N96HCv6G6ZUlAMJUXfW1WtT7/GmDudPqqvF9jIzZoTBC+n6FuYvBMVAkAay2KKBYxj3xPwoZWRsZjCzgB7emCb3VlnVIwYDvtC/trmn2ngj9YgMY3Kv84+EslV32tQ03SxNxmLnhXQpOMZAkAztwUbO8bx6l+kr/AOAOLKwzhxJQbcZBw6Mkivvjm1H/cir8o/nKhr8jM/sLZsr3WPELmc5wxdffHC/cX2BXK9";
	
	
	/**
	 * 生成密钥对
	 * @author zhangyingbin
	 *
	 */
	public static Map<String, Object> generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ConstString.ALGORIGHM_RSA);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(KEY_PAIR_PUBLIC, publicKey);
        keyMap.put(KEY_PAIR_PRIVATE, privateKey);
        
        return keyMap;
    }
	
	/**
	 * 获取公钥
	 * @author zhangyingbin
	 * @param keyMap    密钥对
	 * @return String
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key)keyMap.get(KEY_PAIR_PUBLIC);
		byte[] publicKey = key.getEncoded();
		return encryptBASE64(publicKey);
	}
	
	/**
	 * 获取私钥
	 * @author zhangyingbin
	 * @param keyMap    密钥对
	 * @return String
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key)keyMap.get(KEY_PAIR_PRIVATE);
		byte[] privateKey = key.getEncoded();
		return encryptBASE64(privateKey);
	}
	
	/**
	 * BASE64编码
	 * @author zhangyingbin
	 * @param str   待编码字符
	 * @return String
	 */
	public static String encryptBASE64(byte[] str) {
		return (new BASE64Encoder()).encodeBuffer(str);
	}
	
	/**
	 * BASE64解码
	 * @author zhangyingbin
	 *
	 * @param str   待解码字符
	 * @return byte[]
	 */
	public static byte[] decryptBASE64(String str) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(str);
	}
	
	/**
	 * 加密
	 * @author zhangyingbin
	 * @param str 明文
	 * @param key 加密公钥（BASE64编码）
	 * @return String
	 */
	public static String encrypt(String str, String key) throws Exception {
		//对公钥解码
		byte[] keyBytes = decryptBASE64(key);
		
		//取得公钥
		X509EncodedKeySpec x509 = new X509EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance(ConstString.ALGORIGHM_RSA);
		Key publicKey = factory.generatePublic(x509);
		
		//对明文进行加密
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = cipher.doFinal(str.getBytes());
		
		return encryptBASE64(bytes);
    }
	
	/**
	 * 解密
	 * @author zhangyingbin
	 * @param str 密文
	 * @param key 解密私钥（BASE64编码）
	 * @return String
	 */
    public static String decrypt(String str, String key) throws Exception {
    	//对私钥解码
    	byte[] keyBytes = decryptBASE64(key);
    	
    	//取得私钥
    	PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(keyBytes);
    	KeyFactory factory = KeyFactory.getInstance(ConstString.ALGORIGHM_RSA);
    	Key privateKey = factory.generatePrivate(pkcs8);
    	
    	//对密文进行解密
    	Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
    	cipher.init(Cipher.DECRYPT_MODE, privateKey);
//    	byte[] bytes = decryptBASE64(str);
    	byte[] bytes = decode(str);
    	byte[] code = cipher.doFinal(bytes);
        
    	return new String(code);
    }
    public static byte[] decode(String s) {  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        try {  
            decode(s, bos);  
        } catch (IOException e) {  
            throw new RuntimeException();  
        }  
        byte[] decodedBytes = bos.toByteArray();  
        try {  
            bos.close();  
            bos = null;  
        } catch (IOException ex) {  
            System.err.println("Error while decoding BASE64: " + ex.toString());  
        }  
        return decodedBytes;  
    }
    private static void decode(String s, OutputStream os) throws IOException {  
        int i = 0;  
        int len = s.length();  
        while (true) {  
            while (i < len && s.charAt(i) <= ' '){
	            i++;
            }
	        if (i == len){
		        break;
	        }
            int tri = (decode(s.charAt(i)) << 18)
                    + (decode(s.charAt(i + 1)) << 12)  
                    + (decode(s.charAt(i + 2)) << 6)  
                    + (decode(s.charAt(i + 3)));  
            os.write((tri >> 16) & 255);  
            if (s.charAt(i + 2) == '='){
	            break;
            }
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '='){
	            break;
            }
            os.write(tri & 255);
            i += 4;  
        }  
    }
    private static int decode(char c) {  
        if (c >= 'A' && c <= 'Z'){
	        return ((int) c) - 65;
        }
        else if (c >= 'a' && c <= 'z'){
	        return ((int) c) - 97 + 26;
        }
        else if (c >= '0' && c <= '9'){
	        return ((int) c) - 48 + 26 + 26;
        }
        else{
            switch (c) {  
            case '+':  
                return 62;  
            case '/':  
                return 63;  
            case '=':  
                return 0;  
            default:  
                throw new RuntimeException("unexpected code: " + c);  
            }
        }
    }
    /**
	 * 公钥加密
	 * @author zhangyingbin
	 * @param str 明文
	 * @return String
	 */
	public static String encrypt(String str) throws Exception{
		return RSA.encrypt(str, PUBLIC_KEY);
	}
	
	/**
	 * 私钥解密
	 * @author zhangyingbin
	 * @param str 密文
	 * @return String
	 */
	public static String decrypt(String str) throws Exception{
		return RSA.decrypt(str, PRIVATE_KEY);
	}
}
