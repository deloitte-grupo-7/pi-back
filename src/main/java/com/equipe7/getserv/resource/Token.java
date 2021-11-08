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
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.equipe7.getserv.model.RoleEntity;
import com.equipe7.getserv.model.UserEntity;
import com.equipe7.getserv.service.UserService;

public abstract class Token {

	public static final String KEY = "KuxTWb@90NoSx7eo";
	public static final String START = "Bearer ";
	public static final Algorithm ALGORITHM = Algorithm.HMAC256(Token.KEY.getBytes());
	
	public static final long EXP_ACCESS = 1440l * 60000l;
	public static final long EXP_REFRESH = 90l * (1440l * 60000l);
	
	public static Map<String, String> createTokensUser(User user){
		String access_token = START + JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXP_ACCESS))
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(ALGORITHM);
		
		String refresh_token = START + JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXP_REFRESH))
				.sign(ALGORITHM);
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		return tokens;
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
		String access_token = Token.createAccessToken(user, EXP_ACCESS);
		String refresh_token = Token.createRefreshToken(user, EXP_REFRESH);
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		return tokens;
	}
	
	public static DecodedJWT decodedJWT(String bearer) {
		JWTVerifier varifier = JWT.require(ALGORITHM).build();
		return varifier.verify(bearer.substring(START.length()));
	}
	
	public static Map<String, String> refresh(String refresh, UserService userService){
		if (refresh != null && refresh.startsWith(Token.START)) {
			String username = Token.decodedJWT(refresh).getSubject();
			UserEntity user = userService.getUser(username);
			
			return Token.createTokens(user);
		}
		Map<String, String> error = new HashMap<>();
		error.put("error", "Refresh token is missing");
		return error;
	}
	
}
