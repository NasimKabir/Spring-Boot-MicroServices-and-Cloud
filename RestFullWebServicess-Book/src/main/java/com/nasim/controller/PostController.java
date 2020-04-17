package com.nasim.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	// @ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Post> createdPost(@Valid @RequestBody Post post,BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(post,HttpStatus.BAD_REQUEST);
		}
		Post savePost=postRepository.save(post);
		return new ResponseEntity<Post>(savePost,HttpStatus.CREATED);
	}

	@GetMapping
	public List<Post> getPost() {
		return postRepository.findAll();
	}

	@GetMapping("/{id}")
	public Post findPost(@PathVariable("id") int id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not post found with id = " + id));
	}

	@PutMapping("/{id}")
	public Post updatePost(@PathVariable("id") int id, @RequestBody Post post) {
		postRepository.findById(id).
		orElseThrow(() -> new ResourceNotFoundException("Not post found with id = " + id));
		return postRepository.save(post);
	}

	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable("id") int id) {
		Post post=postRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Not post found with id = " + id));
		postRepository.deleteById(post.getId());
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/{id}/comments")
	public void createPostComment(@PathVariable("id")int id,@RequestBody Comment comment) {
		Post post=postRepository.findById(id).
				orElseThrow(()->new ResourceNotFoundException("Not post found with id = " + id));
		post.getComments().add(comment);
	}
	
	@DeleteMapping("/{postId}/comments/{commentId}")
	public void deletePostComment(@PathVariable("postId") int postId,@PathVariable("commentId")int commentId) {
		commentRepository.deleteById(commentId);
	}

}
