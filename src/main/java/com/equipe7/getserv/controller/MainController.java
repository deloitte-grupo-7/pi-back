package com.equipe7.getserv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.controller.form.PostForm;
import com.equipe7.getserv.controller.form.ProviderForm;
import com.equipe7.getserv.controller.form.SignInForm;
import com.equipe7.getserv.controller.form.SignUpForm;
import com.equipe7.getserv.model.ProfileEntity;
import com.equipe7.getserv.model.RegisterEntity;
import com.equipe7.getserv.model.UserEntity;
import com.equipe7.getserv.repository.RoleRepository;
import com.equipe7.getserv.repository.UserRepository;
import com.equipe7.getserv.resource.Table;
import com.equipe7.getserv.resource.Token;
import com.equipe7.getserv.service.UserService;

@RestController
@RequestMapping("")
public class MainController {
	
	private final UserService userServ;

	@Autowired
	private RoleRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	public MainController(UserService userServ) {
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
		
		user.setRegisterDep(register);
		user.setProfileDep(new ProfileEntity());
		
		if (Table.getUsernames().size() == 0)
			Table.reset(userRepository);

		if (Table.getUsername(user.getUsername()))
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já cadastrado");
		if (Table.getEmail(register.getEmail()))
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
//		if (Table.getEncodedCPF(register.getCpf()))
//			return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado");
		
		userServ.encodePassword(user);
		return ResponseEntity.created(null).body(userServ.saveUser(user));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<Map<String, String>> signIn(@RequestBody SignInForm form) {
		UserEntity user = userServ.getUser(form.getUsername());
		Map<String, String> response = new HashMap<>();
		
		if (user == null) {
			response.put("error", "Usuário inválido");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		if (!userServ.matches(form.getPassword(), user)) {
			response.put("error", "Senha inválida");
			return ResponseEntity.badRequest().body(response);
		}
		
		response = Token.createTokens(user);
		response.put("username", user.getUsername());
		response.put("imgUrl", "https://www.newsclick.in/sites/default/files/2019-04/Deloitte.jpg");
		
		return ResponseEntity.accepted().body(response);
	}
	
	@GetMapping("/token-refresh")
	public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request, HttpServletResponse response) {
		String auth0 = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (auth0 != null && auth0.startsWith(Token.START)) {
			String username = Token.decodedJWT(auth0).getSubject();
			UserEntity user = userServ.getUser(username);
			
			Map<String, String> tokens = Token.createTokens(user);
			response.setHeader(HttpHeaders.AUTHORIZATION, tokens.get("access_token"));
			
			return ResponseEntity.ok(tokens);
		} else {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Refresh token is missing");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error); 
		}
	}
	
	@GetMapping("/providers")
	public ResponseEntity<List<ProviderForm>> getProvaders() {
		List<UserEntity> users = userServ.getUsers();
		List<ProviderForm> providers = new ArrayList<>();
		users.forEach(user -> {
			if (user.getProfile().getServices().size() > 0) {
				
				ProviderForm provider = new ProviderForm(
					user.getUsername(),
					user.getRegister().getName(),
					user.getProfile().getAbout(),
					user.getProfile().getPictureURL(), 5);
				
				user.getProfile().getServices().forEach(service -> {
					provider.getServices().add(new PostForm(
						service.getId(), service.getTitle(),
						service.getImageURL(), service.getDescription()
					));
				});
				
				providers.add(provider);
			}	
		});
		
		return ResponseEntity.ok(providers);
	}
	
}
