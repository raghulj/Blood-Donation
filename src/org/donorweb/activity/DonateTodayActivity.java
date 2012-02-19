package org.donorweb.activity;

import org.donorweb.R;
import org.donorweb.utils.CalendarUtils;
import org.donorweb.utils.Constants;
import org.donorweb.utils.Utils;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DonateTodayActivity extends AbstractDonateBloodActivity {

	Button mDonatedTodayButton;

	RelativeLayout mOuterLayout;
	TextView mTextview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donate_today_layout);
		mDonatedTodayButton = (Button) findViewById(R.id.donated_today_button);
		mOuterLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
		mTextview = (TextView) findViewById(R.id.next_blood_donate_date);

		String donatedDate = Utils.getStringPreference(this,
				Constants.NEXT_BLOOD_DONATION_DATE);

		Log.d("SDFSDF", "donatedDate " + donatedDate);
		if (donatedDate == null) {
			mDonatedTodayButton.setEnabled(true);
			mTextview.setVisibility(View.GONE);

		} else {
			mDonatedTodayButton.setVisibility(View.GONE);

			String nextDonationDate = Utils.getStringPreference(this,
					Constants.NEXT_BLOOD_DONATION_DATE);
			long days = CalendarUtils.getDateDifference(nextDonationDate);
			Log.d("SDFSDF", "remainfgin days " + days);

			if (days <= 0) {
				Utils.storeStringPreference(this,
						Constants.NEXT_BLOOD_DONATION_DATE, "");
				Utils.storeStringPreference(this, Constants.BLOOD_DONATED_DATE,
						"");
				mDonatedTodayButton.setEnabled(true);
				mDonatedTodayButton.setVisibility(View.VISIBLE);
			} else {
				long per = days / 100L;
				int transparency = (int) Math.ceil(per) * 100;
				mOuterLayout.setBackgroundDrawable(new DrawableGradient(
						new int[] { 0xfff99900, 0xffffffff }, 0)
						.SetTransparency(transparency));
				mTextview.setText("You still have to wait for "
						+ (int) Math.ceil(days + 1) + " days");
			}

		}

		mDonatedTodayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDonatedTodayButton.setVisibility(View.GONE);
				CalendarUtils.addThreeMonths(DonateTodayActivity.this);
				mOuterLayout.setBackgroundDrawable(new DrawableGradient(
						new int[] { 0xfff99900, 0xffffffff }, 0)
						.SetTransparency(10));
			}
		});

		// mDonateTodayDatePicker.se
	}

	public class DrawableGradient extends GradientDrawable {
		DrawableGradient(int[] colors, int cornerRadius) {
			super(GradientDrawable.Orientation.TOP_BOTTOM, colors);

			try {
				this.setShape(GradientDrawable.RECTANGLE);
				this.setGradientType(GradientDrawable.LINEAR_GRADIENT);
				this.setCornerRadius(cornerRadius);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public DrawableGradient SetTransparency(int transparencyPercent) {
			this.setAlpha(255 - ((255 * transparencyPercent) / 100));

			return this;
		}
	}
}
