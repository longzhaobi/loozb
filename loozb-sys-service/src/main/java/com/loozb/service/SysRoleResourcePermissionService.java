package com.loozb.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.loozb.core.base.BaseService;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.SysAuth;
import com.loozb.model.SysPermission;
import com.loozb.model.SysRoleResourcePermission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色资源权限表，用于描述角色拥有哪些资源和此资源对应的权限 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@Service
@CacheConfig(cacheNames = "SysRoleResourcePermission")
public class SysRoleResourcePermissionService extends BaseService<SysRoleResourcePermission> {

    @Autowired
    private SysAuthService sysAuthService;

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 通过角色ID获取权限和资源
     * @param roleId
     * @return
     */
    public List<SysRoleResourcePermission> findSrrpByRoleId(Long roleId) {
        if(roleId == null) {
            return null;
        }
        List<Long> values = new ArrayList<Long>();
        values.add(roleId);
        return findSrrpByRoleIdList(values);
    }

    private List<SysRoleResourcePermission> findSrrpByRoleIdList(List<Long> values) {
        Wrapper<SysRoleResourcePermission> wrapper = new EntityWrapper<>();
        wrapper.eq("available", "1").in("role_id", values);
        return mapper.selectList(wrapper);
    }
    /**
     * 根据userId获取拥有的资源信息
     *
     * @param userId
     * @return
     */
    public List<SysRoleResourcePermission> findSrrpByUserId(Long userId) {
        // 1.根据用户ID查出其角色
        List<SysAuth> auths = sysAuthService.queryByUserId(userId);
        // 2.将角色ID组装到List中
        List<Long> values = new ArrayList<Long>();
        for (SysAuth auth : auths) {
            values.add(auth.getRoleId());
        }

        // 3.通过角色ID去获取拥有的资源和资源对应的权限
        List<SysRoleResourcePermission> list = findSrrpByRoleIdList(values);
        List<SysRoleResourcePermission> srrps = new ArrayList<SysRoleResourcePermission>();
        for (SysRoleResourcePermission l : list) {
            String permissions = l.getPermissionIds();
            if(StringUtils.isNotBlank(permissions)) {
                String[] ids = permissions.split(",");
                for (String id : ids) {
                    if(StringUtils.isNotBlank(id)) {
                        SysPermission permission = sysPermissionService.queryById(Long.valueOf(id));
                        if(null != permission && "view".equals(permission.getPermission())) {
                            //拥有查看权限，添加到集合里
                            srrps.add(l);
                        }
                    }
                }
            }
        }
        return srrps;
    }

    public List<String> getPermissionByRoleIdAndResourceId(Long resourceId, Long roleId) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("resourceId", resourceId);
        params.put("roleId", roleId);
        params.put("available", "1");
        List<SysRoleResourcePermission> list = super.queryList(params);
        List<String> permList = new ArrayList<String>();
        if (list != null && list.size() == 1) {
            String ids = list.get(0).getPermissionIds();
            if (StringUtils.isNotBlank(ids)) {
                String[] permIds = ids.split(",");
                for (int i = 0; i < permIds.length; i++) {
                    SysPermission o = sysPermissionService.queryById(Long
                            .valueOf(permIds[i]));
                    if (o != null) {
                        permList.add(o.getPermission());
                    }
                }
            }
        }
        return permList;
    }

    public List<Long> selectIdsByResourceIdAndRoleId(Long resourceId, Long roleId) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("resourceId", resourceId);
        params.put("roleId", roleId);
        return mapper.selectIdPage(params);
    }
}
