package me.zyb.framework.core.builder;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 生成验证码图片工具
 * @author zhangyingbin
 */
@Slf4j
public class CaptchaBuilder {

    /** 验证码字符集 */
    private static final char[] CHARS = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    /** 字符数量 */
    private static final int SIZE = 4;
    /** 干扰线数量 */
    private static final int LINES = 16;
    /** 图片宽度 */
    private static final int WIDTH = 80;
    /** 图片高度 */
    private static final int HEIGHT = 40;
    /** 字体大小 */
    private static final int FONT_SIZE = 30;

	/**
	 * 生成纯数字验证码
	 * @param size  验证码字符个数
	 * @return String
	 */
	public static String getPureNumberCaptcha(int size) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int num = size < 1 ? SIZE : size;
		for (int i = 0; i < num; i++) {
			//取随机字符索引
			int n = random.nextInt(10);
			//拼接字符成串
			sb.append(n);
		}
		return sb.toString();
    }

    /**
     * <P>生成随机验证码及图片</P>
     * <p>Object[0]：验证码字符串</p>
     * <p>Object[1]：验证码图片字节数组</p>
     * @param size  验证码字符个数（默认4）
     * @return Object[]
     */
    public static Object[] getImageCaptchaInfo(int size) {
    	//验证码字符串
	    StringBuilder sb = new StringBuilder();
        //1.创建空白图片
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //2.获取图片画框
        Graphics graphic = image.getGraphics();
        //3.设置画框颜色
        graphic.setColor(Color.LIGHT_GRAY);
        //4.填充画框背景色
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        //5.画随机字符
        Random random = new Random();
        int num = size < 1 ? SIZE : size;
        for (int i = 0; i < num; i++) {
            //取随机字符索引
            int n = random.nextInt(CHARS.length);
            //设置验证码颜色
            graphic.setColor(getRandomColor4Captcha());
            //设置字体大小
            graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
            //画字符
            graphic.drawString(CHARS[n] +  "", i * WIDTH / num, HEIGHT * 2 / 3);
            //拼接字符成串
            sb.append(CHARS[n]);
        }
        //6.画干扰线
        for (int i = 0; i < LINES; i++) {
            //设置干扰线颜色
            graphic.setColor(getRandomColor4Line());
            //随机画线
            graphic.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT), random.nextInt(WIDTH), random.nextInt(HEIGHT));
        }
        //7.返回验证码和图片字节
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            log.error("验证码图片生成失败！[info = " + e.getMessage() + "]");
            return null;
        }
        return new Object[]{sb.toString(), outputStream.toByteArray()};
    }

    /**
     * 验证码随机取色（颜色更深）
     * @return Color
     */
    private static Color getRandomColor4Captcha() {
        Random ran = new Random();
        int r = ran.nextInt(256) % 200 + 56;
        int g = ran.nextInt(256) % 200 + 56;
        int b = ran.nextInt(256) % 200 + 56;
        return new Color(r, g, b);
    }

	/**
	 * 干扰线随机取色（颜色更浅）
	 * @return Color
	 */
	private static Color getRandomColor4Line() {
		Random ran = new Random();
		int r = ran.nextInt(256) % 100 + 156;
		int g = ran.nextInt(256) % 100 + 156;
		int b = ran.nextInt(256) % 100 + 156;
		return new Color(r, g, b);
    }
}
