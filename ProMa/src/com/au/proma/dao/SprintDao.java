package com.au.proma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.au.proma.model.Client;
import com.au.proma.model.Sprint;
import com.mysql.jdbc.Statement;

@Repository
public class SprintDao {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int insertSprint(Sprint sprint, int project_id) {
		String sql = "insert into dbo.sprints(project_id,startdate,enddate,milestone,completed_date) values(?,?,?,?,?)";

		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, project_id);
				ps.setDate(2, sprint.getStartdate());
				ps.setDate(3, sprint.getEnddate());
				ps.setString(4, sprint.getMilestone());
				ps.setDate(5, sprint.getCompleted_date());
				return ps;
			  }
			},holder);
		
		return holder.getKey().intValue();
	}
		

	public int updateSprint(Sprint sprint) {
		String sql = "update dbo.sprints set startdate = ?,enddate = ? , milestone = ?,completed_date = ? where sprint_id = ?";

		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(5, sprint.getSprint_id());
				ps.setDate(1, sprint.getStartdate());
				ps.setDate(2, sprint.getEnddate());
				ps.setString(3, sprint.getMilestone());
				ps.setDate(4, sprint.getCompleted_date());
				return ps.executeUpdate();
			}

		});
	}

	public int deleteSprint(int sprint_id) {
		String sql = "delete from dbo.sprints where sprint_id = ?";

		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, sprint_id);
				return ps.executeUpdate();
			}

		});
	}

	public Sprint getSprint(int sprint_id) {
		String sql = "select *  from dbo.sprints where sprint_id = ?";

		return jdbcTemplate.queryForObject(sql, new Object[] { sprint_id }, new RowMapper<Sprint>() {

			@Override
			public Sprint mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return getSprintFromResultSet(rs);
			}

		});
	}

	public List<Sprint> getAllSprintsUnderProject(int project_id) {
		String sql = "select *  from dbo.sprints where project_id = ?";

		return jdbcTemplate.query(sql, new Object[] {project_id}, new RowMapper<Sprint>() {

			@Override
			public Sprint mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return getSprintFromResultSet(rs);
			}

		});
	}
	
	public int deleteAllSprintsUnderProject(int project_id) {
		String sql = "delete from dbo.sprints where project_id = ?";

		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				ps.setInt(1, project_id);
				return ps.executeUpdate();
			}

		});
	}


	public Sprint getSprintFromResultSet(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		Sprint sprint = new Sprint();
		sprint.setSprint_id(rs.getInt("sprint_id"));
		sprint.setStartdate(rs.getDate("startdate"));
		sprint.setEnddate(rs.getDate("enddate"));
		sprint.setMilestone(rs.getString("milestone"));
		sprint.setCompleted_date(rs.getDate("completed_date"));
		return sprint;
	}
}
