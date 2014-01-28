package com.gstat.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.gstat.R;


@SuppressWarnings("deprecation")
public class SpectatorActivity extends Activity implements TextWatcher, OnItemSelectedListener{
	
	//public static Boolean webTaskFlag = false;
	private static final String TAG = "Spectator";
	private AutoCompleteTextView stateAutoComplete;
	private String item[] = {
			"Alabama", "Alaska", "Arizona", "Arkansas",
			"California", "Colorado","Connecticut",
			"Delaware",
			"Florida",
			"Georgia",
			"Hawaii",
			"Idaho", "Illinois", "Indiana", "Iowa",
			"Kansas", "Kentucky",
			"Louisiana",
			"Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
			"Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
			"Ohio", "Oklahoma", "Oregon",
			"Pennsylvania",
			"Rhode Island",
			"South Carolina", "South Dakota",
			"Tennessee", "Texas",
			"Utah",
			"Vermont", "Virginia",
			"Washington", "West Virginia", "Wisconsin", "Wyoming"
	};
	
	public static Spinner spinner;
	private String stateRequest;
	private MyApp appState;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Activity Created");
		setContentView(R.layout.state_seach);
		
		appState = ((MyApp)getApplicationContext());
		
		final ArrayList<String> colleges = appState.getColleges();
		
		stateAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoComp);
		stateAutoComplete.addTextChangedListener(this);
		stateAutoComplete.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item));
		
		spinner = (Spinner) findViewById(R.id.schoolSpinner);
		spinner.setVisibility(4);//Invisible
		spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colleges));
		spinner.setOnItemSelectedListener(this);
		spinner.setSelection(0);
		
		stateAutoComplete.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				appState.clearColleges();
				appState.clearTeams();
				String selState = (String) arg0.getItemAtPosition(arg2);
				Log.d(TAG, "ARG1: " + selState);
				Log.d(TAG, appState.getStateAbbrev(selState));
				stateRequest += appState.getStateAbbrev(selState);
				try {
					new WebTask().execute(stateRequest, null, appState).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				spinner.setVisibility(0);
			}
		});
	}

	/*
	 * Listener Methods for the autocompletetextview(non-Javadoc)
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		stateRequest = "http://qualifiers.golfstat.com/webservices/remote.cfc?method=getSchoolsByState&stateID=";
		spinner.setVisibility(4);
		spinner.setSelection(0);
	}

	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Listener Methods for the spinner(non-Javadoc)
	 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
	 */
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 != 0) {
			String teamRequest = "http://qualifiers.golfstat.com/webservices/remote.cfc?method=getEventsByGSID&GSID=";
			Team team = new Team(appState.getTeam((String) arg0.getItemAtPosition(arg2)));
			teamRequest += team.getGSID();
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
			intent.putExtra("CLASSNAME", "spectator");
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
