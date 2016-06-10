package Mongo;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Connector {
	 private static Connector connector = new Connector( );
	 private MongoClient mongoClient;
	 
	   protected static DBCollection wordColl = Connector.getInstance().mongoClient.getDB("dictDB").getCollection("words");
	   protected static DBCollection usersColl = Connector.getInstance().mongoClient.getDB("dictDB").getCollection("users");
	   protected static DBCollection chartColl = Connector.getInstance().mongoClient.getDB("dictDB").getCollection("chart");

	 
	   /* A private Constructor prevents any other 
	    * class from instantiating.
	    */
	   private Connector(){ 
		   mongoClient = new MongoClient( "localhost" , 27017 );
	   }
	   
	   /* Static 'instance' method */
	   public static Connector getInstance( ) {
	      return connector;
	   }
   
//	   protected static DBCollection wordColl = Connector.getInstance().mongoClient.getDB("dictDB").getCollection("words");
//	   protected static DBCollection usersColl = Connector.getInstance().mongoClient.getDB("dictDB").getCollection("users");
//	   protected static DBCollection chartColl = Connector.getInstance().mongoClient.getDB("dictDB").getCollection("chart");
}
