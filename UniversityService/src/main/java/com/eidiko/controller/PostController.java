package com.eidiko.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
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
	public ResponseEntity<ResponseModel<Object>> saveImage(@RequestParam("description") String description,
			@RequestParam("postType") String postType, 
			@RequestParam("mentionEmployee") List<String> mentionEmployee,
			@RequestParam("postEmployee") Long postEmployee,
			@RequestParam("file") MultipartFile file)
			throws IOException, SQLException, FileUploadException {
		
		System.out.println("======================================================");
		if (file.getSize() > 15 * 1024 * 1024) { // 20MB in bytes
			throw new FileUploadException("File size should not exceed 20MB.");
			
		}
 
		try {
			
		Posts posts = new Posts();
		posts.setDescription(description);
		posts.setMentionEmployee(mentionEmployee);
		posts.setPostEmployee(postEmployee);
		posts.setPostType(postType);

		String res = postService.saveImage(posts, file);
		
		
		return new CommonResponse<>().prepareSuccessResponseObject(res);
		}
		catch (IllegalArgumentException e) {
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
		}
		catch (RuntimeException e) {
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

	// get all the data with latest posts are first(images are came encode code)
//	@GetMapping("/getAllPostByTime")
//	public ResponseEntity<ResponseModel<Object>> getAllPostsByTimeStamp() throws SQLException {
//
//		try {
//			List<Posts> posts = postService.getAllPostsByTimeStamp();
//			return new CommonResponse<>().prepareSuccessResponseObject(posts);
//		} catch (RuntimeException e) {
//
//			return new CommonResponse<>().prepareFailedResponse1(e.getMessage());
//		}
//
//	}
	@GetMapping("/getAllPostByTime")
	public ResponseEntity<ResponseModel<Object>> getAllPostsByTimeStamp() throws SQLException {
	    try {
	        // Call the service method to get all posts ordered by timestamp
	        List<Posts> posts = postService.getAllPostsByTimeStamp();
	        
	        // Convert byte array back to Base64 string before sending response
//	        for (Posts post : posts) {
//	            if (post.getImage() != null) {
//	                post.setImage(Base64.getEncoder().encode(post.getImage()));
//	            }
//	        }
	        
	        // Prepare and return a successful response with the list of posts
	        return new CommonResponse<>().prepareSuccessResponseObject(posts);
	    } catch (RuntimeException e) {
	        // If an exception occurs, prepare and return a failed response with the error message
	        return new CommonResponse<>().prepareFailedResponse1(e.getMessage());
	    }
	}


	


}
