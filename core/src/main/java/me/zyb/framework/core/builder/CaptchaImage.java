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
public class CaptchaImage {

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
     * <P>生成随机验证码及图片</P>
     * <p>Object[0]：验证码字符串</p>
     * <p>Object[1]：验证码图片字节数组</p>
     */
    public static Object[] getCaptchaInfo() {
    	//验证码字符串
	    StringBuilder sb = new StringBuilder();
        //1.创建空白图片
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //2.获取图片画笔
        Graphics graphic = image.getGraphics();
        //3.设置画笔颜色
        graphic.setColor(Color.LIGHT_GRAY);
        //4.绘制矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        //5.画随机字符
        Random ran = new Random();
        for (int i = 0; i < SIZE; i++) {
            //取随机字符索引
            int n = ran.nextInt(CHARS.length);
            //设置随机颜色
            graphic.setColor(getRandomColor());
            //设置字体大小
            graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
            //画字符
            graphic.drawString(CHARS[n] +  "", i * WIDTH / SIZE, HEIGHT * 2 / 3);
            //拼接字符成串
            sb.append(CHARS[n]);
        }
        //6.画干扰线
        for (int i = 0; i < LINES; i++) {
            //设置随机颜色
            graphic.setColor(getRandomColor());
            //随机画线
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
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
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        int r = ran.nextInt(256) % 196 + 60;
        int g = ran.nextInt(256) % 196 + 60;
        int b = ran.nextInt(256) % 196 + 60;
        return new Color(r, g, b);
    }
}
