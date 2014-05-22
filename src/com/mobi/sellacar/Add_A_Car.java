package com.mobi.sellacar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import android.widget.Toast;

import com.mobi.mobicarz.ItemList;
import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.MakeObjects;
import com.mobi.mobicarz.ModelListHandler;
import com.mobi.mobicarz.ModelObjects;
import com.mobi.mobicarz.R;

public class Add_A_Car extends Activity {

	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	String add_make, add_model, add_year, add_price, add_mileage;

	EditText et_make, et_model, et_year, et_price, et_mileage;
	Button btn_add_submit, btn_addacar_back;

	String seller_carid, url, session_id, url1, seller_uid;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	JSONArray contacts = null, contacts1 = null, logout_contacts = null;
	String packageid, success, packagename, userpackid;
	Spinner sp_addacar_make, sp_addcar_year;
	String sp_makevalue;
	ProgressDialog pDialog;
	Spinner spinnerModels;
	ArrayList<String> getModels = new ArrayList<String>();
	ArrayList<MakeObjects> modelList = new ArrayList<MakeObjects>();
	String[] items = { "2013", "2012", "2011", "2010", "2009", "2008", "2007",
			"2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999",
			"1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991",
			"1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983",
			"1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975",
			"1974", "1973", "1972", "1971", "1970", "1969", "1968", "1967",
			"1966", "1965", "1964", "1963", "1962", "1961", "1960", "1959",
			"1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951",
			"1950", "1949", "1948", "1947", "1946", "1945", "1944", "1943",
			"1942", "1941", "1940", "1939", "1938", "1937", "1936", "1935",
			"1934", "1933", "1932", "1931", "1930", "1929", "1928", "1927",
			"1926", "1925", "1924", "1923", "1922", "1922", "1921", "1919",
			"1918", "1917", "1916", "1915", "1914", "1913", "1912", "1911",
			"1910" };

