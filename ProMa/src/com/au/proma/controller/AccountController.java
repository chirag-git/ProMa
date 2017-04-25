package com.au.proma.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.au.proma.model.BU;
import com.au.proma.model.Details;
import com.au.proma.service.AccountService;

@RequestMapping("/accounts")
@Controller
public class AccountController {
	@Autowired
	private AccountService accountservice;
	

	@RequestMapping(value="/login",method=RequestMethod.POST, produces="text/plain")
	@ResponseBody
	public void  login(@RequestParam("id_token")String token,HttpServletRequest request,HttpServletResponse response){
		//System.out.println("sachin");
		accountservice.login(token,request);
		
		try {
			response.sendRedirect("/ProMa/index.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.POST, produces="text/plain")
	@ResponseBody
	public void  logout(HttpServletRequest request,HttpServletResponse response){
		//System.out.println("sachin");
		//accountservice.login(token,request);
		request.setAttribute("set", "false");
		request.getSession().invalidate();
		try {
			response.sendRedirect("/ProMa");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
//	@RequestMapping(value="/forgot",method=RequestMethod.POST, produces="text/plain")
//	@ResponseBody
//	public ModelAndView sendEmailForPassword(@RequestParam("name")String name,HttpServletRequest request){
//		accountservice.sendEmailForPassword(name,request);
//		return new ModelAndView("loginpage", "message", "emailsend");
//		
//	}
	
//	@RequestMapping(value="/confirm",method=RequestMethod.GET, produces="text/plain")
//	@ResponseBody
//	public ModelAndView setPassword(@RequestParam("token")String token,HttpServletRequest request){
//		request.getSession().setAttribute("token", token);
//		request.getSession().setAttribute("username",accountservice.getUserName(token));
//		return new ModelAndView("confirmpassword", "username","random");
//		
//	}
	
//	@RequestMapping(value="/confirmfinal",method=RequestMethod.POST, produces="text/plain")
//	@ResponseBody
//	public String pushNewPassword(@RequestParam("newpassword")String newpassword,HttpServletRequest request){
//		String token=(String)request.getSession().getAttribute("token");
//		accountservice.setPassword(newpassword,token);
//		return "changed";
//		
//	}
	@RequestMapping(value="/getSessionDetails",method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Details getSessionDetails(HttpServletRequest request)
	{
		Details details=new Details(request.getSession().getAttribute("role").toString() , request.getSession().getAttribute("set").toString());
		return details;
		
	}
}
