package com.loozb.service;

import com.loozb.core.base.BaseService;
import com.loozb.model.SysOrgan;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织机构部门信息表 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@Service
@CacheConfig(cacheNames = "SysOrgan")
public class SysOrganService extends BaseService<SysOrgan> {
	
}
