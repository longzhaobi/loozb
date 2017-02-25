package com.loozb.service;

import com.loozb.core.base.BaseService;
import com.loozb.model.SysUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
@Service
@CacheConfig(cacheNames = "SysUser")
public class SysUserService extends BaseService<SysUser> {

}
