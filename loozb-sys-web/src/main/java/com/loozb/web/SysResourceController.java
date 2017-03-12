package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.core.base.Parameter;
import com.loozb.core.support.Assert;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.SysResource;
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
 * 资源管理信息表 前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@RestController
@Api(value = "资源管理", description = "资源管理")
@RequestMapping(value = "/resources")
public class SysResourceController extends AbstractController<ISysProvider> {

    @Override
    public String getService() {
        return "sysResourceService";
    }

    @ApiOperation(value = "查询资源列表")
    @RequiresPermissions("resource:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("keyword", keyword);
        params.put("available", "1");
        return super.queryList(modelMap, params);
    }

    /**
     * 创建创建资源
     * @param modelMap
     * @param param
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建资源信息")
    @RequiresPermissions("resource:create")
    public Object create(ModelMap modelMap, SysResource param) {
        return super.update(modelMap, param);
    }

    /**
     * 更新资源
     * @param modelMap
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation(value = "更新资源信息")
    @RequiresPermissions("resource:update")
    public Object update(ModelMap modelMap, SysResource param) {
        Assert.notNull(param, "RESOURCE");
        Assert.notNull(param.getId(), "ID");
        Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
        SysResource user = (SysResource)provider.execute(parameter).getModel();
        Assert.notNull(user, "RESOURCE", param.getId());
        return super.update(modelMap, param);
    }

    /**
     * 删除资源
     * @param modelMap
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "更新资源信息")
    @RequiresPermissions("resource:remove")
    public Object remove(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);
    }

    @ApiOperation(value = "根据角色ID获取资源权限")
    @RequiresPermissions("resource:view")
    @GetMapping("/auth")
    public Object auth(ModelMap modelMap,
                        @ApiParam(required = true, value = "资源父ID") @RequestParam("pid") Long pid,
                        @ApiParam(required = true, value = "角色ID") @RequestParam("roleId") Long roleId) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("pid", pid);
        params.put("roleId", roleId);
        params.put("available", "1");
        Parameter parameter = new Parameter(getService(), "queryResourceByPidAndRoleId").setMap(params);
        List<?> list = provider.execute(parameter).getList();
        return setSuccessModelMap(modelMap, list);
    }
}
