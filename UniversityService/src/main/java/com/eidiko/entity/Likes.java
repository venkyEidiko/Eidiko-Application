package com.eidiko.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//this class is  Likes entity for to add Likes in the posts
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	// give number(based number front end assign one symbol)
	private int emoji;

	// empId is who is giving like
	private int empId;

	//@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "postId")
	private Posts posts;
	
	@JsonIgnore
	public Posts getPosts() {
	    return posts;
	}
}
