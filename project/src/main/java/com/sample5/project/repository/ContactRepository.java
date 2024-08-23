package com.sample5.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample5.project.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact,Integer>{
	
	
	

}
