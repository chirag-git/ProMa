package com.au.proma.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.au.proma.dao.SprintDao;
import com.au.proma.model.Project;
import com.au.proma.model.Sprint;
import com.au.proma.util.Colour;
import com.au.proma.util.Constants;

@Service
public class SprintService {

	@Autowired
	private SprintDao sprintDao;
	
	@Autowired
	private ProjectService projectService;

	public Colour getSprintStatus(Sprint sprint) {
		double percentage_days_paased;
		double no_of_days_paased_after_sprint;
		Date completed_date = sprint.getCompleted_date();
		Date startDate = sprint.getStartdate();
		Date endDate = sprint.getEnddate();
		double no_of_days_in_sprint = (((double) endDate.getTime() - startDate.getTime()) / 86400000);
		if (completed_date != null)
			no_of_days_paased_after_sprint = (((double) completed_date.getTime() - endDate.getTime()) / 86400000);
		else {
			Date currDate = new Date(Calendar.getInstance().getTime().getTime());
			if (currDate.after(startDate) && currDate.before(endDate))
				return Colour.GREEN;
			no_of_days_paased_after_sprint = Math.abs(((double) currDate.getTime() - endDate.getTime()) / 86400000);
		}
		try {
			percentage_days_paased = no_of_days_paased_after_sprint / no_of_days_in_sprint;
			if (percentage_days_paased > Constants.YELLOW_RED_THRESHOLD)
				return Colour.RED;
			else if (percentage_days_paased > Constants.GREEN_YELLOW_THRESHOLD)
				return Colour.YELLOW;
			else
				return Colour.GREEN;
		} catch (ArithmeticException e) {
			return Colour.GREEN;
		}
	}

	public List<Sprint> getAllSprints(int pid) {
		// TODO Auto-generated method stub
		List<Sprint> sprints = sprintDao.getAllSprintsUnderProject(pid);
		for (Sprint sprint : sprints)
			sprint.setColour(getSprintStatus(sprint));
		return sprints;
	}

	public String addSprint(Sprint sprint, int pid) {
		// TODO Auto-generated method stub
		int id_of_inserted_sprint = sprintDao.insertSprint(sprint, pid);
		Project project = projectService.getProject(pid);
		sprint.setSprint_id(id_of_inserted_sprint);
		project.setCurrentSprint(sprint);
		Boolean modifyProject = projectService.updateProject(pid, project);
		if(modifyProject)
			return Constants.SUCCESS_MESSAGE;
		else
			return Constants.FAILURE_MESSAGE;
	}

	public Boolean updateSprint(Sprint sprint, int sprintid) {
		// TODO Auto-generated method stub
		sprint.setSprint_id(sprintid);
		int no_of_rows_affected = sprintDao.updateSprint(sprint);
		if (no_of_rows_affected > 0)
			return true;
		else
			return false;
	}
	
	public List<Integer> getDataPoints(int projectid){
		List<Sprint> sprints = getAllSprints(projectid);
		List<Integer> result = new ArrayList<>();
		int momentum = 0;
		result.add(momentum);
		for (Sprint sprint : sprints) {
			if(sprint.getColour() == Colour.GREEN){
				momentum++;
			}
			if(sprint.getColour() == Colour.RED){
				momentum--;
			}
			result.add(momentum);
		}
		return result;
	}
}
