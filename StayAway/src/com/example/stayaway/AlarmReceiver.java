package com.example.stayaway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	// http://justcallmebrian.com/?p=129

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		try {
			Bundle bundle = arg1.getExtras();
			String message = bundle.getString("alarm_message");
			Toast.makeText(arg0, message + " from AlarmReceiver",
					Toast.LENGTH_SHORT).show();

			Intent newIntent = new Intent(arg0, MainActivity.class);
			newIntent.putExtra("alarm_message", message);
			newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			arg0.startActivity(newIntent);

		} catch (Exception e) {
			Toast.makeText(
					arg0,
					"There was an error somewhere, but we still received an alarm",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

}
