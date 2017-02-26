package com.loozb.core.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.loozb.core.base.BaseProvider;
import com.loozb.core.base.Parameter;
import com.loozb.core.util.WebUtil;
import com.loozb.core.utils.PasswordUtil;
import com.loozb.model.SysSession;
import com.loozb.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录权限管理
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 19:25
 */
public class Realm extends AuthorizingRealm {
    private final Logger logger = LogManager.getLogger();
    @Autowired
    @Qualifier("sysProvider")
    protected BaseProvider provider;
    @Autowired
    private RedisOperationsSessionRepository sessionRepository;

    // 权限
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        Long userId = WebUtil.getCurrentUser();
//        Parameter parameter = new Parameter("sysAuthorizeService", "queryPermissionByUserId").setId(userId);
//        List<?> list = provider.execute(parameter).getList();
//        for (Object permission : list) {
//            if (StringUtils.isNotBlank((String) permission)) {
//                // 添加基于Permission的权限信息
//                info.addStringPermission((String) permission);
//            }
//        }
        // 添加用户权限
        info.addStringPermission("user");
        return info;
    }

    // 登录验证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("available", "1");
        params.put("account", token.getUsername());
        Parameter parameter = new Parameter("sysUserService", "query").setMap(params);
        Page<?> pageInfo = provider.execute(parameter).getPage();
        if (pageInfo.getTotal() == 1) {
            SysUser user = (SysUser) pageInfo.getRecords().get(0);
            if(user == null) {
                throw new UnknownAccountException();//没找到帐号
            }

            if("1".equals(user.getLocked())) {
                throw new LockedAccountException(); //帐号锁定
            }

            String password = String.valueOf(token.getPassword());

            if(user.getPassword().equals(PasswordUtil.decryptPassword(password, user.getSalt()))) {
                WebUtil.saveCurrentUser(user.getId());
                saveSession(user.getUsername());
                //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
                AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                        user.getUsername(), //用户名
                        user.getPassword(),
                        user.getUsername() //realm name
                );
                return authenticationInfo;
            }
            logger.warn("USER [{}] PASSWORD IS WRONG: {}", token.getUsername(), password);
            return null;
        } else {
            logger.warn("No user: {}", token.getUsername());
            return null;
        }
    }

    /** 保存session */
    private void saveSession(String account) {
        // 踢出用户
        SysSession record = new SysSession();
        record.setAccount(account);
        Parameter parameter = new Parameter("sysSessionService", "querySessionIdByAccount").setModel(record);
        List<?> sessionIds = provider.execute(parameter).getList();
        if (sessionIds != null) {
            for (Object sessionId : sessionIds) {
                record.setSessionId((String) sessionId);
                parameter = new Parameter("sysSessionService", "deleteBySessionId").setModel(record);
                provider.execute(parameter);
                sessionRepository.delete((String) sessionId);
                sessionRepository.cleanupExpiredSessions();
            }
        }
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        record.setSessionId(session.getId().toString());
        String host = (String) session.getAttribute("HOST");
        record.setIp(StringUtils.isBlank(host) ? session.getHost() : host);
        record.setStartTime(session.getStartTimestamp());
        parameter = new Parameter("sysSessionService", "update").setModel(record);
        provider.execute(parameter);
    }
}
