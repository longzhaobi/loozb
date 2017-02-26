package com.loozb.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.base.BaseModel;


/**
 * <p>
 * 资源管理信息表
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@TableName("sys_resource")
public class SysResource extends BaseModel {

    private static final long serialVersionUID = 1L;

	private Long pid;
    /**
     * 父子链
     */
	private String pids;
    /**
     * 资源名称
     */
	private String name;
    /**
     * 资源标识
     */
	private String identity;
    /**
     * 资源图片
     */
	private String icon;
    /**
     * 资源地址
     */
	private String url;
    /**
     * 排序编码
     */
	private String weight;
    /**
     * 菜单类型-1.父子类型，2.分组类型，3.普通类型
     */
	@TableField("menu_type")
	private String menuType;
    /**
     * 拥有权限
     */
	@TableField("has_permission")
	private String hasPermission;


	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getHasPermission() {
		return hasPermission;
	}

	public void setHasPermission(String hasPermission) {
		this.hasPermission = hasPermission;
	}

}
