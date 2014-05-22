package com.mobi.mobicarz;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCarsFour extends Activity implements OnClickListener {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	Button btn_car, btn_search, btn_preferencs, btn_mylist;
	final String PREFS_NAME = "LoginPrefs";
	String st_make4 = "", st_model4, st_price4, st_mileage4, st_year4,
			st_zip4 = "", url1, n1 = "";
	String preferenceresultyearofmake, preferenceresultmodel,
			preferenceresultmake, preferenceresultmillage,
			preferenceresultprice, preferenceresultpicloc, preferenceresultpic,
			preferenceresultpicurl, preferenceresultcarid, imageurl;

	ListView searchresult_lv;
	com.mobi.adapters.ListViewAdapter adapter;
	TextView tv_searchresults_content;
	public static final String TAG_MAKE = "_make";
	public static final String TAG_YEAR = "_yearOfMake";
	public static final String TAG_model = "_model";
	public static final String TAG_Mileage = "_mileage";
	public static final String TAG_PRICE = "_price";
	public static final String TAG_picloc = "_PICLOC0";
	public static final String TAG_pic = "_PIC0";
	public static final String TAG_IMAGE = null;
	public static final String TAG_CARID = "_carid";
	TextView textView1;
	int mPrevTotalItemCount = 0;

	String Sort = "asc";
	String price = "price";
	String PageNumber = "9";
	String CurrentPage = "1";
	static int pageNo = 1;
	static int MAX_PAGE = 18;
	String uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String cid = MainHomeScreen.CustomerID;
	String service_pagecount;
	public static final String TAG_pagecount = "_PageCount";
	ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	JSONArray contacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			//System.out.println("this is view cars one");
			int ot = getResources().getConfiguration().orientation;
			switch (ot) {
			case Configuration.ORIENTATION_LANDSCAPE:
				setContentView(R.layout.land_showvalues);
				break;
			case Configuration.ORIENTATION_PORTRAIT:
				setContentView(R.layout.showvalues);
				break;
			}
			InitializeUI();
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						getApplicationContext()).create();
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

	private void InitializeUI() {
		// TODO Auto-generated method stub

		SharedPreferences vi_settings5 = getSharedPreferences(PREFS_NAME, 0);
		if (vi_settings5.getString("preference_logged4", "").toString()
				.equals("logged_4")) {

			st_make4 = vi_settings5.getString("st_preference_four_make", "");
			// st_make1kk=settings3.getInt("preference_one_make", 0);
			st_model4 = vi_settings5.getString("st_preference_four_model", "");
			// st_model1kk=settings3.getInt("preference_one_model", 0);
			st_mileage4 = vi_settings5.getString("preference_four_mileage", "");
			st_price4 = vi_settings5.getString("preference_four_price", "");
			st_year4 = vi_settings5.getString("preference_four_year", "");
			st_zip4 = vi_settings5.getString("preference_four_zip", "");
		}
		//System.out.println("this is make " + st_make4);
		//System.out.println("this is model " + st_model4);

		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
		Button back = (Button) findViewById(R.id.preferencesback);
		btn_car = (Button) findViewById(R.id.car);
		btn_preferencs = (Button) findViewById(R.id.preference);
		btn_mylist = (Button) findViewById(R.id.mylist);
		btn_search = (Button) findViewById(R.id.search);
		textView1 = (TextView) findViewById(R.id.nodata);
		textView1.setVisibility(View.GONE);
		
		btn_preferencs.setBackgroundResource(R.drawable.preference_act);
		btn_car.setOnClickListener(this);
		btn_preferencs.setOnClickListener(this);
		btn_mylist.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// finish();
				Intent in = new Intent(getApplicationContext(),
						PreferenceView.class);
				startActivity(in);

			}
		});
		if (st_mileage4.equalsIgnoreCase("0-5000 miles")) {
			st_mileage4 = "Mileage1";
		} else if (st_mileage4.equalsIgnoreCase("5000-10000 miles")) {
			st_mileage4 = "Mileage2";
		} else if (st_mileage4.equalsIgnoreCase("10000-25000 miles")) {
			st_mileage4 = "Mileage3";
		} else if (st_mileage4.equalsIgnoreCase("25000-50000 miles")) {
			st_mileage4 = "Mileage4";
		} else if (st_mileage4.equalsIgnoreCase("50000-75000 miles")) {
			st_mileage4 = "Mileage5";
		} else if (st_mileage4.equalsIgnoreCase("75000-100000 miles")) {
			st_mileage4 = "Mileage6";
		} else if (st_mileage4.equalsIgnoreCase("100000+ miles")) {
			st_mileage4 = "Mileage7";
		}
		// YEAR
		if (st_year4.equalsIgnoreCase("Current Year")) {
			st_year4 = "Year1a";
		} else if (st_year4.equalsIgnoreCase("One Year Old")) {
			st_year4 = "Year1b";
		} else if (st_year4.equalsIgnoreCase("Upto 3 Years Old")) {
			st_year4 = "Year1";
		} else if (st_year4.equalsIgnoreCase("Upto 5 Years Old")) {
			st_year4 = "Year2";
		} else if (st_year4.equalsIgnoreCase("Upto 10 Years Old")) {
			st_year4 = "Year3";
		} else if (st_year4.equalsIgnoreCase("Any")) {
			st_year4 = "Year4";
		}

		if (st_price4.equalsIgnoreCase("Below 20,000")) {
			st_price4 = "Price1";
		} else if (st_price4.equalsIgnoreCase("20,000 to 50,000")) {
			st_price4 = "Price2";
		} else if (st_price4.equalsIgnoreCase("50,000 to 75,000")) {
			st_price4 = "Price3";
		} else if (st_price4.equalsIgnoreCase("75,000 to 100,000")) {
			st_price4 = "Price4";
		} else if (st_price4.equalsIgnoreCase("Above 100,000")) {
			st_price4 = "Price5";
		}
		searchresult_lv = (ListView) findViewById(R.id.searchgrid);
		n1 = st_zip4;
		url1 = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetCarsFilterAndroidMobile/"
				+ st_make4.replace(" ", "%20")
				+ "/"
				+ st_model4.replace(" ", "%20")
				+ "/"
				+ st_mileage4
				+ "/"
				+ st_year4
				+ "/"
				+ st_price4
				+ "/"
				+ Sort
				+ "/"
				+ price
				+ "/"
				+ PageNumber
				+ "/"
				+ CurrentPage
				+ "/"
				+ n1
				+ "/"
				+ uid
				+ "/"
				+ cid;
		getPreferenceFourData();

		searchresult_lv.setAdapter(adapter);

		searchresult_lv.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (view.getAdapter() != null
						&& ((firstVisibleItem + visibleItemCount) >= totalItemCount)
						&& totalItemCount != mPrevTotalItemCount) {
					Log.v("MainActivity", "onListEnd, extending list");
					mPrevTotalItemCount = totalItemCount;

					if (pageNo < MAX_PAGE) {
						String page = "" + (++pageNo);

						url1 = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetCarsFilterAndroidMobile/"
								+ st_make4.replace(" ", "%20")
								+ "/"
								+ st_model4.replace(" ", "%20")
								+ "/"
								+ st_mileage4
								+ "/"
								+ st_year4
								+ "/"
								+ st_price4
								+ "/"
								+ Sort
								+ "/"
								+ price
								+ "/"
								+ PageNumber
								+ "/"
								+ page
								+ "/"
								+ n1
								+ "/"
								+ uid + "/" + cid;

						getPreferenceFourData();
					}
				}

			}

		});

		searchresult_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent previewMessage = new Intent(getApplicationContext(),
						PreferenceCarDetailViewFour.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				TextView tv_carid = (TextView) view
						.findViewById(R.id.tv_selldisplay_carid);
				String playerChanged = tv_carid.getText().toString();
				/*System.out
						.println("this is car id from search" + playerChanged);*/
				previewMessage.putExtra("CAR_ID", playerChanged);

				startActivity(previewMessage);
			}
		});
	}

	private void getPreferenceFourData() {
		// TODO Auto-generated method stub
		//System.out.println("this is preference one url" + url1);
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url1);

		try {
			// Getting Array of Contacts

			JSONArray contacts = json
					.getJSONArray("GetCarsFilterAndroidMobileResult");

			for (int i = 0; i < contacts.length(); i++) {
				JSONObject c = contacts.getJSONObject(i);
				// reg_CarIDs=c.getString("CarIDs");
				String caruid = c.getString("_CarUniqueID");
				preferenceresultmake = c.getString(TAG_MAKE)
						.replace("Emp", "");
				preferenceresultmillage = c.getString(TAG_Mileage)
						.replace("Emp", "");
				preferenceresultmodel = c.getString(TAG_model)
						.replace("Emp", "");
				preferenceresultprice = c.getString(TAG_PRICE)
						.replace("Emp", "");
				preferenceresultyearofmake = c.getString(TAG_YEAR)
						.replace("Emp", "");
				preferenceresultpic = c.getString(TAG_pic).replace(" ", "%20")
						.replace("Emp", "");
				preferenceresultpicloc = c.getString(TAG_picloc)
						.replace(" ", "%20").replace("Emp", "");
				preferenceresultcarid = c.getString(TAG_CARID);

				// sellpicurl=c.getString(TAG_IMAGE);

				imageurl = "http://www.unitedcarexchange.com/"
						+ preferenceresultpicloc
						+ preferenceresultpic.replaceAll(" ", "%20");
				//System.out.println("this is image url" + imageurl);

				HashMap<String, String> map = new HashMap<String, String>();

				// adding each child node to HashMap key => value
				map.put(TAG_MAKE, preferenceresultmake);
				map.put(TAG_Mileage, preferenceresultmillage);
				map.put(TAG_model, preferenceresultmodel);
				map.put(TAG_PRICE, preferenceresultprice);
				map.put(TAG_YEAR, preferenceresultyearofmake);
				map.put(TAG_IMAGE, imageurl);
				map.put(TAG_CARID, preferenceresultcarid);

				contactList.add(map);
				searchresult_lv.invalidate();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),
					"Server Error occured,Please try again", Toast.LENGTH_LONG)
					.show();

		}
		adapter = new com.mobi.adapters.ListViewAdapter(getApplicationContext(),
				getApplicationContext(), contactList);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.car:
			Intent preferene_in = new Intent(getApplicationContext(),
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(preferene_in);
			break;
		case R.id.mylist:
			Intent mylist_in = new Intent(getApplicationContext(),
					MyListView.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mylist_in);
			break;
		case R.id.preference:
			Intent prefernce_in = new Intent(getApplicationContext(),
					PreferenceView.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(prefernce_in);
			break;
		case R.id.search:
			Intent search_in = new Intent(getApplicationContext(),
					SearchView.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(search_in);
			break;

		default:
			break;

		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		int ot = getResources().getConfiguration().orientation;
		switch (ot) {
		case Configuration.ORIENTATION_LANDSCAPE:
			setContentView(R.layout.land_showvalues);
			break;
		case Configuration.ORIENTATION_PORTRAIT:
			setContentView(R.layout.showvalues);
			break;
		}
		InitializeUI();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		return super.onRetainNonConfigurationInstance();
	}
}
