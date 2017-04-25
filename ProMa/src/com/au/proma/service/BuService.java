package com.au.proma.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.au.proma.dao.BuDao;
import com.au.proma.model.BU;
import com.au.proma.model.Project;
import com.au.proma.model.User;
import com.au.proma.util.Colour;

@Service
public class BuService {
	
	@Autowired
	private BuDao buDao;
	
	public List<BU> getAllBU(){
		return buDao.getAllBU();
	}
	
	public int addBU(BU bu){
		return buDao.addBU(bu);
	}

	public Boolean addBUHead(BU bu, User user){
		return buDao.addBUHead(bu, user);
	}
	
	public Boolean removeBU(BU bu){
		return buDao.removeBU(bu);
	}
	
	public Boolean removeBUHead(BU bu,User user){
		return buDao.removeBUHead(bu, user);
	}
	
	public BU getBUDetails(int buid){
		List<BU> allBUs = getAllBU();
		for (BU bu : allBUs) {
			if(bu.getBuid()==buid)
				return bu;
		}
		return null;
	}
	
	public List<Color> statusOfEveryBU() {
		List<Color> list = new ArrayList<Color>();

		List<BU> buListWithBuStatus = buDao.statusOfEveryBU();

		for (BU temp : buListWithBuStatus) {
			Color c = new Color();
			c.setBu(temp);

			List<Project> projectUnderBU = temp.getProjects();
			for (Project project : projectUnderBU) {
				if (project.getStatus() == Colour.RED)
					c.incrementRed();
				else if (project.getStatus() == Colour.GREEN)
					c.incrementGreen();
				else if (project.getStatus() == Colour.YELLOW)
					c.incrementYellow();
				else if (project.getStatus() == Colour.BLACK)
					c.incrementBlack();
			}

			c.modifyTotal();
			list.add(c);
		}
		return list;
	}
}
