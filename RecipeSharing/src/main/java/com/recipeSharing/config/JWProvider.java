package com.recipeSharing.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWProvider {
	private SecretKey key = Keys.hmacShaKeyFor(JWTConstant.JWT_SECRET.getBytes());

	//This token will be generated during the time of account creation/login.
	public String generateToken(Authentication auth) {
		String JWT = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+86400000))
				.claim("email", auth.getName()) //using email so that we can retrive email using jwt token
				.signWith(key).compact();
		
		return JWT;
	}
	
	public String getEmailbyJwt(String jwt) {
		String jwt1 = jwt.substring(7); //Bearer JWT
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt1).getBody();
		String email = String.valueOf(claims.get("email")); //getting email using the jwttoken key
		
		return email;
	}
}
