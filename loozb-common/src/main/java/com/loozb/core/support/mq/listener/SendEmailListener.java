package com.loozb.core.support.mq.listener;

import com.loozb.core.support.email.Email;
import com.loozb.core.util.EmailUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * 发送邮件队列
 * 
 * @author ShenHuaJie
 * @version 2016年6月14日 上午11:00:53
 */
public class SendEmailListener implements MessageListener {
	private final Logger logger = LogManager.getLogger();

	public void onMessage(Message message) {
		try {
			Email email = (Email) ((ObjectMessage) message).getObject();
			logger.info("将发送邮件至：" + email.getSendTo());
			EmailUtil.sendEmail(email);
		} catch (JMSException e) {
			logger.error(e);
		}
	}
}
