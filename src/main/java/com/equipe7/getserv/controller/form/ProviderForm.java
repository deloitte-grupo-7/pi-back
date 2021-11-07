package com.equipe7.getserv.controller.form;

import java.util.HashSet;
import java.util.Set;

public class ProviderForm extends ProfileForm{
	
	protected Set<PostForm> services = new HashSet<>();
	
	public ProviderForm() {
		super();
	}

	public ProviderForm(String username, String name, String about, String imgUrl, Integer rate) {
		super(username, name, about, imgUrl, rate);
	}
	
	public Set<PostForm> getServices() {
		return services;
	}
	
	public void setServices(Set<PostForm> services) {
		this.services = services;
	}

}
