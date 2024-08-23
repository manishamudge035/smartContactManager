 package com.sample5.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sample5.project.entities.User;

public interface userRepository extends JpaRepository<User,Integer> {
	
	
	
	@Query("Select u from User u where u.email=:email")
	public User getUserByUserName(@Param("email") String email);
	
	//getting dynamic value of email from database
	
	

}
