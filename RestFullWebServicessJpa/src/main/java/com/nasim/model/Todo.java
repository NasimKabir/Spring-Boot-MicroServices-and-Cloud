package com.nasim.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Todo {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private Date targetDate;
	private boolean isDone;
	
	
}