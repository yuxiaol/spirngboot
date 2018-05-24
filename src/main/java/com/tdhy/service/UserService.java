package com.tdhy.service;

import com.tdhy.domain.User;

public interface UserService {
	
	public User findByName(String name);
	
	 
	 public User addUser(User user);
}
