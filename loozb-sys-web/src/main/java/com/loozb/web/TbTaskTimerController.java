package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.provider.ISysProvider;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 定时器任务表 前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@RestController
@Api(value = "定时任务管理", description = "定时任务管理")
@RequestMapping(value = "/taskTimer")
public class TbTaskTimerController extends AbstractController<ISysProvider> {

    @Override
    public String getService() {
        return null;
    }
}
