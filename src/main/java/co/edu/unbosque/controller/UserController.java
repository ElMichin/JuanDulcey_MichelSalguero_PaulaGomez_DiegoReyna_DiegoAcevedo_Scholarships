package co.edu.unbosque.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.User;
import co.edu.unbosque.service.UserService;
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

	@PostMapping(path="/createuserjson", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>createUser(@RequestBody User newUser){	
		int status = userServ.create(newUser);
		if (status == 0) {
			return new ResponseEntity<String>("User succesfuly created", HttpStatus.CREATED);	
		} else {
			return new ResponseEntity<String>("Error crating the user", HttpStatus.NOT_ACCEPTABLE); 			
		}
	}
}
