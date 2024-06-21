package com.eidiko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Comments;
@Repository
public interface CommentRepo extends JpaRepository<Comments, Long> {

}
