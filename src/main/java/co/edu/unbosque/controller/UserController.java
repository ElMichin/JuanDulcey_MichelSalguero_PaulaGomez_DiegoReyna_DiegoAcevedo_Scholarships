package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.User;
import co.edu.unbosque.service.UserService;
import co.edu.unbosque.util.Email;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "*" })
@Transactional
public class UserController {

	@Autowired
	private UserService userServ;

	public UserController() {
	}

	@PostMapping(path = "/createuser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createUser(@RequestBody User newUser) {
		int status = userServ.create(newUser);
		if (status == 0) {
			Email email = new Email();
			email.sendMessage(newUser.getEmail(),
					"Sir/ Madam " + newUser.getName() + ", your ScholarChips account was successfully created.",
					"Thank you for choosing us to start a new learning adventure, we hope to fulfill your expectations and that you achieve your dreams. \n\nYour Dates: \n- "
							+ newUser.getName() + "\n- " + newUser.getUsername() + "\n- " + newUser.getEmail() + "\n- "
							+ newUser.getPassword() + "\n- " + newUser.getCountry() + "\n- " + newUser.getCity());
			return new ResponseEntity<String>("User succesfuly created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error creating the user", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping(path = "/loginuser")
	public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
		int status = userServ.login(username, password);
		if (status == 0) {
			return new ResponseEntity<String>("Login is succesfuly", HttpStatus.FOUND);
		} else if (status == 1)
			return new ResponseEntity<String>("Not found username", HttpStatus.NOT_FOUND);
		else if (status == 2)
			return new ResponseEntity<String>("Not found password", HttpStatus.NOT_FOUND);
		return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
	}

	@PutMapping(path = "/updateuser")
	public ResponseEntity<String> update(@RequestParam long id, @RequestParam String username,
			@RequestParam String name, @RequestParam String password, @RequestParam String country,
			@RequestParam String city, @RequestParam String email) {

		User newUser = new User(username, name, password, country, city, email);
		int status = userServ.updateByID(id, newUser);
		if (status == 0) {
			return new ResponseEntity<>("User updated successfully", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("New username already taken", HttpStatus.IM_USED);
		} else if (status == 2) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Error on update", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deletebynameuser")
	ResponseEntity<String> deleteById(@RequestParam String name) {
		int status = userServ.deleteByUsername(name);
		if (status == 0) {
			return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error on delete", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getall")
	ResponseEntity<List<User>> getAll() {
		List<User> users = userServ.getAll();
		if (users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
		}

	}
}
