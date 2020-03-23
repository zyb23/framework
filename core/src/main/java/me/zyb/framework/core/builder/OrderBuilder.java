package me.zyb.framework.core.builder;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.dict.ConstString;
import me.zyb.framework.core.util.AddressUtil;
import org.apache.commons.lang3.time.FastDateFormat;
import sun.misc.CRC16;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * 订单编码生成工具
 * @author zhangyingbin
 *
 */
@Slf4j
public class OrderBuilder {
	/** yyyyMMddHHmmssSSS */
	private static FastDateFormat fdfSSS = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");

	/**
	 * 获取4位机器码（CRC16校验码高4位）
	 * @return String
	 */
	private static String getMachineCode() {
		String macAddress = AddressUtil.getMACAddress();
		if(null == macAddress) {
			return "0000";
		}
		byte[] data = macAddress.getBytes(Charset.forName(ConstString.CHARACTER_UTF_8));
		CRC16 crc16 = new CRC16();
		for(byte b : data) {
			crc16.update(b);
		}
		return String.valueOf(crc16.value).substring(0, 4);
	}

	/**
	 * <pre>
	 *     生成订单编码，长度32位
	 *     格式：yyyyMMddHHmmssSSS（17位） + 4位机器码 + 11位订单号
	 * </pre>
	 * @return String
	 */
	public static String generateOrderCode() {
		UUID uuid = UUID.randomUUID();
		int code = uuid.hashCode();
		if(code < 0){
			code = -code;
		}
		String id = String.format("%011d", code);
		String machineCode = getMachineCode();
		return fdfSSS.format(new Date()) + machineCode + id;
	}

	/**
	 * <pre>
	 *     生成带前缀的订单编码
	 *     格式：prefix + 32位订单编码
	 * </pre>
	 * @param prefix    前缀
	 * @return String
	 */
	public static String generateOrderCodeWithPrefix(String prefix) {
		String code = generateOrderCode();
		return prefix + code;
	}

	/**
	 * <pre>
	 *     生成带后缀的订单编码
	 *     格式：32位订单编码 + suffix
	 * </pre>
	 * @param suffix    后缀
	 * @return String
	 */
	public static String generateOrderCodeWithSuffix(String suffix) {
		String code = generateOrderCode();
		return code + suffix;
	}

	public static void main(String[] args) {
		log.info(generateOrderCode());
	}
}
