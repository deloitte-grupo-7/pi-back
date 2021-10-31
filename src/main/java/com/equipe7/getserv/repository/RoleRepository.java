package com.equipe7.getserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipe7.getserv.model.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{

	RoleEntity findByName(String name);
	
}
