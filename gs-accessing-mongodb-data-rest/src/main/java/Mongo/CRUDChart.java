
package Mongo;

import Model.GlobalChart;

import com.mongodb.BasicDBObject;

public class CRUDChart {
	public static void insertQueryWord(GlobalChart chart){
		BasicDBObject doc = new BasicDBObject()
				.append("wordInMainLang", "something");
		Connector.chartColl.insert(doc);
	}
}
