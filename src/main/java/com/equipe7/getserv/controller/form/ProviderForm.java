package com.equipe7.getserv.controller.form;

import java.util.HashSet;
import java.util.Set;

import com.equipe7.getserv.model.UserEntity;

public class ProviderForm extends ProfileForm{
	
	protected Set<PostForm> services = new HashSet<>();
	
	public ProviderForm() {
		super();
	}

	public ProviderForm(String username, String name, String description, String imgUrl, Integer rating, String tagline) {
		super(username, name, description, imgUrl, rating, tagline);
	}
	
	public ProviderForm(UserEntity user) {
		super(user);
		user.getProfile().getServices().forEach(service -> {
			services.add(new PostForm(service));			
		});
	}
	
	public Set<PostForm> getServices() {
		return services;
	}
	
	public void setServices(Set<PostForm> services) {
		this.services = services;
	}

}
