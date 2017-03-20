package com.loozb.core.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 支持跨域调用
 * 因为采用代理模式，所以此程序不再使用
 * @author LONGZB
 *
 */
public class SimpleCORSFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
	    HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers","x-requested-with,Access-Control-Allow-Origin,Access-Token");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            chain.doFilter(req, res);

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
