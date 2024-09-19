package com.example.project.springsecurityapp.UserService;

import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.springsecurityapp.Model.Users;
import com.example.project.springsecurityapp.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	 AuthenticationManager authmanager;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService JwtService;

	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	public Users AddUser(Users user) {
		// TODO Auto-generated method stub
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}



	public List<Users> GetAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}



	public String verify(Users user) {
		// TODO Auto-generated method stub
		org.springframework.security.core.Authentication authentication=authmanager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
		if(authentication.isAuthenticated())
		{
			
			     return JwtService.GenerateToken(user.getUserName());
			
		}
		return null;
		
	}



	
	
	

}
