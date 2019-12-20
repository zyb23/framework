package me.zyb.framework.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.HttpUtil;
import me.zyb.framework.core.util.security.SHA;
import me.zyb.framework.wechat.dict.GrantType;
import me.zyb.framework.wechat.dict.WechatApi;
import me.zyb.framework.wechat.entity.WechatConfig;
import me.zyb.framework.wechat.model.WechatAccessToken;
import me.zyb.framework.wechat.repository.WechatConfigRepository;
import me.zyb.framework.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
public class WechatServiceImpl implements WechatService {
	
	@Autowired
	private WechatConfigRepository wechatConfigRepository;

	@Override
	public boolean checkSignature(String appKey, String signature, String timestamp, String nonce) {
		List<WechatConfig> list = wechatConfigRepository.findByAppKeyAndIsEnable(appKey, true);
		if(null != list && list.size() > 0){
			WechatConfig wechatConfig = list.get(0);
			String token = wechatConfig.getToken();
			
			String[] array = new String[]{token, timestamp, nonce};
			Arrays.sort(array);
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < array.length; i++){
				sb.append(array[i]);
			}
			
			String tmp = SHA.encryptSHA(sb.toString());
			
			return signature != null && signature.equalsIgnoreCase(tmp);
		}
		else{
			log.error("No valid WechatConfig Config");
			return false;
		}
	}

	@Override
	public WechatAccessToken getAccessToken(String appId, String appSecret) {
		String url = WechatApi.GET_ACCESS_TOKEN
				+ "?grant_type=" + GrantType.CLIENT_CREDENTIAL
				+ "&appid=" + appId
				+ "&secret=" + appSecret;
		
		String str = HttpUtil.doGet4String(url);

		return JSON.parseObject(str, WechatAccessToken.class);
	}
	
}
