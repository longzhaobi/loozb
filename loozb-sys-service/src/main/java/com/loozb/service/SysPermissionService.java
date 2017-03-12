package com.loozb.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.loozb.core.base.BaseService;
import com.loozb.model.SysPermission;
import com.loozb.model.ext.Columns;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@Service
@CacheConfig(cacheNames = "SysPermission")
public class SysPermissionService extends BaseService<SysPermission> {

    public List<SysPermission> findPermissionByIds(String permissionIds) {
        if(StringUtils.isBlank(permissionIds)) {
            return null;
        }
        List<Long> values = new ArrayList<Long>();
        String[] strs = permissionIds.split(",");
        for (String str : strs) {
            values.add(Long.valueOf(str));
        }
        Wrapper<SysPermission> wrapper = new EntityWrapper<>();
        wrapper.in("id", values).eq("available", "1");
        return mapper.selectList(wrapper);
    }

    public List<Columns> queryColumns(Map<String, Object> params) {
        List<SysPermission> list = super.queryList(params);
        List<Columns> columns = new ArrayList<Columns>();
        Columns page = new Columns();
        page.setDataIndex("pageName");
        page.setTitle("页面名称");
        columns.add(page);
        for (SysPermission perm : list) {
            Columns c = new Columns();
            c.setDataIndex(perm.getPermission());
            c.setTitle(perm.getName());
            c.setKey(perm.getId().toString());
            columns.add(c);
        }
        return columns;
    }
}
