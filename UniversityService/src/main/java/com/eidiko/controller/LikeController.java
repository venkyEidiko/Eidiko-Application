package com.eidiko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@CrossOrigin(origins="*")
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
		//return new ResponseEntity<>(likeService.updateLike(likeId, likes),HttpStatus.CREATED);
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
		//return new ResponseEntity<>(likeService.deleteLike(likeId ),HttpStatus.OK);
		try {
		String deleteLikeResult = likeService.deleteLike(likeId );
		return new CommonResponse<>().prepareSuccessResponseObject(deleteLikeResult);
		}
		catch (Exception e) {
			return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
