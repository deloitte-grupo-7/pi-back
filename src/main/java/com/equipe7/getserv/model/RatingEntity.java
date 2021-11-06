package com.equipe7.getserv.model;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_rating")
public class RatingEntity implements Serializable{
	/*
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;*/
	
	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private ProfileEntity profile;
	
	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private ServiceEntity service;
	/*
	@Column(nullable = true)
	@OneToMany(mappedBy = "link_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties("link_id")
	private List<CommentEntity> comments = new ArrayList<>();*/
	
	@Column(nullable = true)
	private double rate;

	public RatingEntity() {
		super();
	}

	public RatingEntity(Long id, ProfileEntity profile, ServiceEntity service, double rate) {
		super();
		//this.id = id;
		this.profile = profile;
		this.service = service;
		this.rate = rate;
	}
	
	
/*
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
*/
	public void setProfile(ProfileEntity profile) {
		this.profile = profile;
	}

	public void setService(ServiceEntity service) {
		this.service = service;
	}

	public ProfileEntity getProfile() {
		return profile;
	}

	public void setPerfilId(ProfileEntity profile) {
		this.profile = profile;
	}

	public ServiceEntity getService() {
		return service;
	}

	public void setServiceId(ServiceEntity service) {
		this.service = service;
	}
/*
	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}
*/
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
}
