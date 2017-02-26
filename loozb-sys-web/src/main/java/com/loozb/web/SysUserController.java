package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.core.util.paramUtil;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return super.query(modelMap,  paramUtil.getPageParams(pages, size, keyword, orderBy));
    }
}
