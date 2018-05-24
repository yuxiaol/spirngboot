package com.tdhy;

import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.tdhy.domain.Permission;
import com.tdhy.domain.Role;
import com.tdhy.domain.User;
import com.tdhy.repository.PermissionRepository;
import com.tdhy.repository.RoleRepository;
import com.tdhy.repository.UserRepository;
import com.tdhy.service.UserService;
import com.tdhy.util.PasswordHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroApplicationTests {

	@Autowired
	UserRepository userRepository ;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PermissionRepository permissionRepository;
	
	@Autowired
	UserService userService;
	
	@Test
	@Rollback(false)
	public void contextLoads() {
		User user = new User();
		user.setName("admin123");
		user.setPassword("123456789");
		PasswordHelper.encryptPassword(user);
		userRepository.save(user);
		
		Role role  = new Role();
		role.setRoleName("管理员");
		role.setUser(user);
		
		roleRepository.save(role);
		
		Permission  pm = new Permission();
		pm.setPermission("nb");
		pm.setRole(role);
		permissionRepository.save(pm);
		
	}
	
	
	@Test
	public void test01() {
		 User user = userService.findByName("admin123");
	        //添加角色和权限
	        
	        List<Role> roles = roleRepository.findAll((root,query,cb) -> {
	        	Join<Role, User> join = root.join("user",JoinType.LEFT);
	        	Path<Long> exp = join.get("id");
	        	return  cb.equal(exp, user.getId());
	        });
	        
	        for (Role role:roles) {
	            //添加角色
	        	 System.out.println(role);
	            List< Permission> permissions = permissionRepository.findAll((root,query,cb) -> {
	              	Join<Permission, Role> join = root.join("role",JoinType.LEFT);
	              	Path<Long> exp = join.get("id");
	              	return  cb.equal(exp, role.getId());
	              });
	            for (Permission permission:permissions) {
	                //添加权限
	               System.out.println(permission);
	            }
	        }
	}

}
