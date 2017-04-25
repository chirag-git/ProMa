package com.au.proma.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.au.proma.model.User;
import com.au.proma.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	// @RequestMapping(method=RequestMethod.POST,consumes =
	// "application/json",produces="application/json")
	// public @ResponseBody String addUser(@RequestBody User user){
	// Boolean isSuccess = userService.addUser(user);
	// return isSuccess == true ? "Success" : "Failure";
	// }
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<User> getAllUsers(HttpServletRequest request) {
		if (request.getSession() != null)
			return userService.getAllUsers();
		else
			return null;
	}

	@RequestMapping(value = "/convertToAdmin", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Boolean convertVisitorToAdmin(@RequestBody User user, HttpServletRequest request) {
		if (request.getSession() != null && request.getSession().getAttribute("role").equals("admin") == true)
			return userService.convertVisitorToAdmin(user);
		else
			return null;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody User getUser(@PathVariable("id") int userId, HttpServletRequest request) {
		if (request.getSession().getAttribute("set").equals("true") == true) {
			User user = userService.getUser(userId);
			return user;
		} else
			return null;

	}
	
	@RequestMapping(value = "/convertToVisitor", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Boolean convertAdminToVisitor(@RequestBody User user, HttpServletRequest request) {
		if (request.getSession() != null && request.getSession().getAttribute("role").equals("admin"))
			return userService.convertAdminToVisitor(user);
		else
			return null;
	}
	@RequestMapping(value = "/visitors",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<User> getAllVisitors(HttpServletRequest request) {
		if (request.getSession() != null)
			return userService.getAllVisitors();
		else
			return null;
	}
	@RequestMapping(value = "/admins",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<User> getAllAdmins(HttpServletRequest request) {
		if (request.getSession() != null)
			return userService.getAllAdmins();
		else
			return null;
	}

}
