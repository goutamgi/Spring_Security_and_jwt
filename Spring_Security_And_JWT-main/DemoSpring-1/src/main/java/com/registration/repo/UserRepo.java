package com.registration.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registration.entity.UserInfo;

public interface UserRepo extends JpaRepository<UserInfo, Integer> {
	
	Optional<UserInfo>findByName(String name);

}
