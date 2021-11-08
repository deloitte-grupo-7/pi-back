package com.equipe7.getserv.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.equipe7.getserv.model.ServiceEntity;
import com.equipe7.getserv.repository.ServiceRepository;

@Service
public class ServiceService {
	
	private final ServiceRepository repository;

	public ServiceService(ServiceRepository repository) {
		super();
		this.repository = repository;
	}
	
	public Set<ServiceEntity> getServices() {
		Set<ServiceEntity> hash = new HashSet<>();
		List<ServiceEntity> list = repository.findAll();
		list.forEach(service -> hash.add(service));
		return hash;
	}
	
	public ServiceEntity getService(Long id) {
		return repository.getById(id);
	}
	
	public void delService(Long id) {
		repository.deleteById(id);
	}

}
