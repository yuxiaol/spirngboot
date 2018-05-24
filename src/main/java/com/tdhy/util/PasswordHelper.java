package com.tdhy.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.tdhy.domain.User;

/** 
 * 密码加密
 * @see PasswordHelper 
 * @author yuxiaolong@tiandihengye.com
 * @version 0.0.1  
 */
public class PasswordHelper {
	
	public final static String TEMPLATE = "md5";
	public  final static int NUM = 2;

	public static void encryptPassword(User user) {
		String newPassword = new SimpleHash(TEMPLATE, user.getPassword(),  ByteSource.Util.bytes(user.getName()), NUM).toHex();
		user.setPassword(newPassword);
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setName("admin");
		user.setPassword("123456");
		encryptPassword(user);
		System.out.println(user);
	}
}
