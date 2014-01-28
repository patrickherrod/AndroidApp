package com.gstat.activities;

import java.util.ArrayList;
import java.util.Iterator;

public class Team {
	
	private String name;
	private String gender;
	private String gsid;
	private String abbreviation;
	private ArrayList<Event> events;

	public Team(Team newTeam) {
		this.name = newTeam.getName();
		this.gender = newTeam.getGender();
		this.gsid = newTeam.getGSID();
		this.abbreviation = newTeam.getAbbreviation();
		if(events != null) {
			this.events.addAll(newTeam.getEvents());
		}
		else{
			this.events = new ArrayList<Event>();
		}
	}
	
	public Team(String name, String gender, String  gsid, String abbreviation, ArrayList<Event> events) {
		this.name = name;
		this.gender = gender;
		this.gsid = gsid;
		this.abbreviation = abbreviation;
		if(events != null) {
			this.events.addAll(events);
		}
		else{
			this.events = new ArrayList<Event>();
		}
		//May need to add methods to access myapp lists!!!!!
	}
	
	public void addEvent(Event event) {
		
		events.add(event);
	}
	
	public void deleteEvent(String tourneyDescr) {
		Iterator<Event> i = events.iterator();
		while (i.hasNext()) {
			if(i.next().getTournamentDescription().equals(tourneyDescr))
				i.remove();
		}
	}
	
	public void clearEvents() {
		events.clear();
	}
	
	public ArrayList<Event> getEvents() {
		return events;
	}
	
	public String getName() { return name;}
	
	public String getGender() { return gender;}
	
	public String getGSID() { return gsid;}
	
	public String getAbbreviation() { return abbreviation;}
}
