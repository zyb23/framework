package me.zyb.framework.core.util.security;

import me.zyb.framework.core.dict.ConstString;
import me.zyb.framework.core.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * SHA加密
 * @author zhangyingbin
 *
 */
public class SHA {
	/*** 
     * SHA加密
     * @param str 			待加密字符串
     * @param algorithm		算法
     * @return 返回40位SHA码
     */
    public static String encrypt(String str, String algorithm){
    	if(str == null) {
			throw new IllegalArgumentException("加密字符原串不能为null");
		}
    	
        try{
        	MessageDigest sha = MessageDigest.getInstance(algorithm);
        	byte[] byteArray = str.getBytes(StandardCharsets.UTF_8);
            byte[] shaBytes = sha.digest(byteArray);
            
            return StringUtil.byte2Hex(shaBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    /**
     * SHA/SHA1/SHA-1加密
     * @author zhangyingbin
     * @param str   明文
     * @return String
     */
    public static String encryptSHA(String str){
    	if(str == null) {
			throw new IllegalArgumentException("加密字符原串不能为null");
		}
    	
    	try{
        	MessageDigest sha = MessageDigest.getInstance(ConstString.ALGORIGHM_SHA);
        	byte[] byteArray = str.getBytes(StandardCharsets.UTF_8);
            byte[] shaBytes = sha.digest(byteArray);
            
            return StringUtil.byte2Hex(shaBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * SHA-256加密
     * @author zhangyingbin
     * @param str   明文
     * @return String
     */
    public static String encryptSHA256(String str){
    	if(str == null) {
			throw new IllegalArgumentException("加密字符原串不能为null");
		}
    	
    	try{
        	MessageDigest sha = MessageDigest.getInstance(ConstString.ALGORIGHM_SHA256);
        	byte[] byteArray = str.getBytes(StandardCharsets.UTF_8);
            byte[] shaBytes = sha.digest(byteArray);
            
            return StringUtil.byte2Hex(shaBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
}
