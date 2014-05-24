package com.mobi.sellacar;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.adapters.ViewGalleryAdapter;
import com.mobi.cropimage.ImageLoaders;
import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class SellCarDetails_Gallery extends Activity {
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
	Button sellcardetails_gallery_btn;
	JSONArray contacts = null;
	String domainName = "http://www.unitedcarexchange.com/";
	private String PICLOC1 = null;

	private String PIC0 = null;
	private String PIC1 = null;
	private String PIC2 = null;
	private String PIC3 = null;
	private String PIC4 = null;
	private String PIC5 = null;
	private String PIC6 = null;
	private String PIC7 = null;
	private String PIC8 = null;
	private String PIC9 = null;
	private String PIC10 = null;
	private String PIC11 = null;
	private String PIC12 = null;
	private String PIC13 = null;
	private String PIC14 = null;
	private String PIC15 = null;
	private String PIC16 = null;
	private String PIC17 = null;
	private String PIC18 = null;
	private String PIC19 = null;
	private String PIC20 = null;
	String picurl1=null, picurl2=null, picurl3=null, picurl4=null, picurl5=null, picurl6=null, picurl7=null,
			picurl8=null, picurl9=null, picurl10=null, picurl0=null, picurl11=null, picurl12=null, picurl13=null,
			picurl14=null, picurl15=null, picurl16=null, picurl17=null, picurl18=null, picurl19=null,
			picurl20=null;
	public ArrayList<String> carGallerList = new ArrayList<String>();

	Bitmap myBmp = null;
	ImageLoaders imageLoader;
	Gallery viewGallery;
	private ImageView selectedImageView;

	private ImageView leftArrowImageView;
	private ImageView rightArrowImageView;
	private int selectedImagePosition = 0;
	TextView tv_seller_gallery_cars, tv_gallerycarno;
	public static final int TABLET_MIN_DP_WEIGHT = 450;
	int k;
	ViewGalleryAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.sellcardetails_gallery);
			if (isSmartphone(SellCarDetails_Gallery.this)) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			//	Toast.makeText(this, "Smartphone", Toast.LENGTH_SHORT).show();
			} else {
			//	Toast.makeText(this, "Tablet", Toast.LENGTH_SHORT).show();
			}
			Bundle b = this.getIntent().getExtras();
			car_id = b.getString("car_id");
		//	System.out.println("thsi is car_id" + car_id);
			String year = SellCarDetailView.sellcardetails_year;

			String make = SellCarDetailView.sellcardetails_make;
			String model = SellCarDetailView.sellcardetails_model;
			sellcardetails_gallery_btn = (Button) findViewById(R.id.sellcargallery_button1);
			viewGallery = (Gallery) findViewById(R.id.viewGaleery);
			leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
			rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
			tv_seller_gallery_cars = (TextView) findViewById(R.id.tv_sellergallerycars);
			tv_gallerycarno = (TextView) findViewById(R.id.tv_gallerycarno);
			imageLoader = new ImageLoaders(getApplicationContext());

			
			tv_seller_gallery_cars.setText(year+" "+make+" "+model);
			
			sellcardetails_gallery_btn
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			loaddata_gallery();

			leftArrowImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (selectedImagePosition > 0) {
						--selectedImagePosition;

					}

					viewGallery.setSelection(selectedImagePosition, false);
				}
			});
			rightArrowImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (selectedImagePosition < carGallerList.size() - 1) {
						++selectedImagePosition;

					}

					viewGallery.setSelection(selectedImagePosition, false);

				}
			});
			viewGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int pos, long id) {

					selectedImagePosition = pos;
					int m = pos + 1;
					tv_gallerycarno.setText (m  + " of "+ k);
					if (selectedImagePosition > 0
							&& selectedImagePosition < carGallerList.size() - 1) {

						/*leftArrowImageView.setImageDrawable(getResources()
								.getDrawable(R.drawable.arrow_left_disabled));
						rightArrowImageView.setImageDrawable(getResources()
								.getDrawable(R.drawable.arrow_right_disabled));*/
						leftArrowImageView.setImageDrawable(getResources()
								.getDrawable(R.drawable.arrow_left_enabled));
						rightArrowImageView.setImageDrawable(getResources()
								.getDrawable(R.drawable.arrow_right_enabled));

					} else if (selectedImagePosition == 0) {

						/*leftArrowImageView.setImageDrawable(getResources()
								.getDrawable(R.drawable.arrow_left_enabled));*/
						leftArrowImageView.setImageDrawable(getResources()
								.getDrawable(R.drawable.arrow_left_disabled));

					} else if (selectedImagePosition == carGallerList.size() - 1) {

						/*rightArrowImageView.setImageDrawable(getResources()
								.getDrawable(R.drawable.arrow_right_enabled));*/
						rightArrowImageView.setImageDrawable(getResources()
								.getDrawable(R.drawable.arrow_right_disabled));
					}
					//
					// changeBorderForSelectedImage(selectedImagePosition);
					// setSelectedImage(selectedImagePosition);
				}

				//
				// private void setSelectedImage(int selectedImagePosition) {
				// // TODO Auto-generated method stub
				//
				// }

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}

			});
			if (carGallerList.size() > 0) {

				viewGallery.setSelection(selectedImagePosition, false);

			}

			if (carGallerList.size() == 1) {

				rightArrowImageView.setImageDrawable(getResources()
						.getDrawable(R.drawable.arrow_right_enabled));
			}
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						SellCarDetails_Gallery.this).create();
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

	private void loaddata_gallery() {
		// TODO Auto-generated method stub
		carGallerList.clear();
		carGallerList.removeAll(carGallerList);
		
		url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/FindCarID/"
				+ car_id + "/" + Uid + "/" + Uno + "/";
		// http://test1.unitedcarexchange.com/carservice/ServiceMobile.svc/FindCarID/1902/ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654/12345/
	//	System.out.println("this is detail view url" + url);

		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(url);
	//	System.out.println("this is json obj" + json);

		try {
			contacts = json.getJSONArray("FindCarIDResult");
			for (int i = 0; i < contacts.length(); i++) {
				JSONObject c = contacts.getJSONObject(i);
				PICLOC1 = c.getString("_PICLOC0");
				PIC0 = c.getString("_PIC0");
				PIC1 = c.getString("_PIC1");
				PIC2 = c.getString("_PIC2");
				PIC3 = c.getString("_PIC3");
				PIC4 = c.getString("_PIC4");
				PIC5 = c.getString("_PIC5");
				PIC6 = c.getString("_PIC6");
				PIC7 = c.getString("_PIC7");
				PIC8 = c.getString("_PIC8");
				PIC9 = c.getString("_PIC9");
				PIC10 = c.getString("_PIC10");
				PIC11 = c.getString("_PIC11");
				PIC12 = c.getString("_PIC12");
				PIC13 = c.getString("_PIC13");
				PIC14 = c.getString("_PIC14");
				PIC15 = c.getString("_PIC15");
				PIC16 = c.getString("_PIC16");
				PIC17 = c.getString("_PIC17");
				PIC18 = c.getString("_PIC18");
				PIC19 = c.getString("_PIC19");
				PIC20 = c.getString("_PIC20");

				for (int j = 0; i <= 20; j++) {
					/*
					 * if (PIC0.equalsIgnoreCase("Emp")) { break; } else {
					 * picurl0 = domainName + PICLOC1 + PIC0.replaceAll(" ",
					 * "%20"); //picurl0=
					 * "http://www.unitedcarexchange.com/CarImages/2011/BMW/325/2011_BMW_325_1902Thumb.jpg"
					 * ; System.out.println("this is pic url"+picurl0);
					 * carGallerList.add(picurl0); }
					 */
					if (PIC1.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl1 = domainName + PICLOC1
								+ PIC1.replaceAll(" ", "%20");
						carGallerList.add(picurl1);
					// System.out.println("this is 1 pic"+picurl1);
					}
					if (PIC2.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl2 = domainName + PICLOC1
								+ PIC2.replaceAll(" ", "%20");

						carGallerList.add(picurl2);
						// System.out.println("this is 2 pic"+picurl2);
					}
					/*
					 * if (PIC2.equalsIgnoreCase("Emp")) { break; } else {
					 * picurl3=domainName+PICLOC1+PIC3; carGallerList.add(PIC2);
					 * }
					 */
					if (PIC3.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl3 = domainName + PICLOC1
								+ PIC3.replaceAll(" ", "%20");
						carGallerList.add(picurl3);
						// System.out.println("this is 3 pic"+picurl3);
					}
					if (PIC4.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl4 = domainName + PICLOC1
								+ PIC4.replaceAll(" ", "%20");
						carGallerList.add(picurl4);
						// System.out.println("this is 4 pic"+picurl4);
					}
					if (PIC5.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl5 = domainName + PICLOC1
								+ PIC5.replaceAll(" ", "%20");
						carGallerList.add(picurl5);
						//System.out.println("this is 5 pic"+picurl5);
					}
					if (PIC6.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl6 = domainName + PICLOC1
								+ PIC6.replaceAll(" ", "%20");
						carGallerList.add(picurl6);
						// System.out.println("this is 6 pic"+picurl6);
					}
					if (PIC7.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl7 = domainName + PICLOC1
								+ PIC7.replaceAll(" ", "%20");
						carGallerList.add(picurl7);
						// System.out.println("this is 7 pic"+picurl7);
					}
					if (PIC8.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl8 = domainName + PICLOC1
								+ PIC8.replaceAll(" ", "%20");

						carGallerList.add(picurl8);
						// System.out.println("this is 8 pic"+picurl8);
					}
					if (PIC9.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl9 = domainName + PICLOC1
								+ PIC9.replaceAll(" ", "%20");

						carGallerList.add(picurl9);
						// System.out.println("this is 9 pic"+picurl9);
					}
					if (PIC10.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl10 = domainName + PICLOC1
								+ PIC10.replaceAll(" ", "%20");

						carGallerList.add(picurl10);
						// System.out.println("this is 10 pic"+picurl10);
					}

					if (PIC11.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl11 = domainName + PICLOC1
								+ PIC11.replaceAll(" ", "%20");

						carGallerList.add(picurl11);
						// System.out.println("this is 10 pic"+picurl11);
					}
					if (PIC12.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl12 = domainName + PICLOC1
								+ PIC12.replaceAll(" ", "%20");

						carGallerList.add(picurl12);
						// System.out.println("this is 10 pic"+picurl12);
					}
					if (PIC13.equalsIgnoreCase("Emp")) {

						break;
					} else {
						picurl13 = domainName + PICLOC1
								+ PIC13.replaceAll(" ", "%20");

						carGallerList.add(picurl13);
						System.out.println("this is 10 pic" + picurl12);
					}
					if (PIC14.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl14 = domainName + PICLOC1
								+ PIC14.replaceAll(" ", "%20");

						carGallerList.add(picurl14);
						// System.out.println("this is 10 pic"+picurl12);
					}
					if (PIC15.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl15 = domainName + PICLOC1
								+ PIC15.replaceAll(" ", "%20");

						carGallerList.add(picurl15);
						// System.out.println("this is 10 pic"+picurl12);
					}
					if (PIC16.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl16 = domainName + PICLOC1
								+ PIC16.replaceAll(" ", "%20");

						carGallerList.add(picurl16);
						// System.out.println("this is 10 pic"+picurl12);
					}
					if (PIC17.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl17 = domainName + PICLOC1
								+ PIC17.replaceAll(" ", "%20");

						carGallerList.add(picurl17);
						// System.out.println("this is 10 pic"+picurl12);
					}
					if (PIC18.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl18 = domainName + PICLOC1
								+ PIC18.replaceAll(" ", "%20");

						carGallerList.add(picurl18);
						// System.out.println("this is 10 pic"+picurl12);
					}
					if (PIC19.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl19 = domainName + PICLOC1
								+ PIC19.replaceAll(" ", "%20");

						carGallerList.add(picurl19);
						// System.out.println("this is 10 pic"+picurl12);
					}
					if (PIC20.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl20 = domainName + PICLOC1
								+ PIC20.replaceAll(" ", "%20");

						carGallerList.add(picurl20);
						// System.out.println("this is 10 pic"+picurl12);
					}
					break;
				}
				// String url1 =
				// "http://www.unitedcarexchange.com/CarImages/2011/BMW/325/2011_BMW_325_1902Thumb.jpg";
				// myBmp=imageLoader.getBitmap(url1);
				/*
				 * myBmp = imageLoader.getBitmap(domainName +
				 * PICLOC1.replace(" ", "%20").replace("Emp", "") +
				 * PIC0.replace(" ", "%20").replace("Emp", ""));
				 */
				 k = carGallerList.size();
			//	System.out.println("this is no of cars" + k);
				if (k == 0) {
					Toast.makeText(getApplicationContext(), "Their is no cars",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			//	adapter.isEmpty();
				 adapter = new ViewGalleryAdapter(
						getApplicationContext(), carGallerList);
				viewGallery.setAdapter(adapter);
				viewGallery.postInvalidate();
				viewGallery.setSpacing(10);
				viewGallery.setPadding(20, 20, 20, 20);

			}
		} catch (JSONException e) {
			e.printStackTrace();

			Toast.makeText(getApplicationContext(),
					"Server Error occured,Please try again", Toast.LENGTH_LONG)
					.show();

		}
	}
	 @Override
	    public void onDestroy() {
	        super.onDestroy();
	        imageLoader.clearCache();
	    }
}
