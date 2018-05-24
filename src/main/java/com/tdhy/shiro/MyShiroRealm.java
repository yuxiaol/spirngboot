package com.tdhy.shiro;

import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.tdhy.domain.Permission;
import com.tdhy.domain.Role;
import com.tdhy.domain.User;
import com.tdhy.repository.PermissionRepository;
import com.tdhy.repository.RoleRepository;
import com.tdhy.service.UserService;
import com.tdhy.util.BaseSystemConstants;

public class MyShiroRealm extends AuthorizingRealm{

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//获取登录用户名
        String name= (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        User user = userService.findByName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        
        List<Role> roles = roleRepository.findAll((root,query,cb) -> {
        	Join<Role, User> join = root.join("user",JoinType.LEFT);
        	Path<Long> exp = join.get("id");
        	return  cb.equal(exp, user.getId());
        });
        
        for (Role role:roles) {
            //添加角色
          simpleAuthorizationInfo.addRole(role.getRoleName());
            List< Permission> permissions = permissionRepository.findAll((root,query,cb) -> {
              	Join<Permission, Role> join = root.join("role",JoinType.LEFT);
              	Path<Long> exp = join.get("id");
              	return  cb.equal(exp, role.getId());
              });
            for (Permission permission:permissions) {
                //添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		 //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = userService.findByName(name);
        if (user == null) {
            //这里返回后会报出对应异常
        	throw new UnknownAccountException();
        
        }else {
        	  SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                      user, //用户
                      user.getPassword(), //密码
                      ByteSource.Util.bytes(name),
                      getName()  //realm name
              );
        	  Session session = SecurityUtils.getSubject().getSession();
        	  session.setAttribute(BaseSystemConstants.MANAGE_SESSION_KEY, user);
        	  return authenticationInfo;
        }
	}

}
