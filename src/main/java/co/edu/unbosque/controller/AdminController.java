package co.edu.unbosque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.Admin;
import co.edu.unbosque.service.AdminService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "*" })
@Transactional
public class AdminController {

	@Autowired
	private AdminService adminServ;

	public AdminController() {
	}

	@PostMapping(path = "/createadmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createUser(@RequestBody Admin newAdmin) {
		int status = adminServ.create(newAdmin);
		if (status == 0) {
			return new ResponseEntity<String>("User succesfuly created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error crating the user", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping(path = "/loginadmin")
	public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
		int status = adminServ.login(username, password);
		if (status == 0) {
			return new ResponseEntity<String>("Login is succesfuly", HttpStatus.FOUND);
		} else if (status == 1)
			return new ResponseEntity<String>("Not found username", HttpStatus.NOT_FOUND);
		else if (status == 2)
			return new ResponseEntity<String>("Not found password", HttpStatus.NOT_FOUND);
		return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/deletebynameadmin")
	ResponseEntity<String> deleteById(@RequestParam String name) {
		int status = adminServ.deleteByUsername(name);
		if (status == 0) {
			return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error on delete", HttpStatus.NOT_FOUND);
		}
	}

}
