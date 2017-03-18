package com.loozb.core.shiro.filter;

import com.loozb.core.util.WebUtil;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 拦截获取当前用户信息，将其方法request请求体中，放在验证登陆拦截器后面
 * @author LONGZB
 *
 */
public class CurrentUserFilter extends PathMatchingFilter {

	@Override
	protected boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		String accessToken = WebUtil.getCookieValue((HttpServletRequest)request, "access_token", null);
		return true;
	}
}
