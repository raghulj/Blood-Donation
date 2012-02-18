package org.donorweb.activity;

import org.donorweb.R;
import org.donorweb.utils.Constants;
import org.donorweb.utils.Utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class DonateBloodActivity extends Activity {

	private ImageButton mDonationPlaces;
	private ImageButton mBloodStock;
	private ImageButton mDateBloodDonated;
	private ImageButton mAboutDontation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mDonationPlaces = (ImageButton) findViewById(R.id.donationPlaces);

		mBloodStock = (ImageButton) findViewById(R.id.bloodStock);

		mDateBloodDonated = (ImageButton) findViewById(R.id.idontatedblood);

		mAboutDontation = (ImageButton) findViewById(R.id.aboutIcon);

		mAboutDontation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(DonateBloodActivity.this,
						AboutBloodDonationActivity.class);
				startActivity(in);
			}
		});

		mBloodStock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(DonateBloodActivity.this,
						BloodStockActivity.class);
				startActivity(in);

			}
		});

		mDonationPlaces.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(DonateBloodActivity.this,
						DonationPlacesActivity.class);
				startActivity(in);

			}
		});

		mDateBloodDonated.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(DonateBloodActivity.this,
						DonateTodayActivity.class);
				startActivity(in);

			}
		});

		Log.d("SDFSDFSDF",
				"REgistration id "
						+ Utils.getStringPreference(this, Constants.C2DM_AUTH));

		if (Utils.getStringPreference(this, Constants.C2DM_AUTH) == null) {
			Log.d("Sdfsd", "registering to push");
			// register();
		}

	}

	public void register() {
		Log.w("C2DM", "start registration process");
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
		intent.putExtra("app",
				PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		// Sender currently not used
		intent.putExtra("sender", Constants.C2DM_EMAIL_ID);
		startService(intent);
	}

	public void showRegistrationId(View view) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String string = prefs.getString(Constants.C2DM_AUTH, "n/a");
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
		Log.d("C2DM RegId", string);

	}

}