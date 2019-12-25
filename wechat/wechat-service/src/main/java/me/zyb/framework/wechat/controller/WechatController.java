package me.zyb.framework.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.wechat.dict.WechatParam;
import me.zyb.framework.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhangyingbin
 */
@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatController extends BaseController {
	@Autowired
	private WechatService wechatService;

	/**
	 * <p>服务器配置</p>
	 * <p>验证消息是否来自微信</p>
	 */
	@GetMapping("/checkSignature")
	public void checkSignature(){
		String signature = request.getParameter(WechatParam.SIGNATURE);
		String timestamp = request.getParameter(WechatParam.TIMESTAMP);
		String nonce = request.getParameter(WechatParam.NONCE);
		String echostr = request.getParameter(WechatParam.ECHOSTR);
		try(PrintWriter out = response.getWriter()) {
			boolean flag = wechatService.checkSignature(signature, timestamp, nonce);
			if(flag){
				out.write(echostr);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 从微信获取access_token，并刷新数据库中WechatConfig的access_token
	 */
	@GetMapping("/refreshAccessToken")
	public Object refreshAccessToken(){
		return wechatService.refreshAccessToken();
	}
}
