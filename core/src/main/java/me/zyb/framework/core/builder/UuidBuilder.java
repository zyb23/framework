package me.zyb.framework.core.builder;

import java.util.UUID;

/**
 * 
 * @author zhangyingbin
 *
 */
public class UuidBuilder {
	private static final String[] CHARS = new String[] {
		"a", "b", "c", "d", "e", "f", "g",
		"h", "i", "j", "k", "l", "m", "n",
		"o", "p", "q", "r", "s", "t",
		"u", "v", "w", "x", "y", "z",
		"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"A", "B", "C", "D", "E", "F", "G",
		"H", "I", "J", "K", "L", "M", "N",
		"O", "P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y", "Z" };
	
	/**
	 * 生成8位字符
	 * @author zhangyingbin
	 * @return String
	 */
	public static String generateUuid8() {
		StringBuilder shortBuffer = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
		    String str = uuid.substring(i * 4, i * 4 + 4);
		    int x = Integer.parseInt(str, 16);
		    shortBuffer.append(CHARS[x % CHARS.length]);
		}
		return shortBuffer.toString();
	}
	
	/**
	 * 生成16位字符
	 * @author zhangyingbin
	 * @return String
	 */
	public static String generateUuid16() {
		StringBuilder shortBuffer = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 16; i++) {
		    String str = uuid.substring(i * 2, i * 2 + 2);
		    int x = Integer.parseInt(str, 16);
		    shortBuffer.append(CHARS[x % CHARS.length]);
		}
		return shortBuffer.toString();
	}
	
	/**
	 * 生成24位字符
	 * @author zhangyingbin
	 * @return String
	 */
	public static String generateUuid24() {
		StringBuilder shortBuffer = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
		    String str = uuid.substring(i * 4, i * 4 + 4);
		    int x = Integer.parseInt(str, 16);
		    shortBuffer.append(CHARS[x % CHARS.length]);
		}
		for (int i = 0; i < 16; i++) {
		    String str = uuid.substring(i * 2, i * 2 + 2);
		    int x = Integer.parseInt(str, 16);
		    shortBuffer.append(CHARS[x % CHARS.length]);
		}
		return shortBuffer.toString();
	}
	
	/**
	 * 生成32位字符
	 * @author zhangyingbin
	 * @return String
	 */
	public static String generateUuid32() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		
		return uuid;
	}
}
