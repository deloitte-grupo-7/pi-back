package com.equipe7.getserv.model;

import java.util.ArrayList;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "_profile")
public class ProfileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "picture", length = 512)
	private String pictureURL;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private UserEntity user;

	@JsonIgnoreProperties("profile_id")
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ServiceEntity> services = new ArrayList<>();

	@JsonIgnoreProperties("profile_id")
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RateEntity> rates = new ArrayList<>();
	
	public ProfileEntity() {
		super();
		this.id = null;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		user.setProfile(this);
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<ServiceEntity> getServices() {
		services.forEach(service -> service.setProfile(this));
		return services;
	}

	public void setServices(List<ServiceEntity> services) {
		this.services = services;
	}

	public List<RateEntity> getRates() {
		rates.forEach(rate -> rate.setProfile(this));
		return rates;
	}

	public void setRates(List<RateEntity> rates) {
		this.rates = rates;
	}
	
}
