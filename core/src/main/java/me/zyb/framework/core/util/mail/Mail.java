package me.zyb.framework.core.util.mail;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 邮件实体
 * @author zhangyingbin
 *
 */
public class Mail implements Serializable{
	private static final long serialVersionUID = 6271507314419877541L;
	
	public static final String CHARSET = "UTF-8";
	
	/** 发件邮箱 */
	private String from;
    /** 账号 */
    private String username;
    /** 密码 */
    private String password;
    /** 收件邮箱列表 */
    private String[] toEmails;
    /** 抄送邮箱列表 */
    private String[] copyEmails;
    /** 邮件标题 */
    private String subject;
    /** 邮件内容（支持HTML） */
    private String content;
    /** 邮件模板 */
    private String template;
    /** 模板参数 */
    private Map<String, Object> model;
    /** 附件(Spring form 表单提交的文件) */
    private MultipartFile[] attachment;
    /** 附件（普通文件） */
    private List<File> files;

	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
		
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String[] getToEmails() {
		return toEmails;
	}
	
	public void setToEmails(String[] toEmails){		
		this.toEmails = toEmails;
	}
	
	public String[] getCopyEmails() {
		return copyEmails;
	}

	public void setCopyEmails(String[] copyEmails) {
		this.copyEmails = copyEmails;
	}

	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public MultipartFile[] getAttachment() {
		return attachment;
	}

	public void setAttachment(MultipartFile[] attachment) {
		this.attachment = attachment;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
	
}
