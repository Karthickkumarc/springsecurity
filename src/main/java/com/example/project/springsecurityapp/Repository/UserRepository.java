package com.example.project.springsecurityapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.springsecurityapp.Model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUserName(String username);
	
	

}