	public static final int TABLET_MIN_DP_WEIGHT = 450;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.addacar);
			if (isSmartphone(Add_A_Car.this)) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				Toast.makeText(this, "Smartphone", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Tablet", Toast.LENGTH_SHORT).show();
			}
			// et_make = (EditText) findViewById(R.id.et_add_make);
			// et_model = (EditText) findViewById(R.id.et_add_model);
			et_price = (EditText) findViewById(R.id.et_price);
			// et_year = (EditText) findViewById(R.id.et_year);
			sp_addcar_year = (Spinner) findViewById(R.id.sp_year);
			btn_add_submit = (Button) findViewById(R.id.btn_add_submit);
			sp_addacar_make = (Spinner) findViewById(R.id.sp_addacar_make);
			btn_addacar_back = (Button) findViewById(R.id.btn_addacar_back);
			et_mileage = (EditText) findViewById(R.id.et_mileage);
			seller_carid = Seller_Login.UID;
			session_id = Seller_Login.SessionID;
			seller_uid = Seller_Login.UID;

			Intent in = getIntent();
			packagename = in.getStringExtra("Packageid");
			userpackid = in.getStringExtra("userpackid");
		//	System.out.println("this is packname from class" + packagename);
			//System.out.println("this is userpackid from class" + userpackid);
			url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetMakes/"
					+ Uid + "/" + Uno;
			//System.out.println("this is make url" + url);
			grabURL(url.replace(" ", "%20"));
			ArrayAdapter aa = new ArrayAdapter(this,
					android.R.layout.simple_spinner_item, items);

			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp_addcar_year.setAdapter(aa);

			sp_addcar_year
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							add_year = sp_addcar_year.getSelectedItem()
									.toString();
							//System.out.println("this is add year" + add_year);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

			btn_addacar_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			btn_add_submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					add_price = et_price.getText().toString().trim()
							.replaceAll(" ", "%20");
					add_mileage = et_mileage.getText().toString().trim()
							.replace(" ", "%20");

					addacar_senddata();
				}
			});

		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						Add_A_Car.this).create();
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

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		Context mContext;

		public MyOnItemSelectedListener(Context context) {
			this.mContext = context;
		}

		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long row) {
			// System.out.println("spinner 1 value"+v);

			ItemList.makeSelected = (String) sp_addacar_make
					.getItemAtPosition(sp_addacar_make
							.getSelectedItemPosition());

		//	System.out.println("spinner 34 value" + ItemList.makeSelected);

			ModelListHandler handler = new ModelListHandler(
					getApplicationContext());

			ArrayList<ModelObjects> modelObjects = handler
					.getModelListForMake(modelList.get(pos).getModelId());
			getModels.clear();
			for (int i = 0; i < modelObjects.size(); i++) {

				getModels.add(modelObjects.get(i).getModel());
			}
			modelObjects.clear();
			spinnerModels = (Spinner) findViewById(R.id.spin2);
			ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_spinner_item, getModels);
			adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerModels.setAdapter(adapter1);

			spinnerModels.invalidate();
			ItemList.modelSelected = (String) spinnerModels
					.getItemAtPosition(spinnerModels.getSelectedItemPosition());
		//	System.out.println("spinner 35 value" + ItemList.modelSelected);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// nothing here
		}
	}

	protected void addacar_senddata() {
		// TODO Auto-generated method stub

		String SOAP_ACTION = "http://tempuri.org/AddCarDetails";
		String METHOD_NAME = "AddCarDetails";
		String NAMESPACE = "http://tempuri.org/";
		String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=AddCarDetails";
		String TAG = "HELLO";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	//	System.out.println("this is package name is" + packagename);

		// request.addProperty("CarID", SellCarDetailView.sellcardetails_carid);
		request.addProperty("make", ItemList.makeSelected);
		request.addProperty("model", ItemList.modelSelected);
		request.addProperty("price", add_price);

		request.addProperty("year", add_year);
		request.addProperty("mileage", add_mileage);
		request.addProperty("UID", seller_uid);
		request.addProperty("userPackID", userpackid);
		request.addProperty("packageID", packagename);
		request.addProperty("AuthenticationID", Uid);
		request.addProperty("CustomerID", Uno);
		request.addProperty("SessionID", session_id);
		try {
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;

			envelope.setOutputSoapObject(request);

			AndroidHttpTransport aht = new AndroidHttpTransport(URL);

			aht.call(SOAP_ACTION, envelope);

			SoapObject response = (SoapObject) envelope.getResponse();
			// SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			String temp1 = response.toString();
			// bank.setText(temp1);
			Log.v("TAG", temp1);
			for (int i = 0; i < ((SoapObject) response).getPropertyCount(); i++) {
				Object root = ((SoapObject) response).getProperty(i);
				if (root instanceof SoapObject) {
					success = ((SoapObject) root).getProperty("AASuccess")
							.toString();
				//	System.out.println("tanstran" + success);
				}
			}
			if (success.equals("Success")) {
				Toast.makeText(getApplicationContext(),
						"New car added succesfully, Please enter more details",
						Toast.LENGTH_SHORT).show();

				Intent in = new Intent(getApplicationContext(), Sell_Home.class);

				startActivity(in);
				finish();

			} else if (temp1.equals("Session timed out")) {
				Intent in = new Intent(getApplicationContext(),
						Seller_Login.class);
				startActivity(in);
			} else if (temp1.equals("Failed")) {
				Toast.makeText(getApplicationContext(),
						"Error occured,Please try again", Toast.LENGTH_LONG)
						.show();
			}

		} catch (Exception e) {
			e.printStackTrace();

			Toast.makeText(getApplicationContext(),
					"Server Error occured,Please try again", Toast.LENGTH_LONG)
					.show();

		}

	}

	public void grabURL(String url) {
		Log.v("Android Spinner JSON Data Activity", url);
		new GrabURL().execute(url.replace(" ", "%20"));
	}

	private class GrabURL extends AsyncTask<String, Void, String> {
		private static final int REGISTRATION_TIMEOUT = 3 * 1000;
		private static final int WAIT_TIMEOUT = 30 * 1000;
		private final HttpClient httpclient = new DefaultHttpClient();
		final HttpParams params = httpclient.getParams();
		HttpResponse response;
		private String content = null;
		private boolean error = false;
		// private ProgressDialog dialog = new
		// ProgressDialog(SearchActivity.this);
		ProgressDialog dialog = ProgressDialog.show(Add_A_Car.this, "",
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
			Toast toast = Toast.makeText(Add_A_Car.this,
					"Error connecting to Server", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 25, 400);
			toast.show();

		}

		protected void onPostExecute(String content) {
			dialog.dismiss();
			Toast toast;
			if (error) {
				toast = Toast.makeText(Add_A_Car.this, content,
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 25, 400);
				toast.show();
			} else {
				displayCountries(content);
			}
		}

	}

	public void displayCountries(String content) {
		// TODO Auto-generated method stub
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		try {
			// Getting Array of Contacts
			JSONArray MakeListObj = json.getJSONArray("GetMakesResult");

			final String[] MakeList_array = new String[MakeListObj.length()];

			// looping through All Contacts
			MakeObjects modelCarObjects = null;

			for (int i = 0; i < MakeListObj.length(); i++) {

				JSONObject c = MakeListObj.getJSONObject(i);
				// Storing each json item in variable

				String name = c.getString("_make");

				MakeList_array[i] = c.getString("_make");
				//System.out.println("Hello events " + MakeList_array);

				modelCarObjects = new MakeObjects();
				modelCarObjects.setModelName(MakeListObj.getJSONObject(i)
						.getString("_make"));
				modelCarObjects.setModelId(MakeListObj.getJSONObject(i)
						.getString("_makeID"));
				modelList.add(modelCarObjects);
				MakeList_array[i] = modelCarObjects.getModelName();
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, MakeList_array);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp_addacar_make.setAdapter(adapter);
			OnItemSelectedListener spinnerListener = new MyOnItemSelectedListener(
					this);
			sp_addacar_make.setOnItemSelectedListener(spinnerListener);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
