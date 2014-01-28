package com.gstat.activities;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import com.gstat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GolfStatProtoActivity extends Activity {
	
	private static final String TAG = "Home";
	private MyApp appState;
	
	
    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.d(TAG, "Home Created");
        
        Display display = getWindowManager().getDefaultDisplay();
		int height = display.getHeight();
        
        RelativeLayout titleBox = (RelativeLayout) findViewById(R.id.titleBox);
        titleBox.setMinimumHeight(height/3);
        LinearLayout buttonBox = (LinearLayout) findViewById(R.id.buttonBox);
        buttonBox.setMinimumHeight(height/3*2);
        
		appState = ((MyApp)getApplicationContext());
		try {
			new MapTask().execute(appState).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    /*
     * Called when the user hits the Spectators Button
     */
    public void startSpecAct(View view) {
    	Log.d(TAG, "Starting Spectator Activity");
    	Intent intent = new Intent(this, SpectatorActivity.class);
    	intent.putExtra("message", "Straight from the main activity!");
    	startActivity(intent);
    }
    
    
    /*
     * Called when the user hits the Coaches and Players Button
     */
    public void startCPAct(View view) {
    	Log.d(TAG, "Starting Coach/Player Activity");
    	Intent intent = new Intent(this, CoachPlayerActivity.class);
    	intent.putExtra("message", "Straight from the main activity!");
    	startActivity(intent);
    }
}