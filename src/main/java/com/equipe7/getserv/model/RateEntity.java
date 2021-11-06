package com.equipe7.getserv.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "_rate")
public class RateEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user", "profiles", "services"})
	private ProfileEntity profile;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user", "profiles", "services"})
	private ServiceEntity service;
	
	@Column(nullable = true)
	@JsonIgnoreProperties("rate")
	@OneToMany(mappedBy = "rate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CommentEntity> comments = new ArrayList<>();
	
	@Column(nullable = true)
	private double rate;

	public RateEntity() {
		super();
	}

	public RateEntity(Long id, ProfileEntity profile, ServiceEntity service, double rate) {
		super();
		this.profile = profile;
		this.service = service;
		this.rate = rate;
	}

	public ProfileEntity getProfile() {
		return profile;
	}

	public void setProfile(ProfileEntity profile) {
		this.profile = profile;
	}

	public ServiceEntity getService() {
		return service;
	}

	public void setService(ServiceEntity service) {
		this.service = service;
	}
	
	public double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate > 5d || rate < 0d ? 0d : rate;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}
	
}
