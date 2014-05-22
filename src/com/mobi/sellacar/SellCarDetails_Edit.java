package com.mobi.sellacar;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class SellCarDetails_Edit extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	String edit_make, edit_model, edit_year, edit_carstatus;
	TextView tv_sellcardeailsedit_make, tv_sellcardetailsedit_model,
			tv_sellcardetailsedit_year, tv_customersupport;
	ListView sellcardetailsedit_lv;
	String[] values = new String[] { "Registration Info", "Packages",
			"Customer Support",

	};
	Button sellcardetails_done;
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	// TableLayout tl1, tl2;
	LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8;
	TextView carstatus;
	String reg_CarIDs, seller_uid, sessionid;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	String car;
	public static final int TABLET_MIN_DP_WEIGHT = 450;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
		//	System.out.println("this is car details edit");
			setContentView(R.layout.sellcardetails_edit);

			reg_CarIDs = Sell_Home.reg_CarIDs;
			seller_uid = Seller_Login.UID;
			sessionid = Seller_Login.SessionID;
			// Bundle b = this.getIntent().getExtras();
			edit_make = SellCarDetailView.sellcardetails_make;
		//	System.out.println("thsi is product" + edit_make);
			edit_model = SellCarDetailView.sellcardetails_model;
			edit_year = SellCarDetailView.sellcardetails_year;
			edit_carstatus = SellCarDetailView.sellcardetails_carstatus;
			tv_customersupport = (TextView) findViewById(R.id.tv_customersupport);

			tv_sellcardeailsedit_make = (TextView) findViewById(R.id.tv_sellcardetailedit_make);
			tv_sellcardetailsedit_year = (TextView) findViewById(R.id.tv_sellcardetailedit_year);
			tv_sellcardetailsedit_model = (TextView) findViewById(R.id.tv_sellcardetailedit_model);
			carstatus = (TextView) findViewById(R.id.tv_vehiclecarstatus);
			sellcardetails_done = (Button) findViewById(R.id.btn_sellcardetailedit_done);

			carstatus.setText("This car status is: " + edit_carstatus);
			tv_customersupport.setText("Ads of  " + edit_year + " " + edit_make
					+ " " + edit_model);
			// sellcardetailsedit_lv=(ListView)findViewById(R.id.sellcardetailedit_lv);
			tv_sellcardeailsedit_make.setText(edit_make);
			tv_sellcardetailsedit_model.setText(edit_model);
			tv_sellcardetailsedit_year.setText(edit_year);
			ll1 = (LinearLayout) findViewById(R.id.ll1);
			ll2 = (LinearLayout) findViewById(R.id.ll2);
			ll3 = (LinearLayout) findViewById(R.id.ll3);
			ll4 = (LinearLayout) findViewById(R.id.ll4);
			ll5 = (LinearLayout) findViewById(R.id.ll5);
			ll6 = (LinearLayout) findViewById(R.id.ll6);
			ll7 = (LinearLayout) findViewById(R.id.ll7);
			ll8 = (LinearLayout) findViewById(R.id.ll8);

			Intent in = getIntent();
			// getting attached intent data
			Bundle b = this.getIntent().getExtras();
			car = b.getString("product");
			//System.out.println("thsi is product" + car);
			sellcardetails_done.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					Intent in = new Intent(getApplicationContext(),
							SellCarDetailView.class);
					Bundle b = new Bundle();
					b.putString("product", car);
					// in.putExtra("product", playerChanged);
					in.putExtras(b);
					startActivity(in);

					// finish();
				}
			});
			ll1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent in = new Intent(getApplicationContext(),
							Sell_VehicleType.class);
					startActivity(in);

				}
			});

			ll2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent sellinfo_in = new Intent(getApplicationContext(),
							SellerInformation.class);
					startActivity(sellinfo_in);

				}
			});
			ll3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					Intent vehicleinfo_in = new Intent(getApplicationContext(),
							VehicleInformation.class);
					startActivity(vehicleinfo_in);

				}
			});
			ll4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent vehiclefeature_in = new Intent(
							getApplicationContext(), VehicleFeatures.class);
					startActivity(vehiclefeature_in);

				}
			});
			ll5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent vehicledes_in = new Intent(getApplicationContext(),
							VehicleDescription.class);
					startActivity(vehicledes_in);

				}
			});
			ll6.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent vehiclephotos_in = new Intent(
							getApplicationContext(), Vehiclephotos_Upload.class);
					startActivity(vehiclephotos_in);

				}
			});
			ll7.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (edit_carstatus.equals("Active")) {
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								SellCarDetails_Edit.this);

						// Setting Dialog Title
						alertDialog.setTitle("Car Status");

						// Setting Dialog Message
						alertDialog.setMessage("The car status is: "
								+ edit_carstatus);
						
						alertDialog.setPositiveButton("Withdraw",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										String withdraw = "Withdraw";
										String url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/UpdateMobileCarStatusByCarID/"
												+ reg_CarIDs
												+ "/"
												+ seller_uid
												+ "/"
												+ withdraw
												+ "/"
												+ Uid
												+ "/" + Uno + "/" + sessionid;
										JSONParser jParser = new JSONParser();

										// getting JSON string from URL
										JSONObject json = jParser
												.getJSONFromUrl(url);
										try {
											String res = json
													.getString("UpdateMobileCarStatusByCarIDResult");
										
											if (res.equals("Success")) {
												Toast.makeText(
														getApplicationContext(),
														"Thank you, Car Status Updated",
														Toast.LENGTH_SHORT)
														.show();
												finish();
											} else if (res.equals("UnSuccess")) {
												Toast.makeText(
														getApplicationContext(),
														"An error occured while saving information",
														Toast.LENGTH_SHORT)
														.show();
											} else if (res
													.equals("Session timed out")) {
												Intent in = new Intent(
														getApplicationContext(),
														Seller_Login.class);
												startActivity(in);
											} else {
												Toast.makeText(
														getApplicationContext(),
														"An error occured",
														Toast.LENGTH_SHORT)
														.show();
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();

											Toast.makeText(
													getApplicationContext(),
													"Server Error occured,Please try again",
													Toast.LENGTH_LONG).show();

										}
									}

								});

						// Setting Negative "NO" Button
						alertDialog.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

									}
								});

						// Setting Netural "Cancel" Button
						alertDialog.setNeutralButton("Sold",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										
										String sold = "Sold";
										String url = "http://test1.unitedcarexchange.com/CarService/ServiceMobile.svc/UpdateMobileCarStatusByCarID/"
												+ reg_CarIDs
												+ "/"
												+ seller_uid
												+ "/"
												+ sold
												+ "/"
												+ Uid
												+ "/"
												+ Uno + "/" + sessionid;
										JSONParser jParser = new JSONParser();

										// getting JSON string from URL
										JSONObject json = jParser
												.getJSONFromUrl(url);
										try {
											String res = json
													.getString("UpdateMobileCarStatusByCarIDResult");
											
											if (res.equals("Success")) {
												Toast.makeText(
														getApplicationContext(),
														"Thank you, Car Status Updated",
														Toast.LENGTH_SHORT)
														.show();

												finish();
											} else if (res.equals("UnSuccess")) {
												Toast.makeText(
														getApplicationContext(),
														"An error occured while saving information",
														Toast.LENGTH_SHORT)
														.show();
											} else if (res
													.equals("Session timed out")) {
												Intent in = new Intent(
														getApplicationContext(),
														Seller_Login.class);
												startActivity(in);
											} else {
												Toast.makeText(
														getApplicationContext(),
														"An error occured",
														Toast.LENGTH_SHORT)
														.show();
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								});

						// Showing Alert Message
						alertDialog.show();
					} else {
						Toast.makeText(
								getApplicationContext(),
								"Status cannot be changed, Please call customer support to change the car status",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			ll8.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent in = new Intent(getApplicationContext(),
							MultisiteListing.class);
					startActivity(in);

				}
			});
		} else {

			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						SellCarDetails_Edit.this).create();
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

}
