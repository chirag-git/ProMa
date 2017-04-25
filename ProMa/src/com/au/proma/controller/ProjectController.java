package com.au.proma.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.au.proma.model.BU;
import com.au.proma.model.Project;
import com.au.proma.model.Sprint;
import com.au.proma.service.BuService;
import com.au.proma.service.Color;
import com.au.proma.service.ProjectService;
import com.au.proma.service.SprintService;
import com.au.proma.service.UserService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private BuService buService;

	@Autowired
	private SprintService sprintService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Color> statusOfEveryBU(HttpServletRequest request) {
		if (request.getSession().getAttribute("set").equals("true") == true)
			return buService.statusOfEveryBU();
		else
			return null;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public int insertProject(@RequestBody Project p, HttpServletRequest request) {
		if (request.getSession() != null && request.getSession().getAttribute("role").equals("admin") == true) {
			int row_affected = projectService.insertProject(p);
			if (row_affected > 0)
				userService.notifyEachAdmin("admin", p, "Project Added");
			return row_affected;
		} else
			return -1;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Boolean editProject(@RequestBody Project project, @PathVariable("id") int project_id,
			HttpServletRequest request) {
		if (request.getSession() != null && request.getSession().getAttribute("role").equals("admin") == true) {
			Boolean isSuccess = projectService.updateProject(project_id, project);
			if (isSuccess)
				userService.notifyEachAdmin("admin", project, "Project Updated");
			return isSuccess;
		} else
			return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Project getProject(@PathVariable("id") int project_id) {
		return projectService.getProject(project_id);
	}

	@RequestMapping(value = "/bus/{bu_id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Project> getProjectsUnderBU(@PathVariable("bu_id") int buid, HttpServletRequest request) {
		if (request.getSession().getAttribute("set").equals("true") == true) {
			BU bu = new BU();
			bu.setBuid(buid);
			return projectService.getProjectsUnderBU(bu);
		} else
			return null;
	}

	@RequestMapping(value = "/{pid}/sprints", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Sprint> getSprintsUnderProject(@PathVariable("pid") int pid, HttpServletRequest request) {
		if (request.getSession().getAttribute("set").equals("true") == true)
			return sprintService.getAllSprints(pid);
		else
			return null;

	}

	@RequestMapping(value = "/{pid}/sprints", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Boolean addSprintUnderProject(@RequestBody Sprint sprint,@PathVariable("pid") int pid,HttpServletRequest request){
		if(request.getSession()!=null&&request.getSession().getAttribute("role").equals("admin")==true) {
			sprintService.addSprint(sprint, pid);
			return true;
		}
		else {
			return false;
		}
	}

	@RequestMapping(value = "/{pid}/sprints/{sprintid}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Boolean updateSprintUnderProject(@RequestBody Sprint sprint, @PathVariable("sprintid") int sprintid,
			@PathVariable("pid") int pid, HttpServletRequest request) {
		if (request.getSession() != null && request.getSession().getAttribute("role").equals("admin") == true)
			{
			return sprintService.updateSprint(sprint, sprintid);
			}
		else
			return null;

	}

	@RequestMapping(value = "/closeSprint", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody Boolean closeCurrentSprintUnderProject(@RequestBody Project project){
		 projectService.closeCurrentSprint(project,project.getCurrentSprint());
		 return true;
		
	}

	@RequestMapping(value = "/{id}/getDataPoints", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Integer> getDataPoints(@PathVariable("id") int projectid) {
		return sprintService.getDataPoints(projectid);
	}
	@RequestMapping(value="/{id}/complete",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	@ResponseBody
	public int completeProject(@PathVariable("id") int id,HttpServletRequest request)
	{
		if(request.getSession()!=null&&request.getSession().getAttribute("role").equals("admin")==true)
		{
			Project p =new Project();
			p.setProjectid(id);
			int row_affected =  projectService.completeProject(p);
			
			return row_affected;
		}
		else
			return -1;
	}
	
}
