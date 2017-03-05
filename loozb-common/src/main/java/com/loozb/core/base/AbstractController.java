package com.loozb.core.base;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

/**
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 19:46
 */
public abstract class AbstractController<T extends BaseProvider> extends BaseController {
    protected final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    protected T provider;

    public abstract String getService();

    /**
     * 返回分页信息
     * @param modelMap
     * @param param
     * @return
     */
    public Object query(ModelMap modelMap, Map<String, Object> param) {
        Parameter parameter = new Parameter(getService(), "query").setMap(param);
        Page<?> list = provider.execute(parameter).getPage();
        return setSuccessModelMap(modelMap, list);
    }

    public Object queryList(ModelMap modelMap, Map<String, Object> param) {
        Parameter parameter = new Parameter(getService(), "queryList").setMap(param);
        List<?> list = provider.execute(parameter).getList();
        return setSuccessModelMap(modelMap, list);
    }

    public Object get(ModelMap modelMap, BaseModel param) {
        Parameter parameter = new Parameter(getService(), "queryById").setId(1L);
        BaseModel result = provider.execute(parameter).getModel();
        return setSuccessModelMap(modelMap, result);
    }

    public Object update(ModelMap modelMap, BaseModel param) {
        Long userId = getCurrUser();
        if (param.getId() == null) {
            param.setCreateId(userId);
        }
        Parameter parameter = new Parameter(getService(), "update").setModel(param);
        provider.execute(parameter);
        return setSuccessModelMap(modelMap,"更新成功");
    }

    public Object delete(ModelMap modelMap, Long id) {
        Parameter parameter = new Parameter(getService(), "delete").setId(id);
        provider.execute(parameter);
        return setSuccessModelMap(modelMap);
    }
}
