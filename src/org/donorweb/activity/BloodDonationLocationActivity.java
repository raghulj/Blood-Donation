package org.donorweb.activity;

import org.donorweb.R;

import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class BloodDonationLocationActivity extends MapActivity {

	private MapView mMapViewLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view_layout);

		mMapViewLayout = (MapView) findViewById(R.id.mapView_element);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
