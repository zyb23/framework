package me.zyb.framework.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.wechat.model.WechatMenuModel;
import me.zyb.framework.wechat.service.WechatMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author zhangyingbin
 */
@Slf4j
@RestController
@RequestMapping("/wechatMenu")
public class WechatMenuController extends BaseController {
	@Autowired
	private WechatMenuService wechatMenuService;

	/**
	 * 新增菜单
	 * @param model 菜单信息
	 * @return Object
	 */
	@PostMapping("/add")
	public Object add(@Valid @RequestBody WechatMenuModel model){
		WechatMenuModel wechatMenuModel = wechatMenuService.save(model);
		return rtSuccess(wechatMenuModel);
	}
}
