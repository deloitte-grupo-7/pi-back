package com.equipe7.getserv.resource;

import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.equipe7.getserv.model.RoleEntity;
import com.equipe7.getserv.model.UserEntity;

public abstract class UserToken {

	public static final String KEY = "KuxTWb@90NoSx7eo";
	public static final String START = "Bearer ";
	public static final Algorithm ALGORITHM = Algorithm.HMAC256(UserToken.KEY.getBytes()); 
	
	public static String createAccessToken(User user, HttpServletRequest request, long exp) {
		return START + JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(ALGORITHM);
	}
	
	public static String recreateAccessToken(UserEntity user, HttpServletRequest request, long exp) {
		return START + JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()))
				.sign(ALGORITHM);
	}
	
	public static String createRefreshToken(User user, HttpServletRequest request, long exp) {
		return START + JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.withIssuer(request.getRequestURL().toString())
				.sign(ALGORITHM);
	}
	
	public static String recreateRefreshToken(UserEntity user, Algorithm algorithm, HttpServletRequest request, long exp) {
		return null;
	}
	
}
