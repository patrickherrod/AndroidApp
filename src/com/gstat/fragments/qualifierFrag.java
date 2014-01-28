package com.gstat.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gstat.R;
import com.gstat.activities.Event;
import com.gstat.activities.LeaderBoardActivity;
import com.gstat.activities.MyApp;
import com.gstat.activities.Team;

public class qualifierFrag extends Fragment implements ListAdapter {

	private final static String TAG = "qualifier Frag";
	public static ArrayList<String> arr;
	Context mActivity;
	MyApp appState;
	public static ListView lv;
	String teamName;
	public static ArrayAdapter<String> adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		teamName = args.getString("TEAMNAME");
		Log.d(TAG, teamName);
		Log.d(TAG, "Into qualifier Frag");
		mActivity = getActivity();
		appState = ((MyApp)mActivity.getApplicationContext());
		Team team = appState.getTeam(teamName);
		arr = new ArrayList<String>();
		for(Event e : team.getEvents()) {
			if(e.getEventDescription().contains("Qualifying") || e.getEventDescription().contains("qualifying"))
				arr.add(e.getEventDescription() + " - " + e.getTournamentDescription());
		}
		Log.d(TAG, arr.toString());
	}
	
	@SuppressWarnings("unchecked")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (arr != null) {
			adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, arr);
			lv = (ListView) getActivity().findViewById(R.id.eventList);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new OnItemClickListener() {
	
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					String[] res =  ((String) arg0.getItemAtPosition(arg2)).split("- ");
					
					Intent intent = new Intent(mActivity, LeaderBoardActivity.class);
					intent.putExtra("TeamName", teamName);
					intent.putExtra("TournamentDescription", res[1]);
					startActivity(intent);
				}
				
			});
		}
		return null;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getItemViewType(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
