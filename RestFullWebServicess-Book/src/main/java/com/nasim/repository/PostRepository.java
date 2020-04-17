package com.nasim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nasim.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
