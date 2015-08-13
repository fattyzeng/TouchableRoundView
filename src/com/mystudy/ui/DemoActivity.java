package com.mystudy.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

@SuppressLint("ResourceAsColor")
public class DemoActivity extends Activity {

	private static final String TAG = "TAG";

	private RoundView roundProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_main);

		roundProgressBar = (RoundView) findViewById(R.id.roundProgressBar);
		

	}
}
