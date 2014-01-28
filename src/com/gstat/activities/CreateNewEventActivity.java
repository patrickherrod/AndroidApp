package com.gstat.activities;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gstat.R;
import com.gstat.fragments.practiceFrag;
import com.gstat.fragments.qualifierFrag;

public class CreateNewEventActivity extends Activity {
	
	private String TAG = "CREATENEW";
	String eventType;
	Bundle args;
	MyApp appState;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_new_event);
		appState = (MyApp)getApplicationContext();
		eventType = getIntent().getStringExtra("TABTAG");
		if (eventType.equals("Practice Rounds"))
			eventType = "practice";
		else
			eventType = "qualifying";
		args = getIntent().getExtras();
	}
	
	public void submit(View view) {
		ArrayList<String> list = new ArrayList<String>();
		EditText t = (EditText)findViewById(R.id.newName);
		list.add(t.getText().toString());
		t = (EditText)findViewById(R.id.newStart);
		list.add(t.getText().toString());
		t = (EditText)findViewById(R.id.newEnd);
		list.add(t.getText().toString());
		/*
		t = (EditText)findViewById(R.id.newPlayers);
		list.add(t.getText().toString());
		t = (EditText)findViewById(R.id.newRounds);
		list.add(t.getText().toString());
		t = (EditText)findViewById(R.id.newRounds);
		list.add(t.getText().toString());
		t = (EditText)findViewById(R.id.newScorePass);
		list.add(t.getText().toString());
		t = (EditText)findViewById(R.id.newSpecPass);
		list.add(t.getText().toString());
		*/
		try {
			String query;
			//query = URLEncoder.encode(args.getString("GSID"),"utf-8");
			String url = "http://qualifiers.golfstat.com/webservices/remote.cfc?method=createNewTournament&gsid=" + args.getString("GSID");
			//query = URLEncoder.encode(args.getString("PASSWORD"),"utf-8");
			url += "&gspw=" + args.getString("PASSWORD");
			//query = URLEncoder.encode(eventType,"utf-8");
			url += "&type=" + eventType;
			query = URLEncoder.encode(list.get(0),"utf-8");
			url += "&description=" + query;
			//query = URLEncoder.encode(list.get(1),"utf-8");
			url += "&start_date=" + list.get(1);
			//query = URLEncoder.encode(list.get(2),"utf-8");
			url += "&end_date=" + list.get(2);
			final String address = url;
			Thread th = new Thread(new Runnable() {
				public void run() {
					try {
						Log.d(TAG, address);
						Document doc = Jsoup.connect(address).get();
						Log.d(TAG, doc.toString());
						Element el = doc.select("number").first();
						if(el.html() != null) {
							Log.d(TAG, "Successfully created an event");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			th.start();
			try {
				th.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//Toast.makeText(getApplicationContext(), "Event Created!", Toast.LENGTH_SHORT).show();
			
			
			 //To do: Update Team Events and practiceFrag.adapter or qualifier.adapter
			String teamRequest = "http://qualifiers.golfstat.com/webservices/remote.cfc?method=getEventsByGSID&GSID=" + args.getString("GSID");
			try {
				appState.getTeam(args.getString("TEAMNAME")).clearEvents();
				new WebTask().execute(teamRequest, appState.getTeam(args.getString("TEAMNAME")), appState).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
			if(eventType.equals("practice")) {
				practiceFrag.arr.clear();
				Log.d(TAG, practiceFrag.arr.toString());
				//practiceFrag.adapter.notifyDataSetChanged();
				for(Event e : appState.getTeam(args.getString("TEAMNAME")).getEvents()) {
					if(e.getEventDescription().contains("Practice") || e.getEventDescription().contains("practice"))
						practiceFrag.arr.add(e.getEventDescription() + " - " + e.getTournamentDescription());
				}
				practiceFrag.adapter.notifyDataSetChanged();
			} else {
				qualifierFrag.arr.clear();
				//qualifierFrag.adapter.notifyDataSetChanged();
				for(Event e : appState.getTeam(args.getString("TEAMNAME")).getEvents()) {
					if(e.getEventDescription().contains("Qualifying") || e.getEventDescription().contains("qualifying"))
						qualifierFrag.arr.add(e.getEventDescription() + " - " + e.getTournamentDescription());
				}
				qualifierFrag.adapter.notifyDataSetChanged();
			}
			 //COULD POSSIBLY REQUERY THE TEAM(RESTART practiceFrag or qualifierFrag)
			finish();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
