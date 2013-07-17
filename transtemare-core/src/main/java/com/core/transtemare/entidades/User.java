package com.core.transtemare.entidades;

public class User {

	private Long id;
	private String user;
	private String password;
	
	public User() {
	}
	
	public User(String user, String password) {
		this.user=user;
		this.password=password;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
