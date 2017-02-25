package com.loozb.core;

import com.loozb.core.listener.ServerListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;

public class SysServerListener extends ServerListener {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	public void contextInitialized(ServletContextEvent contextEvent) {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
//		context.getBean(SysUserService.class).init();
//		SysDicService sysDicService = context.getBean(SysDicService.class);
//		sysDicService.getAllDic();
		super.contextInitialized(contextEvent);
	}
}