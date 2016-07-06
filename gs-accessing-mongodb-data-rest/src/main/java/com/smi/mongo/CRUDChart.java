
package com.smi.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.smi.model.GlobalChart;
import com.smi.model.User;

public class CRUDChart {
	public static String getFirstThree(){
		DBCursor iterable = Connector.usersColl.find()
		        .sort(new BasicDBObject("score", -1)).limit(3);
		return iterable.toArray().toString();
		
	}
	

}
