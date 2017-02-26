package com.loozb.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.base.BaseModel;


/**
 * <p>
 * 定时器任务表
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@TableName("tb_task_timer")
public class TbTaskTimer extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 任务分组
     */
	@TableField("task_group")
	private String taskGroup;
    /**
     * 任务名称
     */
	@TableField("task_name")
	private String taskName;
    /**
     * 时间表达式
     */
	@TableField("cron_expression")
	private String cronExpression;
	private String address;
    /**
     * 1.是，0.否，默认1
     */
	@TableField("is_sync")
	private String isSync;
    /**
     * 任务描述
     */
	private String description;
	private String status;
	private String params;


	public String getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
