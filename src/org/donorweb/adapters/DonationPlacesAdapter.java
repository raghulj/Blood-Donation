package org.donorweb.adapters;

import java.text.ParseException;
import java.util.ArrayList;

import org.donorweb.R;
import org.donorweb.activity.AbstractDonateBloodActivity;
import org.donorweb.model.EventInformation;
import org.donorweb.utils.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DonationPlacesAdapter extends ArrayAdapter<EventInformation> {

	protected LayoutInflater mLayoutInflater;

	protected ArrayList<EventInformation> mDataItems;
	private AbstractDonateBloodActivity mActivity;

	public DonationPlacesAdapter(AbstractDonateBloodActivity activity,
			int textViewResourceId, ArrayList<EventInformation> objects) {
		super(activity, textViewResourceId, objects);
		mDataItems = objects;
		mActivity = activity;
		mLayoutInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	static class DonatePlaceViewHolder {
		RelativeLayout itemLayout;
		TextView titleText;
		TextView locationText;
		TextView weekendText;
		TextView eventTimeText;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listView = null;
		DonatePlaceViewHolder donatePlaceHolder;
		if (convertView == null) {
			listView = mLayoutInflater.inflate(R.layout.place_item_view, null);

			donatePlaceHolder = new DonatePlaceViewHolder();

			donatePlaceHolder.itemLayout = (RelativeLayout) listView
					.findViewById(R.id.donationPlaceItem);

			donatePlaceHolder.weekendText = (TextView) listView
					.findViewById(R.id.WeekDay);
			donatePlaceHolder.eventTimeText = (TextView) listView
					.findViewById(R.id.eventTime);
			donatePlaceHolder.titleText = (TextView) listView
					.findViewById(R.id.eventTitle);
			donatePlaceHolder.locationText = (TextView) listView
					.findViewById(R.id.eventLocation);
			listView.setTag(donatePlaceHolder);

		} else {
			listView = convertView;
		}
		donatePlaceHolder = (DonatePlaceViewHolder) listView.getTag();
		final EventInformation eventTime = mDataItems.get(position);
		donatePlaceHolder.weekendText.setText(Utils.getDate(eventTime
				.getEventStartTime()));
		String eventtime = "";
		try {
			eventtime = Utils.getTime(eventTime.getEventStartTime()) + "-"
					+ Utils.getTime(eventTime.getEventEndTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		donatePlaceHolder.eventTimeText.setText(eventtime);
		donatePlaceHolder.titleText.setText(eventTime.getTitle());
		donatePlaceHolder.locationText.setText(Utils.truncate(eventTime
				.getLocation()));

		donatePlaceHolder.itemLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mActivity.startPlaceLocationActivity(eventTime);

			}
		});

		return listView;

	}
}
