package me.zyb.framework.upms.service;


/**
 * @author zhangyingbin
 */
public interface CaptchaService {
	/**
	 * <pre>
	 *     生成随机验证码及图片，并保存验证码到shiro:session中
	 *     String[0]：验证码字符串
	 *     String[1]：验证码图片字节数组
	 * </pre>>
	 * @return String[]
	 */
    public String[] generateCaptchaImage();

	/**
	 * 校验验证码（与shiro:session中的验证码比对）
	 * @param captcha       验证码
	 * @return Boolean
	 */
	public Boolean checkCaptcha(String captcha);
}
