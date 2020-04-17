package com.nasim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nasim.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
