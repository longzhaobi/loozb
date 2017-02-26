LOOZB
======================

[![Build Status](https://travis-ci.org/mybatis/spring.svg?branch=master)](https://travis-ci.org/mybatis/spring)
[![Coverage Status](https://coveralls.io/repos/mybatis/spring/badge.svg?branch=master&service=github)](https://coveralls.io/github/mybatis/spring?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/5619b698a193340f2f000520/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5619b698a193340f2f000520)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis-spring/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis-spring)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

![mybatis-spring](http://mybatis.github.io/images/mybatis-logo.png)

## LOOZB项目简介

- 本项目架构参考开源项目 [iBase4J](https://git.oschina.net/iBase4J/iBase4J)
- 采用Java语言的分布式系统架构，使用Spring整合开源框架。
- 使用Maven对项目进行模块化管理，提高项目的易开发性、扩展性。
- 系统包括三个模块：公共模块、系统管理模块、Web展示模块。
- 公共模块：公共功能(AOP、缓存、基类、调度等等)、公共配置、工具类。
- 系统管理模块：包括用户管理、权限管理、数据字典、系统参数管理等等。
- 每个模块都是独立的系统，可以无限的扩展模块，模块之间使用Dubbo或MQ进行通信。
- 每个模块服务多系统部署，注册到同一个Zookeeper集群服务注册中心，实现集群部署。

## 主要功能
 1. 数据库：Druid数据库连接池，监控数据库访问性能，统计SQL的执行性能。 数据库密码加密，加密方式请查看PropertiesUtil，decryptProperties属性配置需要解密的key。
 2. 持久层：mybatis持久化，使用MyBatis-Plus优化，减少sql开发量；aop切换数据库实现读写分离。Transtraction注解事务。
 3. MVC： 基于spring mvc注解,Rest风格Controller。Exception统一管理。
 4. 调度：Spring+quartz, 可以查询、修改周期、暂停、删除、新增、立即执行，查询执行记录等。
 5. 无状态，session统一存放到redis。
 6. 缓存：注解redis缓存数据。
 7. 多系统交互：Dubbo,ActiveMQ多系统交互，ftp/sftp/fastdafs发送文件到独立服务器，使文件服务分离。
 8. 前后端分离：前端使用nginx。
 9. 日志：log4j2打印日志，业务日志和调试日志分开打印。同时基于时间和文件大小分割日志文件。
 10. QQ、微信、新浪微博第三方登录。
 11. 工具类：excel导入导出，汉字转拼音，身份证号码验证，数字转大写人民币，FTP/SFTP/fastDFS上传下载，发送邮件，redis缓存，加密等等。

## 技术选型
    ● 核心框架：Spring Framework 4.3.0 + Dubbo 2.5.3
    ● 安全框架：Apache Shiro 1.2
    ● 任务调度：Spring + Quartz
    ● 持久层框架：MyBatis 3.4 + MyBatis-Plus 2.0
    ● 数据库连接池：Alibaba Druid 1.0
    ● 缓存框架：Redis
    ● 日志管理：SLF4J、Log4j2
    ● 前端框架：reactjs、dva、antd

## 启动说明
    * 项目依赖activemq、Redis和ZooKeeper服务。
    * 启动service命令：clean package -P build tomcat7:run-war-only -f pom-sys-service-server.xml
    * 启动web命令：clean package -P build tomcat7:run-war-only -f pom-sys-web-server.xml
    * 使用nginx代理UI：修改配置里的UI目录后重启nginx。

Essentials
----------

* [See the url](http://www.loozb.com/)