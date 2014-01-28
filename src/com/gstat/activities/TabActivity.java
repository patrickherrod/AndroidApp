package com.gstat.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;

import com.gstat.R;
import com.gstat.fragments.practiceFrag;
import com.gstat.fragments.qualifierFrag;

public class TabActivity extends Activity {
	
	private final static String TAG = "TabActivity";
	private ActionBar bar;
	MyApp appState;
	private String TeamName;
	Bundle args;
	
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_list);
		appState = ((MyApp)getApplicationContext());
		
		Intent intent = getIntent();
		String callingClass = intent.getStringExtra("CLASSNAME");
		if(callingClass.equals("spectator")) {
			Log.d(TAG, "Spectator Calling class");
			LinearLayout b1 = (LinearLayout) findViewById(R.id.buttonList);
			b1.setVisibility(4);
		}
		args = intent.getExtras();
		TeamName = args.getString("TEAMNAME");
		bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
		
		Tab tab = bar.newTab()
	            .setText("Practice Rounds")
	            .setTabListener(new TabListener<practiceFrag>(
	                    this, "pr", practiceFrag.class, args));
	    bar.addTab(tab);

	    tab = bar.newTab()
	        .setText("Qualifier Rounds")
	        .setTabListener(new TabListener<qualifierFrag>(
	                this, "qr", qualifierFrag.class, args));
	    bar.addTab(tab);
	    
	}

    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        private final Bundle mArgs;
        private Fragment mFragment;

        public TabListener(Activity activity, String tag, Class<T> clz) {
            this(activity, tag, clz, null);
        }

        public TabListener(Activity activity, String tag, Class<T> clz, Bundle args) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            mArgs = args;

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            mFragment = (Fragment) mActivity.getFragmentManager().findFragmentByTag(mTag);
            if (mFragment != null && !mFragment.isDetached()) {
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ft.detach(mFragment);
                ft.commit();
            }
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if (mFragment == null) {
            	Log.d(TAG, "Into Selected Listener Method");
                mFragment = (Fragment) Fragment.instantiate(mActivity, mClass.getName(), mArgs);
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
                if(tab.getText().equals("Practice Rounds"))
                	practiceFrag.lv.setOnItemLongClickListener(null);
                else
                	qualifierFrag.lv.setOnItemLongClickListener(null);
            }
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            //Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
        }
    }
    
	public void createEvent(View view) {
		final String tabTag = (String) bar.getSelectedTab().getText();
		String message;
		if (tabTag == "Practice Rounds")
			message = "Would you like to create a practice event?";
		else
			message = "Would you like to create a qualifier event?";
		AlertDialog dBox = new AlertDialog.Builder(this)
		.setTitle("Create Event")
		.setMessage(message)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(appState, CreateNewEventActivity.class);
				intent.putExtras(args);
				intent.putExtra("TABTAG", tabTag);
				startActivityForResult(intent, 0);
				
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.create();
		dBox.show();
	}

	public void deleteEvent(View view) {
		AlertDialog dBox = new AlertDialog.Builder(this)
		.setTitle("Delete Events?")
		.setMessage("To delete an event, press and hold.")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				String tabTag = (String) bar.getSelectedTab().getText();
				if (tabTag == "Practice Rounds") {
					practiceFrag.lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			
						public boolean onItemLongClick(final AdapterView<?> arg0, View arg1,
								final int arg2, long arg3) {
							AlertDialog dBox = new AlertDialog.Builder(TabActivity.this)
							.setTitle("Positive?")
							.setMessage("Are you sure you want to delete this item?")
							.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									String[] res =  ((String) arg0.getItemAtPosition(arg2)).split("- ");
									appState.getTeam(TeamName).deleteEvent(res[1]);
									practiceFrag.adapter.remove((String) arg0.getItemAtPosition(arg2));
									practiceFrag.adapter.notifyDataSetChanged();
								}
							})
							.setNegativeButton("No", new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									
								}

							})
							.create();
							dBox.show();
							return false;
						}
						
					});
				} else {
					qualifierFrag.lv.setOnItemLongClickListener(new OnItemLongClickListener() {
						
						public boolean onItemLongClick(final AdapterView<?> arg0, View arg1,
								final int arg2, long arg3) {
							AlertDialog dBox = new AlertDialog.Builder(TabActivity.this)
							.setTitle("Positive?")
							.setMessage("Are you sure you want to delete this item?")
							.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									String[] res =  ((String) arg0.getItemAtPosition(arg2)).split("- ");
									appState.getTeam(TeamName).deleteEvent(res[1]);
									qualifierFrag.adapter.remove((String) arg0.getItemAtPosition(arg2));
									qualifierFrag.adapter.notifyDataSetChanged();	
								}
							})
							.setNegativeButton("No", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
								}
							})
							.create();
							dBox.show();
							return false;
						}
						
					});
				}
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.create();
		dBox.show();
	}
}
