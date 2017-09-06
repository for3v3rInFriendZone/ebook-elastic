package com.elastic.srb.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.elastic.srb.model.User;
import com.elastic.srb.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	UserService userSer;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {

		List<User> users = (List<User>) userSer.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long id) {

		User user = userSer.findOne(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> editUser(@PathVariable("id") Long id, @RequestBody User user) {

		User editedUser = userSer.findOne(id);
		editedUser.setFirstname(user.getFirstname());
		editedUser.setLastname(user.getLastname());
		editedUser.setPassword(user.getPassword());
		editedUser.setImage(user.getImage());
		
		userSer.save(editedUser);
		return new ResponseEntity<User>(editedUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {

		userSer.delete(id);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<User> saveUser(@RequestBody User user) throws IOException {
		/*
		byte[] decodedImg = Base64.getDecoder().decode(user.getImage().split(",")[1].getBytes(StandardCharsets.UTF_8));
		Path destinationFile = Paths.get("/home/martel/git/ebook/app/src/main/resource/booksPdf", "myImage.jpg");
		Files.write(destinationFile, decodedImg);
		*/		
		User newUser = userSer.save(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
}
