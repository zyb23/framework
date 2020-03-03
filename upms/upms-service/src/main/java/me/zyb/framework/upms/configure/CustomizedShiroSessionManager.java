package me.zyb.framework.upms.configure;

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
public class CustomizedShiroSessionManager extends DefaultWebSessionManager {
	private static final String AUTHORIZATION = "Authorization";

	private static final String COOKIE = "Cookie";

	public CustomizedShiroSessionManager() {
		super();
	}

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
		//如果请求头中有 Authorization 则其值为sessionId
		if (StringUtils.isNotEmpty(sessionId)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, COOKIE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return sessionId;
		} else {
			//否则按默认规则从cookie取sessionId
			return super.getSessionId(request, response);
		}
	}
}
