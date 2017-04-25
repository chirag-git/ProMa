package com.au.proma.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.au.proma.dao.RoleDao;
import com.au.proma.model.*;
@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	
	public ArrayList<Role> getAllRoles()
	{
		return roleDao.getAllRoles();
	}
	
}
