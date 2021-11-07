package com.equipe7.getserv.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import com.equipe7.getserv.resource.Regex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "_register")
public class RegisterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, length = 255)
    private String name = "";

	@Column(nullable = false)
    private String cpf = "";

	@Column(unique = true, nullable = false, length = 128)
    private String email = "";
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
	
	/* -- */
	
	@JsonIgnoreProperties("register")
	@OneToMany(mappedBy = "register", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<PhoneEntity> phones = new HashSet<>();
	
	@JsonIgnoreProperties("register")
	@OneToMany(mappedBy = "register", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<AddressEntity> addresses = new HashSet<>();
	
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
			Set<PhoneEntity> phones, Set<AddressEntity> addresses) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.birthday = birthday;
		this.phones = phones;
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
		if (Regex.digit(cpf, 11, 11, false))
			errors.put("cpf", cpf);
		/*else {
			char[] digits = cpf.toCharArray();
			for (int d = 0; d < 2; d++) {
				Integer digit = 0;
				for (int i = 0; i < 9 + d; i++)
					digit += Character.getNumericValue
						(digits[i]) * ((10 + d) - i);
				digit = (11 - (digit%11)) % 10;
				digits[d+9] = digit.toString().charAt(0);
			}
			
			if (new String(digits).subSequence(0, 11) != cpf)
				errors.put("cpf", "inválido [" + new String(digits).substring(9) + "]");
		}*/
		
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
	
	/* -- */

	public Set<PhoneEntity> getPhones() {
		return phones;
	}

	public void setPhones(Set<PhoneEntity> phones) {
		this.phones = phones;
	}

	public Set<AddressEntity> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<AddressEntity> addresses) {
		this.addresses = addresses;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	/* -- */

	public void addPhone(PhoneEntity phone) {
		phone.setRegister(this);
		phones.add(phone);
	}

	public void addAddress(AddressEntity address) {
		address.setRegister(this);
		addresses.add(address);
	}

	public void setPhonesDep(Set<PhoneEntity> phones) {
		phones.forEach(phone -> phone.setRegister(this));
		this.phones = phones;
	}

	public void setAddressesDep(Set<AddressEntity> addresses) {
		addresses.forEach(address -> address.setRegister(this));
		this.addresses = addresses;
	}
	
	public void setUserDep(UserEntity user) {
		user.setRegister(this);
		this.user = user;
	}
}
