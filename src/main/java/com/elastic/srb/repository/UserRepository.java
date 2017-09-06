package com.elastic.srb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.elastic.srb.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{ 
	
	
}
