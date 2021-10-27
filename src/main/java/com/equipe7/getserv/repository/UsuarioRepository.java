package com.equipe7.getserv.repository;

import java.util.Optional;

import javax.swing.Spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equipe7.getserv.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Optional<Usuario> findByUsername(Spring username);
	
}
