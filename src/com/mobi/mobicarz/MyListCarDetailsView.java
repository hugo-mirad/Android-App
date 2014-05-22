package com.mobi.mobicarz;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.cropimage.ImageLoaders;

public class MyListCarDetailsView extends Activity implements OnClickListener {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	Button btn_car, btn_search, btn_preferencs, btn_mylist,
			btn_mylistcardetail_back;
	String Phone, Make1, PIC1, makes, models;
	SQLiteDatabase mydb = null;
	final String domainName = "http://www.unitedcarexchange.com/";
	ImageView imageView = null;
	Button viewGal;
	TextView Email = null;
	// TextView State=null;
	TextView Zip = null;
	TextView Phone1 = null;
	TextView Make = null;
	TextView Drive = null;
	TextView Vin = null;
	TextView fuel = null;
	TextView Millage = null;
	TextView price = null;
	TextView year = null;
	TextView model = null;
	TextView make = null;
	TextView Engione = null;
	TextView Transmission = null;
	TextView ConditionDescription = null;
	TextView Doors = null;
	TextView interiorcolor = null;
	TextView exteriorcolor = null;
	TextView Bodystyle = null;
	TextView Year = null;
	TextView Model = null;
	TextView description = null;
	TextView carres, carnote;
	Button call, preferences, back;
	Bitmap myBmp = null;
	private static int RESULT_LOAD_IMAGE = 1;
	CheckBox mylist;

	Context context = this;
	LinearLayout galeeryLayout, viewGalLayout;
	Gallery viewGallery;
	public ArrayList<String> carGallerList = new ArrayList<String>();
	ImageView customGalImg;
	ArrayList<String> thumbnails = new ArrayList<String>();
	public static int count = 0;
	byte[] bytes = null;
	byte[] bytes1 = null;

	ProgressDialog dialog;
	CarInfo carInfo = new CarInfo();
	int img = R.drawable.icon;
	String imgurl;
	SQLiteDatabase db;
	ContentValues contentValues;
	String StockUrl;
	Button image;
	Button mylistcardetails_features;
	public static int mylistfeatures_car_id;
	TextView tv_make, tv_year, tv_model;
	String aid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String uid = MainHomeScreen.CustomerID;
	private static final String TAG_GETCARFEATURES = "GetCarFeaturesResult";

