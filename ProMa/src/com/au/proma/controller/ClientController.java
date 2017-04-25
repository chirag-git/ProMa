package com.au.proma.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.au.proma.model.Client;
import com.au.proma.service.ClientService;

@Controller
@RequestMapping("/clients")
public class ClientController {
	@Autowired
	private ClientService clientService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Client> getAllClients(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("set").equals("true") == true) {
			List<Client> c = clientService.getAllClients();
			return c;
		} else
			return null;
	}

	@RequestMapping(method = RequestMethod.POST,produces = "application/json")
	public @ResponseBody Boolean addClient(@RequestBody Client client, HttpServletRequest request) {
		if (request.getSession() != null && request.getSession().getAttribute("role").equals("admin") == true) {
			Boolean isSuccess = clientService.addClient(client);
			return isSuccess;
		} else
			return null;
	}
}
