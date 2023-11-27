package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Country;
import co.edu.unbosque.model.User;
import co.edu.unbosque.repository.UserRepository;

@Service
public class UserService implements CRUDOperation<User> {

	@Autowired
	private UserRepository userRep;

	public UserService() {
	}

	@Override
	public int create(User data) {
		if (findUsernameAlreadyTaken(data)) {
			return 1;
		} else {
			userRep.save(data);
		}
		return 0;
	}

	@Override
	public List<User> getAll() {
		return userRep.findAll();
	}

	@Override
	public int deleteByID(Long id) {
		Optional<User> found = userRep.findById(id);
		if (found.isPresent()) {
			userRep.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteByUsername(String username) {
		Optional<User> found = userRep.findByUsername(username);
		if (found.isPresent()) {
			userRep.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateByID(Long id, User newData) {
		Optional<User> found = userRep.findById(id);
		Optional<User> newFound = userRep.findByUsername(newData.getUsername());
		if (found.isPresent() && !newFound.isPresent()) {
			User temp = found.get();
			temp.setUsername(newData.getUsername());
			temp.setName(newData.getName());
			temp.setPassword(newData.getPassword());
			temp.setCountry(newData.getCountry());
			temp.setCity(newData.getCity());
			temp.setEmail(newData.getEmail());
			userRep.save(temp);
			return 0;
		} else if (found.isPresent() && newFound.isPresent())
			return 1;
		else if (!found.isPresent())
			return 2;
		return 3;
	}

	@Override
	public long count() {
		return userRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return userRep.existsById(id) ? true : false;
	}

	public boolean findUsernameAlreadyTaken(User newUser) {
		Optional<User> found = userRep.findByUsername(newUser.getUsername());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	public int login(String username, String password) {

		ArrayList<User> newUser = (ArrayList<User>) getAll();
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
