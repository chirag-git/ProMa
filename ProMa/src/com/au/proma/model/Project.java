package com.au.proma.model;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.au.proma.util.Colour;

@XmlRootElement
@Component
public class Project {
	private int projectid;
	private Client client;
	private String projectname;
	private User projectmanager;
	private int resourceworking;
	private Sprint currentSprint;
	private Colour status;
	private int completed;
	private BU bu;

	public int getProjectid() {
		return projectid;
	}

	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public User getProjectmanager() {
		return projectmanager;
	}

	public void setProjectmanager(User projectmanager) {
		this.projectmanager = projectmanager;
	}

	public int getResourceworking() {
		return resourceworking;
	}

	public void setResourceworking(int resourceworking) {
		this.resourceworking = resourceworking;
	}

	public BU getBu() {
		return bu;
	}

	public void setBu(BU bu) {
		this.bu = bu;
	}

	public Project(int projectid, Client client, String projectname, User projectmanager, int resourceworking,
			Sprint currentSprint, Colour status, BU bu, int completed) {
		super();
		this.projectid = projectid;
		this.client = client;
		this.projectname = projectname;
		this.projectmanager = projectmanager;
		this.resourceworking = resourceworking;
		this.currentSprint = currentSprint;
		this.status = status;
		this.bu = bu;
		this.completed = completed;
	}

	public Colour getStatus() {
		return status;
	}

	public void setStatus(Colour status) {
		this.status = status;
	}

	public Sprint getCurrentSprint() {
		return currentSprint;
	}

	public void setCurrentSprint(Sprint currentSprint) {
		this.currentSprint = currentSprint;
	}

	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

}