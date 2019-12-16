package me.zyb.framework.core.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtil {
	/**
	 * 获取子串，separator不区分大小写
	 * @author zhangyingbin
	 * @param str       原字符串
	 * @param separator 分隔器
	 * @return String
	 */
	public static String substringBeforeIgnoreCase(final String str, final String separator){
		if(StringUtils.isEmpty(str) || separator == null){
            return str;
        }
        if(separator.isEmpty()){
            return StringUtils.EMPTY;
        }
        final int pos = StringUtils.indexOfIgnoreCase(str, separator);
        if(pos == StringUtils.INDEX_NOT_FOUND){
            return str;
        }
        return str.substring(0, pos);
	}
	
	/**
	 * 获取子串，separator不区分大小写
	 * @author zhangyingbin
	 * @param str       原字符串
	 * @param separator 分隔器
	 * @return String
	 */
	public static String substringAfterIgnoreCase(final String str, final String separator){
		if(StringUtils.isEmpty(str)){
            return str;
        }
        if(separator == null){
            return StringUtils.EMPTY;
        }
        final int pos = StringUtils.indexOfIgnoreCase(str, separator);
        if(pos == StringUtils.INDEX_NOT_FOUND){
            return StringUtils.EMPTY;
        }
        return str.substring(pos + separator.length());
	}
	
	/**
	 * 获取子串，separator不区分大小写
	 * @author zhangyingbin
	 * @param str       原字符串
	 * @param separator 分隔器
	 * @return String
	 */
	public static String substringBeforeLastIgnoreCase(final String str, final String separator){
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(separator)){
            return str;
        }
        final int pos = StringUtils.lastIndexOfIgnoreCase(str, separator);
        if(pos == StringUtils.INDEX_NOT_FOUND){
            return str;
        }
        return str.substring(0, pos);
    }
	
	/**
	 * 获取子串，separator不区分大小写
	 * @author zhangyingbin
	 * @param str       原字符串
	 * @param separator 分隔器
	 * @return String
	 */
	public static String substringAfterLastIgnoreCase(final String str, final String separator){
        if(StringUtils.isEmpty(str)){
            return str;
        }
        if(StringUtils.isEmpty(separator)){
            return StringUtils.EMPTY;
        }
        final int pos = StringUtils.lastIndexOfIgnoreCase(str, separator);
        if(pos == StringUtils.INDEX_NOT_FOUND || pos == str.length() - separator.length()){
            return StringUtils.EMPTY;
        }
        return str.substring(pos + separator.length());
    }
	
	/**
	 * 字符串数组转化为字符串
	 * @author zhangyingbin
	 * @param array     字符串数组
	 * @param separator （以第一个为准，默认为“,”）
	 * @return
	 */
    public static String arrayToString(String[] array, String... separator){
    	String sep = null;
        if(array == null || array.length == 0){
            return null;  
        }
        if(null != separator && separator.length > 0){
        	sep = separator[0];
		}
		else{
			sep = ",";
		}
        
        StringBuffer sb = new StringBuffer();
        for(String str : array){
            sb.append(str + sep);
        }
        
        return sb.toString().substring(0, sb.length() - 1);
    }
    
    /**
	 * 字符串列表转化为字符串
	 * @author zhangyingbin
	 * @param array     字符串列表
	 * @param separator （以第一个为准，默认为“,”）
	 * @return
	 */
    public static String arrayToString(List<String> array, String... separator){
    	String sep = null;
        if(array == null || array.size() == 0){
            return null;  
        }
        if(null != separator && separator.length > 0){
        	sep = separator[0];
		}
		else{
			sep = ",";
		}

        StringBuffer sb = new StringBuffer();
        for(String str : array){
            sb.append(str + sep);
        }

        return sb.toString().substring(0, sb.length() - 1);
    }
    
    /**
     * 计算父字符串里包含子字符串的个数
     * <pre>child.length() == 0 return 0</pre>
     * @author zhangyingbin
     * @param parent    父字符串
     * @param child     子字符串
     * @return int
     */
    public static int contain(String parent, String child){
    	int parentLen = parent.length();
    	int childLen = child.length();
    	if(childLen == 0){
    		return 0;
    	}
    	int replaceLen = parentLen - parent.replace(child, "").length();
    	return replaceLen / childLen;
    }
    
    /**
     * 获取一个指定长度的随机数
     * @author zhangyingbin
     * @param strLength 长度
     * @return String
     */
    public static String getFixLengthString(int strLength) {
		//获得随机数，线程安全的
		double pross = ThreadLocalRandom.current().nextDouble();
		//StringBuilder非线程安全，但比StringBuffer快
		StringBuilder sb = new StringBuilder("0.");
		for(int i = 0; i < strLength; i++){
			sb.append("0");
		}
		//将获得的获得随机数转化为字符串
		String fixLenthString = new DecimalFormat(sb.toString()).format(pross);
		//返回固定的长度的随机数
		return fixLenthString.substring(2, strLength + 2);
	}
    
    /**
     * 字母转换成大写（index不传默认首字母）
     * @author zhangyingbin
     * @param str       字符串
     * @param index     下标
     * @return String
     */
    public static String toUpperCase(String str, int... index) {
    	if(StringUtils.isBlank(str)){
    		return null;
    	}
    	if(null == index || index.length <= 0){
        	index = new int[1];
        	index[0] = 0;
		}

    	int length = str.length();
    	char[] cs = str.toCharArray();
    	for(int i : index){
    		if(i < length){
    			if(cs[i] >= 97 && cs[i] <= 122){
				    //a-z[97-122]、A-Z[65-90]
        			cs[i] -= 32;
    			}
    		}
    	}
    	
    	return String.valueOf(cs);
    }
    
    /**
     * 字母转换成小写
     * @author zhangyingbin
     * @param str       字符串
     * @param index     下标
     * @return String
     */
    public static String toLowerCase(String str, int... index) {
    	if(StringUtils.isBlank(str)){
    		return null;
    	}
    	if(null == index || index.length <= 0){
        	index = new int[1];
        	index[0] = 0;
		}
    	
    	int length = str.length();
    	char[] cs = str.toCharArray();
    	for(int i : index){
    		if(i < length){
    			if(cs[i] >= 65 && cs[i] <= 90){
				    //A-Z[65-90]、a-z[97-122]
        	    	cs[i] += 32;
    			}
    		}
    	}
    	
    	return String.valueOf(cs);
    }
    
    
    /**
     * 将byte转为16进制
     * @author zhangyingbin
     * @param byt       字符
     * @return String
     */
    public static String byte2Hex(byte byt){
    	int val = 0xFF & byt;
		if(val < 16){
			return "0" + Integer.toHexString(val);
		}
		else{
			return "" + Integer.toHexString(val);
		}
    }
    
    /**
     * 将byte转为16进制
     * @author zhangyingbin
     * @param bytes     字符数组
     * @return String
     */
    public static String byte2Hex(byte[] bytes){
    	StringBuilder sb = new StringBuilder();
    	String tmp = null;
    	for(int i = 0; i < bytes.length; i++){
    		tmp = byte2Hex(bytes[i]);
    		sb.append(tmp);
    	}
    	
    	return sb.toString();
    }

	/**
	 * sql like 查询拼接“%”（两边）
	 * @param str   字符串
	 * @return String
	 */
	public static String like(String str){
		return "%" + str + "%";
	}

	/**
	 * sql like 查询拼接“%”（左边）
	 * @param str   字符串
	 * @return String
	 */
	public static String likeLeft(String str){
		return "%" + str;
	}

	/**
	 * sql like 查询拼接“%”（右边）
	 * @param str   字符串
	 * @return String
	 */
	public static String likeRight(String str){
		return str + "%";
	}
}
