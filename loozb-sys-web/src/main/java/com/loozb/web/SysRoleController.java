package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.core.base.Parameter;
import com.loozb.core.support.Assert;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.SysRole;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "查询角色列表，默认查询20条")
    @RequiresPermissions("role:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") String current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return super.query(modelMap,  ParamUtil.getPageParams(current, size, keyword, orderBy));
    }

    /**
     * 创建角色
     * @param modelMap
     * @param param
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建角色信息")
    @RequiresPermissions("role:create")
    public Object create(ModelMap modelMap, SysRole param) {
        return super.update(modelMap, param);
    }

    /**
     * 更新用户
     * @param modelMap
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation(value = "更新角色信息")
    @RequiresPermissions("role:update")
    public Object update(ModelMap modelMap, SysRole param) {
        Assert.notNull(param, "ROLE");
        Assert.notNull(param.getId(), "ID");
        Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
        SysRole user = (SysRole)provider.execute(parameter).getModel();
        Assert.notNull(user, "ROLE", param.getId());
        return super.update(modelMap, param);
    }

    /**
     * 授权
     * @param modelMap
     * @param roleId 角色ID
     * @param pid 资源ID
     * @param auths 权限信息，用=隔开
     * @return
     */
    @PostMapping("/auth")
    @ApiOperation(value = "角色授权")
    @RequiresPermissions("role:allot")
    public Object doAuth(ModelMap modelMap, String roleId, String pid, String auths) {
        Assert.notNull(roleId, "ROLEID");
        Assert.notNull(pid, "PID");
        Map<String, Object> params = ParamUtil.getMap();
        params.put("roleId", roleId);
        params.put("pid", pid);
        params.put("auths", auths);
        Parameter parameter = new Parameter(getService(), "doAuth").setMap(params);
        provider.execute(parameter);
        return setSuccessModelMap(modelMap);
    }

    /**
     * 删除角色
     * @param modelMap
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "逻辑删除角色信息")
    @RequiresPermissions("role:remove")
    public Object remove(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);
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
