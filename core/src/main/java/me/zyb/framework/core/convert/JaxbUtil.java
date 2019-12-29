package me.zyb.framework.core.convert;

import me.zyb.framework.core.dict.ConstString;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
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
	public static String convertToXml(Object obj) throws JAXBException {
		return convertToXml(obj, ConstString.CHARACTER_UTF_8);
	}
	
	/**
	 * JavaBean转化成xml
	 * @author zhangyingbin
	 * @param obj       JavaBean
	 * @param encoding  字符集
	 * @return String
	 */
	public static String convertToXml(Object obj, String encoding) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}
	/**
	 * xml转化成JavaBean
	 * @author zhangyingbin
	 * @param inputStream   xml输入流
	 * @param clazz         JavaBean的class
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToJavaBean(InputStream inputStream, Class<T> clazz) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (T)unmarshaller.unmarshal(inputStream);
	}

	/**
	 * xml转化成JavaBean
	 * @author zhangyingbin
	 * @param xml   xml字符串
	 * @param clazz JavaBean的class
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToJavaBean(String xml, Class<T> clazz) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (T)unmarshaller.unmarshal(new StringReader(xml));
	}
	
	/**
	 * xml文件转化成JavaBean
	 * @author zhangyingbin
	 * @param file  xml文件
	 * @param clazz JavaBean的class
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToJavaBean(File file, Class<T> clazz) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (T)unmarshaller.unmarshal(file);
	}
}
