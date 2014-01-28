package com.gstat.activities;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gstat.R;

public class CoachPlayerActivity extends Activity {
	
	private static final String TAG = "Coach/Player Activity";
	private MyApp appState;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Activity Created");

		setContentView(R.layout.login);

		appState = ((MyApp)getApplicationContext());
		
	}
	
	public void onCreateOptionsMenu() {
		
	}
	
	@SuppressWarnings("deprecation")
	public void login(View view) {
		
		EditText gsid = (EditText)findViewById(R.id.gsid);
		EditText password = (EditText)findViewById(R.id.password);
		final String loginRequest = "http://qualifiers.golfstat.com/webservices/remote.cfc?method=confirmUser&gsid=" +
								gsid.getText().toString() + "&gspw=" + password.getText().toString();
		Thread th = new Thread(new Runnable() {
			public void run() {
				try {
					Document doc = Jsoup.connect(loginRequest).get();
					Element el = doc.select("string").first();
					MyApp.loginResult = el.html();
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
		
		if(MyApp.loginResult.equals("true")) {

			//Populate Global array with state teams(Info provided from login and new webservice)
			String stateRequest = "http://qualifiers.golfstat.com/webservices/remote.cfc?method=getSchoolsByState&stateID=KY";
			try {
				new WebTask().execute(stateRequest, null, appState).get();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String teamRequest = "http://qualifiers.golfstat.com/webservices/remote.cfc?method=getEventsByGSID&GSID=" + gsid.getText().toString();
			Team team = new Team(appState.getTeam("University of Kentucky W"));
			//teamRequest += team.getGSID();
			try {
				new WebTask().execute(teamRequest, team, appState).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(this, TabActivity.class);
			
			
			Bundle bundle = new Bundle();
			bundle.putString("TEAMNAME", team.getName());
			bundle.putString("GSID", gsid.getText().toString());
			bundle.putString("PASSWORD", password.getText().toString());
			intent.putExtra("CLASSNAME", "login");
			intent.putExtras(bundle);
			startActivity(intent);
		} else {
			//Display Login fail option
			AlertDialog box = new AlertDialog.Builder(CoachPlayerActivity.this).create();
			box.setTitle("Login Fail!");
			box.setMessage("You have entered an incorrect ID number or password.");
			box.setButton("Continue", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}});
			box.show();
		}
	}

}
