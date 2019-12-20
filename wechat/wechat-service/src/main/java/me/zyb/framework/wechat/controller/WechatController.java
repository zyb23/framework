package me.zyb.framework.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.wechat.configure.WechatProperties;
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
	private WechatProperties wechatProperties;
	@Autowired
	private WechatService wechatService;

	/**
	 * 验证消息是否来自微信
	 */
	@GetMapping("/checkSignature")
	public void checkSignature(){
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		boolean flag = wechatService.checkSignature(wechatProperties.getAppKey(), signature, timestamp, nonce);
		try(PrintWriter out = response.getWriter()) {
			if(flag){
				String echostr = request.getParameter("echostr");
				out.write(echostr);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
