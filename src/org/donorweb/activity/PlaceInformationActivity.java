package org.donorweb.activity;

import org.donorweb.R;
import org.donorweb.listener.ShakeEventListener;
import org.donorweb.utils.Constants;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceInformationActivity extends AbstractDonateBloodActivity {

	private LinearLayout mMapViewButtonLayout;

	private TextView mAddressText;

	private String mLocationAddressString;

	private boolean isPushMessage = false;
	private String mPushNotificationMessage;
	private String mapLocation;

	private SensorManager mSensorManager;

	private ShakeEventListener mSensorListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_information);
		setTitle("Address");
		isPushMessage = getIntent().getBooleanExtra(Constants.PUSH_MESSAGE,
				false);
		mPushNotificationMessage = getIntent().getStringExtra("payload");

		mapLocation = getIntent().getStringExtra("location_point");

		mMapViewButtonLayout = (LinearLayout) findViewById(R.id.map_view_layout_button);

		mAddressText = (TextView) findViewById(R.id.addressTextView);

		mLocationAddressString = getIntent().getStringExtra(
				Constants.EVENT_ADDRESS);

		if (isPushMessage) {
			mMapViewButtonLayout.setVisibility(View.GONE);
			mAddressText.setText(mPushNotificationMessage);
		} else {
			mAddressText.setText(Html.fromHtml(mLocationAddressString));

			mMapViewButtonLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					startMapLocationActivity(mapLocation);
				}
			});
		}

		registerCalendar();

	}

	private void registerCalendar() {
		if (!isPushMessage) {
			mSensorListener = new ShakeEventListener();
			mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			mSensorManager.registerListener(mSensorListener,
					mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_UI);

			mSensorListener
					.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

						public void onShake() {
							Toast.makeText(PlaceInformationActivity.this,
									"Event added to the calendar.",
									Toast.LENGTH_SHORT).show();
						}
					});
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onStop() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onStop();
	}

}
