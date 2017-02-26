package com.loozb.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.base.BaseModel;


/**
 * <p>
 * 角色资源权限表，用于描述角色拥有哪些资源和此资源对应的权限
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@TableName("sys_role_resource_permission")
public class SysRoleResourcePermission extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编码
     */
	@TableField("role_id")
	private Long roleId;
    /**
     * 资源编码
     */
	@TableField("resource_id")
	private Long resourceId;
    /**
     * 权限集合
     */
	@TableField("permission_ids")
	private String permissionIds;


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

}
