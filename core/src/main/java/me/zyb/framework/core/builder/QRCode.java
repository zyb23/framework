package me.zyb.framework.core.builder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import me.zyb.framework.core.dict.ConstString;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

/**
 * @author zhangyingbin
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class QRCode {
	/** 二维码尺寸 宽度 */
	private static final int QR_CODE_WIDTH = 300;
	/** 二维码尺寸 高度 */
	private static final int QR_CODE_HEIGHT = 300;
	/** logo 压缩宽度 */
	private static final int LOGO_WIDTH = 60;
	/** logo 压缩高度 */
	private static final int LOGO_HEIGHT = 60;
	
	/**
	 * 创建二维码图像
	 * @author zhangyingbin
	 * @param content		内容
	 * @param imgPath		logo
	 * @param needCompress	是否压缩logo
	 * @return BufferedImage
	 */
	private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception{
		Hashtable ht = new Hashtable();
		ht.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		ht.put(EncodeHintType.CHARACTER_SET, ConstString.CHARACTER_UTF_8);
		ht.put(EncodeHintType.MARGIN, 1);
		
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT, ht);
		int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        
        //插入图片
        insertImage(image, imgPath, needCompress);
        
        return image;
	}
	
	/**
	 * 插入图像
	 * @author zhangyingbin
	 * @param source		原图
	 * @param imgPath		logo
	 * @param needCompress	是否压缩logo
	 */
	private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception{
		File file = new File(imgPath);
		if(!file.exists()){
			System.out.println(imgPath + " 该文件不存在！");
			return;
		}
		
		Image src = ImageIO.read(file);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        
        //压缩logo
        if (needCompress) {
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
	        //绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        
        //插入logo
        Graphics2D graph = source.createGraphics();
        int x = (QR_CODE_WIDTH - width) / 2;
        int y = (QR_CODE_HEIGHT - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
	}
	
	/**
	 * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
	 * @author zhangyingbin
	 * @param destPath  输出路径
	 */
	private static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }
	
	/**
	 * 生成二维码
	 * @author zhangyingbin
	 * @param content		内容
	 * @param imgPath		logo
	 * @param needCompress	是否压缩logo
	 * @param destPath		输出路径
	 */
	public static void encode(String content, String imgPath, boolean needCompress, String destPath) throws Exception {
        BufferedImage image = createImage(content, imgPath, needCompress);
        mkdirs(destPath);
        String file = new Random().nextInt(99999999) + ".jpg";
        ImageIO.write(image, ConstString.FILE_FORMAT_JPG, new File(destPath + "/" + file));
    }
	
	/**
	 * 生成二维码（logo不压缩）
	 * @author zhangyingbin
	 * @param content		内容
	 * @param imgPath		logo
	 * @param destPath		输出路径
	 */
	public static void encode(String content, String imgPath, String destPath)
            throws Exception {
        encode(content, imgPath, false, destPath);
    }
	
	/**
	 * 生成二维码（不带logo）
	 * @author zhangyingbin
	 *
	 * @param content   内容
	 * @param destPath  输出路径
	 */
	public static void encode(String content, String destPath) throws Exception {
		//不带logo时needCompress无作用（true/false无所谓）
        encode(content, null, false, destPath);
	}
	
	/**
	 * 生成二维码
	 * @author zhangyingbin
	 * @param content		内容
	 * @param imgPath		logo
	 * @param needCompress  是否压缩logo
	 * @param output		输出流
	 */
	public static void encode(String content, String imgPath, boolean needCompress, OutputStream output) throws Exception {
        BufferedImage image = createImage(content, imgPath, needCompress);
        ImageIO.write(image, ConstString.FILE_FORMAT_JPG, output);
    }
	
	/**
	 * 生成二维码（不带logo）
	 * @author zhangyingbin
	 * @param content	内容
	 * @param output	输出流
	 */
	public static void encode(String content, OutputStream output) throws Exception {
		//不带logo时needCompress无作用（true/false无所谓）
        encode(content, null, false, output);
    }
	
	/**
	 * 识别二维码
	 * @author zhangyingbin
	 * @param file		文件
	 * @return String
	 */
	public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        //TODO
        
        return "";
    }

	/**
	 * 识别二维码
	 * @author zhangyingbin
	 * @param path		路径
	 * @return String
	 */
	public static String decode(String path) throws Exception {
        return decode(new File(path));
    }
}
