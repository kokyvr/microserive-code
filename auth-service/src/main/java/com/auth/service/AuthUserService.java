package com.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.dto.AuthUserDto;
import com.auth.dto.TokenDto;
import com.auth.entity.AuthUser;
import com.auth.repository.AuthUserRepository;
import com.auth.security.JwtProvider;

@Service
public class AuthUserService {

	@Autowired
	private AuthUserRepository authRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	public AuthUser save(AuthUserDto dto) {
		Optional<AuthUser> user = authRepository.findByUsername(dto.getUsername());
		if(user.isPresent()) {
			return null;
		}
		String password = passwordEncoder.encode(dto.getPassword());
		AuthUser authUser = AuthUser.builder().username(dto.getUsername()).password(password) .build();
		
		return authRepository.save(authUser);
	}
	
	public TokenDto login(AuthUserDto auth) {
		Optional<AuthUser> user = authRepository.findByUsername(auth.getUsername());
		if(!user.isPresent()) {
			return null;
		}
		if(passwordEncoder.matches(auth.getPassword(),user.get().getPassword())) {
			return new TokenDto(jwtProvider.createToken(user.get()));
		}
		return null;
		
	}
	
	public TokenDto validate(String token) {
		if(!jwtProvider.validate(token))
			return null;
		String username = jwtProvider.getUserNameFromToken(token);
		if(!authRepository.findByUsername(username).isPresent())
			return null;
	
		return new TokenDto(token);
	}
}
