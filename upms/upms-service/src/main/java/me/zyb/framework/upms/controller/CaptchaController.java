package me.zyb.framework.upms.controller;

import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.upms.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 验证码
 * @author zhangyingbin
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController extends BaseController {

	@Value("${spring.profiles.active: prod}")
	private String active;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 生成captchaKey
     * @return Object
     */
    @RequestMapping("/captchaKey")
    public Object captchaKey() {
        String captchaKey = captchaService.generateCaptchaKey();
        return rtSuccess(captchaKey);
    }

    /**
     * 生成验证码图片
     * @return Object
     */
    @RequestMapping("/image")
    public Object image() {
        String[] captchaImage = captchaService.generateCaptchaImage();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("code", "prod".equals(active) ?  "****" : captchaImage[0]);
        retMap.put("image", captchaImage[1]);
        return rtSuccess(retMap);
    }
}
