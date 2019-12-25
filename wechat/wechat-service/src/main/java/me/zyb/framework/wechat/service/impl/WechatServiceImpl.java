package me.zyb.framework.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.HttpUtil;
import me.zyb.framework.core.util.security.SHA;
import me.zyb.framework.wechat.WechatException;
import me.zyb.framework.wechat.configure.WechatProperties;
import me.zyb.framework.wechat.dict.GrantType;
import me.zyb.framework.wechat.dict.WechatApi;
import me.zyb.framework.wechat.model.WechatAccessToken;
import me.zyb.framework.wechat.model.WechatConfigModel;
import me.zyb.framework.wechat.service.WechatConfigService;
import me.zyb.framework.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
public class WechatServiceImpl implements WechatService {
	@Autowired
	private WechatProperties wechatProperties;
	@Autowired
	private WechatConfigService wechatConfigService;

	@Override
	public boolean checkSignature(String appKey, String signature, String timestamp, String nonce) {
		WechatConfigModel wechatConfigModel = wechatConfigService.queryValid(appKey);
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
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		return checkSignature(wechatProperties.getAppKey(), signature, timestamp, nonce);
	}

	@Override
	public WechatAccessToken refreshAccessToken(String appKey) {
		WechatConfigModel wechatConfigModel = wechatConfigService.queryValid(appKey);

		String url = MessageFormat.format(WechatApi.GET_ACCESS_TOKEN,
											GrantType.CLIENT_CREDENTIAL,
											wechatConfigModel.getAppId(),
											wechatConfigModel.getAppSecret());
		String str = HttpUtil.doGet4String(url);
		WechatAccessToken wechatAccessToken = JSON.parseObject(str, WechatAccessToken.class);
		if(null == wechatAccessToken){
			throw new WechatException("请求微信access_token失败");
		}
		String accessToken = wechatAccessToken.getAccessToken();
		log.info("access_token：{}", accessToken);

		wechatConfigService.updateAccessToken(wechatConfigModel.getId(), accessToken);

		return wechatAccessToken;
	}

	@Override
	public WechatAccessToken refreshAccessToken() {
		return refreshAccessToken(wechatProperties.getAppKey());
	}
}
