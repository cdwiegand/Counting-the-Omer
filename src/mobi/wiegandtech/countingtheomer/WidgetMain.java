package mobi.wiegandtech.countingtheomer;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetMain extends AppWidgetProvider {
	protected static final int DIALOG_MENU = 435668;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetId);
		}
	}

	@Override
	public void onReceive(final Context context, Intent intent) {
		if ("MOBI_WIEGANDTECH_COUNTINGTHEOMER_UPDATE"
				.equals(intent.getAction())) {
			updateAppWidget(context, AppWidgetManager.INVALID_APPWIDGET_ID);
		} else {
			super.onReceive(context, intent);
		}
	}

	public static void updateAppWidget(Context context, int appWidgetId) {
		RemoteViews remoteView = new RemoteViews(context.getPackageName(),
				R.layout.main);

		remoteView.setTextViewText(R.id.TextView01, getOmerText());

		Intent it = new Intent(context, WidgetMain.class);
		it.setAction("MOBI_WIEGANDTECH_COUNTINGTHEOMER_UPDATE");
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, it, 0);
		remoteView.setOnClickPendingIntent(R.id.TextView01, pi);

		// have to call this AFTER setOnClickPendingIntent!
		AppWidgetManager mgr = AppWidgetManager.getInstance(context);
		if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			ComponentName me = new ComponentName(context, WidgetMain.class);
			mgr.updateAppWidget(me, remoteView);
		} else {
			mgr.updateAppWidget(appWidgetId, remoteView);
		}
	}

	public static String getOmerText() {
		long now = convertMillisToDays(Calendar.getInstance());
		// code for 2011 only!
		Calendar startOfOmerCal = GregorianCalendar.getInstance();
		startOfOmerCal.set(2011, 3, 19);
		// I HATE JAVA MONTHS STARTING AT 0!!!
		long startOfOmer = convertMillisToDays(startOfOmerCal);
		// get days
		long dayOfOmer = (now - startOfOmer);
		if (dayOfOmer == -1)
			return Math.abs(dayOfOmer) + " day until we count!";
		if (dayOfOmer < 0)
			return Math.abs(dayOfOmer) + " days until we count!";
		if (dayOfOmer >= 49)
			return "Done counting the omer for 2011! Congrats!";
		return "Tonight is day " + (dayOfOmer + 1) + " of the omer.";
	}

	private static long convertMillisToDays(Calendar value) {
		return value.getTimeInMillis() / 1000 / 60 / 60 / 24;
	}
}
