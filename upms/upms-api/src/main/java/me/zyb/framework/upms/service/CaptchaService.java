package me.zyb.framework.upms.service;


/**
 * @author zhangyingbin
 */
public interface CaptchaService {
    /**
     * 生成captchaKey
     * @return String
     */
    public String generateCaptchaKey();

	/**
	 * <P>生成随机验证码及图片</P>
	 * <p>String[0]：验证码字符串</p>
	 * <p>String[1]：验证码图片字节数组</p>
	 * @return String[]
	 */
    public String[] generateCaptchaImage();

	/**
	 * 校验验证码
	 * @param captchaKey    验证码Key
	 * @param captcha       验证码
	 * @return Boolean
	 */
	public Boolean checkCaptcha(String captchaKey, String captcha);
}
