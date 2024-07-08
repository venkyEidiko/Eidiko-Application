package com.eidiko.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.entity.Posts;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.FileUploadException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.PostService;

@RestController
@RequestMapping("/posts")

@CrossOrigin(origins = "*", allowedHeaders = "*")

public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private CommonResponse<String> commonResponse;

	// saves data and file in db
	@PostMapping("/saveimage")

	public ResponseEntity<ResponseModel<Object>> saveImage(
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "postType", required = false) String postType,
			@RequestParam(value = "mentionEmployee", required = false) List<String> mentionEmployee,
			@RequestParam(value = "postEmployee", required = false) Long postEmployee,
			@RequestParam(value = "file", required = false) MultipartFile file

	) throws IOException, SQLException, FileUploadException, UserNotFoundException {
		System.out.println("saveImage :" + description);
		System.out.println("saveImage :" + postEmployee);
		if (file != null && file.getSize() > 20 * 1024 * 1024) { // 20MB in bytes
			throw new FileUploadException("File size should not exceed 5MB.");
		}

		try {

			Posts posts = new Posts();
			posts.setDescription(description);
			posts.setMentionEmployee(mentionEmployee);
			posts.setPostEmployee(postEmployee);
			posts.setPostType(postType);
			if (file != null) {
				posts.setImage(file.getBytes());
			}
			String res = postService.saveImage(posts);
			return new CommonResponse<>().prepareSuccessResponseObject(res);

		} catch (IllegalArgumentException e) {
			return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// get image based on post id
	@GetMapping("/get/{id}")
	public ResponseEntity<byte[]> getImageOnlyById(@PathVariable("id") int id) throws SQLException {

		Posts post = postService.getImageOnlyByIdMethod(id);
		ByteArrayOutputStream ab = new ByteArrayOutputStream();

		try {
			ab.write(post.getImage());
		} catch (IOException e) {

			e.printStackTrace();

		}

		byte[] allImageData = ab.toByteArray();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).contentType(MediaType.IMAGE_PNG)
				.body(allImageData);

	}

	// get post data and image(encoded base64 code)
	@GetMapping("/getDataWithImg/{id}")
	public ResponseEntity<Posts> gettingData1(@PathVariable long id) throws SQLException {
		return new ResponseEntity<>(postService.getByIdMethod1(id), HttpStatus.OK);

	}

	// delete the post
	@DeleteMapping("/deletePost/{id}")
	public ResponseEntity<ResponseModel<Object>> deletePosts(@PathVariable long id) throws SQLException {
		try {
			String message = postService.deletePost(id);

			return new CommonResponse<>().prepareSuccessResponseObject(message);
		} catch (RuntimeException e) {
			return new CommonResponse<Object>().prepareFailedResponse(e.getMessage());
		}

	}

	// update the posts
	@PutMapping("updatePost/{postId}")
	public ResponseEntity<ResponseModel<Object>> updatePostByPostId(@PathVariable Long postId, @RequestBody Posts post)
			throws SQLException {
		try {

			Posts upadtedPost = postService.updatePostById(postId, post);
			return new CommonResponse<>().prepareSuccessResponseObject(upadtedPost);
		} catch (RuntimeException e) {

			return new CommonResponse<>().prepareFailedResponse(e.getMessage());
		}
	}

	
	
	//get all the posts
	@GetMapping("/getAllPostByTime")
	public ResponseEntity<ResponseModel<Object>> getAllPostsByTimeStamp() throws SQLException {
	    try {
	        // Call the service method to get all posts ordered by timestamp
	        List<Posts> posts = postService.getAllPostsByTimeStamp();

	        
	        return new CommonResponse<>().prepareSuccessResponseObject(posts);
	    } catch (RuntimeException e) {
	       
	        return new CommonResponse<>().prepareFailedResponse1(e.getMessage());
	    }

	}

}
