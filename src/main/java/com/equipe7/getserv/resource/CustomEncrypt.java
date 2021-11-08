package com.equipe7.getserv.resource;

import com.auth0.jwt.JWT;

public abstract class CustomEncrypt {
	
	public static String encrypt(String cpf) { 
		String nCpf = JWT.create()
			.withSubject(cpf+"aliq48a9z")
			.sign(Token.ALGORITHM);
		return nCpf.substring(nCpf.length() - 32);
	}
}
