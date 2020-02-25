package me.zyb.framework.usoa.service;

import me.zyb.framework.usoa.model.UsoaAccountModel;

/**
 * @author zhangyingbin
 */
public interface UsoaAccountService {

	/**
	 * 短信验证码登录（首次登录自动注册）
	 * @param mobile    手机号
	 * @param smsCode   短信验证码
	 * @return UsoaAccountModel
	 */
	public UsoaAccountModel loginBySmsCode(String mobile, String smsCode);
}
