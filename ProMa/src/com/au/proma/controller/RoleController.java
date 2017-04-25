package com.au.proma.controller;
import com.au.proma.model.*;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.au.proma.service.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	RoleService roleService;
	
	@RequestMapping(method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ArrayList<Role> getAllRoles(HttpServletRequest request)
	{
		if(request.getSession().getAttribute("set").equals("true")==true)
		return roleService.getAllRoles();
		else
			return null;
	}
	
	

}
