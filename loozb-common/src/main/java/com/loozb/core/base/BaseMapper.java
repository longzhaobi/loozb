package com.loozb.core.base;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 19:54
 */
public interface BaseMapper<T extends BaseModel> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

    List<Long> selectIdPage(RowBounds rowBounds, @Param("cm") Map<String, Object> params);
}
