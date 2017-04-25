package com.au.proma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.au.proma.model.Role;

@Repository
public class RoleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public ArrayList<Role> getAllRoles()
	{
		String query="select * from dbo.role";
			
					return jdbcTemplate.query(query, new ResultSetExtractor< ArrayList<Role> >() {
			
						public ArrayList<Role> extractData(ResultSet rs) throws SQLException, DataAccessException {
							
							
							ArrayList<Role> temp=new ArrayList<Role>(); 
							while (rs.next()){
							Role role =new Role();
								
							role.setRoleid(rs.getInt("roleid"));
							
							role.setRolename(rs.getString("rolename"));
							temp.add(role);
							
														
							}
							return temp;
						}
					});
	}

	public int getRoleId(String role){
		// TODO Auto-generated method stub
		String query = "select roleid from role where rolename = ?";
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement pstmt = arg0.prepareStatement(query);
				pstmt.setString(1, role);
				return pstmt;
			}
		};
		return jdbcTemplate.query(creator, new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet arg0) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				return arg0.next() ? arg0.getInt("roleid") : null;
			}
			
		});
	}
	
	public String getRoleName(int roleid){
		// TODO Auto-generated method stub
		String query = "select rolename from role where roleid = ?";
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement pstmt = arg0.prepareStatement(query);
				pstmt.setInt(1, roleid);
				return pstmt;
			}
		};
		return jdbcTemplate.query(creator, new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet arg0) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				return arg0.next() ? arg0.getString("rolename") : null;
			}
			
		});
	}
	
	public int deleteRole(int roleid){
		// TODO Auto-generated method stub
		String query = "select rolename from role where roleid = ?";
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement pstmt = arg0.prepareStatement(query);
				pstmt.setInt(1, roleid);
				return pstmt;
			}
		};
		return jdbcTemplate.update(creator);
	}
	
	public int insertRole(Role role){
		// TODO Auto-generated method stub
		String query = "insert into role(rolename) values(?)";
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement pstmt = arg0.prepareStatement(query);
				pstmt.setInt(1, role.getRoleid());
				return pstmt;
			}
		};
		return jdbcTemplate.update(creator);
	}
	
	public int updateRoleName(int roleid,String rolename){
		// TODO Auto-generated method stub
		String query = "update role set rolename = ? from role where roleid = ?";
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement pstmt = arg0.prepareStatement(query);
				pstmt.setString(1, rolename);
				pstmt.setInt(2, roleid);
				return pstmt;
			}
		};
		return jdbcTemplate.update(creator);
	}
}
				
	

