package me.zyb.framework.core.util.mail;

import me.zyb.framework.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;


/**
 * 邮件发送工具类
 * @author zhangyingbin
 *
 */
@SuppressWarnings("deprecation")
public class MailUtil {
	private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

	private static String host = "smtp.qq.com";
	private static String username = "615794063@qq.com";
	private static String password = "jojpegfzatiubfea";
	
	private static JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	private static TaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	
	/** 异步发送阈值（收件人数超过此阈值时，异步发送） */
	private static int asynSize = 5;
	
	public static void init(){
		javaMailSender.setHost(host);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		javaMailSender.setDefaultEncoding(Mail.CHARSET);
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.port", "465");
		javaMailSender.setJavaMailProperties(javaMailProperties);
	}
	
	/** 发送邮件<br>
     * 注：支持HTML格式
     * @param mail      邮件
     * @return boolean
     */  
    public static boolean send(Mail mail) throws Exception{
    	init();
    	final MimeMessage mimeMsg = createMimeMessage(mail);
    	if(mimeMsg == null){
    		return false;
    	}
    	if(mail.getToEmails().length > asynSize){
    		taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					javaMailSender.send(mimeMsg);
				}
			});
    	}
    	else{
    		javaMailSender.send(mimeMsg);
    	}
    	logger.info("Send mail subject：" + mail.getSubject() + " to：" + StringUtil.arrayToString(mail.getToEmails()));
    	
    	return true;
    }
	
	/**
     * 创建MimeMessage<br>
     * 注：支持HTML格式
     * @author zhangyingbin
     * @param mail
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Mail mail) throws Exception {
        if(!checkMail(mail)) {
            return null;
        }
        
        MimeMessage mimeMsg = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMsgHelper = new MimeMessageHelper(mimeMsg, true, Mail.CHARSET);
	    //发件箱
        mimeMsgHelper.setFrom(mail.getFrom());
        //邮件主题
        mimeMsgHelper.setSubject(mail.getSubject());
	    //收件箱
        mimeMsgHelper.setTo(mail.getToEmails());
        if(null != mail.getTemplate()){
        	mimeMsgHelper.setText("test", true);
        }
        else{
	        //内容（支持HTML格式）
        	mimeMsgHelper.setText(mail.getContent(), true);
        }
        //抄送邮箱
        if(null != mail.getCopyEmails() && mail.getCopyEmails().length > 0){  
        	mimeMsgHelper.setCc(mail.getCopyEmails());
        }
        //附件(Spring form 表单提交的文件)
        if(null != mail.getAttachment()){
	        for(MultipartFile file : mail.getAttachment()){
	        	if(file == null || file.isEmpty()){
	        		continue;
	        	}     
	        	String fileName = file.getOriginalFilename();
	        	try{
	        		fileName = new String(fileName.getBytes(Mail.CHARSET), "ISO-8859-1");
	        	}
	        	catch (Exception e) {
	        		logger.error("file getBytes error");
	        		e.printStackTrace();
	        	}   
	        	mimeMsgHelper.addAttachment(fileName, new ByteArrayResource(file.getBytes()));
	        }
        }
        //附件（普通文件）
        if(null != mail.getFiles()){
        	for(File file : mail.getFiles()){
        		mimeMsgHelper.addAttachment(file.getName(), file);
        	}
        }
        
        return mimeMsg;
    }

    /**
     * 检查邮件 
     * @author zhangyingbin
     * @param mail      邮件
     * @return boolean
     */
    public static boolean checkMail(Mail mail){
        if (mail == null) {
            logger.warn("Warn Mail is null");
            return false;
        }
        if (mail.getSubject() == null) {
            logger.warn("Warn mail.getSubject() is null");
            return false;
        }
        if (mail.getToEmails() == null) {
        	logger.warn("Warn mail.getToEmails()");
            return false;
        }
        
        return true;
    }
}
