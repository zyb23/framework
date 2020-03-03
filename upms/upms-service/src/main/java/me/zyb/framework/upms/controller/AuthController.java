package me.zyb.framework.upms.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.ReturnCode;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.configure.UpmsProperties;
import me.zyb.framework.upms.model.UpmsUserModel;
import me.zyb.framework.upms.service.CaptchaService;
import me.zyb.framework.upms.service.UpmsUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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

	/**
	 * <pre>
	 *     未授权界面
	 *     前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
	 * </pre>
	 * @return Object
	 */
	@RequestMapping("/403")
	public Object fourZeroThree(){
		return rt(ReturnCode.PERMISSION_DENIED);
	}

	/**
	 * <pre>
	 *     配置shiro默认登录界面地址（未登录会重定向到这）
	 *     前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
	 * </pre>
	 * @return Object
	 */
	@RequestMapping("/index")
	public Object index(){
		return rt(ReturnCode.LOGIN_TIMEOUT);
	}

	/**
	 * <pre>
	 *     登录成功后要跳转的链接（前后端分离项目，由前端控制跳转）
	 * </pre>
	 * @return Object
	 */
	@RequestMapping("/home")
	public Object home(){
		return rtSuccess();
	}

	/**
	 * 用户登录
	 * @param model         用户信息
	 * @param captcha       验证码
	 * @return Object
	 */
	@PostMapping("/login")
	public Object login(UpmsUserModel model, String captcha){
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
			if(StringUtils.isBlank(captcha)){
				log.error("参数错误：captcha");
				return rt(ReturnCode.CAPTCHA_ERROR);
			}
			if(!captchaService.checkCaptcha(captcha)){
				return rt(ReturnCode.CAPTCHA_ERROR);
			}
		}

		//登录校验
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPassword);
			subject.login(token);
		} catch (UnknownAccountException | IncorrectCredentialsException e) {
			throw new UpmsException("用户名/密码错误");
		} catch (LockedAccountException e) {
			throw new UpmsException("该用户被锁定");
		} catch (DisabledAccountException e){
			throw new UpmsException("该用户被冻结");
		} catch (AuthenticationException e) {
			throw new UpmsException("用户不存在");
		} catch (Exception e) {
			throw new UpmsException("登录失败");
		}
		session.setTimeout(upmsProperties.getSessionTimeout());

		return rtSuccess(session.getId().toString());
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
