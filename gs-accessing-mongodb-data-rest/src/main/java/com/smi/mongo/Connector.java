package com.smi.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Connector extends MongoClient {
	private final static String DBNAME = "dictDB";
	private final static String DBCOLLWORDS = "words";
	private final static String DBCOLLUSERS = "users";
	private final static String DBCOLLCHART = "chart";
	private static Connector mongoClient = null;
	private static Object check = new Object();
	private final static String HOST = "localhost";
	private final static int PORT = 27017;
	@SuppressWarnings("deprecation")
	public static DBCollection wordColl = Connector.getInstance().getDB(DBNAME).getCollection(DBCOLLWORDS);
	@SuppressWarnings("deprecation")
	public static DBCollection usersColl = Connector.getInstance().getDB(DBNAME).getCollection(DBCOLLUSERS);
	@SuppressWarnings("deprecation")
	public static DBCollection chartColl = Connector.getInstance().getDB(DBNAME).getCollection(DBCOLLCHART);

	private Connector(String x, int y) {
		super(x, y);
	}

	public static Connector getInstance() {
		if (mongoClient == null) {
			synchronized (check) {
				if (mongoClient == null) {
					mongoClient = new Connector(HOST, PORT);
					//DB db = mongoClient.getDB("dictDB");
					//boolean auth = db.authenticate("myUser", "newPassword".toCharArray());
					
					// boolean auth =
					// mongoClient.getDB("dictDB")).authenticate("username",
					// "password".toCharArray());
				}

			}
		}
		return mongoClient;
	}

}
