package com.loozb.provider;

import com.loozb.core.base.BaseProvider;

/**
 * 此处将BaseProvider抽离出来，通过继承的方式实现，其目的是方便其他模块公用一个execute方法
 * 通过给BaseProvider加注解sysProvider,实现一个模块统一管理dubbo的功能
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 19:46
 */
public interface ISysProvider extends BaseProvider {

}
