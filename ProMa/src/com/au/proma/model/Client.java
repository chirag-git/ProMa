package com.au.proma.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Client {
	private int clientid;
	private String clientname;
	public int getClientid() {
		return clientid;
	}
	public void setClientid(int clientid) {
		this.clientid = clientid;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public Client(int clientid, String clientname) {
		super();
		this.clientid = clientid;
		this.clientname = clientname;
	}
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

}
