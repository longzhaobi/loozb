package com.loozb.core;

import com.alibaba.dubbo.config.annotation.Service;
import com.loozb.core.base.BaseProviderImpl;
import com.loozb.provider.ISysProvider;


/**
 * 此处将BaseProviderImpl抽离出来，通过继承的方式实现，其目的是方便其他模块公用一个实现execute方法
 * 需要调到此方法，必须打开dubbo服务器
 * BaseProviderImpl已经实现了接口的execute方法
 * 在BaseProviderImpl里，通过传入的service名称获取到bean，通过service去调用BaseService已经封装好的增删改查等方法
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 19:46
 */
@Service(interfaceClass = ISysProvider.class)
public class SysProviderImpl extends BaseProviderImpl implements ISysProvider {
	
}