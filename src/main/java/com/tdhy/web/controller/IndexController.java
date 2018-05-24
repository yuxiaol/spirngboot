package com.tdhy.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdhy.domain.User;
import com.tdhy.util.BaseSystemConstants;

@Controller
@RequestMapping("manage")
public class IndexController {

	@GetMapping(value = "list")
	public String welcome(Model model) {
		Session session = SecurityUtils.getSubject().getSession();
		User user = (User)session.getAttribute(BaseSystemConstants.MANAGE_SESSION_KEY);
		System.out.println(session.getId());
		System.out.println(user);
		return "index";
	}
	
	@GetMapping(value = "login")
	public String login() {
			return "login";
	}
	@PostMapping(value = "login")
	public String login(HttpServletRequest request, User user, Model model) {
		
		if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getName(),user.getPassword());
        try {
            subject.login(token);
            Session session = SecurityUtils.getSubject().getSession();
    		User user1 = (User)session.getAttribute(BaseSystemConstants.MANAGE_SESSION_KEY);
    		System.out.println(session.getId());
    		System.out.println(user1);
            
            return "redirect:list";
        }catch(UnknownAccountException uke) {
        	token.clear();
            request.setAttribute("msg", "账号不存在！");
            return "login";
        }
        catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        }catch(IncorrectCredentialsException e) {
        	token.clear();
        	request.setAttribute("msg", "密码不正确！");
        	return "login";
        }
	}
}
