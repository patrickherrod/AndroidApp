package com.gstat.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

//import com.loopj.android.http.AsyncHttpClient;

//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;

public class WebTask extends AsyncTask <Object, Void, Boolean> {

	private static final String TAG = "Web Service";
	
	StringBuilder stringBuilder;
	HttpClient httpClient;
	HttpGet httpGet;
	InputStream inputStream;
	
	@Override
	protected Boolean doInBackground(Object... uri) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		String responseString = null;
		MyApp appState = (MyApp) uri[2];
		String url = (String) uri[0];
		try {
			response = httpClient.execute(new HttpGet(url));
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
			} else {
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch(ClientProtocolException e) {
			
		} catch(IOException e) {
			
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(responseString);
		int length = stringBuilder.length();
		stringBuilder.delete(length-29, length);
		stringBuilder.delete(0,49);
		parseFeed(stringBuilder.toString(),(Team) uri[1], appState);
		return true;
	}	

	private void parseFeed(String feed, Team team, MyApp appState) {
		Log.d(TAG, feed);
		try {
			JSONObject jsonObject = new JSONObject(feed);
			JSONArray columns = jsonObject.getJSONArray("COLUMNS");
			JSONArray data = jsonObject.getJSONArray("DATA");
			if(team == null) {
				Log.d(TAG, ""+data.length());
				appState.clearColleges();
				appState.clearTeams();
				for (int x = 0; x < data.length(); x++) {
					JSONArray teamList = data.getJSONArray(x);
					String name = data.getJSONArray(x).getString(0) + " " + data.getJSONArray(x).getString(1);
					Team newTeam = new Team(teamList.getString(0),teamList.getString(1),teamList.getString(2),teamList.getString(3), null);
					appState.putTeam(name, newTeam);
					appState.addCollege(name);
				}
			} else {
				for (int x = 0; x < data.length(); x++) {
					JSONArray eventList = data.getJSONArray(x);
					Log.d(TAG, data.getJSONArray(x).toString());
					Event event = new Event(eventList.getString(0), eventList.getString(1), eventList.getString(2), 
											eventList.getString(3), eventList.getString(4), eventList.getString(5));
					team.addEvent(event);
					appState.putTeam(team.getName(), team);
				}
				Log.d(TAG, appState.getTeam(team.getName()).getEvents().toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
