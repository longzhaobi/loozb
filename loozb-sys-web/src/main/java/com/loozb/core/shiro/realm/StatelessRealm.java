package com.loozb.core.shiro.realm;

import com.loozb.core.base.BaseProvider;
import com.loozb.core.base.Parameter;
import com.loozb.core.shiro.token.StatelessToken;
import com.loozb.core.util.CacheUtil;
import com.loozb.core.util.JsonUtil;
import com.loozb.core.util.WebUtil;
import com.loozb.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.awt.SystemColor.info;


/**
 * 此信息用来登录
 *
 */
public class StatelessRealm extends AuthorizingRealm {
	
	private static final Logger log = Logger.getLogger(StatelessRealm.class);

	@Autowired
	@Qualifier("sysProvider")
	protected BaseProvider provider;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof StatelessToken;// 表示此Realm只支持OAuth2Token类型
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Long userId = WebUtil.getCurrentUser();
		Parameter rolesParameter = new Parameter("sysAuthService", "findRoles").setId(userId);
		Set<String> roles = (Set<String>)provider.execute(rolesParameter).getSet();

		//获取权限信息
		Parameter permissionsParameter = new Parameter("sysAuthService", "findPermissions").setId(userId);
		Set<String> permissions = (Set<String>)provider.execute(permissionsParameter).getSet();
		// 添加用户权限
		authorizationInfo.setRoles(roles);
		authorizationInfo.setStringPermissions(permissions);
		
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		StatelessToken statelesstoken = (StatelessToken) token;
		String accessToken = statelesstoken.getAccessToken();
		// 执行登录
		String accounts = extractUsername(accessToken);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				accounts, accessToken, getName());
		return authenticationInfo;
	}

	private String extractUsername(String accessToken) {
		String result = (String) CacheUtil.getCache().get(accessToken);
		if(StringUtils.isNotBlank(result)) {
			SysUser user = JsonUtil.jsonToPojo(result, SysUser.class);
			if(user != null) {
				return user.getUsername();
			}
		}
		return null;
	}

}
