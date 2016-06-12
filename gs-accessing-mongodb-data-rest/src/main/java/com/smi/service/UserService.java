package com.smi.service;

import com.mongodb.DBObject;
import com.smi.model.User;
import com.smi.mongo.CRUDUser;

public class UserService {
	//public UserService() {
		// TODO Auto-generated constructor stub
//	}
	public void addUser(){
		
	}
	
	public static User findByName(String username){
		//check if user is null
		DBObject user = CRUDUser.findUserByUsername(username);
		if (user == null){
			return null;
		}
		User foundUser = new User( user.get("name").toString(), username,
				user.get("email").toString(), user.get("password").toString());
		foundUser.setScore((float)user.get("score"));
//		foundUser.setLangToLearn(langToLearn);
//		foundUser.setMainLanguage(mainLanguage);
		return foundUser;
	}
	public static boolean isUserExist(User user) {
		if (findByName(user.getUsername()) == null)
			return true;
		else
			return false;
	}
	public static void saveUser(User user) {
		CRUDUser.insertQueryUser(user);
	}
	public static void updateUser(User currentUser) {
		
		
	}
	}
