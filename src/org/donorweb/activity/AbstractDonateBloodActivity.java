package org.donorweb.activity;

import org.donorweb.model.EventInformation;
import org.donorweb.utils.Constants;

import android.app.Activity;
import android.content.Intent;

public class AbstractDonateBloodActivity extends Activity {

	public void startPlaceLocationActivity(EventInformation eventTime) {
		Intent in = new Intent(this, PlaceInformationActivity.class);
		in.putExtra(Constants.EVENT_ADDRESS, eventTime.toString());
		in.putExtra("location_point", eventTime.getLocation());
		startActivity(in);
	}

	public void startMapLocationActivity(String location) {
		Intent in = new Intent(this, BloodMobileTrackerActivity.class);
		in.putExtra("location", location);
		startActivity(in);
	}

}
