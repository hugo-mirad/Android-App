package com.mobi.mobicarz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class SearchView extends Activity implements OnClickListener {
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

	Spinner spinnerMakes, spinnerModels, Within;
	ProgressDialog dialog;
	EditText Zip;
	String makecount;
	TextView tv_search_gotohome;
	private ProgressDialog progressDialog;

	SoapObject response = null;
	AndroidHttpTransport aht = null;
	SoapSerializationEnvelope sse = null;
	String output;
	String n1;
	String[] MakeList_array;
	public static JSONArray MakeListObj;
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://unitedcarexchange.com/carservice/CarsService.asmx";
	private final String METHOD_NAME = "CheckZips";
	Button Searchbutton;
	static final String[] WITHIN = new String[] { "Anywhere", "10 miles",
			"25 miles", "50 miles", "100 miles" };
	ArrayList<String> getModels = new ArrayList<String>();
	ArrayList<MakeObjects> modelList = new ArrayList<MakeObjects>();
	String home_zip, url;
	String content = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {

			int desiredLayoutId;
			int ot = getResources().getConfiguration().orientation;
			switch (ot) {
			case Configuration.ORIENTATION_LANDSCAPE:
				setContentView(R.layout.land_searchview);
				break;
			case Configuration.ORIENTATION_PORTRAIT:
				setContentView(R.layout.searchview);
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

	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		int ot = getResources().getConfiguration().orientation;
		switch (ot) {
		case Configuration.ORIENTATION_LANDSCAPE:
			setContentView(R.layout.land_searchview);
			break;
		case Configuration.ORIENTATION_PORTRAIT:
			setContentView(R.layout.searchview);
			break;
		}
		InitializeUI();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		return super.onRetainNonConfigurationInstance();
	}

	private void InitializeUI() {
		// TODO Auto-generated method stub
		btn_car = (Button) findViewById(R.id.car);
		btn_preferencs = (Button) findViewById(R.id.preference);
		btn_mylist = (Button) findViewById(R.id.mylist);
		btn_search=(Button)findViewById(R.id.search);
		btn_search.setBackgroundResource(R.drawable.search_act);
		btn_car.setOnClickListener(this);
		btn_preferencs.setOnClickListener(this);
		btn_mylist.setOnClickListener(this);

		Zip = (EditText) findViewById(R.id.ZIP);
		home_zip = MainActivity.textString;

		tv_search_gotohome = (TextView) findViewById(R.id.search_gotohome);
		spinnerModels = (Spinner) findViewById(R.id.spin2);

		String uno = MainHomeScreen.CustomerID;
		url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetMakeCounts/ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654"
				+ "/" + uno;

		Searchbutton = (Button) findViewById(R.id.searchbutton);
		Zip.setText(home_zip);
		spinnerMakes = (Spinner) findViewById(R.id.Makesearch);

		grabURL(url.replace(" ", "%20"));

		tv_search_gotohome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent in = new Intent(getApplicationContext(),
						MainHomeScreen.class);

				startActivity(in);

			}
		});
		Searchbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				n1 = Zip.getText().toString();

				if (n1.equalsIgnoreCase("0")) {
					n1 = "0";

					Intent edit = new Intent(getApplicationContext(),
							SearchResultView.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					Bundle b = new Bundle();
					ItemList.modelSelected = (String) spinnerModels
							.getItemAtPosition(spinnerModels
									.getSelectedItemPosition());
					/*System.out.println("this is itme model"
							+ ItemList.modelSelected);*/
					ItemList.modelnameSelected = (String) spinnerModels
							.getItemAtPosition(spinnerModels
									.getSelectedItemPosition());
					/*System.out.println("spinner 35 value"
							+ ItemList.modelnameSelected);*/

					b.putInt("ZIP", Integer.parseInt(n1));
					edit.putExtras(b);

					startActivity(edit);

				} /*else if (n1.length() < 5) {

					Zip.setError("Enter Valid Zip!");
					Zip.setText("");
				}*/ else {

					Map<String, String> propMap = new HashMap<String, String>();
					propMap.put("Zip", n1);
					downloadText(propMap);
				}

			}

			private void downloadText(final Map<String, String> propMap) {
				if (progressDialog != null) {
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
				}
				progressDialog = ProgressDialog.show(SearchView.this, "",
						"Fetching Zip Location...", true);

				new Thread() {
					public void run() {
						getResponse(propMap);
					}
				}.start();
			}

			private void getResponse(Map<String, String> propMap) {

				aht = new AndroidHttpTransport(URL);
				sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				sse.encodingStyle = SoapSerializationEnvelope.ENC2001;
				sse.dotNet = true;

				SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
				PropertyInfo propertyInfo = new PropertyInfo();
				propertyInfo.setName("zipId");
				propertyInfo.setType(int.class);
				propertyInfo.setValue("" + Zip.getText().toString());
				soapObject.addProperty(propertyInfo);
				if (soapObject != null) {
					sse.setOutputSoapObject(soapObject);
				}

				try {

					aht.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
					aht.debug = true;
					aht.call(NAMESPACE + METHOD_NAME, sse);
					Log.d("---" + METHOD_NAME + "-----", aht.requestDump);
					Log.d("---" + METHOD_NAME + "-----", aht.responseDump);
					SoapObject resultsRequestSOAP = (SoapObject) sse.bodyIn;
					Log.d("----- resssss -------",
							"" + resultsRequestSOAP.toString());
					if (!resultsRequestSOAP.toString().equals("")) {
						if (resultsRequestSOAP.getProperty("CheckZipsResult") != null
								&& !resultsRequestSOAP
										.getProperty("CheckZipsResult")
										.toString().equals("anyType{}")) {
							output = resultsRequestSOAP.getProperty(
									"CheckZipsResult").toString();
							if (output != null) {
								messageHandler.sendEmptyMessage(0);
							} else {
								messageHandler.sendEmptyMessage(1);
							}
							Log.d("output", "" + output);
						}
					}
				} catch (Exception e) {
					messageHandler.sendEmptyMessage(1);
				}
			}

			Handler messageHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if (progressDialog != null) {
						if (progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
					}
					Boolean value = Boolean.parseBoolean(output);
					if (value == true) {

						Intent edit = new Intent(getApplicationContext(),
								SearchResultView.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						Bundle b = new Bundle();
						ItemList.modelSelected = (String) spinnerModels
								.getItemAtPosition(spinnerModels
										.getSelectedItemPosition());
						/*System.out.println("this is itme model"
								+ ItemList.modelSelected);*/
						ItemList.modelnameSelected = (String) spinnerModels
								.getItemAtPosition(spinnerModels
										.getSelectedItemPosition());
						/*System.out.println("spinner 35 value"
								+ ItemList.modelnameSelected);*/

						b.putInt("ZIP",
								Integer.parseInt(Zip.getText().toString()));
						b.putString("model", ItemList.modelSelected.toString());
						edit.putExtras(b);

						startActivity(edit);
					} else {
						Zip.setText("");
						Zip.setError("Enter Valid Zip!");
					}
				}

			};

		});
	}

	public void grabURL(String url) {
		Log.v("Android Spinner JSON Data Activity", url);

		try {
			
			
			String uno =  MainHomeScreen.CustomerID; 
			String url1 = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetMakeCounts/ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654"
					+ "/" + uno;
			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.getJSONFromUrl(url1);
		//	System.out.println("this is url" + url1);
			try {
				MakeListObj = json.getJSONArray("GetMakeCountsResult");
				int k = MakeListObj.length();
				//System.out.println("this is json array length" + k);
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Server Error occured,Please try again", Toast.LENGTH_LONG)
						.show();
			}
			
			
			
			
		//	MakeListObj = MainActivity.MakeListObj;
			int k = MakeListObj.length();
			//System.out.println("this is json array length" + k);
			MakeList_array = new String[MakeListObj.length()];

			MakeObjects modelCarObjects = null;

			for (int i = 0; i < MakeListObj.length(); i++) {

				modelCarObjects = new MakeObjects();
				// add to country names array

				modelCarObjects.setModelName(MakeListObj.getJSONObject(i)
						.getString("_Make"));
				modelCarObjects.setModelId(MakeListObj.getJSONObject(i)
						.getString("_MakeID"));
				modelCarObjects.setMakecount(MakeListObj.getJSONObject(i)
						.getString("_MakeCount"));

				modelList.add(modelCarObjects);
				MakeList_array[i] = modelCarObjects.getModelName();
				// MakeList_array makelist=new MakeList_array();
				spinnerMakes.setAdapter(new ArrayAdapter<String>(
						SearchView.this,android.R.layout.simple_spinner_item, MakeList_array));
				OnItemSelectedListener spinnerListener = new MyOnItemSelectedListener(
						this);
				spinnerMakes.setOnItemSelectedListener(spinnerListener);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			Toast.makeText(getApplicationContext(),
					"Server Error occured,Please try again", Toast.LENGTH_LONG)
					.show();

		}

	}

	private void displayCountries(String response) {

		JSONObject responseObj = null;
		@SuppressWarnings("unused")
		int dftIndex = 0;

		try {

			@SuppressWarnings("unused")
			Gson gson = new Gson();
			responseObj = new JSONObject(response);
			JSONArray MakeListObj = responseObj
					.getJSONArray("GetMakeCountsResult");

			// MakeList = new ArrayList<Country>();
			MakeList_array = new String[MakeListObj.length()];

			MakeObjects modelCarObjects = null;

			for (int i = 0; i < MakeListObj.length(); i++) {

				modelCarObjects = new MakeObjects();
				// add to country names array

				modelCarObjects.setModelName(MakeListObj.getJSONObject(i)
						.getString("_Make"));
				modelCarObjects.setModelId(MakeListObj.getJSONObject(i)
						.getString("_MakeID"));
				modelCarObjects.setMakecount(MakeListObj.getJSONObject(i)
						.getString("_MakeCount"));

				modelList.add(modelCarObjects);
				MakeList_array[i] = modelCarObjects.getModelName();
				// MakeList_array makelist=new MakeList_array();

			}

			spinnerMakes.setAdapter(new ArrayAdapter<String>(
					SearchView.this,
					android.R.layout.simple_spinner_item, MakeList_array));
			OnItemSelectedListener spinnerListener = new MyOnItemSelectedListener(
					this);
			spinnerMakes.setOnItemSelectedListener(spinnerListener);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		Context mContext;

		public MyOnItemSelectedListener(Context context) {
			this.mContext = context;
		}

		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long row) {
			// System.out.println("spinner 1 value"+v);

			ItemList.makeSelected = (String) spinnerMakes
					.getItemAtPosition(spinnerMakes.getSelectedItemPosition());
		//	System.out.println("this is make selected" + ItemList.makeSelected);
			ItemList.makecountSelected = (String) spinnerMakes
					.getItemAtPosition(spinnerMakes.getSelectedItemPosition());

		/*	System.out.println("this is make makesssssssss"
					+ ItemList.makeSelected);
			System.out.println("this is make count1"
					+ ItemList.makecountSelected);*/

			ModelListHandler handler = new ModelListHandler(
					getApplicationContext());

			ArrayList<ModelObjects> modelObjects = handler
					.getModelListForMake(modelList.get(pos).getModelId());
			ArrayList<ModelObjects> modelObjects1 = handler
					.getModelListForMake(modelList.get(pos).getModel());

			getModels.clear();
			for (int i = 0; i < modelObjects.size(); i++) {

				getModels.add(modelObjects.get(i).getModel());
			}
			modelObjects.clear();
			spinnerModels = (Spinner) findViewById(R.id.spin2);

			int m = getModels.size();
		//	System.out.println("this is m value" + m);
			if (m > 1) {
				getModels.add(0, "ALL MODELS");
			}

			spinnerModels.setAdapter(new ArrayAdapter<String>(
					SearchView.this,
					android.R.layout.simple_spinner_item, getModels));
			spinnerModels.invalidate();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// nothing here
		}
	}

	private class GrabURL extends AsyncTask<String, Void, String> {

		private static final int REGISTRATION_TIMEOUT = 3 * 1000;
		private static final int WAIT_TIMEOUT = 30 * 1000;
		private final HttpClient httpclient = new DefaultHttpClient();
		final HttpParams params = httpclient.getParams();
		HttpResponse response;
		// private String content = null;
		private boolean error = false;

		ProgressDialog dialog = ProgressDialog.show(SearchView.this, "",
				"Getting your data... Please wait...", true);

		protected void onPreExecute() {
			// dialog.setMessage("Getting your data... Please wait...");
			dialog.show();
		}

		protected String doInBackground(String... urls) {
			String url = null;
			try {

				url = urls[0];
				HttpConnectionParams.setConnectionTimeout(params,
						REGISTRATION_TIMEOUT);
				HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
				ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

				HttpPost httpPost = new HttpPost(url.replace(" ", "%20"));

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						1);
				nameValuePairs.add(new BasicNameValuePair("fakeInput",
						"not in USE for this demo"));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				response = httpclient.execute(httpPost);

				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					content = out.toString();
				} else {
					// Closes the connection.
					Log.w("HTTP1:", statusLine.getReasonPhrase());
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (ClientProtocolException e) {
				Log.w("HTTP2:", e);
				content = e.getMessage();
				error = true;
				cancel(true);
			} catch (IOException e) {
				Log.w("HTTP3:", e);
				content = e.getMessage();
				error = true;
				cancel(true);
			} catch (Exception e) {
				Log.w("HTTP4:", e);
				content = e.getMessage();
				error = true;
				cancel(true);
			}

			return content;

		}

		protected void onCancelled() {
			dialog.dismiss();
			Toast toast = Toast.makeText(SearchView.this,
					"Error connecting to Server", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 25, 400);
			toast.show();

		}

		protected void onPostExecute(String content) {
			dialog.dismiss();
			Toast toast;
			if (error) {
				toast = Toast.makeText(SearchView.this, content,
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 25, 400);
				toast.show();
			} else {
				displayCountries(content);
			}
		}

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
			prefernce_in.putExtra("jsonArray", MakeListObj.toString());
			startActivity(prefernce_in);
			break;
		default:
			break;

		}

	}

}
