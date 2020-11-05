package me.zyb.framework.core.util.security;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.constant.ConstString;
import me.zyb.framework.core.util.StringUtil;

import java.security.MessageDigest;

/**
 * MD5加密
 * @author zhangyingbin
 *
 */
@Slf4j
public class MD5 {

	/**
	 * MD5加密 生成32位MD5码
	 * 
	 * @param str 待加密的字符串
	 * @return 32位MD5码
	 */
	public static String encrypt(String str){
		if(str == null || str.length() == 0) {
			throw new IllegalArgumentException("加密字符原串不能为null或长度不能为0");
		}
				
		try{
			MessageDigest md5 = MessageDigest.getInstance(ConstString.ALGORITHM_MD5);
			byte[] byteArray = str.getBytes(ConstString.CHARACTER_UTF_8);
			byte[] md5Bytes = md5.digest(byteArray);
			
			return StringUtil.byte2Hex(md5Bytes);
		}catch (Exception e){
			log.error("加密异常", e);
		}
		
		return null;
	}
	
}
