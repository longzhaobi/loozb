package com.loozb.mapper;

import com.loozb.model.SysResource;
import com.loozb.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 资源管理信息表 Mapper 接口
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {
    /**
     * 根据PId和id查询
     * @param params
     * @return
     */
    List<Long> selectPidPage(@Param("cm") Map<String, Object> params);
}