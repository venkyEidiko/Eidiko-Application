package com.eidiko.serviceimplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eidiko.entity.Likes;
import com.eidiko.entity.Posts;
import com.eidiko.repository.LikeRepo;
import com.eidiko.repository.PostRepo;

@Service
public class LikeService {

	@Autowired
	private LikeRepo likeRepo;
	@Autowired
	private PostRepo postRepo;

	//for save like
	public String saveLike(Likes like,Long postId) {
		Posts posts = postRepo.findById(postId).orElseThrow(()->new RuntimeException("Post is Not Available !!"));
		like.setPosts(posts);
		Likes savedLike = likeRepo.save(like);
		return "Like Addded..";
	}
	
	//for update like
	public String updateLike(Long likeId,Likes like) {
		Likes likes = likeRepo.findById(likeId).orElseThrow(()->new RuntimeException("Like is Not Available !!"));
		likes.setEmoji(like.getEmoji());
		likeRepo.save(likes);
		return "like updated successfully!!";
	}
	
	//for delete like
	public String deleteLike(Long likeId) {
		Likes likes = likeRepo.findById(likeId).orElseThrow(()->new RuntimeException("Like is Not Available !!"));
		likeRepo.delete(likes);
		return "Likes deleted successfully !";
	}
	
	public List<Likes> getLikesById(Long postId) {
	
		Posts posts = postRepo.findById(postId).orElseThrow(()->new RuntimeException("Post is Not Available !!"));
		List<Likes> likes = posts.getLikes();	
		return likes;
	}
	
}
