package com.equipe7.getserv.resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.equipe7.getserv.model.RoleEntity;
import com.equipe7.getserv.model.UserEntity;

public abstract class Token {

	public static final String KEY = "KuxTWb@90NoSx7eo";
	public static final String START = "Bearer ";
	public static final Algorithm ALGORITHM = Algorithm.HMAC256(Token.KEY.getBytes()); 
	
	public static String createAccessTokenUser(User user, long exp) {
		return START + JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(ALGORITHM);
	}
	
	public static String createRefreshTokenUser(User user, long exp) {
		return START + JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.sign(ALGORITHM);
	}
	
	public static String createAccessToken(UserEntity user, long exp) {
		return START + JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.withClaim("roles", user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()))
				.sign(ALGORITHM);
	}
	
	public static String createRefreshToken(UserEntity user, long exp) {
		return START + JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.sign(ALGORITHM);
	}
	
	public static Map<String, String> createTokens(UserEntity user){
		String access_token = Token.createAccessToken(user, 90l * 60000l);
		String refresh_token = Token.createRefreshToken(user, 30l * (1440l * 60000l));
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		return tokens;
	}
	
}
