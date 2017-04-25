package com.au.proma.dao;

import com.au.proma.model.*;
import com.au.proma.service.SprintService;
import com.au.proma.util.Colour;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao {

	final private String base_query_for_getting_project = "select * from project join bu on bu.buid = project.buid join "
			+ "users on users.userid = project.projectmanagerid join client on client.clientid = project.clientid join "
			+ "role on users.userroleid = role.roleid  left join sprints  on sprints.sprint_id = project.current_sprint_id ";

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Autowired
	public SprintService sprintService;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public ArrayList<Project> extractProjectsUnderBU(BU bu) {
		String query = base_query_for_getting_project + " where bu.buid = " + bu.getBuid();
		return jdbcTemplate.query(query, new ResultSetExtractor<ArrayList<Project>>() {

			public ArrayList<Project> extractData(ResultSet rs) throws SQLException, DataAccessException {

				ArrayList<Project> temp = new ArrayList<Project>();
				while (rs.next()) {
					int projectId = rs.getInt("projectid");
					if(rs.wasNull())
						temp.add(null);
					Project project = getProjectFromResultSet(rs);
					temp.add(project);
				}
				return temp;
			}
		});
	}

	public int updateProject(Project pobj) {
		String query = "update dbo.project set projectmanagerid=?,resourceworking=?,completed=?,"
				+ "buid=?,clientid=?,projectname = ?,current_sprint_id = ? where projectid=?";
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement stmt = arg0.prepareStatement(query);
				stmt.setString(6, pobj.getProjectname());
				stmt.setInt(5, pobj.getClient().getClientid());
				stmt.setInt(1, pobj.getProjectmanager().getUserid());
				stmt.setInt(4, pobj.getBu().getBuid());
				stmt.setInt(2, pobj.getResourceworking());
				stmt.setInt(3, pobj.getCompleted());
				if (pobj.getCurrentSprint() == null) {
					stmt.setNull(7, java.sql.Types.INTEGER);
				} else {
					stmt.setInt(7, pobj.getCurrentSprint().getSprint_id());
				}
				stmt.setInt(8, pobj.getProjectid());
				return stmt;
			}
		};
		return jdbcTemplate.update(psc);
	}

	public int insertProject(Project pobj) {
		String query = "insert into dbo.Project(projectname,clientid,projectmanagerid,buid,resourceworking,completed)"
				+ "values(?,?,?,?,?,?)";
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement stmt = arg0.prepareStatement(query);
				stmt.setString(1, pobj.getProjectname());
				stmt.setInt(2, pobj.getClient().getClientid());
				stmt.setInt(3, pobj.getProjectmanager().getUserid());
				stmt.setInt(4, pobj.getBu().getBuid());
				stmt.setInt(5, pobj.getResourceworking());
				stmt.setInt(6, pobj.getCompleted());
				return stmt;
			}
		};
		return jdbcTemplate.update(psc);
	}

	public List<Project> getAllProjects() {

		String sql = base_query_for_getting_project;

		return jdbcTemplate.query(sql, new RowMapper<Project>() {

			@Override
			public Project mapRow(ResultSet arg0, int arg1) throws SQLException {
				return getProjectFromResultSet(arg0);
			}

		});
	}

	public Project getProject(int projectid) {

		String sql = base_query_for_getting_project + " where project.projectid = " + projectid;

		List<Project> projects = jdbcTemplate.query(sql, new RowMapper<Project>() {

			@Override
			public Project mapRow(ResultSet arg0, int arg1) throws SQLException {
				return getProjectFromResultSet(arg0);
			}

		});

		if (projects == null || projects.isEmpty())
			return null;
		else
			return projects.get(0);
	}

	public int deleteProject(int projectid) {
		String sql = "delete from project where projectid = " + projectid;
		return jdbcTemplate.update(sql);
	}

	public Project getProjectFromResultSet(ResultSet arg0) throws SQLException {
		int current_sprint_id = arg0.getInt("current_sprint_id");
		Colour projectStatus;
		Sprint currentSprint = null;
		if (!arg0.wasNull()) {
			currentSprint = new Sprint(current_sprint_id, arg0.getDate("startdate"), arg0.getDate("enddate"),
					arg0.getString("milestone"), arg0.getDate("completed_date"));
			currentSprint.setColour(sprintService.getSprintStatus(currentSprint));
		}
		Client client = new Client(arg0.getInt("clientid"), arg0.getString("clientname"));
		BU bu = new BU(arg0.getInt("buid"), arg0.getString("buname"));
		Role role = new Role(arg0.getInt("roleid"), arg0.getString("rolename"));
		User projectManager = new User(arg0.getInt("userid"), arg0.getString("username"), arg0.getString("useremail"),
				role);
		if (currentSprint == null)
			projectStatus = Colour.BLACK;
		else
			projectStatus = currentSprint.getColour();
		Project p = new Project(arg0.getInt("projectid"), client, arg0.getString("projectname"), projectManager,
				arg0.getInt("resourceworking"), currentSprint, projectStatus,bu, arg0.getInt("completed"));
		return p;
	}
	public int completeProject(Project pobj) {
		String query = "update dbo.project set completed='1' where projectid=?" ;
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement stmt = arg0.prepareStatement(query);
				stmt.setInt(1, pobj.getProjectid());
				
				return stmt;
			}
		};
		return jdbcTemplate.update(psc);
	}
}
