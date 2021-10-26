package com.equipe7.getserv.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@Size(min = 1, max=128)
    private String name;

	@NotNull
	@Column(unique = true)
	@Size(min = 11, max=11)
    private String cpf;

	@NotNull
	@Column(unique = true)
	@Size(min = 8, max=64)
    private String email;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
    private Date birthday = new java.sql.Date(System.currentTimeMillis());
	
	@NotNull
	@Column(unique = true)
	@Size(min = 3, max = 32)
    private String username;

	@NotNull
	@Size(min = 8, max = 128)
    private String password;

	@Size(min = 3, max = 32)
    private String passconf;
	

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("usuario")
	private List<Telefone> phoneNumbers = new ArrayList<>();
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("usuario")
	private List<Endereco> addresses = new ArrayList<>();

    public Usuario(){

    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		setName(this.name);
		return name;
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		setEmail(this.email);
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getUsername() {
		setUsername(this.username);
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

	public List<Telefone> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<Telefone> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<Endereco> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Endereco> addresses) {
		this.addresses = addresses;
	}

	public String getPassconf() {
		return passconf;
	}

	public void setPassconf(String passconf) {
		this.passconf = passconf;
	}
    
}
