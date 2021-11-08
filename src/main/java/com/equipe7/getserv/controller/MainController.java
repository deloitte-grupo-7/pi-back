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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/* ok */
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpForm form){
		UserEntity user = new UserEntity();
		user.setUsername(form.getUsername());
		user.setPassword(form.getPassword());

		if (user.errors.size() > 0)
			return ResponseEntity.badRequest().body(user.errors);
		
		user.getRoles().add(roleRepository.findByName("ROLE_USER"));
		
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
		if (Table.getCpf(register.getCpf()))
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado");
		
		userService.encodePassword(user);
		Table.addUsername(user.getUsername());
		return ResponseEntity.created(null).body(userService.saveUser(user));
	}
	
	/* ok */
	@PostMapping("/signin")
	public ResponseEntity<Map<String, String>> signIn(@RequestBody SignInForm form) {
		UserEntity user = userService.getUser(form.getUsername().toLowerCase());
		Map<String, String> response = new HashMap<>();
		
		if (user == null) {
			response.put("error", "Usuário inválido");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		if (!userService.matches(form.getPassword(), user)) {
			response.put("error", "Senha inválida");
			return ResponseEntity.badRequest().body(response);
		}
		
		response = Token.createTokens(user);
		response.put("username", user.getUsername());
		response.put("imgUrl", "https://www.newsclick.in/sites/default/files/2019-04/Deloitte.jpg");
		
		return ResponseEntity.accepted().body(response);
	}
	
	/* ok */	
	@GetMapping("/token-refresh")
	public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request) {
		String auth0 = request.getHeader(HttpHeaders.AUTHORIZATION);
		Map<String, String> map = Token.refresh(auth0, userService);
		if (map.size() > 1)
			return ResponseEntity.ok(map);
		return ResponseEntity.badRequest().body(map);
	}
	
	/* ok */
	@GetMapping("/providers")
	public ResponseEntity<List<ProviderForm>> getProvaders() {
		List<UserEntity> users = userService.getUsers();
		List<ProviderForm> providers = new ArrayList<>();
		users.forEach(user -> {
			if (user.getProfile().getServices().size() > 0) {
				ProviderForm provider = new ProviderForm(user);
				providers.add(provider);
			}	
		});
		
		return ResponseEntity.ok(providers);
	}
	
}
