package com.user.userservice.feignclients;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		// TODO Auto-generated method stub
		
	}

}
