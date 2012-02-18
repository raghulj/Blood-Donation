package org.donorweb.activity;

import org.donorweb.R;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class AboutBloodDonationActivity extends AbstractDonateBloodActivity {

	TextView mAboutText;
	String mAboutContent = "<h1><b>Bloodbank@HSA (Health Sciences Authority)</b></h1><br/><h5><b>HSA Located at:</b></h5>Blood Services Group<br/> Health Sciences Authority (opposite Outram Park MRT Station)<br/> 11 Outram Road <br/> Singapore 169078 <br/> <br/><br/> <h5><b>Bus Services Available</b></h5>33, 63, 75, 174, 851, 970 <br/><br/><br/><h5><pre>Blood Donation On		Opening hours </h5> <br/> Tuesday to Thursday		9 am – 6.30 pm<br/>Friday		9 am – 8 pm<br/>Saturday		9 am – 4.30 pm<br/>Sunday		9 am – 2 pm</pre><br/><pre>Monday & Public Holidays		Closed</pre><br/>";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);
		mAboutText = (TextView) findViewById(R.id.aboutText);

		Spanned spannedContent = Html.fromHtml(mAboutContent);
		mAboutText.setText(spannedContent, BufferType.SPANNABLE);

	}

}