	String imageURL, email, address, bodystyle, exteriorcolor1, interiorcolor1,
			doors, vehiclecondition, mileage, fuel1, engine, transmission,
			drivetrain, vin1, description1, price1;
	public static String make1, model1, year1;
	String PIC2, PIC3, PIC4, PIC5, PIC6, PIC7, PIC8, PIC9, PIC10, PIC11, PIC12,
			PIC13, PIC14, PIC15, PIC16, PIC17, PIC18, PIC19, PIC20;
	JSONArray contacts = null;
	JSONObject jsonobject;
	String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.mylistcardetailview);
		//	System.out.println("thsi is mylist car details view");

			btn_search = (Button) findViewById(R.id.search);
			btn_preferencs = (Button) findViewById(R.id.preference);
			btn_mylist = (Button) findViewById(R.id.mylist);
			btn_car = (Button) findViewById(R.id.car);
			btn_mylistcardetail_back = (Button) findViewById(R.id.btn_mylistcardetail_back);
			//btn_mylist=(Button)findViewById(R.id.mylist);
			btn_mylist.setBackgroundResource(R.drawable.my_list_act);

			btn_car.setOnClickListener(this);
			btn_mylist.setOnClickListener(this);
			btn_mylistcardetail_back.setOnClickListener(this);
			btn_preferencs.setOnClickListener(this);
			btn_search.setOnClickListener(this);

			/*image = (Button) findViewById(R.id.button2);
			image.setVisibility(View.INVISIBLE);*/

			galeeryLayout = (LinearLayout) findViewById(R.id.l_layout);
			// carres = (TextView) findViewById(R.id.carresults);
			carnote = (TextView) findViewById(R.id.textView4);
			viewGalLayout = (LinearLayout) findViewById(R.id.viewGalLayout);
			viewGallery = (Gallery) findViewById(R.id.viewGaleery);
			imageView = (ImageView) findViewById(R.id.image_id);
			Phone1 = (TextView) findViewById(R.id.phone);
			Phone1.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
			Zip = (TextView) findViewById(R.id.Zip);
			Email = (TextView) findViewById(R.id.Email);
			Make = (TextView) findViewById(R.id.makedisplay);
			Model = (TextView) findViewById(R.id.modeldisplay);
			Year = (TextView) findViewById(R.id.yeardisplay);
			Bodystyle = (TextView) findViewById(R.id.bodystyledisplay);
			exteriorcolor = (TextView) findViewById(R.id.ExteriorColordisplay);
			interiorcolor = (TextView) findViewById(R.id.InteriorColordisplay);
			Doors = (TextView) findViewById(R.id.Doorsdisplay);
			ConditionDescription = (TextView) findViewById(R.id.VehicleConditiondisplay);
			price = (TextView) findViewById(R.id.Pricedisplay);
			Millage = (TextView) findViewById(R.id.Mileageisplay);
			fuel = (TextView) findViewById(R.id.Fueldisplay);
			Engione = (TextView) findViewById(R.id.Enginedisplay);
			Transmission = (TextView) findViewById(R.id.Transmissiondisplay);
			Drive = (TextView) findViewById(R.id.DriveTraindisplay);
			Vin = (TextView) findViewById(R.id.vindisplay);
			description = (TextView) findViewById(R.id.descriptiondisplay_mylist);

			tv_make = (TextView) findViewById(R.id.tv_cardetailview_make);
			tv_model = (TextView) findViewById(R.id.tv_cardetailview_model);
			tv_year = (TextView) findViewById(R.id.tv_cardetailview_year);

			Bundle b = getIntent().getExtras();
			int value = b.getInt("CAR_ID", 0);
			mylistfeatures_car_id = b.getInt("CAR_ID", 0);
		//	System.out.println("this is my list car" + mylistfeatures_car_id);
		//	System.out.println("this is car value" + value);
			String strzip = String.valueOf(value);

			loaddata();
			// code for call button
			call = (Button) findViewById(R.id.call);
			PhoneCallListener phoneListener = new PhoneCallListener(
					context.getApplicationContext());
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getApplicationContext().getSystemService(
							Context.TELEPHONY_SERVICE);
			telephonyManager.listen(phoneListener,
					PhoneStateListener.LISTEN_CALL_STATE);
			call.setOnClickListener(this);
			// code for feature button
			mylistcardetails_features = (Button) findViewById(R.id.mylistcarddetails_features);
			mylistcardetails_features.setOnClickListener(this);
			// code for gallery
			viewGal = (Button) findViewById(R.id.viewgallery);
			viewGal.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search:
			Intent search_in = new Intent(getApplicationContext(),
					SearchView.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
		case R.id.car:
			Intent in = new Intent(getApplicationContext(), MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			break;
		case R.id.btn_mylistcardetail_back:

			finish();
			break;
		case R.id.call:
			Intent callIntent = new Intent(Intent.ACTION_CALL);

			TelephonyManager num = (TelephonyManager) MyListCarDetailsView.this
					.getSystemService(Context.TELEPHONY_SERVICE);
			String phonenumber = num.getLine1Number();
			String url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/SaveCallRequestMobile/"
					+ phonenumber
					+ "/"
					+ getIntent().getStringExtra("CAR_ID")
					+ "/" + Phone + "/";
			MyAsycall asy = new MyAsycall();
			asy.execute(url.replace(" ", "%20"));
			callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			callIntent.setData(Uri.parse("tel:" + phone + ""));
			context.startActivity(callIntent);
			break;
		case R.id.mylistcarddetails_features:
			String feature_url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetCarFeatures/"
					+ mylistfeatures_car_id + "/" + aid + "/" + uid + "/";
			ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

			// Creating JSON Parser instance
			JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			JSONObject json1 = jParser.getJSONFromUrl(feature_url);

			// Getting Array of Contacts
			JSONArray contacts1 = null;
			try {
				contacts1 = json1.getJSONArray(TAG_GETCARFEATURES);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (contacts1.length() == 0) {
				/*System.out.println("this is so array lemgth"
						+ contacts.length());*/
				Toast.makeText(getApplicationContext(),
						"No Features for this car", Toast.LENGTH_SHORT).show();
			} else {
				Intent features_in = new Intent(getApplicationContext(),
						MyListCarDetails_Features.class);
				startActivity(features_in);
			}
			break;
		case R.id.viewgallery:
			if (carGallerList.size() > 0) {
				Intent previewMessage = new Intent(getApplicationContext(),
						MyListGalleryCars.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				previewMessage.putExtra("cars", carGallerList);
				// startActivity(previewMessage);
				Log.i("arrayvalues1", "" + carGallerList);
				startActivity(previewMessage);

			} else {

				Toast.makeText(getApplicationContext(),
						"no images for this car", Toast.LENGTH_LONG).show();

			}
			break;
		default:
			break;
		}

	}

	private void loaddata() {
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setMinimumFractionDigits(0);

		NumberFormat Millageformat = NumberFormat.getNumberInstance();
		Millageformat.setMinimumIntegerDigits(1);

		String url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/FindCarID/"
				// + getIntent().getStringExtra("CAR_ID")
				+ mylistfeatures_car_id + "/" + aid + "/" + uid + "/";
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
	//	System.out.println("this is url" + url);
		try {
			// Getting Array of Contacts
			contacts = json.getJSONArray("FindCarIDResult");
			for (int i = 0; i < contacts.length(); i++) {
				jsonobject = contacts.getJSONObject(i);
				imageURL = domainName
						+ jsonobject.getString("_PICLOC1").replace(" ", "%20")
						.replace("Emp", "")
						+ jsonobject.getString("_PIC0").replace(" ", "%20")
								.replace("Emp", "");
				// .replace("Emp", "");
		//		System.out.println("this is imageurl" + imageURL);
				ImageLoaders imageLoader = new ImageLoaders(
						getApplicationContext());
				imageLoader.displayImage(imageURL, imageView);
				phone = jsonobject.getString("_phone");
	//			System.out.println("this is phone" + phone);
				email = jsonobject.getString("_email");

				address = jsonobject.getString("_city").trim().replace("Emp", "") + "," + " "
						+ jsonobject.getString("_state").trim().replace("Emp", "") + " "
						+ jsonobject.getString("_zip").replace("Emp", "");
				price1 = jsonobject.getString("_price").trim();
				make1 = jsonobject.getString("_make").trim();
				model1 = jsonobject.getString("_model").trim();
				year1 = jsonobject.getString("_yearOfMake").trim();
				bodystyle = jsonobject.getString("_bodytype");
				exteriorcolor1 = jsonobject.getString("_exteriorColor");
				interiorcolor1 = jsonobject.getString("_interiorColor");
				doors = jsonobject.getString("_numberOfDoors");
				vehiclecondition = jsonobject
						.getString("_ConditionDescription");
				mileage = jsonobject.getString("_mileage");
				fuel1 = jsonobject.getString("_Fueltype");
				transmission = jsonobject.getString("_Transmission");
				engine = jsonobject.getString("_numberOfCylinder");
				drivetrain = jsonobject.getString("_DriveTrain");
				vin1 = jsonobject.getString("_VIN");
				description1 = jsonobject.getString("_description");

				carInfo.setPIC1(domainName
						+ jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC1").replace(" ", "%20")
								.replace("Emp", ""));
				carInfo.setPIC2(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC2"));
				carInfo.setPIC3(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC3"));

				carInfo.setPIC4(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC4"));

				carInfo.setPIC5(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC5"));

				carInfo.setPIC6(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC6"));

				carInfo.setPIC7(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC7"));

				carInfo.setPIC8(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC8"));
				carInfo.setPIC9(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC9"));
				carInfo.setPIC10(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC10"));
				carInfo.setPIC11(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC11"));
				carInfo.setPIC12(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC12"));
				carInfo.setPIC13(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC13"));
				carInfo.setPIC14(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC14"));
				carInfo.setPIC15(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC15"));
				carInfo.setPIC16(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC16"));
				carInfo.setPIC17(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC17"));
				carInfo.setPIC18(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC18"));
				carInfo.setPIC19(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC19"));
				carInfo.setPIC20(domainName + jsonobject.getString("_PICLOC1")
						+ jsonobject.getString("_PIC20"));

				Phone1.setText(phone);
				Email.setText(email);
				Email.setText("Send an email to seller " + "");
				SpannableString emailunderline = new SpannableString(
						"Send an email to seller " + "");
				emailunderline.setSpan(new UnderlineSpan(), 0,
						emailunderline.length(), 0);
				Email.setText(emailunderline);
				Zip.setText(address);
				// price.setText(price1);
				price.setText(price1);
				tv_make.setText(make1);
				tv_model.setText(model1);
				Make.setText(make1);
				Model.setText(model1);
				Year.setText(year1);
				tv_year.setText(year1);
				Bodystyle.setText(bodystyle);
				exteriorcolor.setText(exteriorcolor1);
				interiorcolor.setText(interiorcolor1);
				Doors.setText(doors);
				ConditionDescription.setText(vehiclecondition);
				// Millage.setText(mileage);
				Millage.setText(Millageformat.format(Double.parseDouble(mileage
						+ ""))
						+ " Mi");
				fuel.setText(fuel1);
				Engione.setText(engine);
				Transmission.setText(transmission);
				Drive.setText(drivetrain);
				// Vin.setText(vin1);
				// description.setText(description1);

				if (vin1.equals("Emp")) {
					Vin.setText("");
				} else {
					Vin.setText(vin1);
				}
				if (description1.equals("Emp")) {
					description.setText("");
				} else {
					description.setText(description1);
				}

				for (int j = 0; j <= 20; j++) {
					if (jsonobject.getString("_PIC1").equalsIgnoreCase("Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC1());
					}
					if (jsonobject.getString("_PIC2").equalsIgnoreCase("Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC2());
					}
					if (jsonobject.getString("_PIC3").equalsIgnoreCase("Emp")) {
						break;
						//
					} else {
						carGallerList.add(carInfo.getPIC3());

					}
					if (jsonobject.getString("_PIC4").equalsIgnoreCase("Emp")) {
						break;
					} else {

						carGallerList.add(carInfo.getPIC4());

					}
					if (jsonobject.getString("_PIC5").equalsIgnoreCase("Emp")) {
						break;

					} else {
						carGallerList.add(carInfo.getPIC5());

					}
					if (jsonobject.getString("_PIC6").equalsIgnoreCase("Emp")) {
						break;
						//
					} else {
						carGallerList.add(carInfo.getPIC6());

					}
					if (jsonobject.getString("_PIC7").equalsIgnoreCase("Emp")) {
						break;
						//
					} else {
						carGallerList.add(carInfo.getPIC7());

					}
					if (jsonobject.getString("_PIC8").equalsIgnoreCase("Emp")) {

					} else {
						carGallerList.add(carInfo.getPIC8());

					}
					if (jsonobject.getString("_PIC9").equalsIgnoreCase("Emp")) {

					} else {
						carGallerList.add(carInfo.getPIC9());

					}
					if (jsonobject.getString("_PIC10").equalsIgnoreCase("Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC10());
					}
					if (jsonobject.getString("_PIC11").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC11());
					}
					if (jsonobject.getString("_PIC12").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC12());
					}
					if (jsonobject.getString("_PIC13").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC13());
					}
					if (jsonobject.getString("_PIC14").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC14());
					}
					if (jsonobject.getString("_PIC15").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC15());
					}
					if (jsonobject.getString("_PIC16").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC16());
					}
					if (jsonobject.getString("_PIC17").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC17());
					}
					if (jsonobject.getString("_PIC18").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC18());
					}
					if (jsonobject.getString("_PIC19").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC19());
					}
					if (jsonobject.getString("_PIC20").equalsIgnoreCase(
							"Emp")) {
						break;
					} else {
						carGallerList.add(carInfo.getPIC20());
					}
					break;
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();

			Toast.makeText(getApplicationContext(),
					"Server Error occured,Please try again", Toast.LENGTH_LONG)
					.show();

		}
		Email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent frequentMessages = new Intent(getApplicationContext(),
						EmailActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				frequentMessages.putExtra("Make", Make.getText().toString());
				frequentMessages.putExtra("Model", Model.getText().toString());
				frequentMessages.putExtra("Price", price.getText().toString());
				frequentMessages.putExtra("Year", Year.getText().toString());
				frequentMessages.putExtra("Cid",
						getIntent().getStringExtra("CAR_ID"));
				frequentMessages.putExtra("Phone", phone + "");

				startActivity(frequentMessages);
			}
		});

		// TODO Auto-generated method stub

	}

	class MyAsycall extends
			AsyncTask<String, ArrayList<CarInfo>, ArrayList<CarInfo>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected ArrayList<CarInfo> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return getcallData(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<CarInfo> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

		}

	}

	ArrayList<CarInfo> getcallData(String url) {
		InputStream is = null;
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url.replace(" ", "%20"));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		}
		return myHandler.carList;

	}

}
