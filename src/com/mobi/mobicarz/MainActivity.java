package com.mobi.mobicarz;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};
	 AlertDialog alertDialog1;
	String postalcode="",cur_address="", digits="";
	Button btn_car, btn_search, btn_preferencs, btn_mylist;
	static String textString, zipval1, output;
	EditText userInput;
	final Context context = this;
	String value = "0";
	private ProgressDialog progressDialog;
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://unitedcarexchange.com/carservice/CarsService.asmx";
	private final String METHOD_NAME = "CheckZips";
	SoapObject response = null;
	AndroidHttpTransport aht = null;
	SoapSerializationEnvelope sse = null;
	GridView list;
	final String domainName = "http://www.unitedcarexchange.com/";
	com.mobi.adapters.LazyAdapter adapter;
	public ArrayList<CarInfo> carMainList= new ArrayList<CarInfo>();
	int mPrevTotalItemCount = 0;
	static int pageNo = 1;
	static int MAX_PAGE = 45;
	public static final String PREFS_NAME = "LoginPrefs";
	//TextView  zipval;
	TextView Zipclilcks;
	String zipresult;
	public static JSONArray MakeListObj;
	TextView btn_home;
	double latitude,longitude;
	CurrentLocationUsingBothProviders currentLocation;
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			int desiredLayoutId;
			int ot = getResources().getConfiguration().orientation;
			switch (ot) {
			case Configuration.ORIENTATION_LANDSCAPE:
				setContentView(R.layout.land_main);
				break;
			case Configuration.ORIENTATION_PORTRAIT:
				setContentView(R.layout.activity_main);
				break;
			}
			
			/*if (android.os.Build.VERSION.SDK_INT > 9) {
			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);
			}*/
			
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
		btn_car=(Button)findViewById(R.id.car);
		btn_search = (Button) findViewById(R.id.search);
		btn_preferencs = (Button) findViewById(R.id.preference);
		btn_mylist = (Button) findViewById(R.id.mylist);
		list = (GridView) findViewById(R.id.cars_grid);
		Zipclilcks = (TextView) findViewById(R.id.zipclick);
		btn_home = (TextView) findViewById(R.id.home_gototohome);
		btn_car.setBackgroundResource(R.drawable.popular_act);

		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent search_in = new Intent(getApplicationContext(),
						SearchView.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(search_in);
			}
		});
		btn_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),
						MainHomeScreen.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
		});
		btn_preferencs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent prefernce_in = new Intent(getApplicationContext(),
						PreferenceView.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(prefernce_in);
			}
		});
		btn_mylist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mylist_in = new Intent(getApplicationContext(),
						MyListView.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(mylist_in);
			}
		});
		//zipval = (TextView) findViewById(R.id.textView3);
		//searchLoadData();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		/*searchLoadData();*/
		if (settings.getString("logged", "").toString().equals("logged")) {

			textString = settings.getString("name", "");
			if (textString.equals("")) {
				Zipclilcks.setText(/*"Zip  " +*/ "N/A");
			} else if (textString.length() == 5) {
				Zipclilcks.setText(/*"Zip  " +*/ textString);
			}
			currentLocation = new CurrentLocationUsingBothProviders();
			//System.out.println("this is zip code hme page" + textString);
			String page = "1";
			String url = domainName
					+ "carservice/CarsService.asmx/GetRecentCarsMobile?sCurrentPage="
					+ page + "&PageSize=45&Orderby=Price&Sort=Asc&sPin="
					+ textString + "";
		//	System.out.println("see" + url);

			if (carMainList.size() == 0) {
			//	 searchLoadData();
				MyAsy asy1 = new MyAsy();
				asy1.execute(url.replace(" ", "%20"));
				
				//int i = carMainList.size();
				//System.out.println("thsi is no of cars" + i);
				adapter = new com.mobi.adapters.LazyAdapter(MainActivity.this,
						carMainList);
				list.setAdapter(adapter);
			} else {
				// searchLoadData();
				/*if(carMainList.size()==0){
					Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
				}else{*/
				adapter = new com.mobi.adapters.LazyAdapter(MainActivity.this,
						carMainList);
				list.setAdapter(adapter);
				//}
			}
			

		}
		
		else {
			currentLocation = new CurrentLocationUsingBothProviders();

			 final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(MainActivity.this);
			
			          
			
			         alertDialogBuilder1.setTitle(this.getTitle()+ "Car Finder Would Like to Use Your Current Location");
			
			        
			
			         alertDialogBuilder1.setPositiveButton("Don't Allow",new DialogInterface.OnClickListener() {
			
			                public void onClick(DialogInterface dialog,int id) {
			
			                  
			                	textString = "0";
								Zipclilcks.setText(/*"Zip  " +*/ "N/A");
								SharedPreferences settings = getSharedPreferences(
										PREFS_NAME, 0);
								SharedPreferences.Editor editor = settings
										.edit();
								editor.putString("logged", "logged");
								editor.putString("name", textString);
								editor.commit();

								gettingData();
			
			                }
			
			              });
			
			         // set negative button: No message
			
			         alertDialogBuilder1.setNegativeButton("OK",new DialogInterface.OnClickListener() {
			
			                public void onClick(DialogInterface dialog,int id) {
			
			                  
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					MainActivity.this);

			// Setting Dialog Title
			alertDialog.setTitle("Enter Zip");

			// Setting Dialog Message
			alertDialog
					//.setMessage("Please enter your zip value to get cars in your area");
			.setMessage("Enter Zip code to search for cars in your local area or press SKIP to search all");
			final EditText input = new EditText(MainActivity.this);
			/*if(textString.equals(0)){
				input.setText("");	
				}else{
					input.setText(textString);
				}*/
			InputFilter[] filters = new InputFilter[1];
			filters[0] = new InputFilter.LengthFilter(5); //Filter to 10 characters
			input .setFilters(filters);
			input.setInputType(InputType.TYPE_CLASS_NUMBER);
	//		System.out.println("this is postal code"+postalcode);
			if(postalcode==null){
				//input.setText(text)
				/*String postalcode2= cur_address.replaceAll("[^0-9.]", "");
				System.out.println("this is postal code in digits"+postalcode2);*/
				input.setText(digits);
			}else{
			input.setText(postalcode);
			}
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			input.setLayoutParams(lp);
			alertDialog.setView(input);

			// Setting Icon to Dialog
//			alertDialog.setIcon(R.drawable.icon);

			// Setting Positive "Yes" Button
			alertDialog.setPositiveButton("SKIP",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							textString = input.getText().toString();
							/*System.out.println("this is edit text value"
									+ textString);*/

					//	if (textString.equals("")) {
								textString = "0";
								Zipclilcks.setText(/*"Zip  " +*/ "N/A");
								SharedPreferences settings = getSharedPreferences(
										PREFS_NAME, 0);
								SharedPreferences.Editor editor = settings
										.edit();
								editor.putString("logged", "logged");
								editor.putString("name", textString);
								editor.commit();

								gettingData();
						//	} 
						/*else if (textString.length() < 5) {
								checkingZipLength();
							} else {
								checkingZip();

								if (zipresult.equals("true")) {
									Zipclilcks.setText("Zip:" + textString);
									SharedPreferences settings = getSharedPreferences(
											PREFS_NAME, 0);
									SharedPreferences.Editor editor = settings
											.edit();
									editor.putString("logged", "logged");
									editor.putString("name", textString);
									editor.commit();
									gettingData();
								} else {
									Toast.makeText(getApplicationContext(),
											"Please enter valid code",
											Toast.LENGTH_LONG).show();

								}

							}*/
						}
					});
			// Setting Negative "NO" Button
			alertDialog.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							//dialog.cancel();
							textString = input.getText().toString();
							/*System.out.println("this is edit text value"
									+ textString);*/

							/*if (textString.equals("")) {
								textString = "0";
								Zipclilcks.setText("Zip:" + "N/A");
								SharedPreferences settings = getSharedPreferences(
										PREFS_NAME, 0);
								SharedPreferences.Editor editor = settings
										.edit();
								editor.putString("logged", "logged");
								editor.putString("name", textString);
								editor.commit();

								gettingData();
							}*/ if (textString.length() < 5) {
								checkingZipLength();
							} else {
								checkingZip();

								if (zipresult.equals("true")) {
									Zipclilcks.setText(/*"Zip  " +*/ textString);
									SharedPreferences settings = getSharedPreferences(
											PREFS_NAME, 0);
									SharedPreferences.Editor editor = settings
											.edit();
									editor.putString("logged", "logged");
									editor.putString("name", textString);
									editor.commit();
									gettingData();
								} else {
									Toast.makeText(getApplicationContext(),
											"Please enter valid code",
											Toast.LENGTH_LONG).show();
									checkingZipLength();
								}

							}
						}
					});

			
			alertDialog.show();
		}
			         
			                
			            
			                        	 		
        });
			         alertDialog1 = alertDialogBuilder1.create();
		             
			         alertDialog1.show();
	}

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent in = new Intent(MainActivity.this, CarDetailView.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				in.putExtra("CAR_ID",
						((CarInfo) arg0.getItemAtPosition(arg2)).getCarID());
			//	System.out.println("this is on item click");
				startActivity(in);
			}
		});
		Zipclilcks.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/* carMainList.clear(); */
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompts, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						MainActivity.this);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);
				if(textString.equals(0)){
					userInput.setText("");	
					}else{
						userInput.setText(textString);
					}
				InputFilter[] filters = new InputFilter[1];
				filters[0] = new InputFilter.LengthFilter(5); //Filter to 10 characters
				userInput .setFilters(filters);

				alertDialogBuilder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Write your code here to execute after dialog
								textString = userInput.getText().toString();
								// Zipclilcks.setText(textString);
								/*System.out.println("this is edit text value"
										+ textString);*/

								if (textString.equals("")) {
									textString = "0";
									Zipclilcks.setText(/*"Zip  " +*/ "N/A");
									SharedPreferences settings = getSharedPreferences(
											PREFS_NAME, 0);
									SharedPreferences.Editor editor = settings
											.edit();
									editor.putString("logged", "logged");
									editor.putString("name", textString);
									editor.commit();
									gettingData();
								} else if (textString.length() < 5) {
									checkingZipLength();
								} else {
									checkingZip();
									if (zipresult.equals("true")) {
										SharedPreferences settings = getSharedPreferences(
												PREFS_NAME, 0);
										SharedPreferences.Editor editor = settings
												.edit();
										editor.putString("logged", "logged");
										editor.putString("name", textString);
										editor.commit();
										Zipclilcks.setText(/*"Zip  " +*/ textString);
										gettingData();
									} else {
										/*Toast.makeText(getApplicationContext(),
												"Please enter valid code",
												Toast.LENGTH_LONG).show();*/
										checkingZipLength();
									}

								}

							}
						});
				// Setting Negative "NO" Button
				alertDialogBuilder.setNegativeButton("Close",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								
								dialog.cancel();
							}
						});

				
				alertDialogBuilder.show();

			}
		});
	

	}

	private void searchLoadData() {
		// TODO Auto-generated method stub
		String uno =  MainHomeScreen.CustomerID; 
		String url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetMakeCounts/ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654"
				+ "/" + uno;
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(url);
	//	System.out.println("this is url" + url);
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
	}

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		int ot = getResources().getConfiguration().orientation;
		switch (ot) {
		case Configuration.ORIENTATION_LANDSCAPE:
			setContentView(R.layout.land_main);
			break;
		case Configuration.ORIENTATION_PORTRAIT:
			setContentView(R.layout.activity_main);
			break;
		}
		/*if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}*/
		InitializeUI();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		return super.onRetainNonConfigurationInstance();
	}

	protected void checkingZipLength() {
		// TODO Auto-generated method stub
		final FrameLayout fl = new FrameLayout(MainActivity.this);
		final EditText input = new EditText(MainActivity.this);
		if(textString.equals(0)){
			input.setText("");	
			}else{
				input.setText(textString);
			}
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(5) });
		input.setGravity(Gravity.CENTER);
		fl.addView(input, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT));

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				MainActivity.this);
		builder.setView(fl)
				.setTitle("Invalid Zip")
				.setMessage(
						"Enter Valid Zip Code")
				//.setIcon(R.drawable.icon)
						.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface d, int which) {
						textString = input.getText().toString();
						if (textString.equals("")) {
							textString = "0";
							Zipclilcks.setText(/*"Zip  " +*/ "N/A");
							SharedPreferences settings = getSharedPreferences(
									PREFS_NAME, 0);
							SharedPreferences.Editor editor = settings.edit();
							editor.putString("logged", "logged");
							editor.putString("name", textString);
							editor.commit();

							gettingData();
						} else if (textString.length() < 5) {
							checkingZipLength();
						} else {
							checkingZip();

							if (zipresult.equals("true")) {
								Zipclilcks.setText(/*"Zip  " +*/ textString);
								SharedPreferences settings = getSharedPreferences(
										PREFS_NAME, 0);
								SharedPreferences.Editor editor = settings
										.edit();
								editor.putString("logged", "logged");
								editor.putString("name", textString);
								editor.commit();
								gettingData();
							} else {
								/*Toast.makeText(getApplicationContext(),
										"Please enter valid code",
										Toast.LENGTH_LONG).show();*/
								checkingZipLength();
							}
						}
					}
				});
		

		
		builder.show();

	}

	protected void gettingData() {
		// TODO Auto-generated method stub
		String page = "1";
		String url = domainName
				+ "carservice/CarsService.asmx/GetRecentCarsMobile?sCurrentPage="
				+ page + "&PageSize=45&Orderby=Price&Sort=Asc&sPin="
				+ textString + "";

		//System.out.println("see" + url);
		MyAsy asy1 = new MyAsy();
		asy1.execute(url.replace(" ", "%20"));
		carMainList.clear();
		/*if(carMainList.size()==0){
			Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
		}else{*/
		adapter = new com.mobi.adapters.LazyAdapter(MainActivity.this,
				carMainList);
		list.setAdapter(adapter);
		//}
		list.setOnScrollListener(new OnScrollListener() {
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
					// mAdapter.addMoreData();

					if (pageNo < MAX_PAGE) {
						String page = "" + (++pageNo);
						String url = domainName
								+ "carservice/CarsService.asmx/GetRecentCarsMobile?sCurrentPage="
								+ page
								+ "&PageSize=18&Orderby=Price&Sort=Asc&sPin="
								+ textString + "";
						MyAsy asy = new MyAsy();
						asy.execute(url.replace(" ", "%20"));
					}
				}

			}

		});
	}

	protected void checkingZip() {
		// TODO Auto-generated method stub
		String customerID=MainHomeScreen.CustomerID;
		
		final String SOAP_ACTION = "http://tempuri.org/CheckZips";

		final String METHOD_NAME = "CheckZips";
		final String NAMESPACE = "http://tempuri.org/";

		final String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=CheckZips";
		final String TAG = "HELLO";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("zipId", textString);
		request.addProperty("AuthenticationID",
				"ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654");
		request.addProperty("CustomerID", customerID);
		SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapEnvelope.dotNet = true;
		soapEnvelope.setOutputSoapObject(request);
		HttpTransportSE aht = new HttpTransportSE(URL);
		try {
			Log.v(TAG, "this is login test 24");
			aht.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			aht.debug = true;
			aht.call(SOAP_ACTION, soapEnvelope);
			SoapPrimitive response = (SoapPrimitive) soapEnvelope.getResponse();

			zipresult = response.toString();
			//System.out.println("this is boolean value" + zipresult);
		} catch (Exception e) {
			e.printStackTrace();

			Toast.makeText(getApplicationContext(),
					"Server Error occured,Please try again", Toast.LENGTH_LONG)
					.show();

		}
	}


	class MyAsy extends
			AsyncTask<String, ArrayList<CarInfo>, ArrayList<CarInfo>> {
		// private ProgressDialog dialog = new
		// ProgressDialog(HomeActivity.this);
		ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
				"Getting your data... Please wait...", true);

		// return true;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog.show();
		}

		@Override
		protected ArrayList<CarInfo> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return getPageData(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<CarInfo> result) {
			// TODO Auto-generated method stub

			super.onPostExecute(result);
			// dialog.dismiss();
			try {
				dialog.dismiss();
				dialog = null;
			} catch (Exception e) {
				// nothing
			}

			// finish();

			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					carMainList.add(result.get(i));
				}
				list.invalidateViews();
			} else {
				// textview1.setVisibility(View.VISIBLE);
			}

		}

		ArrayList<CarInfo> getPageData(String url) {
			InputStream is = null;
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpPost = new HttpGet(url.replace(" ", "%20"));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				// System.out.println("TEST3::"+is.available());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			MyHandler myHandler = null;
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser;
			try {
				parser = factory.newSAXParser();
				myHandler = new MyHandler();
				parser.parse(is, myHandler);
				is.close();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NullPointerException e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
			}
			return myHandler.carList;
			//return null;
		}
	}
	public void updateUIlocation(Location location){
//		System.out.println("Location is:"+location.getLatitude()+":"+location.getLongitude());
//		Toast.makeText(getApplicationContext(), location.getLatitude()+":"+location.getLongitude(), Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "Update ui is called", Toast.LENGTH_LONG).show();
		getAddress(location);
	//	System.out.println("location is:"+getAddress(location));
		
	}
	public String getAddress(Location location) {
		String addressText = null;
		if (location != null) {
			List<Address> addresses = null;

			Geocoder geocoder = new Geocoder(getApplicationContext(),
					Locale.getDefault());
			try {
//				locationLatitude = CurrentLocationUsingBothProviders.finalLocation.getLatitude();
//				locationLongitude = CurrentLocationUsingBothProviders.finalLocation.getLongitude();
				//addresses = geocoder.getFromLocation(39.941, -75.126 , 1);
				double lat=location.getLatitude();
				double lon=location.getLongitude();
				//System.out.println("this is cur loc lon and lat"+lat+lon);
			addresses=	geocoder.getFromLocation(location.getLatitude(),
						location.getLongitude(), 1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (addresses != null && addresses.size() > 0) {
			//	System.out.println("this is current loc adress"+addresses);
				Address address = addresses.get(0);
				addressText = String.format(
						"%s, %s, %s, %s",
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(2) : "", address.getLocality(),
						address.getCountryName(), address.getPostalCode());
			//	System.out.println("this is address"+addressText);
				 postalcode=address.getPostalCode();
			//	System.out.println("this is area code"+postalcode);
				 cur_address=address.getAddressLine(2);
			//	System.out.println("this is postal code"+cur_address);
				
				String str="null";
				if(postalcode==null){
			//	System.out.println("this is checking condition");
				digits= cur_address.replaceAll("[^0-9.]", "");
			//	System.out.println("this is postal code in digits"+digits);
				}
				
				
			}
		}
		return addressText;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		currentLocation.setup(getApplicationContext(),(Object)this);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//currentLocation.mLocationManager.removeUpdates(currentLocation.listener);
	}
	 public void enableLocationSettings() {
			Intent settingsIntent = new Intent(
					Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(settingsIntent);
		}
}
