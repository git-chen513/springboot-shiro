package com.kuangshen.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    //ShiroFilterFactoryBean:3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("SecurityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);

        /**
         * 添加shiro的内置过滤器
         * anno:无需认证就可以访问
         * authc:必须认证后才可以访问
         * user:拥有“记住我”功能才可以访问
         * perms:拥有某个资源的权限才可以访问
         * role:拥有某个角色的权限才可以访问
         */

        Map<String,String> filterMap=new LinkedHashMap<>();
        //授权（注意：授权要写在拦截之前，不让起不了作用）
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        //拦截，没有认证不能访问页面
        filterMap.put("/user/*","authc");//使用通配符

        bean.setFilterChainDefinitionMap(filterMap);

        //设置没有身份认证跳转到登录页
        bean.setLoginUrl("/toLogin");
        //设置没有授权跳转到错误页面
        bean.setUnauthorizedUrl("/unauth");
        return bean;
    }

    //DefaultWebSecurityManager:2
    @Bean(name="SecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联userRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象，需要自定义类：1
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //创建shiroDialect到spring容器，用来整合thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
