package com.eidiko.entity;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	//@JsonSerialize(using = ByteArrayToBase64Serializer.class)
	@JsonIgnore
	private byte[] image;
	//private Clob image;
	
	 private String base64Image;
	//content of post
	private String description;
	
	//private LocalDateTime timeStamp;
	private String timeStamp;
	
	//which type (organisation)
	private String postType;
	
	//mentioning list of emp names
	private List<String> mentionEmployee;
	
	//employee id who posted
	private Long postEmployee;
	
	//for Likes
	@JsonManagedReference
	@OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Likes> likes;
	
	//for comments
	@JsonManagedReference
	@OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comments> comments;
	
	
}
