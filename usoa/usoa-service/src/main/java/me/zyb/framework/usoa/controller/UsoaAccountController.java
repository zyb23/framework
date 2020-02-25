package me.zyb.framework.usoa.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.usoa.dict.LoginMode;
import me.zyb.framework.usoa.model.UsoaAccountLoginModel;
import me.zyb.framework.usoa.service.UsoaAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyingbin
 */
@Slf4j
@RequestMapping("/usoaAccount")
@RestController
public class UsoaAccountController extends BaseController {

	@Autowired
	private UsoaAccountService usoaAccountService;

	/**
	 * 用户登录
	 * @param model         登录信息
	 * @return Object
	 */
	@PostMapping("/login")
	public Object login(UsoaAccountLoginModel model){
		if(null == model){
			log.error("参数错误：model");
			return rtParameterError();
		}
		LoginMode loginMode = model.getLoginMode();
		switch (loginMode){
			case SMS_CODE: {
				break;
			}
			case LOGIN_PASSWORD: {
				break;
			}
			case WECHAT: {
				break;
			}
			default: {
				return rtParameterError("登录方式错误！");
			}
		}
		return rtSuccess();
	}
}
