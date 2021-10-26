package com.equipe7.getserv.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.model.Endereco;
import com.equipe7.getserv.model.Telefone;
import com.equipe7.getserv.model.Usuario;
import com.equipe7.getserv.repository.UsuarioRepository;

enum Regex{
	DIGITO, EMAIL, NOME, SENHA, USERNAME
}

@RestController
@RequestMapping
@CrossOrigin(value = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;

	@PostMapping("/register")
	public ResponseEntity<?> postUsuario(@RequestBody Usuario user) {
		List<String> errors = validateUser(user);
		if (errors.size() > 0)
			return ResponseEntity.badRequest().body(errors);
		user.setTelefones(user.getTelefones());
		user.setEnderecos(user.getEnderecos());
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
	}
	
	@PostMapping("/register/enderecos")
	public ResponseEntity<?> postEnderecos(@RequestBody Usuario user) {
		for (int i = 0; user.getEnderecos().size() > i; i++) {
			List<String> errors = validateCep(user.getEnderecos().get(i));
			if (errors.size() > 0)
				return ResponseEntity.badRequest().body(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
	}
	
	@PostMapping("/register/telefones")
	public ResponseEntity<?> postTelefones(@RequestBody Usuario user) {
		for (int i = 0; user.getEnderecos().size() > i; i++) {
			List<String> errors = validateTel(user.getTelefones().get(i));
			if (errors.size() > 0)
				return ResponseEntity.badRequest().body(errors);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
	}
	
	private List<String> validateUser(Usuario user) {
		return errors(new ArrayList<>(),
			validateField(user.getCpf(), Regex.DIGITO, 11, 11),
			validateField(user.getNome(), Regex.NOME, 2, 128),
			validateField(user.getEmail(), Regex.EMAIL, 8, 64),
			validateField(user.getUsername(), Regex.USERNAME, 3, 32),
			validateField(user.getSenha(), Regex.SENHA, 8, 32),
			validateField(user.getNascimento())
		);
	}
	
	private List<String> validateCep(Endereco cep) {
		return errors(new ArrayList<>(),
			validateField(cep.getBairro(), Regex.NOME, 2, 64),
			validateField(cep.getCep(), Regex.DIGITO, 6, 9),
			validateField(cep.getCidade(), Regex.NOME, 2, 64),
			validateField(cep.getComplemento(), Regex.NOME, 2, 128),
			validateField(cep.getEstado(), Regex.NOME, 2, 32),
			validateField(cep.getNumero(), Regex.DIGITO, 1, 8),
			validateField(cep.getRua(), Regex.DIGITO, 2, 128)
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
		String limit = "{"+ min + "," + max + "}";
		switch (regex) {
			case DIGITO:
				return "(\\d)"+limit;
			case USERNAME: 
				return "([a-zA-Z\\d])"+limit;
			case NOME:
				return "([a-zA-ZÀ-ú]+(\\s)?)"+limit;
			case SENHA: 
				return "([\\w\\@\\.\\!\\#\\$\\%\\&\\*\\-\\+\\=\\_\\,])"+limit;
			case EMAIL:
				return "^[^\\_\\.\\-][\\w\\d\\.\\-]{4,}(?<![\\.\\_\\-])\\@\\w{2,}(\\.{1}[a-zA-Z]{2,}){1,2}(?!\\.)$";
			default: return "";
		}
	}
}