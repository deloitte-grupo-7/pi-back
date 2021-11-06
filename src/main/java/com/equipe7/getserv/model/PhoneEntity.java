package com.equipe7.getserv.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.equipe7.getserv.resource.Regex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "_phone")
public class PhoneEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
	@JoinColumn(name = "register_id")
	@JsonIgnoreProperties({"phones_id", "addresses_id", "user_id"})
    private RegisterEntity register;
	
	@Column(length=3, nullable=false)
	private String ddi = "55";
	
	@Column(length=4, nullable=false)
	private String ddd;
	
	@Column(length=16, nullable=false)
    private String number;

	@JsonIgnore
	@Transient
	public Map<String, String> errors = new HashMap<>();

	public PhoneEntity(){

    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDdi() {
		if (Regex.digit(ddi, 1, 3, true))
			errors.put("ddi", ddi);
		return ddi;
	}

	public void setDdi(String ddi) {
		this.ddi = ddi;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		if (Regex.digit(ddd, 1, 4, false))
			errors.put("ddd", ddd);
		this.ddd = ddd;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		if (Regex.digit(number, 5, 16, false))
			errors.put("number", number);
		this.number = number;
	}

	public RegisterEntity getRegister() {
		return register;
	}

	public void setRegister(RegisterEntity register) {
		this.register = register;
	}
    
}
