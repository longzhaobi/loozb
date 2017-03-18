package com.loozb.mapper;

import com.loozb.model.SysColumn;
import com.loozb.model.SysTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
public interface SysTableMapper {
    List<SysTable> selectTable(@Param("cm") Map<String, Object> params);

    List<SysColumn> selectColumns(@Param("cm") Map<String, Object> params);
}