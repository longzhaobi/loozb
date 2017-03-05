package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.core.base.Parameter;
import com.loozb.core.support.Assert;
import com.loozb.core.util.ParamUtil;
import com.loozb.core.utils.PasswordUtil;
import com.loozb.model.SysUser;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
@RestController
@Api(value = "用户管理", description = "用户管理")
@RequestMapping(value = "/users")
public class SysUserController extends AbstractController<ISysProvider> {

    @Override
    public String getService() {
        return "sysUserService";
    }

    // 查询用户列表
    @ApiOperation(value = "查询用户列表，默认查询20条")
    @RequiresPermissions("user:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "pages") String pages,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(defaultValue = "", value = "keyword") String keyword) {
        return super.query(modelMap,  ParamUtil.getPageParams(pages, size, keyword, orderBy));
    }

    /**
     * 创建创建用户
     * @param modelMap
     * @param param
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建用户信息")
    @RequiresPermissions("user:create")
    public Object create(ModelMap modelMap, SysUser param) {
        Assert.idCard(param.getIdcard());
        PasswordUtil.encryptPassword(param);
        return super.update(modelMap, param);
    }

    /**
     * 更新用户
     * @param modelMap
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation(value = "更新用户信息")
    @RequiresPermissions("user:update")
    public Object update(ModelMap modelMap, SysUser param) {
        Assert.notNull(param, "USER");
        Assert.notNull(param.getId(), "ID");
        Assert.idCard(param.getIdcard());
        Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
        SysUser user = (SysUser)provider.execute(parameter).getModel();
        Assert.notNull(user, "USER", param.getId());
        if(StringUtils.isNotBlank(param.getPassword())) {
            if (!param.getPassword().equals(user.getPassword())) {
                PasswordUtil.encryptPassword(param);
            }
        }
        return super.update(modelMap, param);
    }

    /**
     * 删除用户
     * @param modelMap
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "更新用户信息")
    @RequiresPermissions("user:remove")
    public Object remove(ModelMap modelMap, @PathVariable Long id) {
        return super.delete(modelMap, id);
    }

}
