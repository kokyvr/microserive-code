package com.user.userservice.feignclients;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		// TODO Auto-generated method stub
		Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + jwt.getTokenValue());
	}

}
