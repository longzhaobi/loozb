package com.loozb.core.base;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.loozb.core.Constants;
import com.loozb.core.exception.BusinessException;
import com.loozb.core.util.ExceptionUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.Map;

/**
 * 此处已经走到了dubbo里面
 * 统一执行方法，用于根据传入的参数进行分发处理实现层
 * 根据service进行分发，根据method判断调用service的那个方法
 * 通过传入的service名称获取到bean，通过service去调用BaseService已经封装好的增删改查等方法
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 19:46
 */
public abstract class BaseProviderImpl implements ApplicationContextAware, BaseProvider {
	protected static Logger logger = LogManager.getLogger();
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public Parameter execute(Parameter parameter) {
		logger.info("请求：{}", JSON.toJSONString(parameter));
		Object service = applicationContext.getBean(parameter.getService());
		try {
			Long id = parameter.getId();
			BaseModel model = parameter.getModel();
			List<?> list = parameter.getList();
			Map<?, ?> map = parameter.getMap();
			Object result = null;
			MethodAccess methodAccess = MethodAccess.get(service.getClass());
			if (id != null) {
				result = methodAccess.invoke(service, parameter.getMethod(), parameter.getId());
			} else if (model != null) {
				result = methodAccess.invoke(service, parameter.getMethod(), parameter.getModel());
			} else if (list != null) {
				result = methodAccess.invoke(service, parameter.getMethod(), parameter.getList());
			} else if (map != null) {
				result = methodAccess.invoke(service, parameter.getMethod(), parameter.getMap());
			} else {
				result = methodAccess.invoke(service, parameter.getMethod());
			}
			if (result != null) {
				Parameter response = new Parameter(result);
				logger.info("响应：{}", JSON.toJSONString(response));
				return response;
			}
			logger.info("空响应");
			return null;
		} catch (Exception e) {
			throw new BusinessException(Constants.Exception_Head + ExceptionUtil.getStackTraceAsString(e), e);
		}
	}
}
