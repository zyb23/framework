package me.zyb.framework.wechat.dict;

/**
 * 消息加密方式
 * @author zhangyingbin
 */
public class EncryptMode {
	/**
	 * <p>明文模式</p>
	 * <p>明文模式下，不使用消息体加解密功能，安全系数较低</p>
	 */
	public static final String PLAINTEXT = "明文模式";

	/**
	 * <p>兼容模式</p>
	 * <p>兼容模式下，明文、密文将共存，方便开发者调试和维护</p>
	 */
	public static final String COMPATIBLE = "兼容模式";

	/**
	 * <p>安全模式（推荐）</p>
	 * <p>安全模式下，消息包为纯密文，需要开发者加密和解密，安全系数高</p>
	 */
	public static final String SAFE = "安全模式";

}
