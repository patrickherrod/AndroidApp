package com.gstat.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

import com.gstat.R;

public class LeaderBoardActivity extends Activity {
	
	GridLayout gl;
	MyApp appState;
	
	private static String TAG = "LEADERBOARD";
	
	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaderboard);
		appState = ((MyApp)getApplicationContext());
		
		String tourneyDescription = getIntent().getStringExtra("TournamentDescription");
		TextView tx = (TextView)findViewById(R.id.EventTitle);
		tx.setText(tourneyDescription);
		String teamName = getIntent().getStringExtra("TeamName");
		ArrayList<Event> list = appState.getTeam(teamName).getEvents();
		String tourneyID = "";
		Log.d(TAG, appState.getTeam(teamName).getEvents().toString());
		Iterator<Event> i = list.iterator();
		while (i.hasNext()) {
			Event e = (Event) i.next();
			if(e.getTournamentDescription().equals(tourneyDescription)){
				tourneyID = e.getTournamentId();
				break;
			}
		}
		Log.d(TAG, "tourney id: " + tourneyID);
		final String ID = tourneyID;
		Thread th = new Thread(new Runnable() {
			public void run() {
				try {
					Document doc = Jsoup.connect("http://qualifiers.golfstat.com/public/leaderboards/player/index.cfm?tournament_id=" + ID).get();
					Elements entries2 = doc.select("td.tableEntry");
					Elements entries = entries2.clone();
					//Log.d(TAG, entries.toString());
					ListIterator<Element> li = entries.listIterator();
					while (li.hasNext()) {
						Element el = li.next();
						String html = el.html();
						if (html.contains("<")) {
							Element em = el.getElementsByTag("a").first();
							li.remove();
							li.add(em);
							li.next();
							li.remove();
							li.next();
							li.remove();
							Log.d(TAG, "FOUND ONE");
						}
					}
					Log.d(TAG, entries.toString());
					li = entries.listIterator();
					int counter = 1;
					while (li.hasNext()) {
						if (counter != 2 && counter != 4 && counter != 8) {
							MyApp.leaderBoard.add(li.next().html());
							if(counter == 9)	
								counter = 0;
						} else {
							li.next();
						}
						counter++;
					}
					Log.d(TAG, MyApp.leaderBoard.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		th.start();
		try {
			th.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		gl = (GridLayout)findViewById(R.id.leaderTable);
		addDefaultViews(gl);
		TextView t1 = new TextView(this);
		Iterator i = MyApp.leaderBoard.iterator();
		while (i.hasNext()) {
			Log.d(TAG, "One GRIDITEM");
			t1 = new TextView(this);
			t1.setText((CharSequence) i.next());
			t1.setGravity(Gravity.CENTER_HORIZONTAL);
	        //t1.setTextSize(30);
			gl.addView(t1);
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		gl.removeAllViews();
		MyApp.leaderBoard.clear();
	}
	
	private void addDefaultViews(GridLayout gl) {
		TextView t1 = new TextView(this);
		t1.setText("position");
		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		gl.addView(t1);
		t1 = new TextView(this);
		t1.setText("player");
		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		gl.addView(t1);
		t1 = new TextView(this);
		t1.setText("to par");
		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		gl.addView(t1);
		t1 = new TextView(this);
		t1.setText("thru");
		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		gl.addView(t1);
		t1 = new TextView(this);
		t1.setText("today");
		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		gl.addView(t1);
		t1 = new TextView(this);
		t1.setText("total");
		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		gl.addView(t1);
		
	}
}
