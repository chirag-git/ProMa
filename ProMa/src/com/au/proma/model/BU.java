package com.au.proma.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BU {
	
	private int buid;
	private String buname;
	List<User> buheads;
	List<Project>projects;
	
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public BU(int buid, String buname) {
		super();
		this.buid = buid;
		this.buname = buname;
	}
	public BU() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getBuid() {
		return buid;
	}
	public void setBuid(int buid) {
		this.buid = buid;
	}
	public String getBuname() {
		return buname;
	}
	public void setBuname(String buname) {
		this.buname = buname;
	}
	public List<User> getBuheads() {
		return buheads;
	}
	public void setBuheads(List<User> buheads) {
		this.buheads = buheads;
	}
	
	
	

	
	

}
