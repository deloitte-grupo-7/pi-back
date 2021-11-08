package com.equipe7.getserv.model;

import java.util.Collection;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.equipe7.getserv.controller.form.PostForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "_service")
public class ServiceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
    private String title;
	
	@Column(length = 4096, nullable = true)
    private String description;
	
	@Column(nullable = true)
    private String imageURL;
	
	/* -- */
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<TagEntity> tags = new HashSet<>();

	@JsonIgnoreProperties("service")
	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RateEntity> rates = new HashSet<>();
    
    @ManyToOne
	@JsonIgnore
	@JoinColumn(name = "profile_id")
    private ProfileEntity profile;
    
	@Transient
	@JsonIgnore
	public Map<String, String> errors = new HashMap<>();

    public ServiceEntity(){
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	/* -- */

	public Set<RateEntity> getRates() {
		return rates;
	}

	public void setRates(Set<RateEntity> rates) {
		this.rates = rates;
	}

	public ProfileEntity getProfile() {
		return profile;
	}

	public void setProfile(ProfileEntity profile) {
		this.profile = profile;
	}

	public Collection<TagEntity> getTags() {
		return tags;
	}

	public void setTags(Collection<TagEntity> tags) {
		this.tags = tags;
	}
    
	/* -- */

	public void addRate(RateEntity rate) {
		rate.setService(this);
		rates.add(rate);
	}

	public void setRatesDep(Set<RateEntity> rates) {
		rates.forEach(rate -> rate.setService(this));
		this.rates = rates;
	}

	public void setProfileDep(ProfileEntity profile) {
		profile.getServices().add(this);
		this.profile = profile;
	}
	
	public void update(PostForm form) {
		setDescription(form.getDescription());
		setImageURL(form.getImgUrl());
		setTitle(form.getTitle());
		form.setId(getId());
	}
}
