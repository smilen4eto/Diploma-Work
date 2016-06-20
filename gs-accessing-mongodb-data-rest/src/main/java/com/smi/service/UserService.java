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
		if (user !=null){
		User foundUser = new User( user.get("name").toString(), username,
				user.get("mail").toString(), user.get("password").toString());
		return foundUser;
		} else {
			return null;
		}
	}
	public static boolean isUserExist(User user) {
		if (findByName(user.getUsername()) != null){
			return true;
		}else {
			return false;

		}
	}
	public static void saveUser(User user) {
		CRUDUser.insertQueryUser(user);
	}
	public static void updateUser(User currentUser) {
		CRUDUser.updateUserRepeatWords(currentUser.getUsername(), currentUser.getRepeatWronWords());
		CRUDUser.updateLanguage(currentUser.getUsername(), "mainLang" ,currentUser.getMainLanguage().getLanguageName());
		CRUDUser.updateLanguage(currentUser.getUsername(), "langToLearn", currentUser.getLangToLearn().getLanguageName());
		
	}
	
	public static void updateUserScore(User currentUser){
		CRUDUser.updateUserScore(currentUser.getUsername(), currentUser.getScore());
	}

	public static User checkPassword(User user) {
		User savedUser = findByEmail(user.getEmail());
		if (user.getPassword().equals(savedUser.getPassword())){
			return savedUser;
		} else {
			return null;
		}	
	}

	public static User findByEmail(String email) {
		DBObject user = CRUDUser.findUserByEmail(email);
		if (user !=null){
			User foundUser = new User( user.get("name").toString(), user.get("username").toString(),
					email, user.get("password").toString());
			foundUser.setScore((Double)user.get("score"));
			foundUser.setRepeatWronWords((Boolean)user.get("repeatWrWords"));
			return foundUser;
			} else {
				return null;
			}
	}
}
	
