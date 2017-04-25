package com.au.proma.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.au.proma.model.BU;
import com.au.proma.model.Project;
import com.au.proma.model.Sprint;
import com.au.proma.model.User;
import com.au.proma.util.Colour;

@Repository
public class BuDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	
	@Autowired
	private ProjectDao projectDao;	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<BU> getAllBU(){
		String sql = "select * from bu left join BuHeads on bu.buid = BuHeads.buid left join Users on BuHeads.userid = Users.userid";
		return jdbcTemplate.query(sql,new ResultSetExtractor<List<BU>>(){

			@Override
			public List<BU> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				Map<Integer,BU> buMap = new HashMap<>();
				while(rs.next()){
					User user = new User();
					user.setUseremail(rs.getString("useremail"));
					user.setUserid(rs.getInt("userid"));
					user.setUsername(rs.getString("username"));
					user.setRole(null);
					BU bu = new BU();
					bu.setBuid(rs.getInt("buid"));
					bu.setBuname(rs.getString("buname"));
					
					if(buMap.get(bu.getBuid()) == null){
						buMap.put(bu.getBuid(), bu);
					}
					if(buMap.get(bu.getBuid()).getBuheads() == null ){
						buMap.get(bu.getBuid()).setBuheads(new ArrayList<User>());
					}
					buMap.get(bu.getBuid()).getBuheads().add(user);
				}
				return new ArrayList<BU>(buMap.values());
			}
			
		});
	}
	
	public int addBU(BU bu){
		String sql = "insert into dbo.Bu(buname) values('" + bu.getBuname() +"')";
		return jdbcTemplate.update(sql);
	}
	
	public Boolean addBUHead(BU bu,User user){
		String sql = "insert into dbo.BuHeads values(?,?)";
		return jdbcTemplate.update(sql,new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, bu.getBuid());
				ps.setInt(2, user.getUserid());
			}
		})==1;
	}
	
	public Boolean removeBU(BU bu){
		String sql = "delete from dbo.Bu where buid="+ bu.getBuid() ;
		return jdbcTemplate.update(sql)==1;
	}
	
	public Boolean removeBUHead(BU bu,User user){
		String sql = "delete from dbo.BuHeads where buid=? and userid=?";
		return jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, bu.getBuid());
				ps.setInt(2, user.getUserid());
			}
		})==1;
	}

	public List<BU> statusOfEveryBU() {
		// TODO Auto-generated method stub
		List<BU>allBus = getAllBU();
		for(BU bu : allBus){
			bu.setProjects(projectDao.extractProjectsUnderBU(bu));
		}
		return allBus;
	}
	
}
