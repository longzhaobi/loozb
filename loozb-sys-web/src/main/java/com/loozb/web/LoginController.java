package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
                        HttpServletRequest request) {
        return setSuccessModelMap(modelMap);
    }
}
