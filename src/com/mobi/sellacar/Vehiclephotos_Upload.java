package com.mobi.sellacar;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.adapters.ViewGalleryAdapter;
import com.mobi.cropimage.ImageLoaders;
import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class Vehiclephotos_Upload extends Activity {
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
	Button sellcardetails_gallery_btn, btn_vehiclephoto_upload;
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
	String picurl1, picurl2, picurl3, picurl4, picurl5, picurl6, picurl7,
			picurl8, picurl9, picurl10, picurl0, picurl11, picurl12, picurl13,
			picurl14, picurl15, picurl16, picurl17, picurl18, picurl19,
			picurl20;
	int k;
	public ArrayList<String> carGallerList = new ArrayList<String>();

	Bitmap myBmp = null;
	ImageLoaders imageLoader;
	Gallery viewGallery;
	String make, model, year;
	TextView tv_make, tv_model, tv_year;
	private ImageView leftArrowImageView;
	private ImageView rightArrowImageView;
	private int selectedImagePosition = 0;
	private PopupWindow mpopup;
	TextView tv_photototalimages, tv_gallerycarno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.vehiclephotos_upload);
			make = SellCarDetailView.sellcardetails_make;
			model = SellCarDetailView.sellcardetails_model;
			year = SellCarDetailView.sellcardetails_year;
			tv_photototalimages = (TextView) findViewById(R.id.photo_totalimages);
			tv_gallerycarno = (TextView) findViewById(R.id.tv_gallerycarno);
			// Bundle b = this.getIntent().getExtras();
			car_id = SellCarDetailView.sellcardetails_carid;
		//	System.out.println("thsi is car_id" + car_id);
			sellcardetails_gallery_btn = (Button) findViewById(R.id.btn_vehiclephotoupload_back);
			viewGallery = (Gallery) findViewById(R.id.viewGaleery);
			tv_make = (TextView) findViewById(R.id.tv_vehiclephotoupload_make);
			tv_model = (TextView) findViewById(R.id.tv_vehiclephotoupload_model);
			tv_year = (TextView) findViewById(R.id.tv_vehiclephotoupload_year);
			btn_vehiclephoto_upload = (Button) findViewById(R.id.btn_vehiclephoto_upload);

			leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
			rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
			tv_make.setText(make);
			tv_model.setText(model);
			tv_year.setText(year);
			loaddata_gallery();
			imageLoader = new ImageLoaders(getApplicationContext());

			btn_vehiclephoto_upload.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*
					 * Intent in=new
					 * Intent(getApplicationContext(),PhotoUploading.class);
					 * startActivity(in);
					 */
					if (k == 20) {
						Toast.makeText(getApplicationContext(),
								"You can upload only 20 photos",
								Toast.LENGTH_LONG).show();
					} else {
						View popUpView = getLayoutInflater().inflate(
								R.layout.popup, null); // inflating popup layout
						mpopup = new PopupWindow(popUpView,
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT, true); // Creation of
																	// popup
						mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
						mpopup.showAtLocation(popUpView, Gravity.BOTTOM, 0, 0); // Displaying
																				// popup

						// Button btnOk =
						// (Button)popUpView.findViewById(R.id.btnOk);
						Button gallery, camera;
						gallery = (Button) popUpView
								.findViewById(R.id.popupgallery);
						camera = (Button) popUpView
								.findViewById(R.id.popupcamera);
						camera.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent in = new Intent(getApplicationContext(),
										CameraPhotoUploading.class);
								// CameraPhotoUploading.class);
								// startActivity(in);
								startActivityForResult(in, 3);
							}
						});
						gallery.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								mpopup.dismiss();
								Intent in = new Intent(getApplicationContext(),
										Photo.class);
								// startActivity(in);// dismissing the popup
								startActivityForResult(in, 2);
							}
						});

						Button btnCancel = (Button) popUpView
								.findViewById(R.id.btnCancel);
						btnCancel.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								mpopup.dismiss(); // dismissing the popup
							}
						});
					}
				}
			});

			sellcardetails_gallery_btn
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent in = new Intent(getApplicationContext(),
									SellCarDetails_Edit.class);
							Bundle b = new Bundle();
							b.putString("product", car_id);

							in.putExtras(b);
							startActivity(in);
						}
					});

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
					// selectedImagePosition = pos;
					int l = pos + 1;
					tv_gallerycarno.setText (l  + " of "+ k);
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
						Vehiclephotos_Upload.this).create();
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

	private void loaddata_gallery() {
		// TODO Auto-generated method stub

		url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/FindCarID/"
				+ car_id + "/" + Uid + "/" + Uno + "/";
		carGallerList.clear();
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
					//	System.out.println("this is image1"+picurl1);
					}
					if (PIC2.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl2 = domainName + PICLOC1
								+ PIC2.replaceAll(" ", "%20");

						carGallerList.add(picurl2);
						//System.out.println("this is image2"+picurl2);
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
						//System.out.println("this is image3"+picurl3);
					}
					if (PIC4.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl4 = domainName + PICLOC1
								+ PIC4.replaceAll(" ", "%20");
						carGallerList.add(picurl4);
						//System.out.println("this is image4"+picurl4);
					}
					if (PIC5.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl5 = domainName + PICLOC1
								+ PIC5.replaceAll(" ", "%20");
						carGallerList.add(picurl5);
						//System.out.println("this is image5"+picurl5);
					}
					if (PIC6.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl6 = domainName + PICLOC1
								+ PIC6.replaceAll(" ", "%20");
						carGallerList.add(picurl6);
						//System.out.println("this is image6"+picurl6);
					}
					if (PIC7.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl7 = domainName + PICLOC1
								+ PIC7.replaceAll(" ", "%20");
						carGallerList.add(picurl7);
					//	System.out.println("this is image7"+picurl7);
					}
					if (PIC8.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl8 = domainName + PICLOC1
								+ PIC8.replaceAll(" ", "%20");

						carGallerList.add(picurl8);
						//System.out.println("this is image8"+picurl8);
					}
					if (PIC9.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl9 = domainName + PICLOC1
								+ PIC9.replaceAll(" ", "%20");

						carGallerList.add(picurl9);
						//System.out.println("this is image9"+picurl9);
					}
					if (PIC10.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl10 = domainName + PICLOC1
								+ PIC10.replaceAll(" ", "%20");

						carGallerList.add(picurl10);
						//System.out.println("this is image10"+picurl10);
					}

					if (PIC11.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl11 = domainName + PICLOC1
								+ PIC11.replaceAll(" ", "%20");

						carGallerList.add(picurl11);
						//System.out.println("this is image11"+picurl11);
						// System.out.println("this is 10 pic"+picurl11);
					}
					if (PIC12.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl12 = domainName + PICLOC1
								+ PIC12.replaceAll(" ", "%20");

						carGallerList.add(picurl12);
						// System.out.println("this is 12 pic"+picurl12);
					}
					if (PIC13.equalsIgnoreCase("Emp")) {

						break;
					} else {
						picurl13 = domainName + PICLOC1
								+ PIC13.replaceAll(" ", "%20");

						carGallerList.add(picurl13);
						//System.out.println("this is 13 pic" + picurl13);
					}
					if (PIC14.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl14 = domainName + PICLOC1
								+ PIC14.replaceAll(" ", "%20");

						carGallerList.add(picurl14);
						// System.out.println("this is 14 pic"+picurl14);
					}
					if (PIC15.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl15 = domainName + PICLOC1
								+ PIC15.replaceAll(" ", "%20");

						carGallerList.add(picurl15);
						// System.out.println("this is 15 pic"+picurl15);
					}
					if (PIC16.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl16 = domainName + PICLOC1
								+ PIC16.replaceAll(" ", "%20");

						carGallerList.add(picurl16);
						// System.out.println("this is 16 pic"+picurl16);
					}
					if (PIC17.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl17 = domainName + PICLOC1
								+ PIC17.replaceAll(" ", "%20");

						carGallerList.add(picurl17);
						// System.out.println("this is 17 pic"+picurl17);
					}
					if (PIC18.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl18 = domainName + PICLOC1
								+ PIC18.replaceAll(" ", "%20");

						carGallerList.add(picurl18);
						// System.out.println("this is 18 pic"+picurl18);
					}
					if (PIC19.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl19 = domainName + PICLOC1
								+ PIC19.replaceAll(" ", "%20");

						carGallerList.add(picurl19);
						// System.out.println("this is 19 pic"+picurl19);
					}
					if (PIC20.equalsIgnoreCase("Emp")) {
						break;
					} else {
						picurl20 = domainName + PICLOC1
								+ PIC20.replaceAll(" ", "%20");

						carGallerList.add(picurl20);
						// System.out.println("this is 20 pic"+picurl20);
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
				//System.out.println("this is no of cars" + k);

				if (k == 0) {
					Toast.makeText(getApplicationContext(), "Their is no cars",
							Toast.LENGTH_SHORT).show();
					finish();
				}
				//tv_photototalimages.setText("Total images : " + k);
				ViewGalleryAdapter adapter = new ViewGalleryAdapter(
						getApplicationContext(), carGallerList);
				adapter.notifyDataSetChanged();
				viewGallery.setAdapter(adapter);
				viewGallery.postInvalidate();
				viewGallery.setSpacing(10);
				viewGallery.setPadding(20, 20, 20, 20);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// check if the request code is same as what is passed here it is 2
		if (requestCode == 2) {
			/*
			 * String message=data.getStringExtra("MESSAGE");
			 * textView1.setText(message);
			 */

			loaddata_gallery();
		} else if (requestCode == 3) {
			/*
			 * String message=data.getStringExtra("MESSAGE");
			 * textView1.setText(message);
			 */

			loaddata_gallery();
		}

	}
	 @Override
	    public void onDestroy() {
	        super.onDestroy();
	        imageLoader.clearCache();
	    }
}
