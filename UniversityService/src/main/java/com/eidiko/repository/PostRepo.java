package com.eidiko.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.eidiko.entity.Posts;

@Repository
public interface PostRepo extends JpaRepository<Posts, Long>{

	List<Posts> findAllByOrderByTimeStampDesc();
}
