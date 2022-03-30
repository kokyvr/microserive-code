package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.AuthUserDto;
import com.auth.dto.NewUserDto;
import com.auth.dto.RequestDto;
import com.auth.dto.TokenDto;
import com.auth.entity.AuthUser;
import com.auth.service.AuthUserService;

@RestController
@RequestMapping(path = "auth")
public class AuthUserController {

	@Autowired
	AuthUserService authUserService;
		
	@PostMapping(path = "login")
	public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto dto){
		TokenDto tokenDto = authUserService.login(dto);
		if(tokenDto == null)
			return ResponseEntity.badRequest().build();
	
		return ResponseEntity.ok(tokenDto);
	}
	@PostMapping(path = "validate")
	public ResponseEntity<TokenDto> validate(@RequestParam String token,@RequestBody RequestDto dto){
		TokenDto tokenDto = authUserService.validate(token,dto);
		if(tokenDto == null)
			return ResponseEntity.badRequest().build();
		
		return ResponseEntity.ok(tokenDto);
	}
	@PostMapping(path = "create")
	public ResponseEntity<AuthUser> create(@RequestBody NewUserDto dto){
		AuthUser authUser = authUserService.save(dto);
		if(authUser == null)
			return ResponseEntity.badRequest().build();
		
		return ResponseEntity.ok(authUser);
	}
}
