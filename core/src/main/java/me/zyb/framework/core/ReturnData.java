package me.zyb.framework.core;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * Controller返回对象
 * @author zhangyingbin
 * @param <T>
 */
@Data
public class ReturnData<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 返回编码{@link ReturnCode value} */
	private String code;
	
	/** 返回信息 */
	private String message;
	
	/** 返回数据实体 */
	private T data;

	public ReturnData(){

	}

	/**
	 * 返回数据：自定义返回码、返回信息
	 * @param code		返回码
	 * @param message	返回信息
	 */
	public ReturnData(String code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
	}

	/**
	 * 返回数据：自定义返回码、返回信息
	 * @param code		返回码
	 * @param message	返回信息
	 * @param data		数据对象
	 */
	public ReturnData(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	@JsonIgnore
	public Boolean isSuccess(){
		return ReturnCode.SUCCESS.getValue().equals(this.code);
	}

	/**
	 * 微服务之间传递返回值时，将data（Object）转化为对应的JavaObject
	 * @param data      返回值
	 * @param tClass    JavaObject类型的class
	 * @param <T>       JavaObject类型
	 * @return T
	 */
	public static <T> T parseObject(Object data, Class<T> tClass){
		return JSON.parseObject(JSON.toJSONString(data), tClass);
	}

	/**
	 * 微服务之间传递返回值时，将data（Collection）转化为对应的JavaObject
	 * @param data      返回值
	 * @param tClass    JavaObject类型的class
	 * @param <T>       JavaObject类型
	 * @return Collection<T>
	 */
	public static <T> Collection<T> parseArray(Collection data, Class<T> tClass){
		return JSON.parseArray(JSON.toJSONString(data), tClass);
	}
}
