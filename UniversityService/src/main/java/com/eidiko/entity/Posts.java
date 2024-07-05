package com.eidiko.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

//this entity of posts for add posts ,delete,update(on the keka portal dashboard page)
public class Posts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	// file or img for post
	@Lob
	@Column(name = "image", columnDefinition = "LONGBLOB")
	@JsonIgnore
	private byte[] image;
	private String base64Image;


	private String description;

	// private LocalDateTime timeStamp;
	private String timeStamp;

	// which type (organisation)
	private String postType;

	// mentioning list of emp names
	// @ElementCollection
	private List<String> mentionEmployee;
	
	

	// employee id who posted
	private Long postEmployee;
	@ManyToOne
	@JoinColumn(name = "employeeId")
	private Employee postEmployeeName;
	

	// for Likes
	@JsonManagedReference
	@OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Likes> likes;

	// for comments
	@JsonManagedReference
	@OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comments> comments;

}
