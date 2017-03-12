package com.loozb.service;

import com.loozb.core.base.BaseService;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.SysResource;
import com.loozb.model.SysRole;
import com.loozb.model.SysRoleResourcePermission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色信息表 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@Service
@CacheConfig(cacheNames = "SysRole")
public class SysRoleService extends BaseService<SysRole> {

    @Autowired
    private SysAuthService sysAuthService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysRoleResourcePermissionService sysRoleResourcePermissionService;

    public void doAuth(Map<String, Object> params) {
        String auths = (String)params.get("auths");
        String pid = (String)params.get("pid");
        String roleId = (String)params.get("roleId");
        if(StringUtils.isNotBlank(auths) && auths.indexOf("=") == -1) {
            throw new IllegalArgumentException("权限信息格式非法，应为1=2,1=3格式");
        }
//        List<SysAuth> list = sysAuthService.queryByRoleId(Long.valueOf(roleId));

        // 1.先通过PID找出其资源信息，循环删除关联的权限信息
        Map<String, Object> p1 = ParamUtil.getMap();
        p1.put("pid", Long.valueOf(pid));
        List<SysResource> resources = sysResourceService.queryListByAuth(p1);
        // 2.循环删除选择的资源PID的孩子权限
        for (SysResource resource : resources) {
            List<Long> ids = sysRoleResourcePermissionService.selectIdsByResourceIdAndRoleId(resource.getId(), Long.valueOf(roleId));
            for (int i = 0; i < ids.size(); i++) {
                sysRoleResourcePermissionService.delete(ids.get(i));
            }
        }
        // 3.删除自身权限
//        List<Long> ids = sysRoleResourcePermissionService.selectIdsByResourceIdAndRoleId(Long.valueOf(pid), Long.valueOf(roleId));
//        for (int i = 0; i < ids.size(); i++) {
//            sysRoleResourcePermissionService.del(ids.get(i));
//        }
         //4.重新根据传入的信息授权-----------传入的auths格式为：1=3,1=4...，其中等号左边为资源编码，等号右边为权限编码
         //4.1 先根据逗号分割
        if(StringUtils.isNotBlank(auths)) {
            String[] kvs = auths.split("·");
            Map<Long, String> map= new HashMap<Long, String>(); // 最终要的结果
            for (String kv : kvs) {
                //分割后得到1=2类似的键值对，再通过等号分过分别获取资源和权限
                String[] rps = kv.split("=");
                Long resourceId = Long.valueOf(rps[0]);
                String permissionId = rps[1];
                if(map.containsKey(resourceId)) {
                    //假如包含了这个key，则从map里取出其值进行赋值
                    map.put(resourceId, map.get(resourceId) + "," +permissionId);
                } else {
                    map.put(resourceId, permissionId);
                }
            }

            for (Map.Entry<Long, String> entry : map.entrySet()) {
                SysRoleResourcePermission srrp = new SysRoleResourcePermission();
                srrp.setRoleId(Long.valueOf(roleId));
                srrp.setResourceId(entry.getKey());
                srrp.setPermissionIds(entry.getValue());
                srrp.setCtime(new Date());
                sysRoleResourcePermissionService.update(srrp);
            }
        }
    }
}
