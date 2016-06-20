package com.smi.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.smi.model.User;
import com.smi.service.UserService;

@RestController
public class UserControler {
	//@Autowired
    //UserService userService;  //Service which will do all data retrieval/manipulation work
 
     
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<String> listAllUsers() {
        String helloWorld = "Hello world";
        if(helloWorld.isEmpty()){
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<String>(helloWorld, HttpStatus.OK);
    }
 
 
    //-------------------Retrieve Single User--------------------------------------------------------
     
    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("name") String name) {
        System.out.println("Fetching User with name " + name);
        User user = UserService.findByName(name);
        if (user == null) {
            System.out.println("User with name " + name + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
 
     
     
    //-------------------Create a User-------------------------------------------------------- ok
     
    @RequestMapping(value = "/user/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getUsername());
 
        if (UserService.isUserExist(user)) {
            System.out.println("A User with name " + user.getUsername() + " already exist");
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }

        UserService.saveUser(user);
 
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/user/{username}").buildAndExpand(user.getUsername()).toUri());
        return new ResponseEntity<User>(user ,HttpStatus.CREATED);
    }
 
    //-------------------Check User pass-------------------------------------------------------- ok
    
    @RequestMapping(value = "/user/signin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> checkPassword(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Checking user " + user.getEmail());
        User foundUser = UserService.findByEmail(user.getEmail());
        if (foundUser == null) {
            System.out.println("A User with mail " + user.getEmail() + " does not exist");
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
 
        User result = UserService.checkPassword(user);
        if (result != null){
        	result.setPassword("********");
        	return new ResponseEntity<User>(result, HttpStatus.OK);
        } else {
        	
        	return new ResponseEntity<User>(result, HttpStatus.NO_CONTENT);
        }
        
    }
     
    //------------------- Update a User --------------------------------------------------------
     
    @RequestMapping(value = "/user/{username}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody User user) {
        System.out.println("Updating User " + username);
         
        User currentUser = UserService.findByName(username);
         
        if (currentUser==null) {
            System.out.println("User with name " + username + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
//        currentUser(user.getName());

         
        UserService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
 
    //------------------- Update a User Score--------------------------------------------------------
    
    @RequestMapping(value = "/user/", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUserScore(@RequestBody User user) {
        System.out.println("Updating User " + user.getUsername());
         
        User currentUser = UserService.findByName(user.getUsername());
         
        if (currentUser==null) {
            System.out.println("User with name " + user.getUsername() + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
        currentUser.setScore(user.getScore());

         
        UserService.updateUserScore(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
    
    
//    //------------------- Delete a User --------------------------------------------------------
//     
//    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
//        System.out.println("Fetching & Deleting User with id " + id);
// 
//        User user = userService.findById(id);
//        if (user == null) {
//            System.out.println("Unable to delete. User with id " + id + " not found");
//            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//        }
// 
//        userService.deleteUserById(id);
//        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//    }
// 
//     
//    //------------------- Delete All Users --------------------------------------------------------
//     
//    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
//    public ResponseEntity<User> deleteAllUsers() {
//        System.out.println("Deleting All Users");
// 
//        userService.deleteAllUsers();
//        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//    }
 

}
