package com.loozb.core.support.weixin;

import com.loozb.core.support.weixin.company.WeiXinCompanyOAuth;
import com.loozb.core.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Promission {

	protected Logger logger = Logger.getLogger(getClass());

	/** 检查手机访问权限 */
	public static boolean mobileCheck(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		Object user = request.getSession().getAttribute("employee");
		String code = request.getParameter("code");// code
		String userid = "";
		if (user == null) { // 手机用 start
			Object o = request.getSession().getAttribute("userid");
			if (o == null) {
				if (code == null) {
					userid = request.getParameter("userid");
				} else {
					userid = WeiXinCompanyOAuth.getUserInfo(code, PropertiesUtil.getInt("AGENTID_GONGGAO"));
				}
			} else {
				userid = o.toString();
			}
			if (userid == null) {
				// 获取userid失败;
				return false;
			}
			// 手机用 end
		}
		modelMap.put("userid", userid);
		modelMap.put("code", code);
		return true;
	}
}
