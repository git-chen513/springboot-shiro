package com.kuangshen.config;

import com.kuangshen.pojo.User;
import com.kuangshen.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


//自定义的 UserRealm
public class UserRealm extends AuthorizingRealm{

    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权方法");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();

        //设置当前用户的权限，从数据库获取
        info.addStringPermission(currentUser.getPerms());

        return info;//记得要把return null改为return info
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证方法");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        //关联真实数据库，从token中获取用户名，再根据该用户名查询数据库，看看是否有该用户，如果有，再进一步进行密码验证
        User user = userService.queryByName(userToken.getUsername());
        if(user==null){
            return null;//抛出UnknownAccountException异常
        }

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("loginUser",user);

        //密码认证，为了防止密码泄露，不用我们自己做，shiro帮我们做（对密码进行了加密 MD5/MD5盐值加密）
        //return new SimpleAuthenticationInfo("",user.getPwd(),"");
        return new SimpleAuthenticationInfo(user,user.getPwd(),"");//这里把principal传进去是为了在授权操作里面可以获取到user
    }
}
