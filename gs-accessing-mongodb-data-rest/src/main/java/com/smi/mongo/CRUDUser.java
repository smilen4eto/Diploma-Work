package com.smi.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.smi.model.User;


public class CRUDUser {
	public static void insertQueryUser(User user){
		BasicDBObject doc = new BasicDBObject()
				.append("name", user.getName())
		        .append("username", user.getUsername())
		        .append("mail", user.getEmail())
		        .append("password", user.getPassword())
		        .append("score", user.getScore())
		        .append("repeatWrWords", user.getRepeatWronWords())
		        .append("mainLanguage", user.getMainLanguage())
		        .append("langToLearn", user.getLangToLearn());
				
		Connector.usersColl.insert(doc);
	}
	
	public static void updateUserScore(String username, double score){
		Connector.usersColl.update(new BasicDBObject("username", username),
		        new BasicDBObject("$set", new BasicDBObject("score", score)));
	}
	
	public static void updateUserRepeatWords(String username, boolean repeatWords){
		Connector.usersColl.update(new BasicDBObject("username", username),
		        new BasicDBObject("$set", new BasicDBObject("repeatWrWords", repeatWords)));
	}
		
	public static void addMainLanguageAndLanguageToLearn(String username, String mainLanguage, String langToLearn){
		Connector.usersColl.update(new BasicDBObject("username", username),
		new BasicDBObject("$set", new BasicDBObject()
		.append("mainLanguage", mainLanguage)
		.append("langToLearn",langToLearn)));
	}
	
	public static void updateLanguage(String username, String languageType, String language){
		Connector.usersColl.update(new BasicDBObject("username", username),
		        new BasicDBObject("$set", new BasicDBObject(languageType, language)));
	}
	
	//used for non-repeating user names
	public static DBObject findUserByUsername(String username){
		DBObject user = Connector.usersColl.findOne(new BasicDBObject("username", username));
		return user;
	}
	
	public static DBObject findUserByEmail(String email) {
		DBObject user = Connector.usersColl.findOne(new BasicDBObject("mail", email));
		return user;
		
	}

	public static void updateUserSettings(User currentUser,String password) {
		Connector.usersColl.update(new BasicDBObject("username", currentUser.getUsername()),
		new BasicDBObject("$set", new BasicDBObject()
		.append("name", currentUser.getName())));
		Connector.usersColl.update(new BasicDBObject("username", currentUser.getUsername()),
		new BasicDBObject("$set", new BasicDBObject()
		.append("password",password)));
		
	}
}
