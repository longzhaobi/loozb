package com.loozb.core.base;

import com.baomidou.mybatisplus.plugins.Page;
import com.loozb.core.Constants;
import com.loozb.core.util.CacheUtil;
import com.loozb.core.util.DataUtil;
import com.loozb.core.util.InstanceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 统一查询，因为查询已经封装，故将用到的mapper也统一封装
 * 业务逻辑层基类<br/>
 * 继承基类后必须配置CacheConfig(cacheNames="")
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 19:55
 */
public abstract class BaseService<T extends BaseModel> implements ApplicationContextAware {

    protected Logger logger = LogManager.getLogger(getClass());
    @Autowired(required = true)
    protected BaseMapper<T> mapper;
    protected ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /** 分页查询 */
    public static Page<Long> getPage(Map<String, Object> params) {
        Integer current = 1;
        Integer size = 10;
        String orderBy = "id";
        if (DataUtil.isNotEmpty(params.get("pages"))) {
            current = Integer.valueOf(params.get("pages").toString());
        }
        if (DataUtil.isNotEmpty(params.get("size"))) {
            size = Integer.valueOf(params.get("size").toString());
        }
        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
            orderBy = (String) params.get("orderBy");
            params.remove("orderBy");
        }
        if (size == -1) {
            return new Page<Long>();
        }
        Page<Long> page = new Page<Long>(current, size, orderBy);
        page.setAsc(false);
        return page;
    }

    /** 根据Id查询(默认类型T) */
    public Page<T> getPage(Page<Long> ids) {
        if (ids != null) {
            Page<T> page = new Page<T>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<T> records = InstanceUtil.newArrayList();
            for (Long id : ids.getRecords()) {
                records.add(this.queryById(id));
            }
            page.setRecords(records);
            return page;
        }
        return new Page<T>();
    }

    /** 根据Id查询(默认类型T) */
    public Page<Map<String, Object>> getPageMap(Page<Long> ids) {
        if (ids != null) {
            Page<Map<String, Object>> page = new Page<Map<String, Object>>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<Map<String, Object>> records = InstanceUtil.newArrayList();
            for (Long id : ids.getRecords()) {
                records.add(InstanceUtil.transBean2Map(this.queryById(id)));
            }
            page.setRecords(records);
            return page;
        }
        return new Page<Map<String, Object>>();
    }

    /** 根据Id查询(cls返回类型Class) */
    public <K> Page<K> getPage(Page<Long> ids, Class<K> cls) {
        if (ids != null) {
            Page<K> page = new Page<K>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<K> records = InstanceUtil.newArrayList();
            for (Long id : ids.getRecords()) {
                T t = this.queryById(id);
                K k = InstanceUtil.to(t, cls);
                records.add(k);
            }
            page.setRecords(records);
            return page;
        }
        return new Page<K>();
    }

    /** 根据Id查询(默认类型T) */
    public List<T> getList(List<Long> ids) {
        List<T> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (Long id : ids) {
                list.add(this.queryById(id));
            }
        }
        return list;
    }

    /** 根据Id查询(cls返回类型Class) */
    public <K> List<K> getList(List<Long> ids, Class<K> cls) {
        List<K> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (Long id : ids) {
                T t = this.queryById(id);
                K k = InstanceUtil.to(t, cls);
                list.add(k);
            }
        }
        return list;
    }

    @Transactional
    public void del(Long id, Long userId) {
        try {
            T record = this.queryById(id);
            record.setAvailable("0");
            record.setMtime(new Date());
            mapper.updateById(record);
            CacheUtil.getCache().set(getCacheKey(id), record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            mapper.deleteById(id);
            CacheUtil.getCache().del(getCacheKey(id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 新增 or 删除
     * @param record
     * @return
     */
    @Transactional
    public T update(T record) {
        try {
            if (record.getId() == null) {
                record.setCtime(new Date());
                mapper.insert(record);
            } else {
                record.setMtime(new Date());
                String lockKey = getClass().getName() + record.getId();
                if (CacheUtil.getLock(lockKey)) {
                    try {
                        T org = queryById(record.getId());
                        T update = InstanceUtil.getDiff(org, record);
                        update.setId(record.getId());
                        mapper.updateById(update);
                    } finally {
                        CacheUtil.unlock(lockKey);
                    }
                }
            }
            record = mapper.selectById(record.getId());
            CacheUtil.getCache().set(getCacheKey(record.getId()), record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return record;
    }

    /**
     * 通过主键查询，统一使用主键查询，实现缓存
     * @param id
     * @return
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public T queryById(Long id) {
        try {
            String key = getCacheKey(id);
            T record = (T) CacheUtil.getCache().get(key);
            if (record == null) {
                record = mapper.selectById(id);
                CacheUtil.getCache().set(key, record);
            }
            return record;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Page<T> query(Map<String, Object> params) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page);
    }

    public Page<Map<String, Object>> queryMap(Map<String, Object> params) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPageMap(page);
    }

    public List<T> queryList(Map<String, Object> params) {
        List<Long> ids = mapper.selectIdPage(params);
        List<T> list = getList(ids);
        return list;
    }

    protected <P> Page<P> query(Map<String, Object> params, Class<P> cls) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page, cls);
    }

    /** 获取缓存键值 */
    protected String getCacheKey(Object id) {
        String cacheName = null;
        CacheConfig cacheConfig = getClass().getAnnotation(CacheConfig.class);
        if (cacheConfig == null || cacheConfig.cacheNames() == null || cacheConfig.cacheNames().length < 1) {
            cacheName = getClass().getName();
        } else {
            cacheName = cacheConfig.cacheNames()[0];
        }
        return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":").append(id).toString();
    }
}
