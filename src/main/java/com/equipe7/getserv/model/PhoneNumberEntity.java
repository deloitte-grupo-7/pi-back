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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.equipe7.getserv.resource.Regex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_phone")
public class PhoneNumberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
	@JsonIgnoreProperties({"phones", "addresses"})
	@JoinColumn(name = "register_id")
    private RegisterEntity register;
	
	@Size(max = 3)
	private String ddi = "55";
	
	@NotNull
	@Size(max = 4)
	private String ddd;
	
	@NotNull
	@Size(max = 16)
    private String numero;

	@JsonIgnore
	@Transient
	public Map<String, String> errors = new HashMap<>();

	public PhoneNumberEntity(){

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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		if (Regex.digit(numero, 5, 16, false))
			errors.put("numero", numero);
		this.numero = numero;
	}

	public RegisterEntity getRegister() {
		return register;
	}

	public void setRegister(RegisterEntity register) {
		this.register = register;
	}
    
}
