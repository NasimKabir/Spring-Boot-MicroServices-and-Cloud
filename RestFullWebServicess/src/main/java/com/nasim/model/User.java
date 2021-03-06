package com.nasim.model;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Details all about users information.")
public class User {
	
	private int id;
	
	@Size(min=4)
	@ApiModelProperty("Name size should be atleast 4 charecter.")
	private String name;
	
	@Past
	@ApiModelProperty("Date should be past date.")
	private Date date;

	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User(int id, String name, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", date=" + date + "]";
	}

}
