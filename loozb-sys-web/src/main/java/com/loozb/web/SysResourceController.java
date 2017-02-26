package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "/resource")
public class SysResourceController extends AbstractController<ISysProvider> {

    @Override
    public String getService() {
        return null;
    }
}
