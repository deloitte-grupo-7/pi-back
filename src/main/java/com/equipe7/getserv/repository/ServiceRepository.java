package com.equipe7.getserv.repository;

import java.util.Optional;

import javax.swing.Spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equipe7.getserv.model.ServiceEntity;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long>{
	
	public Optional<ServiceEntity> findByTitle(Spring title);
	
}