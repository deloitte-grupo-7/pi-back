package com.equipe7.getserv.model;

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
import javax.persistence.Transient;

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

	/* -- */
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private UserEntity user;

	@JsonIgnoreProperties("profile")
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ServiceEntity> services = new HashSet<>();

	@JsonIgnoreProperties("profile")
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<RateEntity> rates = new HashSet<>();
	
	@JsonIgnore
	@Transient
	public Map<String, String> errors = new HashMap<>();
	
	public ProfileEntity() {
		super();
		this.id = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	
	/* -- */

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Set<ServiceEntity> getServices() {
		return services;
	}

	public void setServices(Set<ServiceEntity> services) {
		this.services = services;
	}

	public Set<RateEntity> getRates() {
		return rates;
	}

	public void setRates(Set<RateEntity> rates) {
		this.rates = rates;
	}
	
	/* -- */

	public void setUserDep(UserEntity user) {
		user.setProfile(this);
		this.user = user;
	}

	public void addService(ServiceEntity service) {
		service.setProfile(this);
		services.add(service);
	}

	public void setServicesDep(Set<ServiceEntity> services) {
		services.forEach(service -> service.setProfile(this));
		this.services = services;
	}

	public void addRate(RateEntity rate) {
		rate.setProfile(this);
		rates.add(rate);
	}

	public void setRatesDep(Set<RateEntity> rates) {
		rates.forEach(rate -> rate.setProfile(this));
		this.rates = rates;
	}
	
}
