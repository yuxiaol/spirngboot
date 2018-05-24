package com.tdhy.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdhy.domain.User;
import com.tdhy.repository.UserRepository;
import com.tdhy.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
	
	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		log.info("findByName");
		return userRepository.findByName(name);
	}


	@Override
	@Transactional(noRollbackFor = Exception.class)
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

}
