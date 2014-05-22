package com.mobi.sellacar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class SellCarDetails_Features extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	String car_id, url;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	JSONArray contacts = null;
	private static final String TAG_GETCARFEATURES = "GetCarFeaturesResult";
	private Map<String, String> mapFeatures;
	String[] keyVal;
	TextView features_comfort, features_seats, features_safety, features_sound,
			features_new1, features_windows, features_others,
			features_specials;
	Button sellcardetailsfeatures_btn;
	String res, seats, safety, sound, new1, windows, others, specials;
	LinearLayout ll_features;
	public static final int TABLET_MIN_DP_WEIGHT = 450;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.sellcardetails_features);
			if (isSmartphone(SellCarDetails_Features.this)) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			//	Toast.makeText(this, "Smartphone", Toast.LENGTH_SHORT).show();
			} else {
			//	Toast.makeText(this, "Tablet", Toast.LENGTH_SHORT).show();
			}
			Bundle b = this.getIntent().getExtras();
			car_id = b.getString("car_id");
			//System.out.println("thsi is car_id" + car_id);
			ll_features = (LinearLayout) findViewById(R.id.ll_features);
			
			sellcardetailsfeatures_btn = (Button) findViewById(R.id.sellcarfeatures_button1);
			sellcardetailsfeatures_btn
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							finish();
						}
					});

			loaddata();
			int padding1 = this.getResources().getInteger(
					R.integer.padding_left);
			int padding2 = this.getResources()
					.getInteger(R.integer.padding_top);
			int padding3 = this.getResources().getInteger(
					R.integer.padding_right);
			int padding4 = this.getResources().getInteger(
					R.integer.padding_bottom);
			TextView tv = new TextView(this);
			TextView tv_comfort = new TextView(this);
			tv_comfort.setText("Comfort: ");
			tv_comfort.setTextSize(getResources()
					.getDimension(R.dimen.textsize));
			tv_comfort.setTypeface(Typeface.DEFAULT_BOLD);
			tv_comfort.setTextColor(Color.parseColor("#333333"));
			tv.setText(res + ".");
			tv.setTextColor(Color.parseColor("#333333"));
			tv.setTextSize(getResources().getDimension(R.dimen.textsize1));
			tv_comfort.setPadding(padding1, padding2, padding3, padding4);
			if (res != null) {
				ll_features.addView(tv_comfort);
				ll_features.addView(tv);

			}
			TextView tv1 = new TextView(this);
			TextView tv_seats = new TextView(this);
			tv_seats.setText("Seats: ");
			tv_seats.setTextSize(getResources().getDimension(R.dimen.textsize));
			tv_seats.setTypeface(Typeface.DEFAULT_BOLD);
			tv_seats.setTextColor(Color.parseColor("#333333"));
			tv1.setText(seats + ".");
			tv1.setTextColor(Color.parseColor("#333333"));
			tv1.setTextSize(getResources().getDimension(R.dimen.textsize1));
			tv_seats.setPadding(padding1, padding2, padding3, padding4);
			if (seats != null) {
				ll_features.addView(tv_seats);
				ll_features.addView(tv1);
			}
			TextView tv2 = new TextView(this);
			TextView tv_safety = new TextView(this);
			tv_safety.setText("Safety: ");
			tv_safety
					.setTextSize(getResources().getDimension(R.dimen.textsize));
			tv_safety.setTypeface(Typeface.DEFAULT_BOLD);
			tv_safety.setTextColor(Color.parseColor("#333333"));
			tv2.setText(safety + ".");
			tv_safety.setPadding(padding1, padding2, padding3, padding4);
			tv2.setTextSize(getResources().getDimension(R.dimen.textsize1));
			tv2.setTextColor(Color.parseColor("#333333"));
			if (safety != null) {
				ll_features.addView(tv_safety);
				ll_features.addView(tv2);

			}
			TextView tv3 = new TextView(this);
			TextView tv_sound = new TextView(this);
			tv_sound.setText("Sound System: ");
			tv_sound.setTextSize(getResources().getDimension(R.dimen.textsize));
			tv_sound.setTypeface(Typeface.DEFAULT_BOLD);
			tv_sound.setTextColor(Color.parseColor("#333333"));
			tv3.setText(sound + ".");
			tv3.setTextColor(Color.parseColor("#333333"));
			tv3.setTextSize(getResources().getDimension(R.dimen.textsize1));
			tv_sound.setPadding(padding1, padding2, padding3, padding4);
			if (sound != null) {
				ll_features.addView(tv_sound);
				ll_features.addView(tv3);

			}
			TextView tv4 = new TextView(this);
			TextView tv_new = new TextView(this);
			tv_new.setText("New: ");
			tv_new.setTextSize(getResources().getDimension(R.dimen.textsize));
			tv_new.setTypeface(Typeface.DEFAULT_BOLD);
			tv_new.setTextColor(Color.parseColor("#333333"));
			tv4.setText(new1 + ".");
			tv4.setTextColor(Color.parseColor("#333333"));
			tv4.setTextSize(getResources().getDimension(R.dimen.textsize1));
			tv_new.setPadding(padding1, padding2, padding3, padding4);
			if (new1 != null) {
				ll_features.addView(tv_new);
				ll_features.addView(tv4);
			}
			TextView tv5 = new TextView(this);
			TextView tv_window = new TextView(this);
			tv_window.setText("Windows: ");
			tv_window
					.setTextSize(getResources().getDimension(R.dimen.textsize));
			tv_window.setTypeface(Typeface.DEFAULT_BOLD);
			tv_window.setTextColor(Color.parseColor("#333333"));
			tv5.setText(windows + ".");
			tv5.setTextSize(getResources().getDimension(R.dimen.textsize1));
			tv5.setTextColor(Color.parseColor("#333333"));
			tv_window.setPadding(padding1, padding2, padding3, padding4);
			if (windows != null) {
				ll_features.addView(tv_window);
				ll_features.addView(tv5);
			}
			TextView tv6 = new TextView(this);
			TextView tv_other = new TextView(this);
			tv_other.setText("Other: ");
			tv_other.setTextSize(getResources().getDimension(R.dimen.textsize));
			tv_other.setTypeface(Typeface.DEFAULT_BOLD);
			tv_other.setTextColor(Color.parseColor("#333333"));
			tv6.setText(others + ".");
			tv6.setTextSize(getResources().getDimension(R.dimen.textsize1));
			tv6.setTextColor(Color.parseColor("#333333"));
			tv_other.setPadding(padding1, padding2, padding3, padding4);
			if (others != null) {
				ll_features.addView(tv_other);
				ll_features.addView(tv6);
			}
			TextView tv7 = new TextView(this);
			TextView tv_specials = new TextView(this);
			tv_specials.setText("Specials: ");
			tv_specials.setTextSize(getResources().getDimension(
					R.dimen.textsize));
			tv_specials.setTypeface(Typeface.DEFAULT_BOLD);
			tv_specials.setTextColor(Color.parseColor("#333333"));
			tv7.setText(specials + ".");
			tv7.setTextColor(Color.parseColor("#333333"));
			tv_specials.setPadding(padding1, padding2, padding3, padding4);
			tv7.setTextSize(getResources().getDimension(R.dimen.textsize1));
			if (specials != null) {
				ll_features.addView(tv_specials);
				ll_features.addView(tv7);

			}
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						SellCarDetails_Features.this).create();
				alertDialog.setTitle("Info");
				alertDialog
						.setMessage("Internet not available, Cross check your internet connectivity and try again");
				alertDialog.setIcon(R.drawable.icon);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						});
				alertDialog.show();
			} catch (Exception e) {

			}
		}

	}

	public static boolean isSmartphone(Activity act) {
		DisplayMetrics metrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int dpi = 0;
		if (metrics.widthPixels < metrics.heightPixels) {
			dpi = (int) (metrics.widthPixels / metrics.density);
		} else {
			dpi = (int) (metrics.heightPixels / metrics.density);
		}

		if (dpi < TABLET_MIN_DP_WEIGHT) {
			return true;
		} else {
			return false;
		}
	}

	private void loaddata() {
		// TODO Auto-generated method stub
		url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetCarFeatures/"
				+ car_id + "/" + Uid + "/" + Uno + "/";

		// "http://test1.unitedcarexchange.com/carservice/ServiceMobile.svc/GetCarFeatures/1902/ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654/12345/";
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		try {
			// Getting Array of Contacts
			contacts = json.getJSONArray(TAG_GETCARFEATURES);
			if (contacts.length() == 0) {
				Toast.makeText(getApplicationContext(),
						"No Features for this car", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				if (mapFeatures == null) {
					mapFeatures = new HashMap<String, String>();
				}
				for (int i = 0; i < contacts.length(); i++) {
					keyVal = contacts.getString(i).split(",");

					if (keyVal.length == 2) {
						String value = "";
						if (mapFeatures.get(keyVal[0]) != null) {
							value = mapFeatures.get(keyVal[0]) + ", ";
						}
						value = value + keyVal[1];
						mapFeatures.put(keyVal[0], value);
						String comfort = value + keyVal[1];

						if (mapFeatures.get("Comfort") != null) {
							res = mapFeatures.get("Comfort").trim();
							

						}
						if (mapFeatures.get("Seats") != null) {
							seats = mapFeatures.get("Seats").trim();
							

						}
						if (mapFeatures.get("Safety") != null) {
							safety = mapFeatures.get("Safety").trim();
							
						}
						if (mapFeatures.get("Sound System") != null) {
							sound = mapFeatures.get("Sound System").trim();
							
						}
						if (mapFeatures.get("New") != null) {
							new1 = mapFeatures.get("New").trim();

							

						}
						if (mapFeatures.get("Windows") != null) {
							windows = mapFeatures.get("Windows").trim();
							
						}
						if (mapFeatures.get("Other") != null) {
							others = mapFeatures.get("Other").trim();
							
						}
						if (mapFeatures.get("Specials") != null) {
							specials = mapFeatures.get("Specials").trim();
						
						}
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();

			Toast.makeText(getApplicationContext(),
					"Server Error occured,Please try again", Toast.LENGTH_LONG)
					.show();

		}
	}

}
