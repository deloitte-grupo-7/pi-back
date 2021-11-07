package com.equipe7.getserv.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.controller.form.PostForm;
import com.equipe7.getserv.model.ServiceEntity;
import com.equipe7.getserv.service.ServiceService;

@RestController
@RequestMapping("/services")
public class ServiceController {

	private ServiceService servService;

	public ServiceController(ServiceService servService) {
		super();
		this.servService = servService;
	}
	
	@GetMapping
	public ResponseEntity<Set<PostForm>> getServices(){
		Set<ServiceEntity> services = servService.getServices();
		Set<PostForm> response = new HashSet<>();
		services.forEach(service -> response.add(new PostForm(service)));
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getService(@PathVariable Long id){
		ServiceEntity service = servService.getService(id);
		if (service == null)
			return ResponseEntity.notFound().build();
		
		PostForm response = new PostForm(service);
		return ResponseEntity.ok(response);
	}
	
}
