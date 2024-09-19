package com.example.project.springsecurityapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.springsecurityapp.Config.jwtFilter;
import com.example.project.springsecurityapp.Model.Users;
import com.example.project.springsecurityapp.UserService.JwtService;
import com.example.project.springsecurityapp.UserService.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController

public class HomeController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

	@GetMapping("/user")
	public String GetString(HttpServletRequest request) {
		return "karthick kumar welcome to our website" + request.getSession().getId();
	}

	@PostMapping("/register")
	public Users AddUSer( @RequestBody Users user )
	{
//		user.setPassword(encoder.encode(user.getPassword()));
		return  userService.AddUser(user);
	}
	@GetMapping("/alluser")
	public List<Users>getAllUSer()
	{
		return userService.GetAllUser();
	}
	@PostMapping("/login")
	public String Loginuser(@RequestBody Users user)
	{
		return userService.verify(user);
	}

	@GetMapping("/validatetoken")
	public ResponseEntity<?>validateToken(@RequestHeader("Authorization") String token)
	{
		String toke =token;
		System.out.println(toke);
		String message=jwtService.tokenValidate(token.substring(7).trim());
		if(message.equals("tokenexpired"))
		{
		return  ResponseEntity.status(401).body("Session Expired");
		}
		return ResponseEntity.ok("valid token");
	}

}
