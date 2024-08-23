package com.sample5.project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sample5.project.entities.User;

public class userDetailsServiceImpl implements UserDetailsService {
	
	
	@Autowired
	 private com.sample5.project.repository.userRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		
		
		//fetching user from database
		
		
		User user=  userRepository.getUserByUserName(username);
		  
		  if(user==null)
		  {
			  throw new UsernameNotFoundException("could not found user");
			  
		  }
		
		//  ConstomeUserDetails ConstomeUserDetails= new ConstomeUserDetails(user);
		   
		  ConstomeUserDetails ConstomeUserDetails=new ConstomeUserDetails(user);
		  
		  
		
		return ConstomeUserDetails;
	}

}
