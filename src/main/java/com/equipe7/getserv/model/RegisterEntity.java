package com.equipe7.getserv.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_register")
public class RegisterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@Size(max=128)
    private String name = "";

	@NotNull
	@Column(unique = true)
	@Size(min = 11, max=11)
    private String cpf = "";

	@NotNull
	@Column(unique = true)
	@Size(min = 8, max=64)
    private String email = "";
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
    private Date birthday = new java.sql.Date(System.currentTimeMillis());
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("usuario")
	private List<Telefone> phoneNumbers = new ArrayList<>();
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("usuario")
	private List<Endereco> addresses = new ArrayList<>();
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private UserEntity user;

    public RegisterEntity(){
    	super();
    }
    
	public RegisterEntity(Long id, String name, String cpf, String email, Date birthday,
			List<Telefone> phoneNumbers, List<Endereco> addresses) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.birthday = birthday;
		this.phoneNumbers = phoneNumbers;
		this.addresses = addresses;
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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
    
}
