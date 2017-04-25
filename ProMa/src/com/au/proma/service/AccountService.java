package com.au.proma.service;

import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.au.proma.dao.RoleDao;
import com.au.proma.dao.UserDao;
import com.au.proma.model.Role;
import com.au.proma.model.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class AccountService {
	@Autowired
	private UserDao userdao;
	@Autowired
	private RoleDao roledao;

	public void login(String idTokenString, HttpServletRequest request) {
		
		HttpTransport transport=new NetHttpTransport();
		JsonFactory jsonFactory=new JacksonFactory();

				
		GoogleIdTokenVerifier verifier=new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Arrays.asList("956281975960-3demri6nn0lc1ou4fl3q4fbjmcrrp0ao.apps.googleusercontent.com"))
				.setIssuer("accounts.google.com")
			    .build();
		GoogleIdToken idToken = null;
		try{
			idToken = verifier.verify(idTokenString);
		
		if (idToken != null) {
		  Payload payload = idToken.getPayload();


		  // Get profile information from payload
		  String email = payload.getEmail();
		  
		  String name=(String)payload.get("name");
		  User uobj=new User();
		  uobj.setUseremail(email);
		  uobj.setUsername(name);
		  Role role=new Role();
		  role.setRoleid(2);
		  uobj.setRole(role);
		  if(userdao.isEmailPresent(email)==0)
		  userdao.addUser(uobj);
		  
		  
		  request.getSession().setAttribute("role",userdao.getRoleFromEmail(email)==1?"admin":"visitor");
		  request.getSession().setAttribute("set", "true");
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		 
	
}
//	public String generateCode() {
//		String password = "";
//		SecureRandom random = new SecureRandom();
//		for (int i = 0; i < 8; i++) {
//			password = password + (char) (random.nextInt(26) + 97);
//		}
//		return password;
//	}

	
//	public void sendEmailForPassword(String name,HttpServletRequest request)
//	{
//		String email=userdao.getEmailID(name);
//		String random_string=generateCode();
//		userdao.addToken(name,random_string);
//		final String username = "saumyadeepjndi@gmail.com";
//		final String password = "jndijndi123123";
//		
//		Properties props = new Properties();
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.port", "587");
//		
//		Session session = Session.getInstance(props,
//				  new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(username, password);
//					}
//				  });
//		
//		try {
//
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress("isendupower@gmail.com"));
//			message.setRecipients(Message.RecipientType.TO,
//				InternetAddress.parse(email));
//			message.setSubject("Reset Password Link");
//			message.setText("http://localhost:8082/ProMa/rest/accounts/confirm?token="+random_string);
//
//			Transport.send(message);
//
//			System.out.println("Done");
//
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
//		
//	}
//	public String getUserName(String token)
//	{
//		return userdao.getUserName(token);
//	}
//	public void setPassword(String pass,String token)
//	{
//		
//		try{
//		MessageDigest md = MessageDigest.getInstance("MD5");
//        byte[] passBytes = pass.getBytes();
//        md.reset();
//        byte[] digested = md.digest(passBytes);
//        StringBuffer sb = new StringBuffer();
//        for(int i=0;i<digested.length;i++){
//            sb.append(Integer.toHexString(0xff & digested[i]));
//        }
//        pass= sb.toString();
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		userdao.setPassword(pass,token);
//	}
}
