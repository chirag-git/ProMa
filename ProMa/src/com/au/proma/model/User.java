package com.au.proma.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class User {
	private int userid;
	private String username;
	private String useremail;
	private Role role;

	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public User() {
		super();
	}

	public User(String username, String useremail) {
		super();
		this.username = username;

		this.useremail = useremail;

	}

	
	public User(int userid, String username, String useremail,Role role) {
		// TODO Auto-generated constructor stub
		this.userid = userid;
		this.username = username;
		this.useremail = useremail;
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	
	

}
