
package com.smi.mongo;

import com.mongodb.BasicDBObject;
import com.smi.model.GlobalChart;

public class CRUDChart {
	public static void insertQueryWord(GlobalChart chart){
		BasicDBObject doc = new BasicDBObject()
				.append("wordInMainLang", "something");
		Connector.chartColl.insert(doc);
	}
}
