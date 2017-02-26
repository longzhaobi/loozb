package com.loozb.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.loozb.core.base.BaseService;
import com.loozb.core.utils.TreeUtil;
import com.loozb.model.SysResource;
import com.loozb.model.SysRoleResourcePermission;
import com.loozb.model.ext.SysResourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private SysRoleResourcePermissionService sysRoleResourcePermissionService;

    /**
     * 获取菜单，用于登录时用户去数据库查询
     *
     * @param userId
     * @return
     */
    public List<SysResourceBean> getMenus(Long userId) {
        // 1.先获取所有资源，因为考虑到资源定型后很少更新，所有查询资源时，需要走缓存
        Wrapper<SysResource> wrapper = new EntityWrapper<>();
        wrapper.eq("available", "1");
        List<SysResource> allResource = mapper.selectList(wrapper);

        List<SysResourceBean> allResourceBean = new ArrayList<SysResourceBean>();
        for (SysResource resource: allResource) {
            SysResourceBean sysResourceBean = new SysResourceBean();
            sysResourceBean.setAvailable(resource.getAvailable());
            sysResourceBean.setCtime(resource.getCtime());
            sysResourceBean.setWeight(resource.getWeight());
            sysResourceBean.setUrl(resource.getUrl());
            sysResourceBean.setHasPermission(resource.getHasPermission());
            sysResourceBean.setPids(resource.getPids());
            sysResourceBean.setName(resource.getName());
            sysResourceBean.setMtime(resource.getMtime());
            sysResourceBean.setMenuType(resource.getMenuType());
            sysResourceBean.setIdentity(resource.getIdentity());
            sysResourceBean.setIcon(resource.getIcon());
            sysResourceBean.setPid(resource.getPid());
            sysResourceBean.setId(resource.getId());
            allResourceBean.add(sysResourceBean);
        }

        // 2. 通过userId去获取当前用户拥有的资源ID，并且其拥有查看权限，考虑到权限变动，此处不走缓存，直接查询数据库
        List<SysRoleResourcePermission> srrps = sysRoleResourcePermissionService
                .findSrrpByUserId(userId);

        List<SysResourceBean> allList = null;
        try {
            allList = TreeUtil.buildListToTree(allResourceBean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. 定义资源集合，循环所有资源将有权限的资源添加到此集合中
        List<SysResourceBean> resources = new ArrayList<SysResourceBean>();
        if (allList != null) {
            packageMenuList(allList, srrps, resources);
        }

        /**
         * 通过资源的id和pid来进行父子组装
         */
        List<SysResourceBean> menuList = null;
        try {
            menuList = TreeUtil.buildListToTree(resources);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }

    private void packageMenuList(List<SysResourceBean> allList, List<SysRoleResourcePermission> srrps, List<SysResourceBean> resources) {
        for (SysResourceBean resource : allList) {
            Boolean flag = false;
            for (SysRoleResourcePermission s : srrps) {
                if (resource.getId() == s.getResourceId()) {
                    resources.add(resource);
                    flag = true;
                    break;
                }
            }
            List<SysResourceBean> children = resource.getChildren();
            if (flag && children != null && children.size() > 0) {
                // 说明有此权限并有子节点
                packageMenuList(children, srrps, resources);
            }
        }
    }

}
