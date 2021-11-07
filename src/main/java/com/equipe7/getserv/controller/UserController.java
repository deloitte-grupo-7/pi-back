package com.equipe7.getserv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.equipe7.getserv.controller.form.PostForm;
import com.equipe7.getserv.model.ProfileEntity;
import com.equipe7.getserv.model.UserEntity;
import com.equipe7.getserv.model.ServiceEntity;
import com.equipe7.getserv.repository.RoleRepository;
import com.equipe7.getserv.repository.UserRepository;
import com.equipe7.getserv.service.UserService;


@RestController
@RequestMapping("/u")
public class UserController {
	
	private final UserService userSv;
	
	@Autowired
	private UserRepository userRp;

	@Autowired
	private RoleRepository roleRp;
	
	
	public UserController(UserService userSv, UserRepository userRp, RoleRepository roleRp) {
		super();
		this.userSv = userSv;
		this.userRp = userRp;
		this.roleRp = roleRp;
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getUser(@PathVariable String username) {
		UserEntity user = userSv.getUser(username);
		if (user == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.status(HttpStatus.FOUND).body(user);
	}

	@GetMapping("/{username}/test")
	public ResponseEntity<?> getTest(@PathVariable String username) {
		UserEntity user = userSv.getUser(username);
		if (user == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.status(HttpStatus.FOUND).body(user.getRegister());
	}
	
	@PostMapping("/{username}")
	public ResponseEntity<?> postService(@RequestBody PostForm form, @PathVariable String username) {
		UserEntity user = userSv.getUser(username);
		if (user == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário Inválido");
		
		ProfileEntity profile = new ProfileEntity();
		user.setProfile(profile);
		ServiceEntity service = new ServiceEntity();
		
		service.setTitle(form.getTitle());
		service.setImageURL(form.getImgUrl());
		service.setDescription(form.getDescription());
		
		profile.getServices().add(service);
		profile.setUser(user);
		
		userSv.saveUser(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(form);
	}
	
	/*
	@PutMapping("/{username}")
	public ResponseEntity<?> test(@RequestBody RegisterEntity form, @PathVariable String username){
		RegisterEntity oldUser = userRp.findByUsername(username).orElse(new RegisterEntity());
		if (oldUser.getId() == null)
			return ResponseEntity.badRequest().body("Usuário Invalido");
		
		return ResponseEntity.ok().body(userRp.save(updateTest(oldUser, user)));
	}
	*/
	@GetMapping("/all/all")
	public ResponseEntity<?> getAllUsers(){
		return ResponseEntity.ok(userRp.findAll());
	}
	/*
	@GetMapping("/u/{id}")
	public ResponseEntity<RegisterEntity> getByUsername(@PathVariable Long id){
		return userRp.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/register")
	public ResponseEntity<?> postUsuario(@RequestBody RegisterEntity user) {
		List<String> errors = validateUser(user);
		if (errors.size() > 0)
			return ResponseEntity.badRequest().body(errors);
		user.setPhoneNumbers(user.getPhoneNumbers());
		user.setAddresses(user.getAddresses());
		return ResponseEntity.ok().body(userRp.save(user));
	}
	
	@PostMapping("/register/{id}/enderecos")
	public ResponseEntity<?> postEnderecos(@RequestBody List<Endereco> enderecos, @PathVariable Long id) {
		RegisterEntity user = userRp.findById(id).orElse(new RegisterEntity());
		if (user.getId() == null)
			return ResponseEntity.badRequest().body("Usuário Invalido");
		
		user.setAddresses(enderecos);
		for (int i = 0; enderecos.size() > i; i++) {
			List<String> errors = validateCep(enderecos.get(i));
			if (errors.size() > 0)
				return ResponseEntity.badRequest().body(errors);
		}
		user.getAddresses().forEach(cep -> cep.setUsuario(user));
		return ResponseEntity.ok().body(userRp.save(user));
	}
	
	@PostMapping("/register/{id}/telefones")
	public ResponseEntity<?> postTelefones(@RequestBody List<Telefone> telefones, @PathVariable Long id) {
		RegisterEntity user = userRp.findById(id).orElse(new RegisterEntity());
		if (user.getId() == null)
			return ResponseEntity.badRequest().body("Usuário Invalido");
		
		user.setPhoneNumbers(telefones);
		for (int i = 0; telefones.size() > i; i++) {
			List<String> errors = validateTel(telefones.get(i));
			if (errors.size() > 0)
				return ResponseEntity.badRequest().body(errors);
		}
		user.getPhoneNumbers().forEach(tel -> tel.setUsuario(user));
		return ResponseEntity.ok().body(userRp.save(user));
	}*/

	@DeleteMapping("/register/id/{id}")
	public ResponseEntity delete(@PathVariable Long id){
		if(userRp.existsById(id)){
			userRp.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}