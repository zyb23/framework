package me.zyb.framework.core.util.mail;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;


/**
 * 邮件发送工具类
 * @author zhangyingbin
 *
 */
@Slf4j
public class MailUtil {
	private static String from;

	private static JavaMailSender javaMailSender;
	private static TemplateEngine templateEngine;
	private static MailProperties mailProperties;
	private static TaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

	public MailUtil(JavaMailSender javaMailSender, TemplateEngine templateEngine, MailProperties mailProperties){
		MailUtil.javaMailSender = javaMailSender;
		MailUtil.templateEngine = templateEngine;
		MailUtil.mailProperties = mailProperties;
		MailUtil.from = mailProperties.getUsername();
	}
	
	/** 发送邮件<br>
     * 注：支持HTML格式
     * @param mail      邮件
     * @return boolean
     */  
    public static boolean send(Mail mail) {
    	final MimeMessage mimeMsg = createMimeMessage(mail);
    	if(mimeMsg == null){
    		return false;
    	}
	    /** 异步发送阈值（收件人数超过此阈值时，异步发送） */
	    int asynSize = 5;
	    if(mail.getToEmails().length > asynSize){
    		taskExecutor.execute(() -> javaMailSender.send(mimeMsg));
    	} else{
    		javaMailSender.send(mimeMsg);
    	}
    	log.info("Send mail subject：" + mail.getSubject() + " to：" + StringUtil.arrayToString(mail.getToEmails()));
    	
    	return true;
    }
	
	/**
     * 创建MimeMessage<br>
     * 注：支持HTML格式
     * @author zhangyingbin
     * @param mail  邮件
     * @return MimeMessage
     */
    public static MimeMessage createMimeMessage(Mail mail) {
    	try {
		    if(!checkMail(mail)) {
			    return null;
		    }

		    MimeMessage mimeMsg = javaMailSender.createMimeMessage();
		    MimeMessageHelper mimeMsgHelper = new MimeMessageHelper(mimeMsg, true, Mail.CHARSET);
		    //发件箱（Mail from address must be same as authorization user）
		    mimeMsgHelper.setFrom(StringUtils.isBlank(mail.getFrom()) ? from : mail.getFrom());
		    //邮件主题
		    mimeMsgHelper.setSubject(mail.getSubject());
		    //收件箱
		    mimeMsgHelper.setTo(mail.getToEmails());
		    if(mail.getUseTemplate()) {
			    mail.setIsHtml(true);
			    Context context = new Context();
			    context.setVariables(mail.getVariables());
			    String emailContext = templateEngine.process(mail.getTemplatePath(), context);
			    mail.setContent(emailContext);
		    }
		    if(mail.getIsHtml()){
			    //内容（支持HTML格式）
			    mimeMsgHelper.setText(mail.getContent(), true);
		    } else{
			    mimeMsgHelper.setText(mail.getContent());
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
					    assert fileName != null;
					    fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
				    }
				    catch (Exception e) {
					    log.error("file getBytes error");
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
	    }catch (Exception e){
    		log.error(e.getMessage());
    		e.printStackTrace();
	    }
	    return null;
    }

    /**
     * 检查邮件 
     * @author zhangyingbin
     * @param mail      邮件
     * @return boolean
     */
    public static boolean checkMail(Mail mail){
        if (mail == null) {
            log.warn("Warn Mail is null");
            return false;
        }
        if (mail.getSubject() == null) {
	        log.warn("Warn mail.getSubject() is null");
            return false;
        }
        if (mail.getToEmails() == null) {
	        log.warn("Warn mail.getToEmails() is null");
            return false;
        }
        if(mail.getUseTemplate()){
        	if(StringUtils.isBlank(mail.getTemplatePath())){
        		log.warn("Warn mail.getTemplatePath() is null");
	        }
        }
        
        return true;
    }
}
