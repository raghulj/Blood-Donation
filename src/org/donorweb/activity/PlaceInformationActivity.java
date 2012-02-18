package org.donorweb.activity;

import org.donorweb.R;
import org.donorweb.utils.Constants;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlaceInformationActivity extends AbstractDonateBloodActivity {

	private LinearLayout mMapViewButtonLayout;

	private TextView mAddressText;

	private String mLocationAddressString;

	private boolean isPushMessage = false;
	private String mPushNotificationMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_information);

		isPushMessage = getIntent().getBooleanExtra(Constants.PUSH_MESSAGE,
				false);
		mPushNotificationMessage = getIntent().getStringExtra("payload");

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

					startMapLocationActivity();
				}
			});
		}

	}

}
