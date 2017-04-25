package com.au.proma.model;

public class Details {
String role;
String loggedin;

public Details() {
	super();
}
public Details(String role, String loggedin) {
	super();
	this.role = role;
	this.loggedin = loggedin;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getLoggedin() {
	return loggedin;
}
public void setLoggedin(String loggedin) {
	this.loggedin = loggedin;
}

}
