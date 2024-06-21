package com.eidiko.serviceimplementation;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.entity.Posts;
import com.eidiko.repository.PostRepo;

@Service
public class PostService {

	@Autowired
	private PostRepo postRepo;

	// saving purpose
	public String saveImage(Posts posts, MultipartFile file) throws IOException, SQLException {

		if (file != null) {
			byte[] fileData = genareteImageTobyteArray(file);
			posts.setImage(fileData);

		}
		
		//for setting time (post created time)	
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String format = LocalDateTime.now().format(dateFormat);
        System.out.println("Date format: " + format);
        posts.setTimeStamp(format);
		
        postRepo.save(posts);
		return "saved !";

	}

	// this checks file/image format,saveImage method uses this method
	public byte[] genareteImageTobyteArray(MultipartFile file) throws IOException {
		
		String contentType = file.getContentType();
		if (!contentType.equals("image/jpeg") && !contentType.equals("application/pdf")) {
			throw new IllegalArgumentException("Invalid file type. Only JPG and PDF are allowed.");
		}

		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".jpeg")
				&& !originalFilename.endsWith(".pdf"))) {
			throw new IllegalArgumentException("Invalid file extension. Only .jpg, .jpeg, and .pdf are allowed.");
		}
		//converted file into bytes
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

	//delete the post
	public String deletePost(Long postId) {
		Posts posts = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post is Not Available !!"));
		postRepo.delete(posts);
		return "Post deleted successfully !";
	}

	

	//update the post
	public Posts updatePostById(long postsId, Posts post) throws SQLException {
		Posts posts = postRepo.findById(postsId).orElseThrow(() -> new RuntimeException("Post is Not Available In DataBase !!"));
		posts.setDescription(post.getDescription());
		posts.setMentionEmployee(post.getMentionEmployee());
		posts.setPostType(post.getPostType());
		return postRepo.save(posts);
	}

	 // Get all posts ordered by timestamp
    public List<Posts> getAllPostsByTimeStamp() {
    List<Posts> byTimeStampDesc = postRepo.findAllByOrderByTimeStampDesc();
         if (byTimeStampDesc.isEmpty()) {
        	 throw new RuntimeException("data no found");
         }
         else {
        	 return byTimeStampDesc;
         }
    }
	
	
}
