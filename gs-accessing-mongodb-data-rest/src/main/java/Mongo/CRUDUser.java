package Mongo;

import Model.User;

import com.mongodb.BasicDBObject;

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
}
