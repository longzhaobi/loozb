package com.loozb.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.loozb.core.base.BaseService;
import com.loozb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限信息表 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@Service
@CacheConfig(cacheNames = "SysAuth")
public class SysAuthService extends BaseService<SysAuth> {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysRoleResourcePermissionService sysRoleResourcePermissionService;

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 通过用户ID获取角色集合
     * @param userId
     * @return
     */
    public Set<String> findRoles(Long userId) {
        if(userId == null) {
            return Collections.emptySet();
        }
        Set<String> sets = new HashSet<String>();
        // 获取用户角色关联表
        List<SysAuth> authList = queryByUserId(userId);
        for (SysAuth auth: authList ) {
            SysRole role = sysRoleService.queryById(auth.getRoleId());
            if(role != null) {
                sets.add(role.getRole());
            }
        }
        return sets;
    }

    /**
     * 通过用户ID获取用户权限信息
     * @param userId
     * @return
     */
    public List<SysAuth> queryByUserId(Long userId) {
        if(userId == null) {
            return null;
        }
        Wrapper<SysAuth> wrapper = new EntityWrapper<>();
        wrapper.eq("available", "1").eq("user_id", userId);
        return mapper.selectList(wrapper);
    }

    /**
     * 根据当前用户ID查找权限列表
     *
     * @param userId
     * @return
     */
    public Set<String> findPermissions(Long userId) {
        if (userId == null) {
            return Collections.emptySet();
        }
        Set<String> sets = new HashSet<String>();
        //获取用户角色关联表得到用户角色信息
        List<SysAuth> authList = queryByUserId(userId);
        for (SysAuth auth: authList ) {
            //通过角色获取资源和对应的权限信息，多个权限以逗号分割
            List<SysRoleResourcePermission> srrsp = sysRoleResourcePermissionService.findSrrpByRoleId(auth.getRoleId());
            for (SysRoleResourcePermission srr : srrsp) {
                //第一步，先根据资源ID去获取资源实体
                SysResource resource = sysResourceService.queryById(srr.getResourceId());
                //第二部，获得资源实体后，再根据权限ID去获取权限集合，然后组装
                List<SysPermission> permissions = null;
                if(srr.getPermissionIds() != null) {
                    permissions = sysPermissionService.findPermissionByIds(srr.getPermissionIds());
                }
                if(null != permissions && resource != null) {
                    //第三部，然后循环组装权限信息（格式:role:create）
                    for (SysPermission per : permissions) {
                        sets.add(resource.getIdentity() + ":" + per.getPermission());
                    }
                }
            }
        }
        return sets;
    }
}
