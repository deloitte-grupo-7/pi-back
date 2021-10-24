package com.equipe7.getserv.controller;

import com.equipe7.getserv.model.Endereco;
import com.equipe7.getserv.model.Telefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.model.Usuario;
import com.equipe7.getserv.repository.UsuarioRepository;

@RestController
@RequestMapping
@CrossOrigin(value = "*", allowedHeaders = "*")
public class UsuarioController {
	// kutebrowser
	@Autowired
	private UsuarioRepository repository;

	@PostMapping("/register")
	public ResponseEntity<Usuario> post(@RequestBody Usuario user) {
		if (!user.getCpf().matches("\\d+"))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		if (!user.getNome().matches("[a-zA-ZÀ-ú\\s]+"))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		if (!user.getEmail().matches("^(.+)@(.+)$"))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		if (!user.getUsername().matches("[a-zA-Z\\d]+"))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		for (Telefone telefone : user.getTelefones()) {
			if (!telefone.getDdd().matches("\\d+"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			if (!telefone.getDdi().matches("\\d+"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			if (!telefone.getTelefone().matches("\\d+"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}

		for (Endereco endereco : user.getEnderecos()) {
			if (!endereco.getEstado().matches("[a-zA-ZÀ-ú\\s]+"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			if (!endereco.getCidade().matches("[a-zA-ZÀ-ú\\s]+"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			if (!endereco.getBairro().matches("[a-zA-ZÀ-ú\\s]+"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			if (!endereco.getRua().matches("[a-zA-ZÀ-ú\\s]+"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			if (!endereco.getCep().matches("\\d+"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			if (!endereco.getNumero().matches("\\d+"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
	}
}
