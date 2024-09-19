package com.example.project.springsecurityapp.Config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.project.springsecurityapp.UserService.JwtService;
import com.example.project.springsecurityapp.UserService.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@DependsOn("jwtService")
public class jwtFilter extends OncePerRequestFilter {
	
	@Lazy
	@Autowired
	private JwtService JwtService;
     @Autowired
    private  ApplicationContext context;
     
     @Autowired
     private UserService userService;
     
     @Autowired
     private MyUserDetailService myUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String authHeader=request.getHeader("Authorization");
		String token=null;
		String username=null;
		
		if(authHeader !=null && authHeader.startsWith("Bearer "))
		{
			token=authHeader.substring(7);
			try {
			username=JwtService.extractUserName(token);
			}
			catch(ExpiredJwtException e)
			{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("token is expired,please login");
				return;
			}
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
		UserDetails userDetails=context.getBean(myUserDetailService.getClass()).loadUserByUsername(username);
//			 UserDetails userDetails=myUserDetailService.loadUserByUsername(username);
               if(JwtService.ValidateToken(token,userDetails))
               {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
               }
		}
		filterChain.doFilter(request, response);
	}

}
