package me.zyb.framework.core.util.mail;

import lombok.Data;
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
@Data
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
    /** 是否是HTML格式邮件 */
    private Boolean isHtml = false;
    /** 是否邮件模板(thymeleaf HTML模板) */
    private Boolean useTemplate = false;
    /** 模板名称/路径 */
    private String templatePath;
    /** HTML模板参数 */
    private Map<String, Object> variables;
    /** 附件(Spring form 表单提交的文件) */
    private MultipartFile[] attachment;
    /** 附件（普通文件） */
    private List<File> files;
	
}
