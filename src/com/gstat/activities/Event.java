package com.gstat.activities;

public class Event {

	private String event_id;
	private String event_description;
	private String tournament_id;
	private String tournament_descr;
	private String start_date;
	private String end_date;
	private char type;
	
	public Event(String event_id, String event_description, String tournament_id, String tournament_description, String start_date, String end_date) {
		this.event_id = event_id;
		this.event_description = event_description;
		this.tournament_id = tournament_id;
		this.tournament_descr = tournament_description;
		this.start_date = start_date;
		this.end_date = end_date;
		
		if(event_description.contains("Practice") || event_description.contains("practice") ||
			tournament_description.contains("Practice") || tournament_description.contains("practice")) {
			type = 'p';
		} else if(event_description.contains("Qualifying") || event_description.contains("qualifying") ||
				tournament_description.contains("Qualifiying") || tournament_description.contains("qualifying") ||
				event_description.contains("Qualifying") || event_description.contains("qualifying") ||
				tournament_description.contains("Qualifying") || tournament_description.contains("qualifying")) {
			type = 'q';
		}
	}
	
	public char getType() { return type;}
	
	public String getEventId() { return event_id;}
	
	public String getEventDescription() { return event_description;}
	
	public String getTournamentId() { return tournament_id;}
	
	public String getTournamentDescription() { return tournament_descr;}
	
	public String getStartDate() { return start_date;}
	
	public String getEndDate() { return end_date;}
}
