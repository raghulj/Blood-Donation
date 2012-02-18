package org.donorweb.activity;

import java.util.HashMap;

import org.donorweb.R;
import org.donorweb.model.BloodGroupDetail;
import org.donorweb.parser.BloodStockParser;
import org.donorweb.utils.Constants;
import org.donorweb.utils.Utils;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BloodStockActivity extends AbstractDonateBloodActivity {

	private ProgressBar mAProgressBar;
	private ProgressBar mABProgressBar;
	private ProgressBar mBProgressBar;
	private ProgressBar mOProgressBar;

	private TextView mNotesText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blood_stock_layout);

		mAProgressBar = (ProgressBar) findViewById(R.id.AProgressBar);
		mABProgressBar = (ProgressBar) findViewById(R.id.ABProgressBar);
		mBProgressBar = (ProgressBar) findViewById(R.id.BProgressBar);
		mOProgressBar = (ProgressBar) findViewById(R.id.OProgressBar);
		mNotesText = (TextView) findViewById(R.id.notes_text);

		mNotesText.setText(Html.fromHtml(notesText));

		new GetBloodStockTask().execute();

	}

	class GetBloodStockTask extends
			AsyncTask<Void, Void, HashMap<String, BloodGroupDetail>> {

		@Override
		protected HashMap<String, BloodGroupDetail> doInBackground(
				Void... params) {
			Log.d("SDFSDFSDF", Utils.getYesterdayDate());
			return new BloodStockParser(BloodStockActivity.this,
					Constants.BLOOD_GROUP_STOCK_URL, Utils.getYesterdayDate())
					.getStockDetails();

		}

		protected void onPostExecute(HashMap<String, BloodGroupDetail> response) {
			if (response != null) {
				BloodGroupDetail bloodDetail = response.get("a");

				Drawable progressDrawable;
				Log.d("SDFSDF", "" + bloodDetail.Count);

				float pro = (bloodDetail.Count * 1.0f / Constants.A_GRE_MAX);

				Log.d("SDFSDF", "" + pro * 100);
				Log.d("SDFSDF", bloodDetail.Count + "  " + Math.ceil(pro * 100)
						+ " reid " + Utils.getOGroupColor(bloodDetail.Count));
				mAProgressBar.setProgress((int) Math.ceil(pro * 100));
				progressDrawable = getResources().getDrawable(
						Utils.getResourceColor(Utils
								.getAGroupColor(bloodDetail.Count)));

				mAProgressBar.setProgressDrawable(progressDrawable);

				bloodDetail = response.get("b");
				pro = (bloodDetail.Count * 1.0f / Constants.B_GRE_MAX);
				Log.d("SDFSDF",
						bloodDetail.Count
								+ "  "
								+ Math.ceil(pro * 100)
								+ " reid "
								+ Utils.getResourceColor(Utils
										.getABGroupColor(bloodDetail.Count)));
				mBProgressBar.setProgress((int) Math.ceil(pro * 100));
				progressDrawable = getResources().getDrawable(
						Utils.getResourceColor(Utils
								.getBGroupColor(bloodDetail.Count)));

				mBProgressBar.setProgressDrawable(progressDrawable);

				bloodDetail = response.get("ab");
				pro = (bloodDetail.Count * 1.0f / Constants.AB_GRE_MAX);
				Log.d("SDFSDF",
						bloodDetail.Count
								+ "  "
								+ Math.ceil(pro * 100)
								+ " reid "
								+ Utils.getResourceColor(Utils
										.getABGroupColor(bloodDetail.Count)));
				mABProgressBar.setProgress((int) Math.ceil(pro * 100));
				progressDrawable = getResources().getDrawable(
						Utils.getResourceColor(Utils
								.getABGroupColor(bloodDetail.Count)));

				mABProgressBar.setProgressDrawable(progressDrawable);

				bloodDetail = response.get("o");
				pro = (bloodDetail.Count * 1.0f / Constants.O_GRE_MAX);
				Log.d("SDFSDF",
						bloodDetail.Count
								+ "  "
								+ Math.ceil(pro * 100)
								+ " reid "
								+ Utils.getResourceColor(Utils
										.getABGroupColor(bloodDetail.Count)));
				mOProgressBar.setProgress((int) Math.ceil(pro * 100));

				progressDrawable = getResources().getDrawable(
						Utils.getResourceColor(Utils
								.getOGroupColor(bloodDetail.Count)));

				mOProgressBar.setProgressDrawable(progressDrawable);
			}

		}
	}

	private String notesText = "Note:<br/>		Healthy level is above <b>540</b> units for type <b>A</b>, <b>360</b> units for type <b>B</b>, <b>810</b> units for type <b>O</b> and <b>90</b> units for type <b>AB</b> blood groups.";
}
