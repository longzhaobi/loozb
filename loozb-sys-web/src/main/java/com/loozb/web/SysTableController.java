package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.core.base.Parameter;
import com.loozb.core.support.Assert;
import com.loozb.core.util.ParamUtil;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 获取数据字典信息
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
@RestController
@Api(value = "数据字典查询", description = "数据字典查询")
@RequestMapping(value = "/tables")
public class SysTableController extends AbstractController<ISysProvider> {

    @Override
    public String getService() {
        return "sysTableService";
    }

    // 查询表明列表
    @ApiOperation(value = "查询表妹列表，查询所有表名")
    @RequiresPermissions("table:view")
    @GetMapping
    public Object queryT(ModelMap modelMap,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("keyword", keyword);

        Parameter parameter = new Parameter(getService(), "selectTable").setMap(params);
        return setSuccessModelMap(modelMap, provider.execute(parameter).getList());
    }

    // 根据表名查询表信息
    @ApiOperation(value = "查询表妹列表，查询所有表名")
    @RequiresPermissions("table:view")
    @GetMapping("/column")
    public Object queryColumns(ModelMap modelMap,
                               @ApiParam(required = true, value = "表名")  @RequestParam(value = "tableName", required = true) String tableName,
                               @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        Assert.notNull(tableName, "TABLENAME");
        Map<String, Object> params = ParamUtil.getMap();
        params.put("keyword", keyword);
        params.put("tableName", tableName);
        Parameter parameter = new Parameter(getService(), "selectColumns").setMap(params);
        return setSuccessModelMap(modelMap, provider.execute(parameter).getList());
    }
}
