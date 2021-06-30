/**
 * 
 */
package com.nidhi.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nidhi.cms.domain.User;
import com.nidhi.cms.service.UserService;

/**
 * @author Devendra Gread
 *
 */
@RestController
public class CMSController {
	@Autowired 
	private UserService userservice;
	
	 @PostMapping(value = "/create-user")
	   public ResponseEntity<Object> createProduct(@RequestBody User user) {
	     if(user!=null)
	    	 userservice.createUser(user);
	      return new ResponseEntity<>("user is created successfully", HttpStatus.CREATED);
	   }

}
