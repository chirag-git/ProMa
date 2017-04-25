package com.au.proma.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.au.proma.model.Role;
import com.au.proma.model.User;
@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate  jdbcTemplate;

	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public Integer isEmailPresent(String email)
	{
		String query ="select username from dbo.users where useremail='"+email+"'";
		return jdbcTemplate.query(query, new ResultSetExtractor< Integer>() {

			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				
				String temp="";
				while (rs.next()){
				temp=rs.getString("username");
				
				}
				if(temp.length()==0)
					return 0;
				else
					return 1;
			}
		});
	}
	public int addUser(User uobj)
	{
		
		String query="insert into users(username,useremail,userroleid)"+
						"values('"+uobj.getUsername()+"','"+uobj.getUseremail()+"','"+uobj.getRole().getRoleid()+"')";
		return jdbcTemplate.update(query);
	}
	public String getEmailID(String name)
	{
		String query ="select useremail from dbo.users where username='"+name+"'";
		return jdbcTemplate.query(query, new ResultSetExtractor< String>() {

			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				
				String temp="";
				while (rs.next()){
				temp=rs.getString("useremail");
				
				}
				return temp;
			}
		});
	}
//	public int addToken(String name,String token)
//	{
//		
//		String query="update dbo.users set token= '"+ token+
//						"' where username='"+name+"'";
//		return jdbcTemplate.update(query);
//	}
//	public String getUserName(String token)
//	{
//		String query ="select username from dbo.users where token='"+token+"'";
//		return jdbcTemplate.query(query, new ResultSetExtractor< String>() {
//
//			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
//				
//				
//				String temp="";
//				while (rs.next()){
//				temp=rs.getString("username");
//				
//				}
//				return temp;
//			}
//		});
//	}
//	public int setPassword(String new_pass,String token)
//	{
//		
//		String query="update dbo.users set userpassword= '"+ new_pass+
//						"' where token='"+token+"'";
//		return jdbcTemplate.update(query);
//	}
	
	public List<User> getUserWithRoleId(int roleId){
		String query = "select * from users where userroleid = "+roleId;
		return jdbcTemplate.query(query, new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				User user = new User();
				user.setUserid(arg0.getInt("userid"));
				user.setUsername(arg0.getString("username"));
				user.setUseremail(arg0.getString("useremail"));
				
				return user;
			}
			
		});
	}
	
	public List<String> getUsersEmailWithRoleId(int roleId){
		String query = "select useremail from users where userroleid = "+roleId;
		return jdbcTemplate.query(query, new ResultSetExtractor<List<String>>(){

			@Override
			public List<String> extractData(ResultSet arg0) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				List<String> emails  = new ArrayList<String>();
				while(arg0.next())
					emails.add(arg0.getString("useremail"));
				return emails;
			}
			
		});
	}
	public Integer getRoleFromEmail(String email){
		String query = "select userroleid from users where useremail = '"+email+"'";
		return jdbcTemplate.query(query, new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet arg0) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				Integer i=-1;
				while(arg0.next())
					i=arg0.getInt("userroleid");
				return i;
			}
			
		});
	}
	public User getUser(int userId) {
		// TODO Auto-generated method stub
		String query = "select * from users where userid = "+userId;
		List<User>users = jdbcTemplate.query(query, new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				User user = new User();
				user.setUserid(userId);
				user.setUsername(arg0.getString("username"));
				user.setUseremail(arg0.getString("useremail"));
				Role role = new Role();
				role.setRoleid(arg0.getInt("userroleid"));
				user.setRole(role);
				return user;
			}
			
		});
		
		if(users.isEmpty())return null;
		else 
			return users.get(0);
	}
	

	public List<User> getAllUsers(){
		String sql = "select * from dbo.Users U , dbo.Role R where U.userroleid=R.roleid";
		return jdbcTemplate.query(sql, new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				Role role = new Role(rs.getInt("roleid"),rs.getString("rolename"));
				User user = new User();
				user.setUseremail(rs.getString("useremail"));
				user.setUsername(rs.getString("username"));
				user.setUserid(rs.getInt("userid"));
				user.setRole(role);
				return user;
			}
			
		});
	}

	
	public Boolean convertVisitorToAdmin(User user){
		String sql = "update dbo.Users set userroleid=1 where userid=" + user.getUserid();
		return jdbcTemplate.update(sql)==1;
	}
	public List<User> getAllVisitors(){
		String sql = "select * from dbo.Users as U where U.userroleid=2";
		return jdbcTemplate.query(sql, new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				
				User user = new User();
				user.setUseremail(rs.getString("useremail"));
				user.setUsername(rs.getString("username"));
				user.setUserid(rs.getInt("userid"));
				
				return user;
			}
			
		});
	}
	public List<User> getAllAdmins(){
		String sql = "select * from dbo.Users as U where U.userroleid=1";
		return jdbcTemplate.query(sql, new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				
				User user = new User();
				user.setUseremail(rs.getString("useremail"));
				user.setUsername(rs.getString("username"));
				user.setUserid(rs.getInt("userid"));
				return user;
			}
			
		});
	}
	
	public Boolean convertAdminToVisitor(User user){
		String sql = "update dbo.Users set userroleid=2 where userid=" + user.getUserid();
		return jdbcTemplate.update(sql)==1;
	}
}
