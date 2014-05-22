package com.mobi.sellacar;

import java.text.NumberFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.cropimage.ImageLoaders;
import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class SellCarDetailView extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	String url, reg_CarIDs;
	static String sellercardetail_carid;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	ImageView sellcardetail_img;
	public static String sellpicloc, sellpic, sellpicurl, imageurl,
			sellcardetails_year, sellcardetails_model, sellcardetails_make,
			sellcardetails_email, sellcardetails_price,
			sellcardetails_sellertype, sellcardetails_phonenumber,
			sellcardetails_address, sellcardetails_makedetail,
			sellcardetails_modeldetail, sellcardetails_yeardetail,
			sellcardetails_exteriorcolor, sellcardetails_interiorcolor,
			sellcardetails_numberofdoors, sellcardetails_vechiclecondition,
			sellcardetails_milleage, sellcardetails_milleagedisplay,
			sellcardetails_fuel, sellcardetails_transimssion,
			sellcardetails_drivetrain, sellcardetails_vin,
			sellcardetails_description, sellcardetails_bodytypeid,
			sellcardetails_uid, sellcardetails_makemodelid,
			sellcardetails_carid, sellcardetails_numberofCylinder,
			sellcardetails_FueltypeID, sellcarderails_zip, sellcarderails_city,
			sellcardetails_title, sellcardetails_StateID,
			sellcardetails_sellerID, sellcardetails_sellerName,
			sellcardetails_bodyid, sellcardetails_carstatus,
			sellcardetails_carstateid;
	JSONArray contacts = null;
	ImageLoader imageLoader;
	Context context;
	TextView tv_sellcardetails_year, tv_sellcardetails_model,
			tv_sellcardetails_make, tv_sellcardetails_email,
			tv_sellcardetails_price, tv_sellcardetails_sellertype,
			tv_sellcardetails_phonenumber, tv_sellcardetails_address,
			tv_sellcardetails_makedetail, tv_sellcardetails_modeldetail,
			tv_sellcardetails_yeardetail, tv_sellcardetails_exteriorcolor,
			tv_sellcardetails_interiorcolor, tv_sellcardetails_numberofdoors,
			tv_sellcardetails_vechiclecondition, tv_sellcardetails_milleage,
			tv_sellcardetails_fuel, tv_sellcardetails_transimssion,
			tv_sellcardetails_drivetrain, tv_sellcardetails_vin,
			tv_sellcardetails_description;
	Button btn_sellcardetails_back, btn_sellcardetails_edit,
			btn_sellcardetails_gallery, btn_sellcardetails_features;

	public static final String TAG_picloc = "_PICLOC0";
	public static final String TAG_pic = "_PIC0";
	public static final String TAG_MAKE = "_make";
	public static final String TAG_YEAR = "_yearOfMake";
	public static final String TAG_model = "_model";
	public static final String TAG_EMAIL = "_email";
	public static final String TAG_price = "_price";
	public static final String TAG_sellertype = "_sellerType";
	public static final String TAG_phoneno = "_phone";
	public static final String TAG_address = "_address1";
	public static final String TAG_exteriorColor = "_exteriorColor";
	public static final String TAG_interiorColor = "_interiorColor";
	public static final String TAG_numberOfDoors = "_numberOfDoors";
	public static final String TAG_ConditionDescription = "_ConditionDescription";
	public static final String TAG_Fueltype = "_Fueltype";
	public static final String TAG_Transmission = "_Transmission";
	public static final String TAG_DriveTrain = "_DriveTrain";
	public static final String TAG_VIN = "_VIN";
	public static final String TAG_description = "_description";
	public static final String TAG__mileage = "_mileage";
	public static final String TAG_bodystyleid = "_bodytype";
	public static final String TAG_UID = "uid";
	public static final String TAG_MAKEMODELID = "_makeModelID";
	public static final String TAG_CARID = "_carid";
	public static final String TAG_Cylinder = "_numberOfCylinder";
	public static final String TAG_fueltypeid = "_FueltypeId";
	public static final String TAG_zip = "_zip";
	public static final String TAG_city = "_city";
	public static final String TAG_title = "_Title";
	public static final String TAG_stateid = "_state";
	public static final String TAG_sellerid = "_sellerID";
	public static final String TAG_sellerName = "_sellerName";
	public static final String TAG_bodyid = "_bodytypeID";
	public static final String TAG_carstatus = "_AdStatus";
	public static final String TAG_StateID = "_stateID";
	public static final int TABLET_MIN_DP_WEIGHT = 450;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 
		if (isOnline()) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy);
			setContentView(R.layout.sellcardetailview);

			this.context = context;
			sellcardetail_img = (ImageView) findViewById(R.id.cardetailimage_id);
			tv_sellcardetails_year = (TextView) findViewById(R.id.tv_sellcardetailview_year);
			tv_sellcardetails_model = (TextView) findViewById(R.id.tv_sellcardetailview_model);
			tv_sellcardetails_make = (TextView) findViewById(R.id.tv_sellcardetailview_make);
			tv_sellcardetails_email = (TextView) findViewById(R.id.tv_sellcardetails_email);
			tv_sellcardetails_price = (TextView) findViewById(R.id.tv_sellcardetails_price);
			tv_sellcardetails_sellertype = (TextView) findViewById(R.id.tv_sellcardetails_sellertype);
			tv_sellcardetails_phonenumber = (TextView) findViewById(R.id.tv_sellcardetails_phone);
			tv_sellcardetails_address = (TextView) findViewById(R.id.tv_sellcardetails_address);
			tv_sellcardetails_exteriorcolor = (TextView) findViewById(R.id.tv_sellcardetails_exteriorcolor);
			tv_sellcardetails_interiorcolor = (TextView) findViewById(R.id.tv_sellcardetails_interiorcolor);
			tv_sellcardetails_numberofdoors = (TextView) findViewById(R.id.tv_sellcardetails_numberofdoors);
			tv_sellcardetails_vechiclecondition = (TextView) findViewById(R.id.tv_sellcardetails_vechiclecondition);
			tv_sellcardetails_fuel = (TextView) findViewById(R.id.tv_sellcardetails_fuel);
			tv_sellcardetails_transimssion = (TextView) findViewById(R.id.tv_sellcardetails_transmission);
			tv_sellcardetails_drivetrain = (TextView) findViewById(R.id.tv_sellcardetails_drivetrain);
			tv_sellcardetails_vin = (TextView) findViewById(R.id.tv_sellcardetails_vin);
			tv_sellcardetails_description = (TextView) findViewById(R.id.tv_sellcardetails_description);
			tv_sellcardetails_milleage = (TextView) findViewById(R.id.tv_sellcardetails_mileagedetail);
			tv_sellcardetails_makedetail = (TextView) findViewById(R.id.tv_sellcardetails_makedetail);
			tv_sellcardetails_modeldetail = (TextView) findViewById(R.id.tv_sellcardetails_modeldetail);
			tv_sellcardetails_yeardetail = (TextView) findViewById(R.id.tv_sellcardetails_yeardetail);
			btn_sellcardetails_back = (Button) findViewById(R.id.btn_sellcardetailview_back);
			btn_sellcardetails_features = (Button) findViewById(R.id.sellcardetails_features);
			btn_sellcardetails_gallery = (Button) findViewById(R.id.sellcardetails_viewgallery);
			btn_sellcardetails_edit = (Button) findViewById(R.id.btn_sellcardetailsview_edit);

			btn_sellcardetails_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent in = new Intent(getApplicationContext(),
							Sell_Home.class);
					startActivity(in);
					// finish();
				}
			});
			btn_sellcardetails_features
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent features_in = new Intent(
									getApplicationContext(),
									SellCarDetails_Features.class);
							Bundle b = new Bundle();
							b.putString("car_id", sellercardetail_carid);
							features_in.putExtras(b);
							startActivity(features_in);
						}
					});
			btn_sellcardetails_gallery
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent gallery_in = new Intent(
									getApplicationContext(),
									SellCarDetails_Gallery.class);
							Bundle b = new Bundle();
							b.putString("car_id", sellercardetail_carid);
							gallery_in.putExtras(b);
							startActivity(gallery_in);
						}
					});
			btn_sellcardetails_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent edit_in = new Intent(getApplicationContext(),
							SellCarDetails_Edit.class);
					Bundle b_edit = new Bundle();
					b_edit.putString("make", sellcardetails_make);
					b_edit.putString("model", sellcardetails_model);
					b_edit.putString("year", sellcardetails_year);
					b_edit.putString("carstatus", sellcardetails_carstatus);
					b_edit.putString("product", sellercardetail_carid);
					edit_in.putExtras(b_edit);
					startActivity(edit_in);
				}
			});

			loaddata();
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						SellCarDetailView.this).create();
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

	private void loaddata() {
		// TODO Auto-generated method stub
		Intent in = getIntent();
		// getting attached intent data
		Bundle b = this.getIntent().getExtras();
		sellercardetail_carid = b.getString("product");
		//System.out.println("thsi is product" + sellercardetail_carid);
		NumberFormat Millageformat = NumberFormat.getNumberInstance();
		Millageformat.setMinimumIntegerDigits(1);
	

		url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/FindCarID/"
				+ sellercardetail_carid + "/" + Uid + "/" + Uno + "/";
		//System.out.println("this is detail view url" + url);

		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(url);
		// System.out.println("this is json obj" + json);

		try {
			contacts = json.getJSONArray("FindCarIDResult");
			for (int i = 0; i < contacts.length(); i++) {
				JSONObject c = contacts.getJSONObject(i);
				sellpic = c.getString(TAG_pic);
				sellpicloc = c.getString(TAG_picloc);
				sellcardetails_make = c.getString(TAG_MAKE);
				sellcardetails_model = c.getString(TAG_model);
				sellcardetails_year = c.getString(TAG_YEAR);
				sellcardetails_description = c.getString(TAG_description);
				sellcardetails_email = c.getString(TAG_EMAIL);
				sellcardetails_price = c.getString(TAG_price);
				sellcardetails_sellertype = c.getString(TAG_sellertype);
				sellcardetails_phonenumber = c.getString(TAG_phoneno);
				sellcardetails_address = c.getString(TAG_address);
				sellcardetails_exteriorcolor = c.getString(TAG_exteriorColor);
				sellcardetails_interiorcolor = c.getString(TAG_interiorColor);
				sellcardetails_numberofdoors = c.getString(TAG_numberOfDoors);
				sellcardetails_vechiclecondition = c
						.getString(TAG_ConditionDescription);

				sellcardetails_milleagedisplay = c.getString(TAG__mileage);
				sellcardetails_milleage = Millageformat.format(Double
						.parseDouble(c.getString(TAG__mileage)));
				sellcardetails_transimssion = c.getString(TAG_Transmission);
				sellcardetails_drivetrain = c.getString(TAG_DriveTrain);
				sellcardetails_vin = c.getString(TAG_VIN);

				imageurl = "http://www.unitedcarexchange.com/" + sellpicloc
						+ sellpic;
				/*System.out.println("this is image url in detail view"
						+ imageurl);*/
				sellcardetails_bodytypeid = c.getString(TAG_bodystyleid);
				sellcardetails_uid = c.getString(TAG_UID);
				sellcardetails_makemodelid = c.getString(TAG_MAKEMODELID);
				sellcardetails_carid = c.getString(TAG_CARID);
				sellcardetails_numberofCylinder = c.getString(TAG_Cylinder);
				sellcardetails_FueltypeID = c.getString(TAG_fueltypeid);
				sellcarderails_zip = c.getString(TAG_zip);
				sellcarderails_city = c.getString(TAG_city).replace("%20", "");
				sellcardetails_title = c.getString(TAG_title);

				sellcardetails_StateID = c.getString(TAG_stateid);
				sellcardetails_fuel = c.getString(TAG_Fueltype);
				sellcardetails_sellerID = c.getString(TAG_sellerid);
				sellcardetails_sellerName = c.getString(TAG_sellerName);
				sellcardetails_bodyid = c.getString(TAG_bodyid);
				sellcardetails_carstatus = c.getString(TAG_carstatus);
				sellcardetails_carstateid = c.getString(TAG_StateID);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			Toast.makeText(getApplicationContext(),
					"Server Error occured,Please try again", Toast.LENGTH_LONG)
					.show();

		}
		ImageLoaders imageLoader = new ImageLoaders(getApplicationContext());
		imageLoader.displayImage(imageurl, sellcardetail_img);

		if (sellcardetails_year.equals("Emp")) {
			tv_sellcardetails_year.setText("");
		} else {
			tv_sellcardetails_year.setText(sellcardetails_year);
		}
		if (sellcardetails_make.equals("Emp")) {
			tv_sellcardetails_make.setText("");
		} else {
			tv_sellcardetails_make.setText(sellcardetails_make);
		}
		if (sellcardetails_model.equals("Emp")) {
			tv_sellcardetails_model.setText("");
		} else {
			tv_sellcardetails_model.setText(sellcardetails_model);
		}

		tv_sellcardetails_address.setText(sellcarderails_city + ","
				+ sellcardetails_StateID + "," + sellcarderails_zip);

		if (sellcardetails_description.equals("Emp")) {
			tv_sellcardetails_description.setText("");
		} else {
			tv_sellcardetails_description.setText(sellcardetails_description);
		}
		if (sellcardetails_drivetrain.equals("Emp")) {
			tv_sellcardetails_drivetrain.setText("");
		} else {
			tv_sellcardetails_drivetrain.setText(sellcardetails_drivetrain);
		}
		if (sellcardetails_email.equals("Emp")) {
			tv_sellcardetails_email.setText("");
		} else {
			tv_sellcardetails_email.setText(sellcardetails_email);
		}
		if (sellcardetails_exteriorcolor.equals("Emp")) {
			tv_sellcardetails_exteriorcolor.setText("");
		} else {
			tv_sellcardetails_exteriorcolor
					.setText(sellcardetails_exteriorcolor);
		}
		if (sellcardetails_fuel.equals("Emp")) {
			tv_sellcardetails_fuel.setText("");
		} else {
			tv_sellcardetails_fuel.setText(sellcardetails_fuel);
		}
		if (sellcardetails_interiorcolor.equals("Emp")) {
			tv_sellcardetails_interiorcolor.setText("");
		} else {
			tv_sellcardetails_interiorcolor
					.setText(sellcardetails_interiorcolor);
		}
		if (sellcardetails_make.equals("Emp")) {
			tv_sellcardetails_makedetail.setText("");
		} else {
			tv_sellcardetails_makedetail.setText(sellcardetails_make);
		}
		if (sellcardetails_model.equals("Emp")) {
			tv_sellcardetails_modeldetail.setText("");
		} else {
			tv_sellcardetails_modeldetail.setText(sellcardetails_model);
		}
		if (sellcardetails_numberofdoors.equals("Emp")) {
			tv_sellcardetails_numberofdoors.setText("");
		} else {
			tv_sellcardetails_numberofdoors
					.setText(sellcardetails_numberofdoors);
		}
		if (sellcardetails_phonenumber.equals("Emp")) {
			tv_sellcardetails_phonenumber.setText("");
		} else {
			tv_sellcardetails_phonenumber.setText(sellcardetails_phonenumber);
		}
		if (sellcardetails_price.equals("Emp")) {
			tv_sellcardetails_price.setText("");
		} else {
			tv_sellcardetails_price.setText(sellcardetails_price);
		}
		if (sellcardetails_sellertype.equals("Emp")) {
			tv_sellcardetails_sellertype.setText("Emp");
		} else {
			tv_sellcardetails_sellertype.setText(sellcardetails_sellertype);
		}
		if (sellcardetails_transimssion.equals("Emp")) {
			tv_sellcardetails_transimssion.setText("");
		} else {
			tv_sellcardetails_transimssion.setText(sellcardetails_transimssion);
		}
		if (sellcardetails_vechiclecondition.equals("Emp")) {
			tv_sellcardetails_vechiclecondition.setText("");
		} else {
			tv_sellcardetails_vechiclecondition
					.setText(sellcardetails_vechiclecondition);
		}
		if (sellcardetails_vin.equals("Emp")) {
			tv_sellcardetails_vin.setText("");
		} else {
			tv_sellcardetails_vin.setText(sellcardetails_vin);
		}
		if (sellcardetails_year.equals("Emp")) {
			tv_sellcardetails_yeardetail.setText("");
		} else {
			tv_sellcardetails_yeardetail.setText(sellcardetails_year);
		}
		if (sellcardetails_milleage.equals("Emp")) {
			tv_sellcardetails_milleage.setText("");
		} else {
			tv_sellcardetails_milleage.setText(sellcardetails_milleage);
		}

	}

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	//	System.out.println("this is resume");
		loaddata();
	}

}
