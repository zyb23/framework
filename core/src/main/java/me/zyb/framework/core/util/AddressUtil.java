package me.zyb.framework.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 地址工具（IP、MAC等）
 * @author zhangyingbin
 */
@Slf4j
public class AddressUtil {
	private static final String UNKNOWN = "unknown";

	private static final String LOCALHOST_IPV4 = "127.0.0.1";

	private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

	/**
	 * 获取MAC地址
	 * @return String
	 */
	public static String getMACAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				if(null == networkInterface) {
					continue;
				}
				byte[] macBytes = networkInterface.getHardwareAddress();
				if(networkInterface.isUp() && !networkInterface.isLoopback() && null != macBytes && macBytes.length == 6) {
					StringBuilder sb = new StringBuilder();
					for(int i = 0, nLen = macBytes.length; i < nLen; i++) {
						byte b = macBytes[i];
						sb.append(Integer.toHexString((b & 240) >> 4));
						sb.append(Integer.toHexString(b & 15));
						if( i < nLen - 1) {
							sb.append("-");
						}
					}
					return sb.toString().toUpperCase();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取http请求的真实IP地址
	 * @author zhangyingbin
	 * @date 2017-5-22 下午9:44:58
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static String getHttpRequestIPAddress(HttpServletRequest request){
		if (request == null){
			return null;
		}

		String ip = request.getHeader("X-Forwarded-For");
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)){
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)){
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		if (LOCALHOST_IPV4.equals(ip) || LOCALHOST_IPV6.equals(ip)){
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			}
			catch (UnknownHostException e) {
				log.error(e.getMessage());
			}
		}

		return ip;
	}

	/**
	 * 获取本机IPV4地址
	 * @return String
	 */
	public static String getLocalhostIpv4() {
		try {
			InetAddress ip4 = Inet4Address.getLocalHost();
			return ip4.getHostAddress();
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取本机IPV6地址
	 * @return String
	 */
	public static String getLocalhostIpv6() {
		try {
			InetAddress ip4 = Inet6Address.getLocalHost();
			return ip4.getHostAddress();
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
