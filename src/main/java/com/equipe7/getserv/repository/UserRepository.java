package com.equipe7.getserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipe7.getserv.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findByUsername(String username);
	
}
