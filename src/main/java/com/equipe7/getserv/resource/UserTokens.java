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

public abstract class UserTokens {

	public static final String SECRET = "easteregg";
	
	public static String createAccessTokenPrincipal(User user, Algorithm algorithm, HttpServletRequest request, long exp) {
		return JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
	}
	
	public static String createAccessTokenUser(UserEntity user, Algorithm algorithm, HttpServletRequest request, long exp) {
		return JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()))
				.sign(algorithm);
	}
	
	public static String createRefreshTokenPrincipal(User user, Algorithm algorithm, HttpServletRequest request, long exp) {
		return JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + exp))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
	}
	
	String createRefreshTokenUser(UserEntity user, Algorithm algorithm, HttpServletRequest request, long exp) {
		return null;
	}
	
}
