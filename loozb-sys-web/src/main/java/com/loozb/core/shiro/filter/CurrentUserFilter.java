package com.loozb.core.shiro.filter;

import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 拦截获取当前用户信息，将其方法request请求体中，放在验证登陆拦截器后面
 * @author LONGZB
 *
 */
public class CurrentUserFilter extends PathMatchingFilter {
//	@Autowired
//	private UserService userService;

	@Override
	protected boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
//		String accessToken = CookieUtils.getCookieValue((HttpServletRequest)request, "access_token");
//		SysUser user = (SysUser)userService.getUserByToken(accessToken).getData();
//		request.setAttribute(Constants.CURRENT_USER, user);
		return true;
	}
}
