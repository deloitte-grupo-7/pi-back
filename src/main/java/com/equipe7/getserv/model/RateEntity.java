package com.equipe7.getserv.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	
	/* -- */
	
	@Column(nullable = true)
	private Integer rate;
	
	/* -- */
	
	@Column(nullable = true)
	@JsonIgnoreProperties("rate")
	@OneToMany(mappedBy = "rate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CommentEntity> comments = new HashSet<>();
	
	@JsonIgnore
	@Transient
	public Map<String, String> errors = new HashMap<>();

	public RateEntity() {
		super();
	}

	public RateEntity(Long id, ProfileEntity profile, ServiceEntity service, Integer rate) {
		super();
		this.profile = profile;
		this.service = service;
		this.rate = rate;
	}
	
	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		if (5 < rate || 0 > rate)
			errors.put("rate", rate.toString());
		this.rate = rate;
	}
	
	/* -- */

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

	public Set<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(Set<CommentEntity> comments) {
		this.comments = comments;
	}
	
	/* -- */

	public void setProfileDep(ProfileEntity profile) {
		profile.getRates().add(this);
		this.profile = profile;
	}

	public void setServiceDep(ServiceEntity service) {
		service.getRates().add(this);
		this.service = service;
	}

	public void addComment(CommentEntity comment) {
		comment.setRate(this);
		comments.add(comment);
	}

	public void setCommentsDep(Set<CommentEntity> comments) {
		comments.forEach(comment -> comment.setRate(this));
		this.comments = comments;
	}
}
