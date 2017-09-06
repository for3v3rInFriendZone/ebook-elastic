package com.elastic.srb.service;

import com.elastic.srb.model.User;

public interface UserService {

	public User save(User user);
	
	public User findOne(Long id);
	
	public Iterable<User> findAll();
	
	public void delete(Long id);
	
	public void delete(User user);
	
	public void deleteAll();
}
