package com.tdhy.repository;

import com.tdhy.domain.User;

public interface UserRepository extends BaseRepository<User,Long>{
    User findByName(String name);
}