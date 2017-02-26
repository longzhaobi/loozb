package com.loozb.service;

import com.loozb.core.base.BaseService;
import com.loozb.model.SysRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

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
	
}
