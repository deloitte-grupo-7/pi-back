package com.equipe7.getserv.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.controller.form.SignInForm;
import com.equipe7.getserv.controller.form.SignUpForm;
import com.equipe7.getserv.model.RegisterEntity;
import com.equipe7.getserv.model.UserEntity;
import com.equipe7.getserv.repository.RoleRepository;
import com.equipe7.getserv.resource.Token;
import com.equipe7.getserv.service.UserService;

@RestController
@RequestMapping("")
public class UserController {
	
	private final UserService userServ;
	
	@Autowired
	private RoleRepository repository;
	
	public UserController(UserService userServ) {
		super();
		this.userServ = userServ;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpForm form){
		UserEntity user = new UserEntity();
		user.setUsername(form.getUsername());
		user.setPassword(form.getPassword());

		if (user.errors.size() > 0)
			return ResponseEntity.badRequest().body(user.errors);
		
		user.getRoles().add(repository.findByName("ROLE_USER"));
		
		RegisterEntity register = new RegisterEntity();
		
		register.setName(form.getName());
		register.setCpf(form.getCpf());
		register.setEmail(form.getEmail());
		register.setBirthday(form.getBirthday());

		if (register.errors.size() > 0)
			return ResponseEntity.badRequest().body(register.errors);
		
		user.setRegister(register);
		register.setUser(user);
		
		userServ.encodePassword(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userServ.saveUser(user));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody SignInForm form) {
		UserEntity user = userServ.getUser(form.getUsername());
		
		if (user == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário inválido");
		
		if (!userServ.matches(form.getPassword(), user))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Senha inválida");
		
		return ResponseEntity.ok(Token.createTokens(user));
	}
	
	@GetMapping("/token/refresh")
	public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request) {
		String auth0 = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (auth0 != null && auth0.startsWith(Token.START)) {
			String username = Token.decodedJWT(auth0).getSubject();
			UserEntity user = userServ.getUser(username);
			
			return ResponseEntity.ok(Token.createTokens(user));
		} else {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Refresh token is missing");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error); 
		}
	}
	
}
