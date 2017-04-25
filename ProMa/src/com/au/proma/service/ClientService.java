package com.au.proma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.au.proma.dao.ClientDao;
import com.au.proma.model.Client;
import com.au.proma.model.Project;

@Service
public class ClientService {

	@Autowired
	private ClientDao clientDao;
	
	public List<Client> getAllClients(){
		return clientDao.getAllClients();
	}

	public Boolean addClient(Client client) {
		// TODO Auto-generated method stub
		int no_of_records_updated = clientDao.insertClient(client);
		if(no_of_records_updated > 0)
			return true;
		else {
			return false;
		}
	}
}
