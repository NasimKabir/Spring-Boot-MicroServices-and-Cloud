package com.nasim.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
public class HelloWorldController {
	@GetMapping("/hello")
	public String HelloWorld() {
		return "Hello world";
	}
	
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public String helloWorldPathVariable(@PathVariable String name) {
		//throw new RuntimeException("Something went wrong");
		return ("Hello World "+name);
	}
}
