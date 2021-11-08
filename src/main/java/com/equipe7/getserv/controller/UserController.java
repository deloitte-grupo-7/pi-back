package com.equipe7.getserv.controller;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.equipe7.getserv.controller.form.PostForm;
import com.equipe7.getserv.controller.form.ProfileForm;
import com.equipe7.getserv.model.UserEntity;
import com.equipe7.getserv.model.ServiceEntity;
import com.equipe7.getserv.repository.RoleRepository;
import com.equipe7.getserv.repository.UserRepository;
import com.equipe7.getserv.service.ServiceService;
import com.equipe7.getserv.service.UserService;


@RestController
@RequestMapping("/u")
public class UserController {
	
	private final UserService userSv;
	
	@Autowired
	private UserRepository userRp;

	@Autowired
	private RoleRepository roleRp;
	
	@Autowired
	private ServiceService serviceSv;
	
	
	public UserController(UserService userSv, UserRepository userRp, RoleRepository roleRp) {
		super();
		this.userSv = userSv;
		this.userRp = userRp;
		//this.roleRp = roleRp;
	}

//	@GetMapping("/users")
//	public ResponseEntity<?> getUsers() {
//		UserEntity user = userSv.getUsers();
//		if (user == null)
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		return ResponseEntity.status(HttpStatus.FOUND).body(user);
//	}

	@GetMapping({"/{username}", "/{username}/profile"})
	public ResponseEntity<?> getProfile(@PathVariable String username) {
		UserEntity user = userSv.getUser(username);
		if (user == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok().body(new ProfileForm(user));
	}

	/* ok */
	@GetMapping("/{username}/services")
	public ResponseEntity<?> getServices(@PathVariable String username) {
		UserEntity user = userSv.getUser(username);
		if (user == null)
			return ResponseEntity.badRequest().body("Usuário Inválido");
		
		Set<ServiceEntity> services = user.getProfile().getServices();
		Set<PostForm> response = new HashSet<>();
		services.forEach(service -> {
			response.add(new PostForm(service));
		});
		return ResponseEntity.ok().body(response); 
	}

	/* ok */
	@GetMapping("/{username}/services/{id}")
	public ResponseEntity<?> getServiceById(@PathVariable Long id, @PathVariable String username) {
		UserEntity user = userSv.getUser(username);
		if (user == null)
			return ResponseEntity.badRequest().body("Usuário Inválido");
		
		Set<ServiceEntity> services = user.getProfile().getServices();
		ServiceEntity response = new ServiceEntity();
		for (ServiceEntity service : services) {
			if (service.getId() == id) {
				response = service;
				break;
			}
		}
		
		if (response.getId() != null)
			return ResponseEntity.ok().body(new PostForm(response));
		
		return ResponseEntity.notFound().build(); 
	}
	
	/* ok */
	@PostMapping("/{username}/services")
	public ResponseEntity<?> postService(@RequestBody PostForm form, @PathVariable String username) {
		UserEntity user = userSv.getUser(username);
		if (user == null)
			return ResponseEntity.badRequest().body("Usuário Inválido");
		
		ServiceEntity service = new ServiceEntity();
		
		service.setTitle(form.getTitle());
		service.setImageURL(form.getImgUrl());
		service.setDescription(form.getDescription());
		
		user.getProfile().getServices().add(service);
		service.setProfileDep(user.getProfile());
		
		userSv.saveUser(user);
		
		form.setId(0L);
		userSv.getUser(username).getProfile().getServices()
			.forEach(serv -> {
				if (serv.getId() > form.getId())
					form.setId(serv.getId());
			});
		return ResponseEntity.created(null).body(form);
	}
	
	/* ok */
	@DeleteMapping("/{username}/services/{id}")
	public ResponseEntity<?> deleteService(@PathVariable String username, @PathVariable Long id){
		UserEntity user = userSv.getUser(username);
		if (user == null)
			return ResponseEntity.badRequest().body("Usuário Inválido");
		
		for (ServiceEntity service : user.getProfile().getServices())
			if (service.getId() == id) {
				service.getProfile().getServices().remove(service);
				serviceSv.delService(id);
				return ResponseEntity.ok("Deleted");
			}
		
		return ResponseEntity.notFound().build(); 
	}
	
	/* ok */
	@PutMapping("/{username}/services/{id}")
	public ResponseEntity<?> editService(@PathVariable String username, @PathVariable Long id, @RequestBody PostForm form){
		UserEntity user = userSv.getUser(username);
		if (user == null)
			return ResponseEntity.badRequest().body("Usuário Inválido");
		
		ServiceEntity service = new ServiceEntity();
		for (ServiceEntity serv : user.getProfile().getServices()) {
			if (serv.getId() == id) {
				service = serviceSv.getService(id);
				break;
			}
		}
		
		if (service.getId() == null)
			return ResponseEntity.notFound().build(); 
		
		service.update(form);
		serviceSv.saveService(service);
		return ResponseEntity.ok(form); 
	}
	
	/*
	@PutMapping("/{username}")
	public ResponseEntity<?> test(@RequestBody RegisterEntity form, @PathVariable String username){
		RegisterEntity oldUser = userRp.findByUsername(username).orElse(new RegisterEntity());
		if (oldUser.getId() == null)
			return ResponseEntity.badRequest().body("Usuário Invalido");
		
		return ResponseEntity.ok().body(userRp.save(updateTest(oldUser, user)));
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
	public ResponseEntity<?> delete(@PathVariable Long id){
		if(userRp.existsById(id)){
			userRp.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}