package me.zyb.framework.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.HttpUtil;
import me.zyb.framework.core.util.security.SHA;
import me.zyb.framework.wechat.WechatException;
import me.zyb.framework.wechat.configure.WechatProperties;
import me.zyb.framework.wechat.dict.WechatApi;
import me.zyb.framework.wechat.dict.WechatGrantType;
import me.zyb.framework.wechat.model.WechatConfigModel;
import me.zyb.framework.wechat.model.response.WXAccessToken;
import me.zyb.framework.wechat.model.response.WXCode2Session;
import me.zyb.framework.wechat.service.WXService;
import me.zyb.framework.wechat.service.WechatConfigService;
import me.zyb.framework.wechat.service.WechatMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
public class WXServiceImpl implements WXService {
	@Autowired
	private WechatProperties wechatProperties;
	@Autowired
	private WechatConfigService wechatConfigService;
	@Autowired
	private WechatMenuService wechatMenuService;

	@Override
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		WechatConfigModel wechatConfigModel = wechatConfigService.queryByAppKey(wechatProperties.getAppKey());
		String token = wechatConfigModel.getToken();

		String[] array = new String[]{token, timestamp, nonce};
		Arrays.sort(array);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < array.length; i++){
			sb.append(array[i]);
		}

		String tmp = SHA.encryptSHA(sb.toString());

		return signature != null && signature.equalsIgnoreCase(tmp);
	}

	@Override
	public WXAccessToken refreshAccessToken() {
		WechatConfigModel wechatConfigModel = wechatConfigService.queryByAppKey(wechatProperties.getAppKey());
		String url = MessageFormat.format(WechatApi.GET_ACCESS_TOKEN,
											WechatGrantType.CLIENT_CREDENTIAL,
											wechatConfigModel.getAppId(),
											wechatConfigModel.getAppSecret());
		String str = HttpUtil.doGet4String(url);
		WXAccessToken wxAccessToken = JSON.parseObject(str, WXAccessToken.class);
		if(null == wxAccessToken){
			throw new WechatException("请求微信access_token失败");
		}
		String accessToken = wxAccessToken.getAccessToken();
		log.info("access_token：{}", accessToken);

		wechatConfigService.updateAccessToken(wechatConfigModel.getId(), accessToken);

		return wxAccessToken;
	}

	@Override
	public WXCode2Session authCode2Session(String code) {
		WechatConfigModel wechatConfigModel = wechatConfigService.queryByAppKey(wechatProperties.getAppKey());
		String url = MessageFormat.format(WechatApi.AUTH_CODE_2_SESSION,
											WechatGrantType.AUTHORIZATION_CODE,
											wechatConfigModel.getAppId(),
											wechatConfigModel.getAppSecret(),
											code);
		String str = HttpUtil.doGet4String(url);
		WXCode2Session wxCode2Session = JSON.parseObject(str, WXCode2Session.class);
		if(null == wxCode2Session){
			throw new WechatException("登录凭证校验失败");
		} else if(!wxCode2Session.isSuccess()){
			throw new WechatException("errcode:" + wxCode2Session.getErrCode() + "errmsg:" + wxCode2Session.getErrMsg());
		}

		return wxCode2Session;
	}
}
