package com.au.proma.test;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.au.proma.controller.ProjectController;
import com.au.proma.dao.ProjectDao;
import com.au.proma.dao.SprintDao;
import com.au.proma.model.BU;
import com.au.proma.model.Client;
import com.au.proma.model.Project;
import com.au.proma.model.Role;
import com.au.proma.model.Sprint;
import com.au.proma.model.User;
import com.au.proma.service.BuService;
import com.au.proma.service.ProjectService;
import com.au.proma.service.SprintService;
import com.au.proma.service.UserService;
import com.au.proma.util.Colour;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class ProjectServiceTest {
	
	@Autowired
    private ProjectDao projectDao;

    private ProjectService projectService;

    @Autowired
	private SprintDao sprintDao;
	

    public ProjectServiceTest() {
    }

    @Before
    public void setUp() {
        projectService = new ProjectService();
        
        ReflectionTestUtils.setField(projectService, "projectDao", projectDao);
        ReflectionTestUtils.setField(projectService, "sprintDao", sprintDao);
    }
    /**
     * Test of getProject method, of class ProjectService.
     */
	@Test
    public void testGetInvalidProject() throws Exception {
        int project_id = 9;
		Project result =  projectService.getProject(project_id);
        assertNull(result);
    }
	
	@Test
    public void testGetValidProject() throws Exception {
        int project_id = 5;
		Project result =  projectService.getProject(project_id);
		int resultantProjectId = result.getProjectid();
        assertEquals(resultantProjectId, project_id);
    }
	
	@Test
    public void testUpdateInvalidProject() throws Exception {
		Client client = new Client(13,"amazon");
		User user = new User(10, "sai", "a@b.c", new Role(1, "admin"));
		BU bu = new BU(1,"fedex");
		Sprint sprint = new Sprint();
		sprint.setSprint_id(1);
        Project project = new Project(9,client,"management",user,12,sprint,Colour.BLACK,bu,0);
		Boolean result =  projectService.updateProject(9, project);
        assertEquals(result, false);    }

	@Test
    public void testUpdateValidProject() throws Exception {
		Client client = new Client();
		client.setClientid(1);
		User projectManager = new User();
		projectManager.setUserid(11);
		BU bu = new BU();
		bu.setBuid(1);
		Sprint sprint = new Sprint();
		sprint.setSprint_id(1);
        Project project = new Project();
        project.setClient(client);
        project.setBu(bu);
        project.setProjectmanager(projectManager);
        project.setCurrentSprint(sprint);
        project.setProjectname("Management");
        project.setResourceworking(12);
		Boolean result =  projectService.updateProject(5, project);
        assertEquals(result, true);
    }
}
