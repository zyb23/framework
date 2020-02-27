package me.zyb.framework.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.wechat.service.WechatService;
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
@RequestMapping("/wechatAuth")
public class WechatAuthController extends BaseController {
	@Autowired
	private WechatService wechatService;

	/**
	 * 登录凭证校验（微信授权用js_code换取openId）
	 * @param code  登录时获取的code
	 * @return Object
	 */
	@PostMapping("/code2Session")
	public Object code2Session(String code){
		if(StringUtils.isBlank(code)){
			log.error("参数错误：code必填");
			return rtParameterError();
		}
		return rtSuccess(wechatService.authCode2Session(code));
	}
}
