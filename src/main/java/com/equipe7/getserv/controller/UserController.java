package com.equipe7.getserv.controller;
/*
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.equipe7.getserv.controller.form.SignUpForm;
import com.equipe7.getserv.model.AddressEntity;
import com.equipe7.getserv.model.PhoneNumberEntity;
import com.equipe7.getserv.model.UserEntity;
import com.equipe7.getserv.model.RegisterEntity;
import com.equipe7.getserv.repository.UsuarioRepository;*/

/*
@RestController
@RequestMapping
@CrossOrigin(value = "*", allowedHeaders = "*", origins = "*", maxAge = 3600)*/
public class UserController {
	/*
	@Autowired
	private UsuarioRepository repository;
	
	@GetMapping("/u")
	public RegisterEntity getUser() {
		return new RegisterEntity();
	}
	
	@PutMapping("/test/{id}")
	public ResponseEntity<?> test(@RequestBody RegisterEntity user, @PathVariable Long id){
		RegisterEntity oldUser = repository.findById(id).orElse(new RegisterEntity());
		if (oldUser.getId() == null)
			return ResponseEntity.badRequest().body("Usuário Invalido");
		
		return ResponseEntity.ok().body(repository.save(updateTest(oldUser, user)));
	}
	
	private String update(String oldUser, String newUser) {
		if (oldUser != newUser || newUser != null)
			return newUser;
		return oldUser;
	}
	
	private RegisterEntity updateTest(RegisterEntity o, RegisterEntity n) {
		o.setName(update(o.getName(), n.getName()));
		o.setCpf(update(o.getCpf(), n.getCpf()));
		o.setEmail(update(o.getEmail(), n.getEmail()));
		//o.setPassword(update(o.getPassword(), n.getPassword()));
		//o.setUsername(update(o.getUsername(), n.getUsername()));
		
		for (int i = 0; i < o.getPhoneNumbers().size(); i++) {
			o.getPhoneNumbers().get(i).setDdi(
					n.getPhoneNumbers().get(i).getDdi());
			o.getPhoneNumbers().get(i).setDdd(
					n.getPhoneNumbers().get(i).getDdd());
			o.getPhoneNumbers().get(i).setNumero(
					n.getPhoneNumbers().get(i).getNumero());
		}
		
		for (int i = 0; i < o.getAddresses().size(); i++) {
			o.getAddresses().get(i).setBairro(
					n.getAddresses().get(i)
					.getBairro());
			o.getAddresses().get(i).setCep(
					n.getAddresses().get(i)
					.getCep());
			o.getAddresses().get(i).setCidade(
					n.getAddresses().get(i)
					.getCidade());
			o.getAddresses().get(i).setComplemento(
					n.getAddresses().get(i)
					.getComplemento());
			o.getAddresses().get(i).setEstado(
					n.getAddresses().get(i)
					.getEstado());
			o.getAddresses().get(i).setNumero(
					n.getAddresses().get(i)
					.getNumero());
			o.getAddresses().get(i).setRua(
					n.getAddresses().get(i)
					.getRua());
		}
		
		return o;
	}
	
	@GetMapping("/all/users")
	public ResponseEntity<List<RegisterEntity>> getAllUsers(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/u/{id}")
	public ResponseEntity<RegisterEntity> getByUsername(@PathVariable Long id){
		return repository.findById(id)
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
		return ResponseEntity.ok().body(repository.save(user));
	}
	
	@PostMapping("/register/{id}/enderecos")
	public ResponseEntity<?> postEnderecos(@RequestBody List<Endereco> enderecos, @PathVariable Long id) {
		RegisterEntity user = repository.findById(id).orElse(new RegisterEntity());
		if (user.getId() == null)
			return ResponseEntity.badRequest().body("Usuário Invalido");
		
		user.setAddresses(enderecos);
		for (int i = 0; enderecos.size() > i; i++) {
			List<String> errors = validateCep(enderecos.get(i));
			if (errors.size() > 0)
				return ResponseEntity.badRequest().body(errors);
		}
		user.getAddresses().forEach(cep -> cep.setUsuario(user));
		return ResponseEntity.ok().body(repository.save(user));
	}
	
	@PostMapping("/register/{id}/telefones")
	public ResponseEntity<?> postTelefones(@RequestBody List<Telefone> telefones, @PathVariable Long id) {
		RegisterEntity user = repository.findById(id).orElse(new RegisterEntity());
		if (user.getId() == null)
			return ResponseEntity.badRequest().body("Usuário Invalido");
		
		user.setPhoneNumbers(telefones);
		for (int i = 0; telefones.size() > i; i++) {
			List<String> errors = validateTel(telefones.get(i));
			if (errors.size() > 0)
				return ResponseEntity.badRequest().body(errors);
		}
		user.getPhoneNumbers().forEach(tel -> tel.setUsuario(user));
		return ResponseEntity.ok().body(repository.save(user));
	}

	@DeleteMapping("/register/id/{id}")
	public ResponseEntity delete(@PathVariable Long id){
		if(repository.existsById(id)){
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	private List<String> validateUser(RegisterEntity user) {
		return errors(new ArrayList<>(),
			validateField(user.getCpf(), Regex.DIGITO, 11, 11),
			validateField(user.getName(), Regex.NOME, 2, 128),
			validateField(user.getEmail(), Regex.EMAIL, 8, 64),
			//validateField(user.getUsername(), Regex.USERNAME, 3, 32),
			//validateField(user.getPassword(), Regex.SENHA, 8, 32),
			validateField(user.getBirthday())
		);
	}
	
	private List<String> validateCep(Endereco cep) {
		return errors(new ArrayList<>(),
			validateField(cep.getBairro(), Regex.NOME, 2, 64),
			validateField(cep.getCep(), Regex.DIGITO, 6, 9),
			validateField(cep.getCidade(), Regex.NOME, 2, 64),
			validateField(cep.getEstado(), Regex.NOME, 2, 32),
			validateField(cep.getNumero(), Regex.DIGITO, 1, 8),
			validateField(cep.getRua(), Regex.NOME, 2, 128)
		);	
	}
	
	private List<String> validateTel(Telefone tel) {
		return errors(new ArrayList<>(),
			validateField(tel.getDdi(), Regex.DIGITO, 1, 4),
			validateField(tel.getDdd(), Regex.DIGITO, 1, 5),
			validateField(tel.getNumero(), Regex.DIGITO, 7, 12)
		);
	}
	
	private String validateField(String field, Regex regex, int min, int max) {
		if (!field.matches(regex(regex, min, max)))
			return field;
		return null;
	}
	
	private String validateField(Date field) {
		return null;
	}
	
	private List<String> errors(List<String> errorList, String... errors) {
		for (String check : errors) {
			if (check != null) {
				errorList.add(check);
			}
		}
		return errorList;
	}*/
}