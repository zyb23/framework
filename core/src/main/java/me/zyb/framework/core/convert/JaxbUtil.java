package me.zyb.framework.core.convert;

import me.zyb.framework.core.dict.ConstString;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * JavaBean与XML互转工具类
 * @author zhangyingbin
 */
public class JaxbUtil {
	
	/**
	 * JavaBean转化成xml，默认编码URF-8
	 * @author zhangyingbin
	 * @param obj   JavaBean
	 * @return String
	 */
	public static String convertToXml(Object obj){
		return convertToXml(obj, ConstString.CHARACTER_UTF_8);
	}
	
	/**
	 * JavaBean转化成xml
	 * @author zhangyingbin
	 * @param obj
	 * @param encoding
	 * @return String
	 */
	public static String convertToXml(Object obj, String encoding){
		String result = null;
		try{
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			
			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * xml转化成JavaBean
	 * @author zhangyingbin
	 * @param xml
	 * @param clazz
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToJavaBean(String xml, Class<T> clazz){
		T t = null;
		try{
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T)unmarshaller.unmarshal(new StringReader(xml));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;
	}
	
	/**
	 * xml文件转化成JavaBean
	 * @author zhangyingbin
	 * @param file
	 * @param clazz
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToJavaBean(File file, Class<T> clazz){
		T t = null;
		try{
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T)unmarshaller.unmarshal(file);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;
	}
}
