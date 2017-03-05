package com.loozb.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.base.BaseModel;


/**
 * <p>
 * 
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
@TableName("sys_user")
public class SysUser extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 机构编码
     */
	@TableField("organ_id")
	private Long organId;
    /**
     * 用户邮箱
     */
	private String email;
    /**
     * 用户名
     */
	private String username;
    /**
     * 联系电话
     */
	private String phone;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 盐
     */
	private String salt;
    /**
     * 用户密码
     */
	private String password;
    /**
     * 职位
     */
	private String job;
    /**
     * 是否锁住
     */
	private String locked;
	private String gender;
	private String idcard;
	private String birthday;

	@TableField(exist = false)
	private String roleIds;
	@TableField(exist = false)
	private String roleNames;


	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
}
