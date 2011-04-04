package mobi.wiegandtech.countingtheomer;

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

		String s = getString(R.string.blessing);
		s += "\n\n";

		int dayOfOmer = WidgetMain.getDayOfOmer() + 1; // we want TONIGHTS
		if (dayOfOmer < 1) {
			s = "We are not yet counting the omer, silly!";
		} else if (dayOfOmer < 7) {
			s += getString(R.string.blessing2days);
			s.replace("{DAY}", Integer.toString(dayOfOmer)
					+ getOrdinalFor(dayOfOmer));
		} else {
			int weeks = dayOfOmer / 7;
			int days = (dayOfOmer - 7) % 7;
			String weeksTmp = Integer.toString(weeks) + " week";
			if (weeks > 1)
				weeksTmp += "s";
			if (days > 0) {
				weeksTmp += " and " + Integer.toString(days) + " day";
				if (days > 1)
					weeksTmp += "s";
			}

			s += getString(R.string.blessing2daysweeks);
			s = s.replace("{DAY}", Integer.toString(dayOfOmer)
					+ getOrdinalFor(dayOfOmer));
			s = s.replace("{WEEK}", weeksTmp);
		}

		setContentView(R.layout.blessing);
		TextView tv = (TextView) findViewById(R.id.TextView01);
		tv.setText(s);
		tv = (TextView) findViewById(R.id.TextView02);
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://www.myjewishlearning.com/holidays/Jewish_Holidays/Passover/In_the_Community/The_Omer/How_to_Count.shtml"));
				Blessing.this.startActivity(i);
			}
		});
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
