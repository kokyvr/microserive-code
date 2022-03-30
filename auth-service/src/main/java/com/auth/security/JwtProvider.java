package com.auth.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth.dto.RequestDto;
import com.auth.entity.AuthUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secret;
	
	@Autowired
	private RouteValidator routeValidator;

	
	@PostConstruct
	protected void init() {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}
	
	public String createToken(AuthUser authUser) {
		Map<String, Object> claims = new HashMap<String, Object>();
		
		claims = Jwts.claims().setSubject(authUser.getUsername());
		claims.put("id",authUser.getId());
		claims.put("role",authUser.getRole());
		Date now = new Date();
		Date exp = new Date(now.getTime() + 3600000);
		
		return Jwts.builder().setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256,this.secret).compact();
 	}
	
	public boolean validate(String token,RequestDto dto) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		if(!isAdmin(token) && routeValidator.isAdminPath(dto)) {
			return false;
		}
		
		return true;
		
	}
	public String getUserNameFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e) {
			return "bad token";
		}
	}
	
	private boolean isAdmin(String token) {
		return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().get("role").equals("admin");
	}
}
