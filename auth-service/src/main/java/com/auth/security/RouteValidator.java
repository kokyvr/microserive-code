package com.auth.security;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.auth.dto.RequestDto;


@ConfigurationProperties(prefix = "admin-paths")
@Component
public class RouteValidator {
	
	private List<RequestDto> paths;
	
	

	public List<RequestDto> getPaths() {
		return paths;
	}



	public void setPaths(List<RequestDto> paths) {
		this.paths = paths;
	}



	public boolean isAdminPath(RequestDto dto) {
		return this.paths.stream().anyMatch(p->
		Pattern.matches(p.getUri(),dto.getUri()) && p.getMethod().equals(dto.getMethod()));
	}
	
}
