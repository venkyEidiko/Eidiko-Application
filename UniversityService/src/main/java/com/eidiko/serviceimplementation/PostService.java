package com.eidiko.serviceimplementation;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.entity.Employee;
import com.eidiko.entity.Posts;
import com.eidiko.exception_handler.FileUploadException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.repository.EmployeeRepo;
import com.eidiko.repository.PostRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;

	private final ObjectMapper objectMapper = new ObjectMapper();

	// saving purpose
	public String saveImage(Posts posts) throws IOException, SQLException, FileUploadException, UserNotFoundException {

//		if (file != null) {
//			byte[] fileData = genareteImageTobyteArray(file);
//			posts.setImage(fileData);
//
//		}

		
		Employee emp=employeeRepo.findById(posts.getPostEmployee()).orElseThrow(()->new UserNotFoundException("Useris not avalable !"));
		// for setting time (post created time)
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String format = LocalDateTime.now().format(dateFormat);
		System.out.println("Date format: " + format);
		posts.setTimeStamp(format);
           posts.setPostEmployeeName(emp);  
		postRepo.save(posts);
		return "saved !";

	
	}

	// this checks file/image format,saveImage method uses this method
	public byte[] genareteImageTobyteArray(MultipartFile file) throws IOException {
		
		String contentType = file.getContentType();
		if (!contentType.equals("image/jpeg") && !contentType.equals("application/pdf")
				&& !contentType.equals("image/png")) {
			throw new IllegalArgumentException("Invalid file type. Only JPG and PDF are allowed.");
		}

		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".jpeg")
				&& !originalFilename.endsWith(".pdf") && !originalFilename.endsWith(".png"))) {
			throw new IllegalArgumentException("Invalid file extension. Only .jpg, .jpeg, and .pdf are allowed.");
		}
		// converted file into bytes
		return file.getBytes();

	}

	// get data based on post id(image it will come)
	public Posts getImageOnlyByIdMethod(long id) {
		Posts image = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post is Not Available !!"));
		return image;
	}

	// gets with image(encoded code) and details
	public Posts getByIdMethod1(long id) throws SQLException {
		Posts posts = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post is Not Available !!"));

		return posts;
	}

	// delete the post
	public String deletePost(Long postId) {
		Posts posts = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post is Not Available !!"));
		postRepo.delete(posts);
		return "Post deleted successfully !";
	}

	// update the post
	public Posts updatePostById(long postsId, Posts post) throws SQLException {
		Posts posts = postRepo.findById(postsId)
				.orElseThrow(() -> new RuntimeException("Post is Not Available In DataBase !!"));
		posts.setDescription(post.getDescription());
		posts.setMentionEmployee(post.getMentionEmployee());
		posts.setPostType(post.getPostType());
		return postRepo.save(posts);
	}

	
	
	//get the all posts based on latest time
	public List<Posts> getAllPostsByTimeStamp() {
	    // Fetch all posts from the repository ordered by timestamp in descending order
	    List<Posts> byTimeStampDesc = postRepo.findAllByOrderByTimeStampDesc();

	    // If the list is empty, throw a RuntimeException with the message "Data not found"
	    if (byTimeStampDesc.isEmpty()) {
	        throw new RuntimeException("Data not found");
	    } else {
	        // Create a new ArrayList to hold modified Posts objects
	        List<Posts> data = new ArrayList<>();

	        // Iterate through each post in the list
	        for (Posts post : byTimeStampDesc) {
	            // If the post has an image (i.e., the image byte array is not null)
	            if (post.getImage() != null) {
	                // Get the image bytes from the post
	                byte[] imageBytes = post.getImage();
	                // Encode the image bytes to a Base64 string
	                String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
	                // Set the Base64 encoded string to a new field in Posts
	                post.setBase64Image(encodedImage);
	               
	              
	            }
	            // Add the modified post to the new ArrayList
	            data.add(post);
	        }

	        // Return the new list of posts with Base64 encoded images
	        return data;
	    }
	}

}
	  
	



/*
	public List<Posts> getAllPostsByTimeStamp() {
		// Fetch all posts from the repository ordered by timestamp in descending order
		List<Posts> byTimeStampDesc = postRepo.findAllByOrderByTimeStampDesc();

		// If the list is empty, throw a RuntimeException with the message "Data not
		// found"
		if (byTimeStampDesc.isEmpty()) {
			throw new RuntimeException("Data not found");
		} else {
			// Create a new ArrayList to hold modified Posts objects
			// List<Posts> data = new ArrayList<>();

			// Iterate through each post in the list
			for (Posts post : byTimeStampDesc) {
			
				if (post.getImage() != null) {
					log.info("list employee post {} :", post.getMentionEmployee());
					// Get the image bytes from the post
					byte[] imageBytes = post.getImage();
					// Encode the image bytes to a Base64 string
					String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
					// Set the Base64 encoded string to a new field in Posts
					post.setBase64Image(encodedImage);

				}

			}

			// Return the new list of posts with Base64 encoded images
			return byTimeStampDesc;
		}
	
}
}
*/
