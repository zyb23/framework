package me.zyb.framework.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.HttpUtil;
import me.zyb.framework.core.util.security.SHA;
import me.zyb.framework.wechat.WechatException;
import me.zyb.framework.wechat.configure.WechatProperties;
import me.zyb.framework.wechat.dict.WechatApi;
import me.zyb.framework.wechat.dict.WechatGrantType;
import me.zyb.framework.wechat.model.WechatAccessToken;
import me.zyb.framework.wechat.model.WechatConfigModel;
import me.zyb.framework.wechat.model.WechatLoginInfo;
import me.zyb.framework.wechat.model.WechatMenuModel;
import me.zyb.framework.wechat.service.WechatConfigService;
import me.zyb.framework.wechat.service.WechatMenuService;
import me.zyb.framework.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Autowired
	private WechatMenuService wechatMenuService;

	@Override
	public boolean checkSignature(String appKey, String signature, String timestamp, String nonce) {
		WechatConfigModel wechatConfigModel = wechatConfigService.queryByAppKey(appKey);
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
		WechatConfigModel wechatConfigModel = wechatConfigService.queryByAppKey(appKey);
		String url = MessageFormat.format(WechatApi.GET_ACCESS_TOKEN,
											WechatGrantType.CLIENT_CREDENTIAL,
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

	@Override
	public void menuCreate() {
		List<WechatMenuModel> menuList = wechatMenuService.queryTree(wechatProperties.getAppKey());
		WechatConfigModel wechatConfigModel = wechatConfigService.queryByAppKey(wechatProperties.getAppKey());
		String url = MessageFormat.format(WechatApi.MENU_CREATE, wechatConfigModel.getAccessToken());

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("button", menuList);
		JSONObject jsonObject = HttpUtil.doPost4Json(url, JSON.toJSONString(param));
		int errcode = jsonObject.getInteger("errcode");
		if(0 != errcode){
			log.error(jsonObject.toJSONString());
			throw new WechatException(jsonObject.getString("errmsg"));
		}
	}

	@Override
	public WechatLoginInfo authCode2Session(String code) {
		WechatConfigModel wechatConfigModel = wechatConfigService.queryByAppKey(wechatProperties.getAppKey());
		String url = MessageFormat.format(WechatApi.AUTH_CODE_2_SESSION,
											WechatGrantType.AUTHORIZATION_CODE,
											wechatConfigModel.getAppId(),
											wechatConfigModel.getAppSecret(),
											code);
		String str = HttpUtil.doGet4String(url);
		WechatLoginInfo wechatLoginInfo = JSON.parseObject(str, WechatLoginInfo.class);
		if(null == wechatLoginInfo){
			throw new WechatException("登录凭证校验失败");
		}

		return wechatLoginInfo;
	}
}
