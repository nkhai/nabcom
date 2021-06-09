package com.nab.icom.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.nab.icom.user.model.User;

public interface UserRepository extends CrudRepository<User, Long>  {

	public User findByUserId(String userId);


}
