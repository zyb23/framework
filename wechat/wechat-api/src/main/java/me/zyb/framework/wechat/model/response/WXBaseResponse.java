package me.zyb.framework.wechat.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信接口返回值公共参数
 * @author zhangyingbin
 */
@Data
public class WXBaseResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 错误码 */
	@JSONField(name = "errcode")
	private String errCode;
	
	/** 错误信息 */
	@JSONField(name = "errmsg")
	private String errMsg;

	/** 请求微信信息成功时返回码 */
	public static final Integer ERR_CODE_OK = 0;

	public boolean isSuccess(){
		return this.errCode == null || String.valueOf(ERR_CODE_OK).equals(errCode);
	}
}
