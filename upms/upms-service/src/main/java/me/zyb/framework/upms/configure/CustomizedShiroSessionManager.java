package me.zyb.framework.upms.configure;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.constant.ConstString;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自己定义session管理
 * @author zhangyingbin
 */
@Slf4j
public class CustomizedShiroSessionManager extends DefaultWebSessionManager {

	public CustomizedShiroSessionManager() {
		super();
	}

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String sessionId = WebUtils.toHttp(request).getHeader(ConstString.KEY_TOKEN);
		//如果请求头中有 Token 则其值为sessionId
		if (StringUtils.isNotEmpty(sessionId)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ConstString.KEY_SESSION);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return sessionId;
		} else {
			//否则按默认规则从cookie取sessionId
			return super.getSessionId(request, response);
		}
	}
}
