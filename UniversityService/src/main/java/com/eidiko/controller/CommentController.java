package com.eidiko.controller;

import java.time.LocalDate;
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

import com.eidiko.entity.Comments;
import com.eidiko.entity.ResponseModel;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.CommentService;

@RestController
@RequestMapping("/posts/")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private CommonResponse<String> commonResponse;
	
	//Save comments to particular post based on postId
	@PostMapping("/saveComment/{postId}")
	public ResponseEntity<ResponseModel<Object>> savecomments(@RequestBody Comments comments,@PathVariable Long postId) {
		
		try {
			LocalDate today = LocalDate.now();
	        System.out.println("Date format:****** " + today);
		
	    Comments commentSave=commentService.saveCommentsByPost(comments, postId);
		return new CommonResponse<>().prepareSuccessResponseObject(commentSave);
		}
		catch (RuntimeException e) {
			return new CommonResponse<Object>().prepareErrorResponseObject(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		}
	
	//update the existing comment 
	@PutMapping("updateComment/{commentId}")
	public ResponseEntity<ResponseModel<Object>> updateCommentById(@PathVariable Long commentId, @RequestBody Comments comment){
		
	try {
		String updatedResult = commentService.updateComment(commentId, comment);
		return new CommonResponse<>().prepareSuccessResponseObject(updatedResult);
	}
	catch (RuntimeException e) {
		return new CommonResponse<>().prepareFailedResponse(e.getMessage());
	}
	}
	
	//delete the comment
	@DeleteMapping("deleteComment/{commentId}")
	public ResponseEntity<ResponseModel<Object>> deleteCommentById(@PathVariable Long commentId){
		
		try {
			String deleteCommentResult = commentService.deleteComment(commentId);
			return new CommonResponse<>().prepareSuccessResponseObject(deleteCommentResult);
		}
		catch (RuntimeException e) {
			return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getCommetsByPostId/{postId}")
	public ResponseEntity<ResponseModel<Object>> getCommentsForSpecificPostByPostId(@PathVariable Long postId){
		try 
		{
			List<Comments> commentsList = commentService.getComentsForSpecificPost(postId);
			return new CommonResponse<>().prepareSuccessResponseObject(commentsList);
		}
		catch (Exception e) {
			return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
}
