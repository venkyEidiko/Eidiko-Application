package com.eidiko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Likes;
@Repository
public interface LikeRepo extends JpaRepository<Likes, Long> {

}
