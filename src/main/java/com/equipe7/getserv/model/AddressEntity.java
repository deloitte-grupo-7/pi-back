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
@Table(name = "_address")
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false,
			length = 255)
	private String rua;
	
	@Column(nullable = false,
			length = 8)
	private String numero;
	
	@Column(nullable = false,
			length = 128)
	private String bairro;
	
	@Column(nullable = false,
			length = 128)
	private String cidade;
	
	@Column(nullable = false,
			length = 128)
	private String estado;
	
	@Column(length = 9)
	private String cep;

	@Column(nullable = true,
			length = 512)
	private String complemento;
	
	/* -- */
	
	@ManyToOne
	@JoinColumn(name = "register_id")
	@JsonIgnoreProperties({"addresses", "phones", "user"})
    private RegisterEntity register;
	
	@JsonIgnore
	@Transient
	public Map<String, String> errors = new HashMap<>(); 
	
    public AddressEntity (){    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		if (Regex.name(rua, 1, 128, false))
			errors.put("rua", rua);
		this.rua = rua.toUpperCase();
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		if (Regex.digit(numero, 1, 8, false))
			errors.put("numero", numero);
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		if (Regex.name(bairro, 1, 64, true))
			errors.put("bairro", bairro);
		this.bairro = bairro.toUpperCase();
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		if (Regex.name(cidade, 1, 64, false))
			errors.put("cidade", cidade);
		this.cidade = cidade.toUpperCase();
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		if (Regex.name(estado, 1, 64, false))
			errors.put("estado", estado);
		this.estado = estado.toUpperCase();
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		if (Regex.digit(cep, 6, 9, true))
			errors.put("cep", cep);
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		if (Regex.any(complemento, 0, 255, true))
			errors.put("complemento", complemento);
		this.complemento = complemento.toUpperCase();
	}
	
	/* -- */

	public RegisterEntity getRegister() {
		return register;
	}

	public void setRegister(RegisterEntity register) {
		this.register = register;
	}
	
	/* -- */

	public void setRegisterDep(RegisterEntity register) {
		register.getAddresses().add(this);
		this.register = register;
	}
	
}
