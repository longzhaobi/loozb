package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户角色信息表 前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@RestController
@Api(value = "角色管理", description = "角色管理")
@RequestMapping(value = "/roles")
public class SysRoleController extends AbstractController<ISysProvider> {

    @Override
    public String getService() {
        return "sysRoleService";
    }

    /**
     * 获取所有角色信息
     * @param modelMap
     * @return
     */
    @GetMapping("all")
    @ApiOperation(value = "获取所有角色信息")
    @RequiresPermissions("role:view")
    public Object fetchRoles(ModelMap modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("available", "1");
        return super.queryList(modelMap,params);
    }
}
