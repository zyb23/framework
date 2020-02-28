package me.zyb.framework.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.core.convert.JaxbUtil;
import me.zyb.framework.wechat.dict.WechatParam;
import me.zyb.framework.wechat.model.WechatMsg;
import me.zyb.framework.wechat.model.response.WXAccessToken;
import me.zyb.framework.wechat.service.WXService;
import me.zyb.framework.wechat.service.WechatMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import java.io.PrintWriter;

/**
 * @author zhangyingbin
 */
@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatController extends BaseController {
	@Autowired
	private WXService wxService;
	@Autowired
	private WechatMsgService wechatMsgService;

	/**
	 * 验证
	 * @return boolean
	 */
	private boolean validate(){
		String signature = request.getParameter(WechatParam.SIGNATURE);
		String timestamp = request.getParameter(WechatParam.TIMESTAMP);
		String nonce = request.getParameter(WechatParam.NONCE);
		return wxService.checkSignature(signature, timestamp, nonce);
	}

	/**
	 * <p>服务器配置校验，微信平台上修改/保存配置时，会调用此方法</p>
	 * <p>需验证消息是否来自微信</p>
	 */
	@GetMapping("/checkSignature")
	public void doGet(){
		try(ServletOutputStream out = response.getOutputStream()) {
			if(validate()){
				String echostr = request.getParameter(WechatParam.ECHOSTR);
				out.print(echostr);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>用户发送信息给公众号，微信服务器转发消息过来，会调用此方法</p>
	 * <p>需验证消息是否来自微信</p>
	 */
	@PostMapping("/checkSignature")
	public void doPost(){
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()) {
			if(validate()){
				WechatMsg msgRequest = JaxbUtil.convertToJavaBean(request.getInputStream(), WechatMsg.class);
				WechatMsg msgResponse = wechatMsgService.handleRequest(msgRequest);
				if(null == msgResponse.getContent()){
					out.write("");
				}else {
					String msgReplay = JaxbUtil.convertToXml(msgResponse);
					out.write(msgReplay);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 从微信获取access_token，并刷新数据库中WechatConfig的access_token
	 * @return Object
	 */
	@GetMapping("/refreshAccessToken")
	public Object refreshAccessToken(){
		WXAccessToken WXAccessToken = wxService.refreshAccessToken();
		return rtSuccess(WXAccessToken);
	}
}
