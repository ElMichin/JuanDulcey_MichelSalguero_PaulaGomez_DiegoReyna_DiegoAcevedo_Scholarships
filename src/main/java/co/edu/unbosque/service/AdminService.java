package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Admin;
import co.edu.unbosque.model.User;
import co.edu.unbosque.repository.AdminRepository;

@Service

public class AdminService implements CRUDOperation<Admin>{

	@Autowired
	private AdminRepository adminRep; 
	
	public AdminService() {
	}

	@Override
	public int create(Admin data) {
		if (findAdminnameAlreadyTaken(data)) {
			return 1;
		} else {
			adminRep.save(data);
		}
		return 0;
	}

	@Override
	public List<Admin> getAll() {
		return adminRep.findAll();
	}

	@Override
	public int deleteByID(Long id) {
		Optional<Admin> found = adminRep.findById(id);
		if (found.isPresent()) {
			adminRep.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateByID(Long id, Admin newData) {
		Optional<Admin> found = adminRep.findById(id);
		Optional<Admin> newFound = adminRep.findByUsername(newData.getUsername());
		if (found.isPresent() && !newFound.isPresent()) {
			Admin temp = found.get();
			temp.setUsername(newData.getUsername());
			temp.setName(newData.getName());
			temp.setPassword(newData.getPassword());
			adminRep.save(temp);
			return 0;
		} else if (found.isPresent() && newFound.isPresent())
			return 1;
		else if (!found.isPresent())
			return 2;
		return 3;
	}

	@Override
	public long count() {
		return adminRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return adminRep.existsById(id) ? true : false;
	}

	public boolean findAdminnameAlreadyTaken(Admin newAdmin) {
		Optional<Admin> found = adminRep.findByUsername(newAdmin.getUsername());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	public int login(String username, String password) {

		ArrayList<Admin> newUser = (ArrayList<Admin>) getAll();
		for (int i = 0; i < newUser.size(); i++) {
			if (newUser.get(i).getUsername().equals(username) && newUser.get(i).getPassword().equals(password)) {
				return 0;
			} else if (!newUser.get(i).getUsername().equals(username))
				return 1;
			else if (!newUser.get(i).getUsername().equals(password))
				return 2;
		}
		return 3;
	}
	
}
