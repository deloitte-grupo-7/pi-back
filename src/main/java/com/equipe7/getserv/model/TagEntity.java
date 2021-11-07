package com.equipe7.getserv.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.equipe7.getserv.resource.Regex;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "_tag")
public class TagEntity {
	
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true,
			nullable = true,
			length = 32)
	private String tag;
	
	@JsonIgnore
	@Transient
	public Map<String, String> errors = new HashMap<>();

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		tag = tag.trim();
		if (Regex.text(tag, 1, 32, true))
			errors.put("tag", tag);
		this.tag = tag;
	}

}
