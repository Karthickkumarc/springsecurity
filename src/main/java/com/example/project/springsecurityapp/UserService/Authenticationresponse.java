package com.example.project.springsecurityapp.UserService;

import org.springframework.stereotype.Service;


public class Authenticationresponse {

	private final String jwt;
	
	public Authenticationresponse(String jwt)
	{
		this.jwt=jwt;
	}
	
	public String getjwt()
	{
		return jwt;
	}
}
