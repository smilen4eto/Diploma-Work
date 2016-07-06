package com.smi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GlobalChart {
	ArrayList<User> bestUsers;

	
	public GlobalChart() {
		this.bestUsers = new ArrayList<User>();
	}


	public ArrayList<User> getBestUsers() {
		return bestUsers;
	}


	public void setBestUsers(ArrayList<User> bestUsers) {
		this.bestUsers = bestUsers;
	}
		
	
}
