package org.donorweb.activity;

import java.util.ArrayList;

import org.donorweb.R;
import org.donorweb.adapters.DonationPlacesAdapter;
import org.donorweb.model.EventInformation;
import org.donorweb.parser.EventInformationParser;
import org.donorweb.utils.Constants;
import org.donorweb.utils.Utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class DonationPlacesActivity extends AbstractDonateBloodActivity {

	private ListView mDonationPlacesListView;

	private DonationPlacesAdapter mPlaceListAdapter;
	private ArrayList<EventInformation> mDataItems = new ArrayList<EventInformation>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donation_placelist);
		setTitle("Donation Centers");
		mDonationPlacesListView = (ListView) findViewById(R.id.donationPlacesList);

		mPlaceListAdapter = new DonationPlacesAdapter(this,
				R.layout.donation_placelist, mDataItems);

		mDonationPlacesListView.setAdapter(mPlaceListAdapter);

		new DownloadListTask().execute();

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	class DownloadListTask extends
			AsyncTask<Void, Void, ArrayList<EventInformation>> {

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = Utils.showProgressDialog(
					DonationPlacesActivity.this, null, false);
			progressDialog.show();
		}

		@Override
		protected ArrayList<EventInformation> doInBackground(Void... arg0) {
			String response = Utils.getHttpRequest(DonationPlacesActivity.this,
					Constants.EVENT_DATE_CALENDAR_URL);
			Log.d("SDF", "net response " + response);
			EventInformationParser eventParser = new EventInformationParser(
					response);
			return eventParser.getJSONEvents();
		}

		@Override
		protected void onPostExecute(ArrayList<EventInformation> response) {
			progressDialog.dismiss();
			if (response != null) {

				for (EventInformation repo : response) {
					mDataItems.add(repo);
				}
				mPlaceListAdapter.notifyDataSetChanged();
			} else {
				Utils.toast(DonationPlacesActivity.this, "No data");
			}

		}

	}

}
