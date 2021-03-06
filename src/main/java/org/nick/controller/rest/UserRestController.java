package org.nick.controller.rest;

import java.util.List;
import java.util.logging.Logger;
import org.nick.model.User;
import org.nick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UserRestController {
	
	private static final Logger LOGGER = Logger.getLogger(UserRestController.class.getName());

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/userStatisticsRest/", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<User>> listAllUsers(Model model) {
		User user = userService.getAuthenticatedUser();
		model.addAttribute("user",user);
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
	
	//-------------------Retrieve Single User--------------------------------------------------------
    
    @RequestMapping(value = "/userStatisticsRest/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
    	LOGGER.info("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
        	LOGGER.info("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    
  //-------------------Create a User--------------------------------------------------------
    
    @RequestMapping(value = "/userStatisticsRest/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
    	LOGGER.info("Creating User " + user.getUsername());
  
        if (userService.usernameExists(user)) {
        	LOGGER.warning("A User with name " + user.getUsername() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        if(!user.getRole().getRole().equals("ROLE_ADMIN") && !user.getRole().getRole().equals("ROLE_USER")) {
        	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

        user.getRole().setId(user.getRole().getRole().equals("ROLE_USER")?2L:1L);
        userService.saveUser(user);
  
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
  
     
      
    //------------------- Update a User --------------------------------------------------------
      
    @RequestMapping(value = "/userStatisticsRest/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
    	LOGGER.info("Updating User " + id);
          
        User userFromDB = userService.findById(id);
        if (userFromDB==null) {
        	LOGGER.warning("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        //if the logged in admin decides to change his username re-authenticate him with the new name
        if(userFromDB.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
        	userService.changeLoggedInAuthenticatedUser(user.getUsername(), user.getPassword());
        }
        userFromDB.setUsername(user.getUsername());
        userFromDB.setEmail(user.getEmail());
        userService.updateUserUsername(userFromDB);
        return new ResponseEntity<User>(userFromDB, HttpStatus.OK);
    }
  
     
     
    //------------------- Delete a User --------------------------------------------------------
      
    @RequestMapping(value = "/userStatisticsRest/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
    	LOGGER.info("Fetching & Deleting User with id " + id);
  
        User user = userService.findById(id);
        if (user == null) {
        	LOGGER.warning("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
  
      
     
    //------------------- Delete All Users --------------------------------------------------------
      
    @RequestMapping(value = "/userStatisticsRest/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");
  
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
