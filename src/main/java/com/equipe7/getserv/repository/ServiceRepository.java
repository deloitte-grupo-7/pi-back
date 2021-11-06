package com.equipe7.getserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equipe7.getserv.model.ServiceEntity;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long>{
	
	//public ServiceEntity findByUsername(Spring username);
	
}