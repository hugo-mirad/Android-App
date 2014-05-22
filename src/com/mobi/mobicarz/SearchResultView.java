package com.mobi.mobicarz;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultView extends Activity implements OnClickListener {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	int mPrevTotalItemCount = 0;
	GridView list;
	com.mobi.adapters.ListViewAdapter adapter;
	public ArrayList<CarInfo> carMainList = new ArrayList<CarInfo>();
	final String domainName = "http://www.unitedcarexchange.com/";
	// public static
	ProgressDialog dialog;
	static int MAX_PAGE = 45;
	static int pageNo = 1;
	JSONArray contacts = null;

	TextView Makes, Models, within, ZIP, textView1;
	// String strMake,strModel,strwithin,strzip;
	String strMake = ItemList.makeSelected.trim().replaceAll(" ", "%20");
	String strModel = ItemList.modelSelected.trim().replaceAll(" ", "%20");
	String strMake1 = ItemList.makeSelected.trim();
	String strModel1 = ItemList.modelSelected.trim();
	// String strwithin=ItemList.Within;
	String strzip;
	Button searchback;
	public static final String TAG_MAKE = "_make";
	public static final String TAG_YEAR = "_yearOfMake";
	public static final String TAG_model = "_model";
	public static final String TAG_Mileage = "_mileage";
	public static final String TAG_PRICE = "_price";
	public static final String TAG_picloc = "_PICLOC0";
	public static final String TAG_pic = "_PIC0";
	public static final String TAG_IMAGE = null;
	public static final String TAG_CARID = "_carid";
	String searchresultyearofmake, searchresultmodel, searchresultmake,
			searchresultmillage, searchresultprice, searchresultpicloc,
			searchresultpic, searchresultpicurl, searchresultcarid, imageurl;
	// ListViewAdapter adapter;
	ListView searchresult_lv;
	TextView tv_searchresults_content;
	Button btn_car, btn_search, btn_preferencs, btn_mylist;
	int value;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	//	System.out.println("this si search result view");
		if (isOnline()) {
			setContentView(R.layout.searchresultview);
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
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setMinimumFractionDigits(0);

		NumberFormat Millageformat = NumberFormat.getNumberInstance();
		Millageformat.setMinimumIntegerDigits(1);
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
		btn_car = (Button) findViewById(R.id.car);
		btn_preferencs = (Button) findViewById(R.id.preference);
		btn_mylist = (Button) findViewById(R.id.mylist);
		btn_search=(Button)findViewById(R.id.search);
		btn_search.setBackgroundResource(R.drawable.search_act);

		btn_car.setOnClickListener(this);
		btn_preferencs.setOnClickListener(this);
		btn_mylist.setOnClickListener(this);
	//	System.out.println("this is modelselected" + strModel);
		Bundle b = getIntent().getExtras();
		value = b.getInt("ZIP", 0);
		textView1 = (TextView) findViewById(R.id.nodata);
		textView1.setVisibility(View.GONE);
		searchback = (Button) findViewById(R.id.Searchback);
		tv_searchresults_content = (TextView) findViewById(R.id.tv_show_content);
		tv_searchresults_content.setText(strMake1 + " " + strModel1);
		searchback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// finish();
				Intent in = new Intent(SearchResultView.this, SearchView.class);
						startActivity(in);
			}
		});
		final String strzip = String.valueOf(value);
		String strwithin = "100 miles";
		if (strwithin == "100 miles") {
			strwithin = "5";
		}
		String pageresultscount = "20";
		String page = "1";
		String udi = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
		String uno =  MainHomeScreen.CustomerID;
		String Price = "Price";
		String url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetCarsSearchJSON/"

				+ strMake
				+ "/"
				+ strModel
				+ "/"
				+ strzip
				+ "/"
				+ strwithin
				+ "/"
				+ page
				+ "/"
				+ pageresultscount
				+ "/"
				+ Price
				+ "/"
				+ udi
				+ "/" + uno + "/";
		//System.out.println("this is my json url" + url);
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
	//	System.out.println("this is json obj" + json);
		try {
			// Getting Array of Contacts

			contacts = json.getJSONArray("GetCarsSearchJSONResult");
			if (contacts.length() == 0) {
				Toast.makeText(getApplicationContext(),
						"No Records,Search again", Toast.LENGTH_SHORT).show();
				// finish();
			}
			for (int i = 0; i < contacts.length(); i++) {
				JSONObject c = contacts.getJSONObject(i);
				// reg_CarIDs=c.getString("CarIDs");
				String caruid = c.getString("_CarUniqueID");
				searchresultmake = c.getString(TAG_MAKE)
						.replace("Emp", "");
				searchresultmillage = c.getString(TAG_Mileage)
						.replace(" ", "%20").replace("Emp", "");
				searchresultmodel = c.getString(TAG_model)
						.replace("Emp", "");
				searchresultprice = c.getString(TAG_PRICE).replace(" ", "%20")
						.replace("Emp", "");
				searchresultyearofmake = c.getString(TAG_YEAR)
						.replace(" ", "%20").replace("Emp", "");
				searchresultpic = c.getString(TAG_pic).replace(" ", "%20")
						.replace("Emp", "");
				searchresultpicloc = c.getString(TAG_picloc)
						.replace(" ", "%20").replace("Emp", "");
				searchresultcarid = c.getString(TAG_CARID);

				// sellpicurl=c.getString(TAG_IMAGE);
				/*System.out.println("this is carid" + caruid);
				System.out.println("this is make" + searchresultmake);
				System.out.println("this is model" + searchresultmodel);
				System.out.println("this is year" + searchresultyearofmake);
				System.out.println("this is price" + searchresultprice);
				System.out.println("this is pic" + searchresultpic);
				System.out.println("this is pic loc" + searchresultpicloc);*/
				imageurl = "http://www.unitedcarexchange.com/"
						+ searchresultpicloc
						+ searchresultpic.replaceAll(" ", "%20");
				//System.out.println("this is image url" + imageurl);

				HashMap<String, String> map = new HashMap<String, String>();

				// adding each child node to HashMap key => value
				map.put(TAG_MAKE, searchresultmake);
				map.put(TAG_Mileage, searchresultmillage);
				map.put(TAG_model, searchresultmodel);
				map.put(TAG_PRICE, searchresultprice);
				map.put(TAG_YEAR, searchresultyearofmake);
				map.put(TAG_IMAGE, imageurl);
				map.put(TAG_CARID, searchresultcarid);

				contactList.add(map);

			}
		} catch (JSONException e) {
			e.printStackTrace();

			Toast.makeText(getApplicationContext(),
					"Server Error occured,Please try again", Toast.LENGTH_LONG)
					.show();

		}

		adapter = new com.mobi.adapters.ListViewAdapter(getApplicationContext(),
				getApplicationContext(), contactList);
		searchresult_lv = (ListView) findViewById(R.id.searchgrid);

		searchresult_lv.setAdapter(adapter);
		searchresult_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent previewMessage = new Intent(getApplicationContext(),
						SearchCarDetailsView.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				TextView tv_carid = (TextView) view
						.findViewById(R.id.tv_selldisplay_carid);
				String playerChanged = tv_carid.getText().toString();
				
				previewMessage.putExtra("CAR_ID", playerChanged);
				previewMessage.putExtra("zip", value);
				
				startActivity(previewMessage);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.car:
			Intent search_in = new Intent(getApplicationContext(),
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(search_in);
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
		default:
			break;

		}

	}

}
