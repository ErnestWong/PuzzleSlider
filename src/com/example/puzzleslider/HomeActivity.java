package com.example.puzzleslider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class HomeActivity extends Activity {
	private Context c;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		c = getApplicationContext();
	}
	
	/**
	 * called when user presses play button; starts main activity 
	 * @param view
	 */
	public void onPlay(View view){
		Intent intent = new Intent();
		intent.setClass(c, MainActivity.class);
		startActivity(intent);
	}
	
	/**
	 * called when user presses settings button; starts settings activity
	 * (note: not functional)
	 * @param v
	 */
	public void onSettings(View v){
		
		Intent intent1 = new Intent();
		intent1.setClass(c, SettingsActivity.class);
		startActivity(intent1);
	}
}
