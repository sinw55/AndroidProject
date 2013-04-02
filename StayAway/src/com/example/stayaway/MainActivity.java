package com.example.stayaway;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btnShowLocation;
	Button btnSave;
	double latitude;
	double longitude;

	private static final int requestCode = 192837;
	// GPSTracker class
	GPSTracker gps;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// create button
		btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
		btnSave = (Button) findViewById(R.id.btnSave);
		// show location button click event
		btnShowLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// create class object
				gps = new GPSTracker(MainActivity.this);

				// check if GPS enabled
				if (gps.canGetLocation()) {

					latitude = gps.getLatitude();
					longitude = gps.getLongitude();

					// \n is for new line
					Toast.makeText(
							getApplicationContext(),
							"Your Location is - \nLat: " + latitude
									+ "\nLong: " + longitude, Toast.LENGTH_LONG)
							.show();
				} else {
					// can't get location
					// GPS or Network is not enabled
					// Ask user to enable GPS/network in settings
					gps.showSettingsAlert();
				}
			}
		});

		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MILLISECOND, 2000);
				Intent intent = new Intent(getApplicationContext(),
						AlarmReceiver.class);
				intent.putExtra("alarm_message", "O'Doyle Rules!");

				// In reality, you would want to have a static variable for the
				// request code instead of 192837
				PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);  
						
				
				AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
				
				Toast.makeText(getApplicationContext(), "Time Set from MainActivity",
						Toast.LENGTH_SHORT).show();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
