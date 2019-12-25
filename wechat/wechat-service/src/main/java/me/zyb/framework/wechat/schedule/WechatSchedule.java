package me.zyb.framework.wechat.schedule;

import me.zyb.framework.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhangyingbin
 */
@Component
public class WechatSchedule {
	@Autowired
	private WechatService wechatService;

	/**
	 * 每2小时刷新一次微信access_token
	 */
	@Scheduled(fixedRate = 1000 * 60 * 60 * 2L)
	public void refreshAccessToken(){
		wechatService.refreshAccessToken();
	}
}
