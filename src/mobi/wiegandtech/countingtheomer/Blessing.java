package mobi.wiegandtech.countingtheomer;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Blessing extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.blessing);
		TextView tv = (TextView) findViewById(R.id.TextView01);
		tv.setText(getBlessingText());

		tv = (TextView) findViewById(R.id.TextView02chabad);
		tv.setOnClickListener(new UrlClickListener(this
				.getString(R.string.url_chabad)));
		tv = (TextView) findViewById(R.id.TextView02aish);
		tv.setOnClickListener(new UrlClickListener(this
				.getString(R.string.url_aish)));
		tv = (TextView) findViewById(R.id.TextView02mjl);
		tv.setOnClickListener(new UrlClickListener(this
				.getString(R.string.url_mjl)));
	}

	private String getBlessingText() {
		String s = "";

		int dayOfOmer = WidgetMain.getDayOfOmer(); // we want TONIGHTS
		if (dayOfOmer < 1) {
			s = "We are not yet counting the omer, silly!";
		} else if (dayOfOmer > 49) {
			s = "We are all done counting the omer - congrats!";
		} else {
			// if after 4, we're in "evening mode", use current day
			if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 16)
			{
				if (dayOfOmer <= 7)
					s = getFirstWeekString(s, dayOfOmer, true);
				else
					s = getNotFirstWeekString(s, dayOfOmer, true);
			} else {
				// roll back a day, we are in "morning" mode
				dayOfOmer--;
				if (dayOfOmer <= 7)
					s = getFirstWeekString(s, dayOfOmer, false);
				else
					s = getNotFirstWeekString(s, dayOfOmer, false);
			}
		}
		return s;
	}

	private String getFirstWeekString(String s, int dayOfOmer, boolean isEvening) {
		if (isEvening)
			s += getString(R.string.blessing);
		else
			s += getString(R.string.blessingMORNING);
		s += "\n\n";
		s += getString(isEvening ? R.string.blessing2daysEVENING
				: R.string.blessing2daysMORNING);
		s = s.replace("{DAY}", Integer.toString(dayOfOmer)
				+ getOrdinalFor(dayOfOmer));
		return s;
	}

	private String getNotFirstWeekString(String s, int dayOfOmer,
			boolean isEvening) {
		int weeks = dayOfOmer / 7;
		int days = dayOfOmer % 7;
		String weeksTmp = Integer.toString(weeks) + " week";
		if (weeks > 1)
			weeksTmp += "s";
		if (days > 0) {
			weeksTmp += " and " + Integer.toString(days) + " day";
			if (days > 1)
				weeksTmp += "s";
		}

		if (isEvening)
			s += getString(R.string.blessing);
		else
			s += getString(R.string.blessingMORNING);
		s += "\n\n";
		s += getString(isEvening ? R.string.blessing2daysweeksEVENING
				: R.string.blessing2daysweeksMORNING);
		s = s.replace("{DAY}", Integer.toString(dayOfOmer)
				+ getOrdinalFor(dayOfOmer));
		s = s.replace("{WEEK}", weeksTmp);
		return s;
	}

	public class UrlClickListener implements OnClickListener {
		private String url = "";

		public UrlClickListener(String urlToOpen) {
			url = urlToOpen;
		}

		@Override
		public void onClick(View v) {
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			Blessing.this.startActivity(i);
		}
	}

	public static String getOrdinalFor(int value) {
		int hundredRemainder = value % 100;
		int tenRemainder = value % 10;
		if (hundredRemainder - tenRemainder == 10) {
			return "th";
		}

		switch (tenRemainder) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

}
