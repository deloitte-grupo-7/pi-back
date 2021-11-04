package com.equipe7.getserv.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.equipe7.getserv.resource.Regex;
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
	@Size(max=11)
    private String cpf = "";

	@NotNull
	@Column(unique = true)
	@Size(max=128)
    private String email = "";
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
    private Date birthday = new java.sql.Date(System.currentTimeMillis());
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("usuario")
	private List<PhoneNumberEntity> phoneNumbers = new ArrayList<>();
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("usuario")
	private List<AddressEntity> addresses = new ArrayList<>();
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private UserEntity user;
	
	@JsonIgnore
	@Transient
	public Map<String, String> errors = new HashMap<>(); 

    public RegisterEntity(){
    	super();
    }
    
	public RegisterEntity(Long id, String name, String cpf, String email, Date birthday,
			List<PhoneNumberEntity> phoneNumbers, List<AddressEntity> addresses) {
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
		return name;
	}

	public void setName(String name) {
		if (Regex.name(name, 2, 128, false))
			errors.put("name", name);
		this.name = name.toUpperCase();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		if (Regex.password(cpf, 8, 32, false))
			errors.put("cpf", cpf);
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (Regex.password(email, 8, 128, false))
			errors.put("email", email);
		this.email = email.toLowerCase();
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		long eighteenYears = (18l * 365l * 1440l * 60000l) + 5760l;
		if (birthday.after(new Date(System.currentTimeMillis() - eighteenYears)))
			errors.put("birthday", birthday.toString());
		this.birthday = birthday;
	}

	public List<PhoneNumberEntity> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumberEntity> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<AddressEntity> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressEntity> addresses) {
		this.addresses = addresses;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
    
}
