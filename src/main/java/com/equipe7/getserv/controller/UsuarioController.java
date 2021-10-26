package com.equipe7.getserv.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.model.Endereco;
import com.equipe7.getserv.model.Telefone;
import com.equipe7.getserv.model.Usuario;
import com.equipe7.getserv.repository.UsuarioRepository;

enum Regex{
	ANY, DIGITO, EMAIL, NOME, SENHA, USERNAME
}

@RestController
@RequestMapping
@CrossOrigin(value = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@GetMapping("/users")
	public ResponseEntity<List<Usuario>> getAllUsers(){
		return ResponseEntity.ok(repository.findAll());
	}

	@PostMapping("/register")
	public ResponseEntity<?> postUsuario(@RequestBody Usuario user) {
		if (user.getPassword() == user.getPassconf())
			user.setPassconf("confirmed");
		else return ResponseEntity.badRequest().body("Senhas não coinci");
		List<String> errors = validateUser(user);
		if (errors.size() > 0)
			return ResponseEntity.badRequest().body(errors);
		user.setPhoneNumbers(user.getPhoneNumbers());
		user.setAddresses(user.getAddresses());
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
	}
	
	@PostMapping("/register/{id}/enderecos")
	public ResponseEntity<?> postEnderecos(@RequestBody List<Endereco> enderecos, @PathVariable Long id) {
		Usuario user = repository.findById(id).orElse(new Usuario());
		if (user.getId() == null)
			return ResponseEntity.badRequest().body("Usuário Invalido");
		
		user.setAddresses(enderecos);
		for (int i = 0; enderecos.size() > i; i++) {
			List<String> errors = validateCep(enderecos.get(i));
			if (errors.size() > 0)
				return ResponseEntity.badRequest().body(errors);
		}
		return ResponseEntity.ok().body(repository.save(user));
	}
	
	@PostMapping("/register/{id}/telefones")
	public ResponseEntity<?> postTelefones(@RequestBody List<Telefone> telefones, @PathVariable Long id) {
		Usuario user = repository.findById(id).orElse(new Usuario());
		if (user.getId() == null)
			return ResponseEntity.badRequest().body("Usuário Invalido");
		
		user.setPhoneNumbers(telefones);
		for (int i = 0; telefones.size() > i; i++) {
			List<String> errors = validateTel(telefones.get(i));
			if (errors.size() > 0)
				return ResponseEntity.badRequest().body(errors);
		}
		return ResponseEntity.ok().body(repository.save(user));
	}
	
	private List<String> validateUser(Usuario user) {
		return errors(new ArrayList<>(),
			validateField(user.getCpf(), Regex.DIGITO, 11, 11),
			validateField(user.getName(), Regex.NOME, 2, 128),
			validateField(user.getEmail(), Regex.EMAIL, 8, 64),
			validateField(user.getUsername(), Regex.USERNAME, 3, 32),
			validateField(user.getPassword(), Regex.SENHA, 8, 32),
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
	}
	
	private String regex(Regex regex, int min, int max) {
		String limit = "^(?=.{"+ min + "," + max + "}$)";
		switch (regex) {
			case ANY:
				return limit + ".+";
			case DIGITO:
				return limit + "\\d+";
			case USERNAME: 
				return limit + "(?=.*[a-zA-Z])+[\\w.-]+";
			case NOME:
				return limit + "([a-zA-ZÀ-ú]+[\\s]?)+";
			case SENHA: 
				return limit + "[\\w.@!#$&*-+=_]+";
			case EMAIL:
				return limit + "^(?=.{1,}$)^[^_.-][\\w\\d.-]{4,}(?<![._-])@\\w{2,}(\\.{1}[a-zA-Z]{2,}){1,2}(?!\\.)$";
			default: return "";
		}
	}
}