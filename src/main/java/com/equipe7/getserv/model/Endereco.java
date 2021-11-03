package com.equipe7.getserv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JsonIgnoreProperties({"enderecos", "telefones"})
    private RegisterEntity usuario;
	
	@NotNull
	@Size(min = 1, max = 128)
	private String rua;
	
	@NotNull
	@Size(min = 1, max = 8)
	private String numero;
	
	@Size(min = 1, max = 64)
	private String bairro;
	
	@NotNull
	@Size(min = 1, max = 64)
	private String cidade;
	
	@NotNull
	@Size(min = 1, max = 32)
	private String estado;
	
	@NotNull
	@Size(min = 6, max = 9)
	private String cep;

	@Size(min = 1, max = 128)
	private String complemento;
	
    public Endereco (){    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		setRua(this.rua);
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua.toUpperCase();
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		setBairro(this.bairro);
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro.toUpperCase();
	}

	public String getCidade() {
		setCidade(this.cidade);
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade.toUpperCase();
	}

	public String getEstado() {
		setEstado(this.estado);
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado.toUpperCase();
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public RegisterEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(RegisterEntity usuario) {
		this.usuario = usuario;
	}
}
