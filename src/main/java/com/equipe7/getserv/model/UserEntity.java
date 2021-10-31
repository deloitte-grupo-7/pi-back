package com.equipe7.getserv.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_user")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(max = 31)
	@Column(unique = true)
	private String username;
	
	@NotNull
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<RoleEntity> roles = new ArrayList<>();

	public UserEntity() {
		super();
		this.id = null;
	}

	public UserEntity(Long id, String username, String password, Collection<RoleEntity> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Collection<RoleEntity> roles) {
		this.roles = roles;
	}
	
}
