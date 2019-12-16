package me.zyb.framework.core.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 模拟http请求工具类
 * @author zhangyingbin
 *
 */
@Slf4j
public class HttpUtil {

	private static final String ENCODE = "UTF-8";

	/**
	 * 解析HttpResponse
	 * @return String
	 */
	private static String parseResponse(HttpResponse httpResponse) throws Exception{
		int statusCode =httpResponse.getStatusLine().getStatusCode();
		if(HttpStatus.SC_OK == statusCode){
			HttpEntity httpEntity = httpResponse.getEntity();
			String strResult = EntityUtils.toString(httpEntity, ENCODE);
			log.info(strResult);
			return strResult;
		}else {
			log.error("Http Status Code： " + statusCode);
			return null;
		}
	}

	/**
	 * Get请求String
	 * @author zhangyingbin
	 * @param url   请求地址
	 * @return String
	 */
	public static String doGet4String(String url){
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			return parseResponse(httpResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Get请求String
	 * @author zhangyingbin
	 * @param url   请求地址
	 * @param param 请求参数
	 * @return String
	 */
	public static String doGet4String(String url, Map<String, Object> param){
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			List<NameValuePair> params = param.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue().toString())).collect(Collectors.toList());
			URI uri = new URI(url + "?" + URLEncodedUtils.format(params, ENCODE));
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			return parseResponse(httpResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}



	/**
	 * Post请求返回String
	 * @author zhangyingbin
	 * @param url   请求地址
	 * @return String
	 */
	public static String doPost4String(String url){
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			return parseResponse(httpResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Post请求返回String
	 * @author zhangyingbin
	 * @param url       请求地址
	 * @param jsonParam 请求参数
	 * @return String
	 */
	public static String doPost4String(String url, String jsonParam){
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(jsonParam, ENCODE);
			httpPost.setEntity(stringEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			return parseResponse(httpResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Get请求返回Json
	 * @author zhangyingbin
	 * @param url   请求地址
	 * @return JSONObject
	 */
	public static JSONObject doGet4Json(String url){
		try {
			return JSONObject.parseObject(doGet4String(url));
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Get请求返回Json
	 * @author zhangyingbin
	 * @param url   请求地址
	 * @param map   请求参数
	 * @return JSONObject
	 */
	public static JSONObject doGet4Json(String url, Map<String, Object> map){
		try {
			return JSONObject.parseObject(doGet4String(url, map));
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Post请求返回Json
	 * @author zhangyingbin
	 * @param url   请求地址
	 * @return JSONObject
	 */
	public static JSONObject doPost4Json(String url){
		try {
			return JSONObject.parseObject(doPost4String(url));
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Post请求返回Json
	 * @author zhangyingbin
	 * @param url       请求地址
	 * @param jsonParam 请求参数
	 * @return JSONObject
	 */
	public static JSONObject doPost4Json(String url, String jsonParam){
		try {
			return JSONObject.parseObject(doPost4String(url, jsonParam));
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Get请求返回Xml<b>暂不支持</b>
	 * @author zhangyingbin
	 * @param url   请求地址
	 * @return
	 */
	public static String doGet4Xml(String url){
		//TODO
		return null;
	}

	/**
	 * Get请求返回Xml<b>暂不支持</b>
	 * @author zhangyingbin
	 * @param url   请求地址
	 * @param param 请求参数
	 * @return
	 */
	public static String doGet4Xml(String url, Map<String, Object> param){
		//TODO
		return null;
	}
	
	/**
	 * Post请求返回Xml<b>暂不支持</b>
	 * @author zhangyingbin
	 * @param url   请求地址
	 * @return
	 */
	public static String doPost4Xml(String url){
		//TODO
		return null;
	}

	/**
	 * Post请求返回Xml<b>暂不支持</b>
	 * @author zhangyingbin
	 * @param url       请求地址
	 * @param jsonParam 请求参数
	 * @return
	 */
	public static String doPost4Xml(String url, String jsonParam){
		//TODO
		return null;
	}

	/**
	 * Get请求返回Boolean
	 * @param url   请求地址
	 * @return  Boolean
	 */
	public static Boolean doGet4Boolean(String url){
		return Boolean.valueOf(doGet4String(url));
	}

	/**
	 * Get请求返回Boolean
	 * @param url       请求地址
	 * @param param     请求参数
	 * @return  Boolean
	 */
	public static Boolean doGet4Boolean(String url, Map<String, Object> param){
		return Boolean.valueOf(doGet4String(url, param));
	}

	/**
	 * Post请求返回Boolean
	 * @param url   请求地址
	 * @return  Boolean
	 */
	public static Boolean doPost4Boolean(String url){
		return Boolean.valueOf(doPost4String(url));
	}

	/**
	 * Post请求返回Boolean
	 * @param url       请求地址
	 * @param jsonParam 请求参数
	 * @return  Boolean
	 */
	public static Boolean doPost4Boolean(String url, String jsonParam){
		return Boolean.valueOf(doPost4String(url, jsonParam));
	}
}
