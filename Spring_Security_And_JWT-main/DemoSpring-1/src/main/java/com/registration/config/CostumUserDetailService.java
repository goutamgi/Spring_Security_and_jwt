package com.registration.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.registration.entity.UserInfo;
import com.registration.repo.UserRepo;

@Component
public class CostumUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userName = userRepo.findByName(username);
		return userName.map(CostumUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User Not Found"+username));
	}

}
