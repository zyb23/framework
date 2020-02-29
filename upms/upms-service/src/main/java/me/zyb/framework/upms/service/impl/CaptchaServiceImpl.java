package me.zyb.framework.upms.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.builder.CaptchaImage;
import me.zyb.framework.upms.configure.ShiroAuthHelper;
import me.zyb.framework.upms.service.CaptchaService;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Override
    public String[] generateCaptchaImage() {
        Object[] captchaInfo = CaptchaImage.getCaptchaInfo();
        if (null == captchaInfo) {
            return null;
        }
        String captcha = (String) captchaInfo[0];
        log.debug("captcha：" + captcha);
        Session session = ShiroAuthHelper.getCurrentSession();
        if (null != session) {
        	//把验证码设置到session中
            session.setAttribute(ShiroAuthHelper.SESSION_IMAGE_CAPTCHA, captcha);
        }
        byte[] bytes = (byte[]) captchaInfo[1];
        BASE64Encoder encoder = new BASE64Encoder();
        return new String[]{captcha, encoder.encode(bytes)};
    }

	@Override
	public Boolean checkCaptcha(String captcha) {
		Session session = ShiroAuthHelper.getCurrentSession();
		if (session == null) {
			return false;
		}
		//获取session中的验证码，并与用户输入的验证码比对
		String captchaSession = (String) session.getAttribute(ShiroAuthHelper.SESSION_IMAGE_CAPTCHA);
		if (null == captcha || !captcha.toLowerCase().equals(null == captchaSession ? null : captchaSession.toLowerCase())) {
			return false;
		}
		//校验成功，清空session中的验证码
		session.setAttribute(ShiroAuthHelper.SESSION_IMAGE_CAPTCHA, null);
		return true;
	}
}
