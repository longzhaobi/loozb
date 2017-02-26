package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.core.base.Parameter;
import com.loozb.core.config.Resources;
import com.loozb.core.exception.LoginException;
import com.loozb.core.support.Assert;
import com.loozb.core.util.WebUtil;
import com.loozb.model.ext.Authority;
import com.loozb.model.ext.SysResourceBean;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 用户登录
 *
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 20:59
 */
@RestController
@Api(value = "登录接口", description = "登录接口")
public class LoginController extends AbstractController<ISysProvider> {
    public String getService() {
        return "sysUserService";
    }

    // 登录
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Object login(ModelMap modelMap,
                        @ApiParam(required = true, value = "登录帐号") @RequestParam(value = "account") String account,
                        @ApiParam(required = true, value = "登录密码") @RequestParam(value = "password") String password) {
        Assert.notNull(account, "ACCOUNT");
        Assert.notNull(password, "PASSWORD");
        String error = null;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account,
                password);
        try {
            token.setRememberMe(true);
            subject.login(token);
        } catch (LockedAccountException e) {
            error = "帐号已经锁定";
        } catch (UnknownAccountException e) {
            error = "用户名/密码错误";
        } catch (IncorrectCredentialsException e) {
            error = "用户名/密码错误";
        } catch (DisabledAccountException e) {
            error = "账号被禁用";
        } catch (ExcessiveAttemptsException e) {
            error = "登录失败次数过多，账号锁定一个小时";
        } catch (ExpiredCredentialsException e) {
            error = "凭证过期";
        } catch (Exception e) {
            e.printStackTrace();
            error = "其他错误：" + e.getMessage();
            throw new LoginException(Resources.getMessage("LOGIN_FAIL"), e);
        }

        if(error != null) {
            Long id = WebUtil.getCurrentUser();
            //获取角色信息
            Parameter rolesParameter = new Parameter("sysAuthService", "findRoles").setId(id);
            Set<String> roles = (Set<String>)provider.execute(rolesParameter).getSet();

            //获取权限信息
            Parameter permissionsParameter = new Parameter("sysAuthService", "findPermissions").setId(id);
            Set<String> permissions = (Set<String>)provider.execute(permissionsParameter).getSet();

            //获取资源信息
            Parameter sysResourceBeanParameter = new Parameter("sysResourceService", "getMenus").setId(id);
            List<SysResourceBean> menus = (List<SysResourceBean>)provider.execute(sysResourceBeanParameter).getList();

            return setSuccessModelMap(modelMap, new Authority(roles, permissions, menus));
        }
        throw new LoginException(error);
    }
}
