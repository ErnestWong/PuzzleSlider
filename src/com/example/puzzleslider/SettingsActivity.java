package com.example.puzzleslider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends Activity {
	private Context c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		c = getApplicationContext();
		
	}
}
