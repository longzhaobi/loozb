package com.loozb.service;

import com.loozb.core.base.BaseService;
import com.loozb.core.util.ParamUtil;
import com.loozb.core.utils.TreeUtil;
import com.loozb.mapper.SysResourceMapper;
import com.loozb.model.SysPermission;
import com.loozb.model.SysResource;
import com.loozb.model.SysRoleResourcePermission;
import com.loozb.model.ext.AuthList;
import com.loozb.model.ext.Columns;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源管理信息表 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@Service
@CacheConfig(cacheNames = "SysResource")
public class SysResourceService extends BaseService<SysResource> {

    @Autowired(required = false)
    private SysResourceMapper sysResourceMapper;

    @Autowired
    private SysPermissionService sysPermissionService;


    @Autowired
    private SysRoleResourcePermissionService sysRoleResourcePermissionService;

    @Override
    public List<SysResource> queryList(Map<String, Object> params) {
        List<SysResource> list = super.queryList(params);

        List<SysResource> resources = new ArrayList<SysResource>();
        for (SysResource resource : list) {
            // 通过权限集合去获取权限名称
            List<SysPermission> permissions = sysPermissionService
                    .findPermissionByIds(resource.getHasPermission());
            String text = "";
            if (permissions != null && permissions.size() > 0) {
                for (int i = 0; i < permissions.size(); i++) {
                    if (i == 0) {
                        text += permissions.get(i).getName();
                    } else {
                        text += "," + permissions.get(i).getName();
                    }
                }
            }
            resource.setPermissionText(text);
            resources.add(resource);
        }
        /**
         * 通过资源的id和pid来进行父子组装
         */
        List<SysResource> treeList = null;
        try {
            treeList = TreeUtil.buildListToTree(resources);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return treeList;
    }

    /**
     * 获取菜单，用于登录时用户去数据库查询
     *
     * @param userId
     * @return
     */
    public List<SysResource> getMenus(Long userId) {
        // 1.先获取所有资源，因为考虑到资源定型后很少更新，所有查询资源时，需要走缓存
        Map<String, Object> params = ParamUtil.getMap();
        params.put("available", "1");
        List<SysResource> allResource = super.queryList(params);

        // 2. 通过userId去获取当前用户拥有的资源ID，并且其拥有查看权限，考虑到权限变动，此处不走缓存，直接查询数据库
        List<SysRoleResourcePermission> srrps = sysRoleResourcePermissionService
                .findSrrpByUserId(userId);

        List<SysResource> allList = null;
        try {
            allList = TreeUtil.buildListToTree(allResource);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. 定义资源集合，循环所有资源将有权限的资源添加到此集合中
        List<SysResource> resources = new ArrayList<SysResource>();
        if (allList != null) {
            packageMenuList(allList, srrps, resources);
        }

        /**
         * 通过资源的id和pid来进行父子组装
         */
        List<SysResource> menuList = null;
        try {
            menuList = TreeUtil.buildListToTree(resources);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }

    private void packageMenuList(List<SysResource> allList, List<SysRoleResourcePermission> srrps, List<SysResource> resources) {
        for (SysResource resource : allList) {
            Boolean flag = false;
            for (SysRoleResourcePermission s : srrps) {
                if (resource.getId().equals(s.getResourceId())) {
                    resources.add(resource);
                    flag = true;
                    break;
                }
            }
            List<SysResource> children = resource.getChildren();
            if (flag && children != null && children.size() > 0) {
                // 说明有此权限并有子节点
                packageMenuList(children, srrps, resources);
            }
        }
    }

    public List<AuthList> queryResourceByPidAndRoleId(Map<String, Object> params) {

        Long roleId = (Long)params.get("roleId");
        List<Long> ids = sysResourceMapper.selectPidPage(params);
        List<SysResource> list = super.getList(ids);

        List<AuthList> kvs = new ArrayList<AuthList>();

        for (SysResource resource : list) {
            AuthList kv = new AuthList();
            kv.setKey(resource.getId().toString());
            kv.setName("pageName");
            kv.setIsAuth(resource.getName());
            kv.setDisable("no");

            // 获取所有资源
            Map<String, Object> colParams = ParamUtil.getMap();
            params.put("available", "1");
            List<Columns> cls = sysPermissionService.queryColumns(colParams);
            // 通过角色和资源去获取权限标识
            List<String> permission = sysRoleResourcePermissionService
                    .getPermissionByRoleIdAndResourceId(resource.getId(),
                            roleId);
            // 通过资源获取资源拥有的权限，用于禁掉没有权限的按钮
            List<String> hasPerm = getHasPermissionById(resource.getId());
            // 循环组装资源

            List<AuthList> children = new ArrayList<AuthList>();

            for (Columns c : cls) {
                if (!c.getDataIndex().equals("pageName")) {
                    AuthList kvss = new AuthList();
                    kvss.setName(c.getDataIndex());
                    kvss.setValue(resource.getId() + "=" + c.getKey());
                    // 先设成全部无权限
                    kvss.setIsAuth("no");
                    // 先全部禁掉
                    kvss.setDisable("yes");

                    // 通过角色和资源和权限标识c.getDataIndex()去判断其是否有权限，如果有设为yes，否则设为no
                    for (String perm : permission) {
                        if (perm.equals(c.getDataIndex())) {
                            // 拥有的权限，设为yes
                            kvss.setIsAuth("yes");
                        }
                    }

                    // 判断此权限是否是其资源拥有
                    for (String perm : hasPerm) {
                        if (perm.equals(c.getDataIndex())) {
                            kvss.setDisable("no");
                        }
                    }
                    children.add(kvss);
                }

            }
            kv.setChildren(children);
            kvs.add(kv);
        }
        return kvs;
    }

    /**
     * 通过资源id获取资源拥有的权限
     *
     * @param id
     * @return
     */
    private List<String> getHasPermissionById(Long id) {
        SysResource resource = super.queryById(id);
        List<String> list = new ArrayList<String>();
        if (resource != null) {
            String hasPermissions = resource.getHasPermission();
            if (StringUtils.isNotBlank(hasPermissions)) {
                String[] ids = hasPermissions.split(",");
                for (int i = 0; i < ids.length; i++) {
                    SysPermission permission = sysPermissionService.queryById(Long
                            .valueOf(ids[i]));
                    if (permission != null) {
                        list.add(permission.getPermission());
                    }
                }
            }
        }
        return list;
    }

    public List<SysResource> queryListByAuth(Map<String, Object> p1) {
        return super.queryList(p1);
    }
}
