package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.model.SysUser;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
@RestController
@Api(value = "用户管理", description = "用户管理")
//@RequestMapping(value = "/user")
public class SysUserController extends AbstractController<ISysProvider> {

    @Override
    public String getService() {
        return "sysUserService";
    }

    // 查询用户
    @ApiOperation(value = "查询用户")
//    @RequiresPermissions("sys.base.user.read")
    @GetMapping(value = "login1")
    public Object query(ModelMap modelMap) {
        return super.get(modelMap, new SysUser());
    }
}
