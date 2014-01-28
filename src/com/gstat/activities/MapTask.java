package com.gstat.activities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;

public class MapTask extends AsyncTask<MyApp, Void, Void> {
	
	private static String TAG = "MAPTASK";

	@Override
	protected Void doInBackground(MyApp... params) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Alabama", "AL");  map.put("Alaska", "AK");  map.put("Arizona", "AZ");  map.put("Arkansas", "AR");
		map.put("California", "CA");  map.put("Colorado", "CO");  map.put("Connecticut", "CT");
		map.put("Delaware", "DE");
		map.put("Florida", "FL");
		map.put("Georgia", "GA");
		map.put("Hawaii", "HI");
		map.put("Idaho", "ID");  map.put("Illinois", "IL");  map.put("Indiana", "IN");  map.put("Iowa", "IA");
		map.put("Kansas", "KS");  map.put("Kentucky", "KY");
		map.put("Louisiana", "LA");
		map.put("Main", "ME");  map.put("Maryland", "MD");  map.put("Massachusetts", "MA");  map.put("Michigan", "MI");
		map.put("Minnesota", "MN");  map.put("Mississippi", "MS");  map.put("Missouri", "MO");  map.put("Montana", "MT");
		map.put("Nebraska", "NE");  map.put("Nevada", "NV");  map.put("New Hampshire", "NH");  map.put("New Jersey", "NJ");
		map.put("New Mexico", "NM");  map.put("New York", "NY");  map.put("North Carolina", "NC");  map.put("North Dakota", "ND");
		map.put("Ohio", "OH");  map.put("Oklahoma", "OK");  map.put("Oregon", "OR");
		map.put("Pennsylvania", "PA");
		map.put("Rhode Island", "RH");
		map.put("South Carolina", "SC");  map.put("South Dakota", "SD");
		map.put("Tennessee", "TN");  map.put("Texas", "TX");
		map.put("Utah", "UT");
		map.put("Vermont", "VT");  map.put("Virginia", "VA");
		map.put("Washington", "WA");  map.put("West Virginia", "WV");  map.put("Wisconsin", "WI");  map.put("Wyoming", "WY");
		params[0].setStateAbbrev(map);
		
		/*
		try {
			Iterator<String> i = map.values().iterator();
			while (i.hasNext()) {
				Log.d(TAG, i.next());
				String stateRequest = "http://qualifiers.golfstat.com/webservices/remote.cfc?method=getSchoolsByState&stateID=" + i.next();
				new WebTask().execute(stateRequest, null, params[0]).get();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//params[0].setGSIDS(map); 
		*/
		return null;

	}
}