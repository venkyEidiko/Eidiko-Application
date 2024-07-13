package com.eidiko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.entity.Likes;
import com.eidiko.entity.ResponseModel;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.LikeService;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class LikeController {

	@Autowired
	private LikeService likeService;
	
	//CommonResponse class for providng meaningfull response
	@Autowired
	private CommonResponse<String> commonResponse;
	
	//for save like
	@PostMapping("/savelike/{postId}")
	public ResponseEntity<ResponseModel<Object>> saveLikes(@RequestBody Likes likes,@PathVariable Long postId) {
		
		
		try {
			String savedLikeResult = likeService.saveLike(likes, postId);
			return new CommonResponse<>().prepareSuccessResponseObject(savedLikeResult);
		}
		catch (Exception e) {
			return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	//for update the like
	@PutMapping("updateLike/{likeId}")
	public ResponseEntity<ResponseModel<Object>> updateLikeById(@PathVariable Long likeId,@RequestBody Likes likes){
		try {
			String likeUpadatedResult = likeService.updateLike(likeId, likes);
		return new CommonResponse<>().prepareSuccessResponseObject(likeUpadatedResult);	
		}
		catch (RuntimeException e) {
			return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	//for delete the like
	@DeleteMapping("deleteLike/{likeId}")
	public ResponseEntity<ResponseModel<Object>> deleteLikeById(@PathVariable Long likeId){
		
		try {
		String deleteLikeResult = likeService.deleteLike(likeId );
		return new CommonResponse<>().prepareSuccessResponseObject(deleteLikeResult);
		}
		catch (Exception e) {
			return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	//FOR GET ALL LIKES FOR SPECIFIC POST BASED ON POSTID
	@GetMapping("/getLikesByPostId/{postId}")
	public ResponseEntity<ResponseModel<Object>> getLikesList(@PathVariable Long postId){
	
	try {
		List<Likes>likesList= likeService.getLikesById(postId);
		return new CommonResponse<>().prepareSuccessResponseObject(likesList);
	}
	catch (Exception e) {
		return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
}
	
	
	
	}
