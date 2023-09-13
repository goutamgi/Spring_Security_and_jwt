package com.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registration.dto.AuthRequest;
import com.registration.dto.JwtResponse;
import com.registration.dto.RefreshTokenRequest;
import com.registration.entity.RefreshToken;
import com.registration.entity.UserInfo;
import com.registration.service.JwtService;
import com.registration.service.RefreshTokenService;
import com.registration.service.UserService;

@RestController
@RequestMapping("/api/pat")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;	
	@PostMapping("/signup")
	public ResponseEntity<String>saveUser(@RequestBody UserInfo userInfo){
		String saveUser = userService.saveUser(userInfo);
		return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public JwtResponse authenticationAndgetToken(@RequestBody AuthRequest authRequest) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
        	RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getName());
           return JwtResponse.builder()
            .accessToken(jwtService.generateToken(authRequest.getName()))
            .token(refreshToken.getToken()).build();
           
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
	}
	
	@PostMapping("/refreshToken")
	public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
 return refreshTokenService.findByToken(refreshTokenRequest.getToken())
		.map(refreshTokenService::verifyExpiration)
		.map(RefreshToken::getUserInfo)
		.map(userInfo->{
			String accessToken = jwtService.generateToken(userInfo.getName());
			
			return JwtResponse.builder()
					.accessToken(accessToken)
					.token(refreshTokenRequest.getToken())
					.build();
			
		}).orElseThrow(()->new RuntimeException("Refresh Token is not in database!"));
		
		
		
	}
	
}
