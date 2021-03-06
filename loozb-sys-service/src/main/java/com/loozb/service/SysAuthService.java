package com.loozb.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.loozb.core.base.BaseService;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Map<String, Object> params = ParamUtil.getMap();
        params.put("userId", userId);
        return super.queryList(params);
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

    public void allot(Map<String, Object> params) {
        Long userId = (Long) params.get("id");
        String roleIds = (String) params.get("roleIds");

        //在给用户授权之前，需要删除目前拥有的所有权限,物理删除
        List<SysAuth> list = mapper.selectList(new EntityWrapper<SysAuth>().eq("user_id", userId));
        for (SysAuth auth: list) {
            super.delete(auth.getId());
        }

        if(StringUtils.isNotBlank(roleIds)) {
            String[] roles = roleIds.split(",");
            for (String roleId : roles) {
                SysAuth auth = new SysAuth();
                auth.setUserId(userId);
                auth.setRoleId(Long.valueOf(roleId));
                super.update(auth);
            }
        }

    }

    /**
     * 根据角色信息获取权限信息
     * @param roleId
     * @return
     */
    public List<SysAuth> queryByRoleId(Long roleId) {
        if(roleId == null) {
            return null;
        }
        Map<String, Object> params = ParamUtil.getMap();
        params.put("roleId", roleId);
        return super.queryList(params);
    }
}
