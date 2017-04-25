package com.au.proma.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.au.proma.model.Client;

@Repository
public class ClientDao {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Client> getAllClients(){
		String sql = "select * from dbo.Client";
		return jdbcTemplate.query(sql, new RowMapper<Client>(){

			@Override
			public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Client(rs.getInt("clientid"),rs.getString("clientname"));
			}
		});
	}
	
	public int insertClient(Client client){
		String sql = "insert into dbo.Client(clientname) values(?)";
		
		
		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, client.getClientname());
				return ps.executeUpdate();
			}
			
		});
	}
	
	public int updateClient(Client client){
		String sql = "update client set clientname = ? where clientid = ?";
		
		
		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, client.getClientname());
				ps.setInt(2, client.getClientid());
				return ps.executeUpdate();
			}
			
		});
	}
	
	public String getClientName(Client client){
		String sql = "select clientname from dbo.Client where clientid=?";
		 
		String name = (String)getJdbcTemplate().queryForObject(
				sql, new Object[] {client.getClientid() }, String.class);
		
		return name;
	}
}
