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
 * @since 2017-02-26
 */
@TableName("sys_log")
public class SysLog extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 请求地址
     */
	private String url;
    /**
     * 用户IP
     */
	private String host;
	@TableField("identity_id")
	private Long identityId;
    /**
     * 用户编码
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 用户名
     */
	private String username;
    /**
     * 访问状态
     */
	private String status;
    /**
     * 反馈信息
     */
	private String msg;
    /**
     * 操作表名
     */
	@TableField("table_name")
	private String tableName;
    /**
     * 日志描述
     */
	private String description;
    /**
     * 请求方式
     */
	private String method;
    /**
     * 操作模块
     */
	private String module;
    /**
     * 请求参数
     */
	@TableField("request_param")
	private String requestParam;
    /**
     * 日志内容
     */
	private String content;
    /**
     * 浏览器信息
     */
	@TableField("user_agent")
	private String userAgent;
    /**
     * 运行时间
     */
	private Long runtime;
    /**
     * 日志编码
     */
	@TableField("unique_key")
	private String uniqueKey;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long getIdentityId() {
		return identityId;
	}

	public void setIdentityId(Long identityId) {
		this.identityId = identityId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Long getRuntime() {
		return runtime;
	}

	public void setRuntime(Long runtime) {
		this.runtime = runtime;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

}
