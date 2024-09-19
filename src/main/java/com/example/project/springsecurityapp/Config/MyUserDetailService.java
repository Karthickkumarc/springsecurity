package com.example.project.springsecurityapp.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.project.springsecurityapp.Model.Users;
import com.example.project.springsecurityapp.Repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Users user=userRepository.findByUserName(username);
         
         if(user==null)
         {
        	 System.out.println("USername not found Exceptyion");
        	 throw new UsernameNotFoundException("userName not found "+user);
        	
         }
         
		return  new MyPrinciple(user);
	}

}
