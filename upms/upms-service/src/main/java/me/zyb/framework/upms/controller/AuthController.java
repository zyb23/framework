package me.zyb.framework.upms.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.core.ReturnCode;
import me.zyb.framework.upms.configure.UpmsProperties;
import me.zyb.framework.upms.model.UpmsUserModel;
import me.zyb.framework.upms.service.CaptchaService;
import me.zyb.framework.upms.service.UpmsUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyingbin
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
	@Autowired
	private UpmsProperties upmsProperties;

	@Autowired
	private UpmsUserService upmsUserService;
	@Autowired
	private CaptchaService captchaService;

	@RequestMapping("/403")
	public Object fourZeroThree(){
		return rt(ReturnCode.PERMISSION_DENIED);
	}

	@RequestMapping("/index")
	public Object index(){
		return rt(ReturnCode.LOGIN_TIMEOUT);
	}

	@RequestMapping("/home")
	public Object home(){
		return rtSuccess();
	}

	/**
	 * 用户登录
	 * @param model         用户信息
	 * @param captchaKey    验证码的Key
	 * @param captcha       验证码
	 * @return Object
	 */
	@PostMapping("/login")
	public Object login(UpmsUserModel model, String captchaKey, String captcha){
		if(null == model){
			log.error("参数错误：model");
			return rtParameterError();
		}
		String loginName = model.getLoginName();
		String loginPassword = model.getLoginPassword();
		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(loginPassword)){
			log.error("参数错误：loginName|loginPassword");
			return rt(ReturnCode.USERNAME_PASSWORD_ERROR);
		}
		//校验验证码
		if(upmsProperties.getSwitchImageCaptcha()){
			if(StringUtils.isBlank(captchaKey) || StringUtils.isBlank(captcha)){
				log.error("参数错误：captchaKey|captcha");
				return rt(ReturnCode.CAPTCHA_ERROR);
			}
			if(!captchaService.checkCaptcha(captchaKey, captcha)){
				return rt(ReturnCode.CAPTCHA_ERROR);
			}
		}

		return upmsUserService.login(loginName, loginPassword);
	}

	/**
	 * 注销/登出
	 */
	@RequestMapping(value = "/logout")
	public Object logout() {
		upmsUserService.logout();
		return rtSuccess();
	}
}
