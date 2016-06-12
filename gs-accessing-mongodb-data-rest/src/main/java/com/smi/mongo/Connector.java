package com.smi.mongo;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Connector extends MongoClient {
	 
	 private static Connector mongoClient=null;
	 private static Object check = new Object();

	   public static DBCollection wordColl = Connector.getInstance().getDB("dictDB").getCollection("words");
	   public static DBCollection usersColl = Connector.getInstance().getDB("dictDB").getCollection("users");
	   public static DBCollection chartColl = Connector.getInstance().getDB("dictDB").getCollection("chart");

	   
	
	   private Connector(String x,int y){
		   super(x,y);
		   counter++;
		   
	   }
	   
	   
	   public static Connector getInstance(){
		   
		   if(mongoClient==null){
		   synchronized (check) {
			if (mongoClient==null) {
				mongoClient = new Connector( "localhost" , 27017 );
			}
			   
		}
		   } 
		   return mongoClient;
	   }
	   
	   /* A private Constructor prevents any other 
	    * class from instantiating.
	    */
	   
	   /*
	   private Connector(){ 
		   mongoClient = new MongoClient( "localhost" , 27017 );
	   }
	   
	   public static Connector getInstance( ) {
	      return connector;
	   }
   
	   */
	   
//	   protected static DBCollection wordColl = Connector.getInstance().mongoClient.getDB("dictDB").getCollection("words");
//	   protected static DBCollection usersColl = Connector.getInstance().mongoClient.getDB("dictDB").getCollection("users");
//	   protected static DBCollection chartColl = Connector.getInstance().mongoClient.getDB("dictDB").getCollection("chart");
}
