package com.loozb.core.shiro.factory;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * <p>
 * 禁用session
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory{
	@Override
    public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}

