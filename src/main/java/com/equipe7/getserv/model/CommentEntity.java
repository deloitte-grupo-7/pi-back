package com.equipe7.getserv.model;

import java.io.Serializable;
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

@Entity
@Table(name = "_comment")
public class CommentEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne
	@JsonIgnore
	@JoinColumn(name = "profile_id")
	@JoinColumn(name = "service_id")
	private RateEntity rate;
	
	@Column(nullable = false, length=1024)
	private String comment;

	@JsonIgnore
	@Transient
	public Map<String, String> errors = new HashMap<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		if (Regex.any(comment, 0, 1024, false))
			errors.put("comment", "Limite de caracteres atingido - limite: 1024");
		this.comment = comment;
	}
	
	/* -- */
	
	public RateEntity getRate() {
		return rate;
	}

	public void setRate(RateEntity rate) {
		this.rate = rate;
	}
	
	/* -- */
	
	public void setRateDep(RateEntity rate) {
		rate.getComments().add(this);
		this.rate = rate;
	}	

}
