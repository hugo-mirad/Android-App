package com.mobi.mobicarz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PreferenceOne extends Activity implements OnClickListener {
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
	Spinner spinnerMakes, spinnerModels, Within, spinnerMillage, spinnerYear,
			spinnerPrice;
	EditText et_zip;
	static final String[] millage = new String[] { "0-5000 miles",
			"5000-10000 miles", "10000-25000 miles", "25000-50000 miles",
			"50000-75000 miles", "75000-100000 miles", "100000+ miles" };
	
	static final String[] Year = new String[] { "Current Year", "One Year Old",
			"Upto 3 Years Old", "Upto 5 Years Old", "Upto 10 Years Old", "Any" };
	
	static final String[] Price = new String[] { "Below 20,000",
			"20,000 to 50,000", "50,000 to 75,000", "75,000 to 100,000",
			"Above 100,000" };
	Button Searchbutton, preferencesback;
	ArrayList<MakeObjects> modelList = new ArrayList<MakeObjects>();
	String[] MakeList_array, make_arrayid;
	JSONArray MakeListObj;
	String PreferencesmodelnameSelected_one = null,
			PreferencesmakenameSelected_one = null,
			PreferencesMillageSelected_one = null,
			PreferencesYearSelected_one = null,
			PreferencespriceSelected_one = null, Preferencesmodel_one = null;
	ArrayList<String> getModels = new ArrayList<String>();
	ArrayList<String> getModelsid = new ArrayList<String>();
	String PriceAl = new String();
	String MillageAl = new String();
	String ModelAl = new String();
	String zip = new String();
	String yearAl = new String();
	String carIDlist = new String();
	String results = new String();
	String make = new String();
	String n1 = "";
	final String PREFS_NAME = "LoginPrefs";

	String st_make1 = "", st_model1, st_price1, st_mileage1, st_year1,
			st_zip1 = "";
	int st_make1kk, st_model1kk;
	int kk, model_kk;
	String service_pagecount;
	public static final String TAG_pagecount = "_PageCount";
	String preferencetwo_model="", preferencethree_model = "",
			preferencefour_model = "", preferencefive_model = "";
     String model;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.preferencepage);
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
		btn_car = (Button) findViewById(R.id.car);
		btn_preferencs = (Button) findViewById(R.id.preference);
		btn_mylist = (Button) findViewById(R.id.mylist);
		btn_search = (Button) findViewById(R.id.search);
		
		btn_preferencs.setBackgroundResource(R.drawable.preference_act);

		btn_car.setOnClickListener(this);
		btn_preferencs.setOnClickListener(this);
		btn_mylist.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		SharedPreferences settings3 = getSharedPreferences(PREFS_NAME, 0);
		if (settings3.getString("preference_logged", "").toString()
				.equals("logged_1")) {

			// st_make1=settings3.getString("preference_one_make", "");
			st_make1kk = settings3.getInt("preference_one_make", 0);
			// st_model1=settings3.getString("preference_one_model","");
			st_model1kk = settings3.getInt("preference_one_model", 0);
			st_mileage1 = settings3.getString("preference_one_mileage", "");
			st_price1 = settings3.getString("preference_one_price", "");
			st_year1 = settings3.getString("preference_one_year", "");
			st_zip1 = settings3.getString("preference_one_zip", "");
		}

		String url = "http://unitedcarexchange.com/CarService/Service.svc/GetMakes";

		grabURL(url.replace(" ", "%20"));
		Button cancel = (Button) findViewById(R.id.cancel);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
		preferencesback = (Button) findViewById(R.id.preferencesback);
		preferencesback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});

		spinnerMillage = (Spinner) findViewById(R.id.spinner5);
		spinnerMillage.setAdapter(new ArrayAdapter<String>(
				PreferenceOne.this, android.R.layout.simple_spinner_item,
				millage));

		List<String> programmingList3 = Arrays.asList(millage);
		boolean result = programmingList3.contains(millage);

		//System.out.println("Does programming Array contains Java? " + result);

		int prv_state = programmingList3.indexOf(st_mileage1);
		//System.out.println("this is index" + prv_state);
		String s = String.valueOf(prv_state);
		//System.out.println("this is indexs" + s);

		spinnerMillage.setSelection(prv_state);
		spinnerMillage.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				Preferenceslist.PreferencesMillageSelected = (String) spinnerMillage
						.getItemAtPosition(spinnerMillage
								.getSelectedItemPosition());
				PreferencesMillageSelected_one = (String) spinnerMillage
						.getItemAtPosition(spinnerMillage
								.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spinnerYear = (Spinner) findViewById(R.id.spinner3);
		spinnerYear.setAdapter(new ArrayAdapter<String>(
							PreferenceOne.this, android.R.layout.simple_spinner_item,
				Year));
		List<String> programmingList4 = Arrays.asList(Year);
		boolean result1 = programmingList4.contains(Year);

		int prv_state1 = programmingList4.indexOf(st_year1);

		String s1 = String.valueOf(prv_state1);

		spinnerYear.setSelection(prv_state1);
		spinnerYear.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Preferenceslist.PreferencesYearSelected = (String) spinnerYear
						.getItemAtPosition(spinnerYear
								.getSelectedItemPosition());
				PreferencesYearSelected_one = (String) spinnerYear
						.getItemAtPosition(spinnerYear
								.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		spinnerPrice = (Spinner) findViewById(R.id.spinner4);
		spinnerPrice.setAdapter(new ArrayAdapter<String>(
				PreferenceOne.this, android.R.layout.simple_spinner_item,
				Price));
		List<String> programmingList5 = Arrays.asList(Price);
		boolean result2 = programmingList5.contains(Price);
		int prv_state2 = programmingList5.indexOf(st_price1);
		String s2 = String.valueOf(prv_state2);
		spinnerPrice.setSelection(prv_state2);
		spinnerPrice.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				Preferenceslist.PreferencespriceSelected = spinnerPrice
						.getItemAtPosition(
								spinnerPrice.getSelectedItemPosition())
						.toString();
				PreferencespriceSelected_one = spinnerPrice.getItemAtPosition(
						spinnerPrice.getSelectedItemPosition()).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		et_zip = (EditText) findViewById(R.id.zipeditText1);
		if (st_zip1.equals("0")) {
			et_zip.setText("");
		} else {
			et_zip.setText(st_zip1);
		}

		Button save = (Button) findViewById(R.id.savebutton1);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (spinnerMillage.getSelectedItem().toString()
						.equalsIgnoreCase("0-5000 miles")) {
					MillageAl = "Mileage1";
				} else if (spinnerMillage.getSelectedItem().toString()
						.equalsIgnoreCase("5000-10000 miles")) {
					MillageAl = "Mileage2";
				} else if (spinnerMillage.getSelectedItem().toString()
						.equalsIgnoreCase("10000-25000 miles")) {
					MillageAl = "Mileage3";
				} else if (spinnerMillage.getSelectedItem().toString()
						.equalsIgnoreCase("25000-50000 miles")) {
					MillageAl = "Mileage4";
				} else if (spinnerMillage.getSelectedItem().toString()
						.equalsIgnoreCase("50000-75000 miles")) {
					MillageAl = "Mileage5";
				} else if (spinnerMillage.getSelectedItem().toString()
						.equalsIgnoreCase("75000-100000 miles")) {
					MillageAl = "Mileage6";
				} else if (spinnerMillage.getSelectedItem().toString()
						.equalsIgnoreCase("100000+ miles")) {
					MillageAl = "Mileage7";
				}
				// YEAR
				if (spinnerYear.getSelectedItem().toString()
						.equalsIgnoreCase("Current Year")) {
					yearAl = "Year1a";
				} else if (spinnerYear.getSelectedItem().toString()
						.equalsIgnoreCase("One Year Old")) {
					yearAl = "Year1b";
				} else if (spinnerYear.getSelectedItem().toString()
						.equalsIgnoreCase("Upto 3 Years Old")) {
					yearAl = "Year1";
				} else if (spinnerYear.getSelectedItem().toString()
						.equalsIgnoreCase("Upto 5 Years Old")) {
					yearAl = "Year2";
				} else if (spinnerYear.getSelectedItem().toString()
						.equalsIgnoreCase("Upto 10 Years Old")) {
					yearAl = "Year3";
				} else if (spinnerYear.getSelectedItem().toString()
						.equalsIgnoreCase("Any")) {
					yearAl = "Year4";
				}
				// PRICE
				if (spinnerPrice.getSelectedItem().toString()
						.equalsIgnoreCase("Below 20,000")) {
					PriceAl = "Price1";
				} else if (spinnerPrice.getSelectedItem().toString()
						.equalsIgnoreCase("20,000 to 50,000")) {
					PriceAl = "Price2";
				} else if (spinnerPrice.getSelectedItem().toString()
						.equalsIgnoreCase("50,000 to 75,000")) {
					PriceAl = "Price3";
				} else if (spinnerPrice.getSelectedItem().toString()
						.equalsIgnoreCase("75,000 to 100,000")) {
					PriceAl = "Price4";
				} else if (spinnerPrice.getSelectedItem().toString()
						.equalsIgnoreCase("Above 100,000")) {
					PriceAl = "Price5";
				}

				n1 = et_zip.getText().toString();
				if (n1.equals("")) {
					n1 = "0";
				}
				String Sort = "asc";
				String price = "price";
				String PageNumber = "1";
				String CurrentPage = "1";
				String uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
				String cid = MainHomeScreen.CustomerID;
				String url1 = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetCarsFilterAndroidMobile/"
						+ PreferencesmakenameSelected_one.replace(" ", "%20")
						+ "/"
						+ PreferencesmodelnameSelected_one.replace(" ", "%20")
						+ "/"
						+ MillageAl
						+ "/"
						+ yearAl
						+ "/"
						+ PriceAl
						+ "/"
						+ Sort
						+ "/"
						+ price
						+ "/"
						+ PageNumber
						+ "/"
						+ CurrentPage + "/" + n1 + "/" + uid + "/" + cid;
		//		System.out.println("this is preference one url" + url1);
				JSONParser jParser = new JSONParser();

				// getting JSON string from URL
				JSONObject json = jParser.getJSONFromUrl(url1);
		//		System.out.println("this is json obj" + json);
				try {
					// Getting Array of Contacts

					JSONArray contacts = json
							.getJSONArray("GetCarsFilterAndroidMobileResult");
					if (contacts.length() == 0) {
						Toast.makeText(getApplicationContext(),
								"No Records,Search again", Toast.LENGTH_SHORT)
								.show();
						//finish();
					}else{
					for (int i = 0; i < contacts.length(); i++) {
						JSONObject c = contacts.getJSONObject(i);
						service_pagecount = c.getString(TAG_pagecount);
					}
					}
					int m = contacts.length();
					/*System.out.println("this is preference result"
							+ service_pagecount);*/

					if(n1.equals("0")){ 
					model = PreferencesmakenameSelected_one + " , "
							+ Preferencesmodel_one + " , "
							+ PreferencesYearSelected_one + " , " + ("$ ")
							+ PreferencespriceSelected_one + " , "
							+ PreferencesMillageSelected_one+".";
					}else{
						 model = PreferencesmakenameSelected_one + " , "
									+ Preferencesmodel_one + " , "
									+ PreferencesYearSelected_one + " , " + ("$ ")
									+ PreferencespriceSelected_one + " , "
									+ PreferencesMillageSelected_one+" , "+n1+".";
							
					}
					/*System.out.println("this is preference data in page"
							+ model);*/
					// final String PREFS_NAME = "LoginPrefs";
					SharedPreferences settings = getSharedPreferences(
							PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("preference_logged", "logged_1");
					editor.putString("st_preference_one_make",
							PreferencesmakenameSelected_one);
					editor.putInt("preference_one_make", kk);
					editor.putString("st_preference_one_model",
							PreferencesmodelnameSelected_one);
					editor.putInt("preference_one_model", model_kk);
					editor.putString("preference_one_year",
							PreferencesYearSelected_one);
					editor.putString("preference_one_price",
							PreferencespriceSelected_one);
					editor.putString("preference_one_mileage",
							PreferencesMillageSelected_one);
					editor.putString("preference_one_zip", n1);
					editor.putString("model", model);
					editor.putString("preference_result", service_pagecount);

					editor.commit();
					SharedPreferences settings3 = getSharedPreferences(
							PREFS_NAME, 0);
					if (settings3.getString("preference_logged2", "").toString()
							.equals("logged_2")) {

						preferencetwo_model = settings3.getString("model_two", "");
						 /*preference_oneresult = settings3.getString(
								"preference_result", "");*/

					}
					SharedPreferences settings5 = getSharedPreferences(
							PREFS_NAME, 0);
					if (settings5.getString("preference_logged3", "")
							.toString().equals("logged_3")) {

						preferencethree_model = settings5.getString(
								"model_three", "");
						
					}
					SharedPreferences settings6 = getSharedPreferences(
							PREFS_NAME, 0);
					if (settings6.getString("preference_logged4", "")
							.toString().equals("logged_4")) {

						preferencefour_model = settings6.getString(
								"model_four", "");
					}
					SharedPreferences settings7 = getSharedPreferences(
							PREFS_NAME, 0);
					if (settings7.getString("preference_logged5", "")
							.toString().equals("logged_5")) {

						preferencefive_model = settings7.getString(
								"model_five", "");
					}
					if (preferencetwo_model.equals(model)
							|| preferencethree_model.equals(model)
							|| preferencefour_model.equals(model)
							|| preferencefive_model.equals(model)) {
						Toast.makeText(getApplicationContext(),
								"Duplicate Preference Found", Toast.LENGTH_LONG)
								.show();
					}

					else {

						finish();
					}
					//finish();
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),
							"Server Error occured,Please try again",
							Toast.LENGTH_LONG).show();
				}

			}

		});
	}

	private void grabURL(String replace) {
		// TODO Auto-generated method stub
		try {
			MakeListObj = PreferenceView.MakeListObj;// json.getJSONArray("GetMakesResult");
			MakeList_array = new String[MakeListObj.length()];
			MakeObjects modelCarObjects = null;
			for (int i = 0; i < MakeListObj.length(); i++) {

				modelCarObjects = new MakeObjects();
				// add to country names array
				modelCarObjects.setModelName(MakeListObj.getJSONObject(i)
						.getString("_make"));
				modelCarObjects.setModelId(MakeListObj.getJSONObject(i)
						.getString("_makeID"));
				modelList.add(modelCarObjects);

				MakeList_array[i] = modelCarObjects.getModelName();
			}
			spinnerMakes = (Spinner) findViewById(R.id.spinner1);

			spinnerMakes.setAdapter(new ArrayAdapter<String>(
					PreferenceOne.this,
					android.R.layout.simple_spinner_item, MakeList_array));

			spinnerMakes.setSelection(st_make1kk);

			OnItemSelectedListener spinnerListener = new MyOnItemSelectedListener(
					this);
			spinnerMakes.setOnItemSelectedListener(spinnerListener);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		Context mContext;

		public MyOnItemSelectedListener(Context context) {
			this.mContext = context;
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			Preferenceslist.PreferencesmakeSelected = modelList.get(pos)
					.getModelId();
			kk = spinnerMakes.getSelectedItemPosition();
			PreferencesmakenameSelected_one = (String) spinnerMakes
					.getItemAtPosition(spinnerMakes.getSelectedItemPosition());
			/*System.out.println("spinner 34 value"
					+ Preferenceslist.PreferencesmakenameSelected_one);
			System.out
					.println("mans" + Preferenceslist.PreferencesmakeSelected);*/

			SharedPreferences sharedPreferences = getSharedPreferences("Mai",
					MODE_PRIVATE);
			sharedPreferences.edit().putInt("PREF_SPINNER", pos).commit();

			ModelListHandler handler = new ModelListHandler(
					getApplicationContext());
			final ArrayList<ModelObjects> modelObjects = handler
					.getModelListForMake(modelList.get(pos).getModelId());
			// ArrayList<String> modelObjectsall;
			getModels.clear();
			getModelsid.clear();
			for (int i = 0; i < modelObjects.size(); i++) {
				getModels.add(modelObjects.get(i).getModel());
				getModelsid.add(modelObjects.get(i).getMakeModelID());
			}
			modelObjects.clear();
			spinnerModels = (Spinner) findViewById(R.id.spinner2);

			int m = getModels.size();
		//	System.out.println("this is m value" + m);
			if (m > 1) {
				getModels.add(0, "ALL MODELS");
			}

			spinnerModels.setAdapter(new ArrayAdapter<String>(
					PreferenceOne.this, android.R.layout.simple_spinner_item,
					getModels));

			if (getModels.size() > st_model1kk) {
				spinnerModels.setSelection(st_model1kk);
			}
			spinnerModels.invalidate();

			spinnerModels
					.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int pos, long arg3) {
							// TODO Auto-generated method stub
							PreferencesmodelnameSelected_one = (String) spinnerModels
									.getItemAtPosition(spinnerModels
											.getSelectedItemPosition());
							Preferencesmodel_one = (String) spinnerModels
									.getItemAtPosition(spinnerModels
											.getSelectedItemPosition());
							model_kk = spinnerModels.getSelectedItemPosition();
							if (PreferencesmodelnameSelected_one
									.equals("ALL MODELS")) {
								PreferencesmodelnameSelected_one = "ALL";
							}
							/*System.out.println("this is preference one"
									+ Preferenceslist.PreferencesmodelSelected);*/

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						}
					});

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}

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
}
