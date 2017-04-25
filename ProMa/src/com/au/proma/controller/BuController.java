package com.au.proma.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.au.proma.model.BU;
import com.au.proma.model.User;
import com.au.proma.service.BuService;

@Controller
@RequestMapping("/bus")
public class BuController {
	
	@Autowired
	private BuService buService;
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<BU> getAllBU(HttpServletRequest request){
		if(request.getSession().getAttribute("set").equals("true")==true)
		{
			return buService.getAllBU();
		}
		else
		{
			return null;
		}
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public BU getBUDetails(@PathVariable("id") int buid){
		return buService.getBUDetails(buid);
	}
	
	@RequestMapping(method=RequestMethod.POST, produces="application/json",consumes="application/json")
	@ResponseBody
	public Integer addBU(@RequestBody BU bu,HttpServletRequest request){
		if(request.getSession()!=null&&request.getSession().getAttribute("role").equals("admin")==true)
		return buService.addBU(bu);
		else
			return null;
	}
	
	@RequestMapping(value="/{buid}",method=RequestMethod.DELETE, produces="application/json")
	@ResponseBody
	public Boolean removeBU(@PathVariable("buid") int buid,HttpServletRequest request){
		if(request.getSession()!=null&&request.getSession().getAttribute("role").equals("admin")==true)
		{BU bu = new BU();
		bu.setBuid(buid);
		return buService.removeBU(bu);}
		else
			return null;
	}
	
	@RequestMapping(value="/{buid}/buheads",method=RequestMethod.POST, produces="application/json",consumes="application/json")
	@ResponseBody
	public Boolean addBUHead(@RequestBody User user,@PathVariable("buid") int buid,HttpServletRequest request){
		if(request.getSession()!=null&&request.getSession().getAttribute("role").equals("admin")==true)
		{
			BU bu = new BU();
			bu.setBuid(buid);
			return buService.addBUHead(bu, user);
		}
		else
			return null;
	}

	@RequestMapping(value="/{buid}/buheads/{userid}",method=RequestMethod.DELETE, produces="application/json",consumes="application/json")
	@ResponseBody
	public Boolean addBUHead(@PathVariable("buid") int buid,@PathVariable("userid") int userid,HttpServletRequest request){
		if(request.getSession()!=null&&request.getSession().getAttribute("role").equals("admin")==true)
		{		
			BU bu = new BU();
			bu.setBuid(buid);
			User user = new User();
			user.setUserid(userid);
			return buService.removeBUHead(bu, user);
	
		}
		else 
			return null;
	}
	

}
