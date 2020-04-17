package com.nasim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nasim.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
