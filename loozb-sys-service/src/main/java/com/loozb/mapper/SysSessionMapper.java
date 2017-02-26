package com.loozb.mapper;

import com.loozb.model.SysSession;
import com.loozb.core.base.BaseMapper;

import java.util.List;

/**
 * <p>
  * 用户角色信息表 Mapper 接口
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
public interface SysSessionMapper extends BaseMapper<SysSession> {
    void deleteBySessionId(String sessionId);

    Long queryBySessionId(String sessionId);

    List<String> querySessionIdByAccount(String account);
}