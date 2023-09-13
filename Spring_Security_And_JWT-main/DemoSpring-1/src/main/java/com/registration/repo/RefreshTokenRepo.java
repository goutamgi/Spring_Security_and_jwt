package com.registration.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registration.entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer>{
	
	Optional<RefreshToken> findByToken(String token);

}
