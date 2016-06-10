package Mongo;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Connector {
	public static MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	public static DBCollection wordColl = mongoClient.getDB("dictDB").getCollection("words");
	public static DBCollection usersColl = mongoClient.getDB("dictDB").getCollection("users");
	public static DBCollection chartColl = mongoClient.getDB("dictDB").getCollection("chart");
}
