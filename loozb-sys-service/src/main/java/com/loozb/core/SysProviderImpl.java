package com.loozb.core;

import com.alibaba.dubbo.config.annotation.Service;
import com.loozb.core.base.BaseProviderImpl;
import com.loozb.provider.ISysProvider;

@Service(interfaceClass = ISysProvider.class)
public class SysProviderImpl extends BaseProviderImpl implements ISysProvider {
	
}