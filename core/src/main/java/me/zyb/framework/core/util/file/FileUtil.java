package me.zyb.framework.core.util.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Scanner;

/**
 * 文件工具
 * @author zhangyingbin
 */
@Slf4j
public class FileUtil {
	private static final String ENCODE = "UTF-8";

	/**
	 * 按指定大小分割文件
	 * @author zhangyingbin
	 * @param filePath  被分割的文件
	 * @param size      指定文件大小
	 * @return  String[]
	 */
	public static String[] divide(String filePath, long size) {
		File file = new File(filePath);
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
		if(!file.exists() || (!file.isFile())) {
			log.error("指定文件不存在！filePath：{}", filePath);
			return null;
		}

		//获得被分割文件父文件（目录），被分割成的小文件便存在这个目录下
		File parentFile = file.getParentFile();
		long fileLength = file.length();
		if(size <= 0) {
			size = fileLength / 2;
		}
		//文件分割数量
		int divideNum = (fileLength % size != 0) ? (int)(fileLength / size + 1) : (int)(fileLength / size);
		//存放被分割后的小文件名
		String[] divideNames = new String[divideNum];

		try(FileInputStream in = new FileInputStream(file)){
			int begin = 0;
			long end = 0;
			for (int i = 0; i < divideNum; i++) {
				//对于前num – 1个小文件，大小都为指定的size
				File outFile = new File(parentFile, fileNameWithoutSuffix + i + suffix);
				try(FileOutputStream out = new FileOutputStream(outFile)){
					//将结束下标后移size
					end += size;
					end = (end > fileLength) ? fileLength : end;
					//从输入流中读取字节存储到输出流中
					for(; begin < end; begin++) {
						out.write(in.read());
					}
					divideNames[i] = outFile.getAbsolutePath();
				}catch (Exception e){
					log.error(e.getMessage(), e);
					return null;
				}
			}
			return divideNames;
		}catch (Exception e){
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 指定行数分割文件
	 * @author zhangyingbin
	 * @param filePath  文件路径
	 * @param size      指定文件的行数
	 * @return String[]
	 */
	public static String[] divideByLine(String filePath, long size) {
		File file = new File(filePath);
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
		if(!file.exists() || (!file.isFile())) {
			log.error("指定文件不存在！filePath：{}", filePath);
			return null;
		}

		//获得被分割文件父文件（目录），被分割成的小文件便存在这个目录下
		File parentFile = file.getParentFile();
		long fileLength = file.length();
		if(size <= 0) {
			size = fileLength / 2;
		}
		//当前行数
		long rowIndex = 0;
		long totalLines = getTotalLines(filePath);
		//文件分割数量
		int divideNum = (totalLines % size != 0 ) ? (int)(totalLines / size + 1) : (int)(totalLines / size);
		//存放被分割后的小文件名
		String[] divideNames = new String[divideNum];

		try(Scanner scanner = new Scanner(new File(filePath))){
			for (int i = 0; i < divideNum; i++) {
				//对于前num – 1个小文件，大小都为指定的size
				File outFile = new File(parentFile, fileNameWithoutSuffix + i + suffix);
				try(OutputStream out = new FileOutputStream(outFile);
				    OutputStreamWriter writer = new OutputStreamWriter(out, ENCODE);
				    BufferedWriter bw = new BufferedWriter(writer)){
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						rowIndex++;
						bw.write(line);
						if(rowIndex / size > i){
							break;
						}
						bw.newLine();
					}
					divideNames[i] = outFile.getAbsolutePath();
				}catch (Exception e){
					log.error(e.getMessage(), e);
					return null;
				}
			}
			return divideNames;
		}catch (Exception e){
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 获取文件总行数
	 * @author zhangyingbin
	 * @param filePath  文件路径
	 * @return  long
	 */
	public static long getTotalLines(String filePath) {
		try(Scanner scanner = new Scanner(new File(filePath))){
			long totalLines = 0;
			while (scanner.hasNextLine()) {
				totalLines++;
				scanner.nextLine();
			}
			log.info("文件总行数：{}", totalLines);

			return totalLines;
		}catch (Exception e){
			log.error(e.getMessage());
			return -1;
		}
	}


	/**
	 * 读取文件内容
	 * @author zhangyingbin
	 * @param fileName  文件路径
	 */
	public static void readFileMessage(String fileName) {
		File file = new File(fileName);
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String string;
			// 按行读取内容，直到读入null则表示读取文件结束
			while ((string = reader.readLine()) != null) {
				log.info(string);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 上传文件
	 * @param file      文件
	 * @param realPath  真实路径
	 * @param urlPath   url路径
	 * @param isRename  是否重命名【原文件名 + yyyyMMddHHmmss】
	 * @return String
	 */
	public static String upload(MultipartFile file, String realPath, String urlPath, boolean isRename){
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
		
		if(isRename){
			FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMddHHmmss");
			fileName = fileNameWithoutSuffix + fdf.format(new Date()) + suffix;
		}
		
		realPath = mkdirs(realPath);
		File uploadFile = new File(realPath + fileName);
		try {
			FileCopyUtils.copy(file.getBytes(), uploadFile);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		urlPath = normingPath(urlPath);
		return urlPath + fileName;
	}

	/**
	 * 规范化目录为“/”结尾
	 * @param pathname  目录
	 * @return String
	 */
	private static String normingPath(@NotBlank String pathname){
		if(!pathname.endsWith("/")){
			pathname = pathname + "/";
		}
		return pathname;
	}

	/**
	 * 判断目录是否存在，不存在则创建<br>
	 * 返回“/”结尾的绝对目录路径
	 * @param pathname 路径
	 * @return boolean
	 */
	private static String mkdirs(@NotBlank String pathname){
		pathname = normingPath(pathname);
		File dir = new File(pathname);
		if(!dir.exists()){
			if(!dir.mkdirs()){
				log.error("创建目录失败！Dir：{}", pathname);
				return null;
			}
		}
		return pathname;
	}

	/**
	 * 下载图片到指定目录
	 * @param realPath  真实路径
	 * @param imageUrl  图片URL
	 * @return String
	 */
	public static File downImage(String realPath, String imageUrl){
		realPath = mkdirs(realPath);
		//截取图片的文件名
		String imageFileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		try {
			//文件名里面可能有中文或者空格，所以这里要进行处理，但空格又会被URLEncoder转义为加号，因此要将加号转化为UTF-8格式的%20
			String urlTail = URLEncoder.encode(imageFileName, ENCODE);
			imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf("/") + 1) + urlTail.replaceAll("\\+", "\\%20");
		}catch (Exception e){
			log.error(e.getMessage(), e);
			return null;
		}
		try{
			//写出的文件
			File file = new File(realPath + imageFileName);
			URL url = new URL(imageUrl);
			URLConnection connection = url.openConnection();
			try(InputStream in = connection.getInputStream();
			    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
				//缓冲区
				byte[] buf = new byte[1024];
				int size;
				while (-1 != (size = in.read(buf))){
					out.write(buf, 0, size);
				}
			}
			return file;
		}catch (Exception e){
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
