package com.nasim.controller;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nasim.exception.ResourceNotFoundException;
import com.nasim.model.Comment;
import com.nasim.model.Post;
import com.nasim.repository.CommentRepository;
import com.nasim.repository.PostRepository;

@RestController
@RequestMapping("/posts")
public class PostController {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@GetMapping
	public List<Post> getPost() {
		
		
		return postRepository.findAll();
		
	}
	
	// @ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Post> createdPost(@Valid @RequestBody Post post,BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(post,HttpStatus.BAD_REQUEST);
		}
		Post savePost=postRepository.save(post);
		return new ResponseEntity<Post>(savePost,HttpStatus.CREATED);
	}


	@GetMapping("/{id}")
	public Resource<Post> findPost(@PathVariable("id") int id) {
		Post post=postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not post found with id = " + id));
		Resource<Post> resource=new Resource<Post>(post);
		Link links=linkTo(ControllerLinkBuilder.methodOn(PostController.class).getPost()).withRel("posts");
		resource.add(links);
		return resource;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> updatePost(@PathVariable("id") int id,@Valid @RequestBody Post post,BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(post,HttpStatus.BAD_REQUEST);
		}
		Post savePost=postRepository.save(post);
		return new ResponseEntity<Post>(savePost,HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable("id") int id) {
		Post post=postRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Not post found with id = " + id));
		postRepository.deleteById(post.getId());
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/{id}/comments")
	public  void createPostComment(@PathVariable("id")int id,@Valid @RequestBody Comment comment,BindingResult result) {
		
		Post post=postRepository.findById(id).
				orElseThrow(()->new ResourceNotFoundException("Not post found with id = " + id));
		post.getComments().add(comment);
		}
	
	@DeleteMapping("/{postId}/comments/{commentId}")
	public void deletePostComment(@PathVariable("postId") int postId,@PathVariable("commentId")int commentId) {
		postRepository.findById(postId).
				orElseThrow(() -> new ResourceNotFoundException("Not post found with id = " + postId));
		commentRepository.findById(commentId).
				orElseThrow(() -> new ResourceNotFoundException("Not comment found with id = " + commentId));
		commentRepository.deleteById(commentId);
	}

}
