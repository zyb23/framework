package me.zyb.framework.wechat.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.wechat.configure.WechatProperties;
import me.zyb.framework.wechat.service.WechatConfigService;
import me.zyb.framework.wechat.service.WechatMenuService;
import me.zyb.framework.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
