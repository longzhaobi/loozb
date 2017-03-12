package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.core.base.Parameter;
import com.loozb.core.support.Assert;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.SysPermission;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@RestController
@Api(value = "权限管理", description = "权限管理")
@RequestMapping(value = "/permissions")
public class SysPermissionController extends AbstractController<ISysProvider> {

    @Override
    public String getService() {
        return "sysPermissionService";
    }


    // 查询权限列表
    @ApiOperation(value = "查询权限列表，默认查询20条")
    @RequiresPermissions("permission:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") String current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return super.query(modelMap,  ParamUtil.getPageParams(current, size, keyword, orderBy));
    }

    // 添加资源时，初始化权限信息
    @ApiOperation(value = "初始化权限信息")
    @RequiresPermissions("permission:view")
    @GetMapping("/init")
    public Object init(ModelMap modelMap) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("available", "1");
        return super.query(modelMap, params);
    }

    /**
     * 创建创建权限
     * @param modelMap
     * @param param
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建权限信息")
    @RequiresPermissions("permission:create")
    public Object create(ModelMap modelMap, SysPermission param) {
        return super.update(modelMap, param);
    }

    /**
     * 更新权限
     * @param modelMap
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation(value = "更新权限信息")
    @RequiresPermissions("permission:update")
    public Object update(ModelMap modelMap, SysPermission param) {
        Assert.notNull(param, "permission");
        Assert.notNull(param.getId(), "ID");
        Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
        SysPermission permission = (SysPermission)provider.execute(parameter).getModel();
        Assert.notNull(permission, "permission", param.getId());
        return super.update(modelMap, param);
    }

    /**
     * 删除权限
     * @param modelMap
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "更新权限信息")
    @RequiresPermissions("permission:remove")
    public Object remove(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);
    }


    @ApiOperation(value = "查询权限列表")
    @RequiresPermissions("permission:view")
    @GetMapping("/columns")
    public Object columns(ModelMap modelMap) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("available", "1");
        Parameter parameter = new Parameter(getService(), "queryColumns").setMap(params);
        List<?> list = provider.execute(parameter).getList();
        return setSuccessModelMap(modelMap, list);
    }
}
