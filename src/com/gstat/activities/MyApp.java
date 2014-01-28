package com.gstat.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;

public class MyApp extends Application {
	
	private ArrayList<String> practiceRoundList = new ArrayList<String>();
	private ArrayList<String> qualifierRoundList = new ArrayList<String>();
	private ArrayList<String> colleges = new ArrayList<String>();
	public static ArrayList<String> leaderBoard = new ArrayList<String>();
	private Map<String, String> stateAbbrev = new HashMap<String, String>();
	private Map<String, Team> teams = new HashMap<String, Team>();
	public static String loginResult = "";
	
	
	public ArrayList<String> getPRList() {
		return practiceRoundList;
	}
	
	public void setPRList(ArrayList<String> prList) {
		practiceRoundList = new ArrayList<String>(prList);
	}

	public ArrayList<String> getQRList() {
		return qualifierRoundList;
	}
	
	public void setQRList(ArrayList<String> qrList) {
		qualifierRoundList = new ArrayList<String>(qrList);
	}
	
	public ArrayList<String> getColleges() {
		colleges.add("Please Select a College");
		return colleges;
	}
	
	public void setColleges(ArrayList<String> collegeList) {
		colleges = new ArrayList<String>(collegeList);
	}
	
	public void addCollege(String name) {
		colleges.add(name);
	}
	
	public void clearColleges() {
		colleges.clear();
		colleges.add("Please Select a College");
	}
	
	public Map<String, String> getStateAbbrevList() {
		return stateAbbrev;
	}
	
	public void setStateAbbrev(Map<String, String> stAbbrev) {
		stateAbbrev = new HashMap<String, String>(stAbbrev);
	}
	
	public String getStateAbbrev(String key) {
		return stateAbbrev.get(key);
	}
	
	public Map<String, Team> getTeams() {
		return teams;
	}
	
	public void setTeams(Map<String, Team> stTeams) {
		teams = new HashMap<String, Team>(stTeams);
	}
	
	public void putTeam(String name, Team newTeam) {
		teams.put(name, newTeam);
	}
	
	public Team getTeam(String name) {
		return teams.get(name);
	}
	
	public void clearTeams() {
		teams.clear();
	}
}
