# 开发须知

## 1，项目分为3个微服务 

    三个业务微服务
    mps-order                订单微服务               8091
    mps-user                 用户微服务               8092
    mps-tool                 工具相关微服务           8095

    LCN 分布式事务协调器
    tx-manager
   
   

    公共模块
    1：common    (提供数据模型，常量，工具，枚举等类的编写)。
    2：parent    (基本的maven依赖)。

## 2，本项目的搭建载体，Spring boot 作为根基，数据持久层使用Spring Data Jpa（)

    数据缓存使用（Spring Data Redis）,服务治理（Spring Cloud Eureka），服务调用（Spring Colud Feign） 
    数据连接池（阿里巴巴druid），web容器（undertow），LCN 分布式事务协调器（Tx-manager） 等。

## 3，错误异常日志，统一使用枚举类 ResultMsg 分ID端来区分不同的业务代码。例如系统级别错误（0-99） 用户登录相关（100-200）其他的自己定义。
## 4，其他相关注意事项待补充。。。。。。。。。

    支持本地Dev环境和test测试环境进行开发调试，Dev环境(hhttps://github.com/wxk843/mps.git) 需要自己手动拉取注册中心的项目并启动（dev）环境，
    test环境无需获取。
