package me.zyb.framework.core.builder;

import java.util.UUID;

/**
 * 生成去除“-”的UUID
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
	 * 将32位uuid拆分后当作16进制数，再作为index计算参数拾取CHARS
	 * @param length    拾取位数（需能被32整除）
	 * @return String
	 */
	private static String pickupChar(int length){
		String uuid32 = generateUuid32();
		//32
		int maxLength = uuid32.length();
		if(0 != (maxLength % length)){
			throw new RuntimeException("length必须能被32整除");
		}
		int quotient = 32 / length;
		StringBuilder shortBuffer = new StringBuilder();
		for (int i = 0; i < length; i++) {
			String str = uuid32.substring(i * quotient, i * quotient + quotient);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(CHARS[x % CHARS.length]);
		}
		return shortBuffer.toString();
	}

	/**
	 * 生成8位字符
	 * @author zhangyingbin
	 * @return String
	 */
	public static String generateUuid8() {
		return pickupChar(8);
	}
	
	/**
	 * 生成16位字符
	 * @author zhangyingbin
	 * @return String
	 */
	public static String generateUuid16() {
		return pickupChar(16);
	}
	
	/**
	 * 生成24位字符
	 * @author zhangyingbin
	 * @return String
	 */
	public static String generateUuid24() {
		String str8 = pickupChar(8);
		String str16 = pickupChar(16);
		return str8 + str16;
	}
	
	/**
	 * 生成32位字符
	 * @author zhangyingbin
	 * @return String
	 */
	public static String generateUuid32() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
