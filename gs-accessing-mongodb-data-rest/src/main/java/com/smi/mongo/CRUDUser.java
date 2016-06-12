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
		        .append("repeatWrWords", user.getRepeatWronWords());
		Connector.usersColl.insert(doc);
	}
	
	public static void updateUserScore(String username, float score){
		Connector.usersColl.update(new BasicDBObject("name", username),
		        new BasicDBObject("$set", new BasicDBObject("score", score)));
	}
	
	public static void updateUserRepeatWords(String username, boolean repeatWords){
		Connector.usersColl.update(new BasicDBObject("name", username),
		        new BasicDBObject("$set", new BasicDBObject("repeatWrWords", repeatWords)));
	}
		
	public static void addMainLanguageAndLanguageToLearn(String username, String mainLanguage, String langToLearn){
		Connector.usersColl.update(new BasicDBObject("name", username),
		new BasicDBObject("$set", new BasicDBObject()
		.append("mainLanguage", mainLanguage)
		.append("langToLearn",langToLearn)));
	}
	
	public static void updateLanguage(String username, String languageType, String language){
		Connector.usersColl.update(new BasicDBObject("name", username),
		        new BasicDBObject("$set", new BasicDBObject(languageType, language)));
	}
	
	//used for non-repeating user names
	public static DBObject findUserByUsername(String username){
		DBObject user = Connector.usersColl.findOne(new BasicDBObject("username", username));
		return user;
	}
	
	//used to verify correct user name and password
	public static void findUserByUnameAndPassword(){
		//maybe not necessary
	}
	
	public static void addWrongWord(){
		
	}
	
	public static void addCorrectWord(){
		
	}
	
	public static void removeWrongOrCorrectWord(){
		
	}
}
