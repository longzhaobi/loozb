package com.loozb.core.shiro.filter;

import com.loozb.core.shiro.token.StatelessToken;
import com.loozb.core.util.CacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessAuthcFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest,
			ServletResponse servletResponse) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String accessToken = request.getParameter("access_token");
		// 将如从token中取不到token
		if (StringUtils.isBlank(accessToken)) {
			return false;
		}
		String result = (String)CacheUtil.getCache().get(accessToken);
		if(StringUtils.isBlank(result)) {
			onLoginFail(response);
			return false;
		}
		StatelessToken token = new StatelessToken(accessToken);
		try {
			getSubject(servletRequest, servletResponse).login(token);
		} catch (Exception e) {
			e.printStackTrace();
			onLoginFail(response); // 6、登录失败
			return false;
		}
		return true;
	}

	/**
	 * 登录失败
	 * @param response
	 */
	private void onLoginFail(HttpServletResponse response) {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);//402
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().write("验证失效，请重新登录");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
