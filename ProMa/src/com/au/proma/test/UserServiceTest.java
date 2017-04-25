package com.au.proma.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.au.proma.dao.UserDao;
import com.au.proma.model.User;
import com.au.proma.service.ProjectService;
import com.au.proma.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserServiceTest {
	
	
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	 @Before
	    public void setUp() {
	        userService = new UserService();        
	        ReflectionTestUtils.setField(userService, "userDao", userDao);
	    }
	 
	 @Test
	 public void testGetInvalidUser(){
		 int userId = 13;
		 User user = userService.getUser(userId);
		 assertNull(user);
	 }
	 
	 @Test
	 public void testGetValidUser(){
		 int userId = 2;
		 User user = userService.getUser(userId);
		 assertEquals(user.getUserid(), userId);
	 }
	 
	 
}
