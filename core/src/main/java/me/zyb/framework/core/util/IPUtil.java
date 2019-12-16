package me.zyb.framework.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具
 * @author zhangyingbin
 *
 */
@Slf4j
public class IPUtil {
	public static final String UNKNOWN = "unknown";

	public static final String LOCALHOST_IPV4 = "127.0.0.1";

	public static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

	/**
	 * 获取http请求的真实IP地址
	 * @author zhangyingbin
	 * @date 2017-5-22 下午9:44:58
	 * @param request HttpServletRequest
	 * @return String
	 */
    public static String getIPAddress(HttpServletRequest request){
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
}
