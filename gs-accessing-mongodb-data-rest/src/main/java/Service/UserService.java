package Service;

import com.mongodb.DBObject;

import Model.User;
import Mongo.CRUDUser;

public class UserService {
	public UserService() {
		// TODO Auto-generated constructor stub
	}
	public void addUser(){
		
	}
	
	public User findByName(String name){
		//check if user is null
		DBObject user = CRUDUser.findUserByUsername(name);
		if (user == null){
			return null;
		}
		User foundUser = new User(name, user.get("username").toString(),
				user.get("email").toString(), user.get("password").toString());
		foundUser.setScore((float)user.get("score"));
//		foundUser.setLangToLearn(langToLearn);
//		foundUser.setMainLanguage(mainLanguage);
		return foundUser;
	}
	public boolean isUserExist(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}
	public void updateUser(User currentUser) {
		// TODO Auto-generated method stub
		
	}
	}
