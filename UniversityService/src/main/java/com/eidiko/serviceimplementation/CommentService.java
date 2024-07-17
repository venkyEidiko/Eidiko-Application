package com.eidiko.serviceimplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eidiko.entity.Comments;
import com.eidiko.entity.Posts;
import com.eidiko.repository.CommentRepo;
import com.eidiko.repository.PostRepo;

@Service
public class CommentService {

	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postsRepo;
	
	//for save comment
	public Comments saveCommentsByPost(Comments comment,Long postId) {
		Posts posts=postsRepo.findById(postId).orElseThrow(()->new RuntimeException("Post is Not Available !!"));
		comment.setPosts(posts);
		return commentRepo.save(comment);
	}
	
	//for upadte comment
	public String updateComment(Long commentId,Comments comment){
		Comments comments = commentRepo.findById(commentId).orElseThrow(()->new RuntimeException("comment is Not Available !!"));
		comments.setComment(comment.getComment());
		Comments updatedComment= commentRepo.save(comments);
		 
		 return "Upaded Successfully please check in DB!";
	}
	
	//for delete comment
	public String deleteComment(Long commentId) {
		Comments comments = commentRepo.findById(commentId).orElseThrow(()->new RuntimeException("comment is Not Available with this commentId!!"+commentId));
		commentRepo.delete(comments);
		return "comment deleted successfully !";
	}
	
	public List<Comments> getComentsForSpecificPost(Long postId) {
		Posts posts=postsRepo.findById(postId).orElseThrow(()->new RuntimeException("Post is Not Available !!"));	
		List<Comments> comments = posts.getComments();
		return comments;
	}
	
}
