package com.mobi.mobicarz;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class PreferenceView extends Activity implements OnClickListener {
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
	final String PREFS_NAME = "LoginPrefs";
	SharedPreferences settings, settings1;
	public static String[] MakeList_array;
	ArrayList<MakeObjects> modelList = new ArrayList<MakeObjects>();
	String results = new String();
	/*com.uce.adapters.MySQLiteHelperpreferencesone helper;
	com.uce.adapters.MySQLiteHelperTwo helper2;
	com.uce.adapters.MySQLiteHelperThree helper3;
	com.uce.adapters.MySQLiteHelperFour helper4;
	com.uce.adapters.MySQLiteHelperFive helper5;*/
	Cursor cursor;

	public static ArrayList<Integer> clickAl = new ArrayList<Integer>();
	public static ArrayList<Integer> clickAl2 = new ArrayList<Integer>();
	public static ArrayList<Integer> clickAl3 = new ArrayList<Integer>();
	public static ArrayList<Integer> clickAl4 = new ArrayList<Integer>();
	public static ArrayList<Integer> clickAl5 = new ArrayList<Integer>();
	TextView tv_preference_gotohome;
	Button preferences2, preferences3, preference2_showdata, preferencesData,
			preferences4, preferences5;
	Button delete;
	JSONArray passing_array;
	public static JSONArray MakeListObj;
	

	
	String preferenceone_model = "", preferencetwo_model = "",
			preferencethree_model = "", preferencefour_model = "",
			preferencefive_model = "";
	String preference_oneresult="", preference_tworesult="", preference_threeresult="",
			preference_fourresult="", preference_fiveresult="";

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy); 
			setContentView(R.layout.preferenceview);

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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		InitializeUI();
	}

	private void InitializeUI() {
		// TODO Auto-generated method stub
		btn_search = (Button) findViewById(R.id.search);
		btn_mylist = (Button) findViewById(R.id.mylist);
		btn_car = (Button) findViewById(R.id.car);
		btn_preferencs=(Button)findViewById(R.id.preference);
		btn_preferencs.setBackgroundResource(R.drawable.preference_act);
		// delete=(Button)findViewById(R.id.preference_delete);
		tv_preference_gotohome = (TextView) findViewById(R.id.preference_gotohome);
		loaddata();

		tv_preference_gotohome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in = new Intent(getApplicationContext(),
						MainHomeScreen.class);

				startActivity(in);

			}
		});
		

		btn_search.setOnClickListener(this);
		btn_car.setOnClickListener(this);
		btn_mylist.setOnClickListener(this);
		// code for preference-1
		preferencesData = (Button) findViewById(R.id.Preferences1);
		preferences2 = (Button) findViewById(R.id.Preferences2);
		preferences3 = (Button) findViewById(R.id.Preferences3);
		preferences4 = (Button) findViewById(R.id.Preferences4);
		preferences5 = (Button) findViewById(R.id.Preferences5);
		

		preferencesData.setText("Preference-1");
		// SharedPreferences 1
		SharedPreferences settings3 = getSharedPreferences(PREFS_NAME, 0);
		if (settings3.getString("preference_logged", "").toString()
				.equals("logged_1")) {

			preferenceone_model = settings3.getString("model", "");
			preference_oneresult = settings3.getString("preference_result", "");
			if (!(preference_oneresult.length() == 0)) {
				preferencesData.setText(preferenceone_model + "  "
						+ preference_oneresult + "  Cars found");
			} else {
				preferencesData.setText(preferenceone_model);
			}
		//	System.out.println("this is preference data" + preferenceone_model);
		} else {

			preferencesData.setText("Preference-1");

		}
		preferences2.setText("Preference-2");
		// SharedPreferences 2
		SharedPreferences settings4 = getSharedPreferences(PREFS_NAME, 0);
		if (settings4.getString("preference_logged2", "").toString()
				.equals("logged_2")) {

			preferencetwo_model = settings4.getString("model_two", "");
			
			preference_tworesult = settings4.getString("preferencetwo_result",
					"");
			if (!(preference_tworesult.length() == 0)) {
				preferences2.setText(preferencetwo_model + "  "
						+ preference_tworesult + "  Cars found");
			} else {
				preferences2.setText(preferencetwo_model);
			}
			//System.out.println("this is preference data" + preferencetwo_model);
		} else {

			preferences2.setText("Preference-2");

		}
		preferences3.setText("Preference-3");
		// SharedPreferences 3
		SharedPreferences settings5 = getSharedPreferences(PREFS_NAME, 0);
		if (settings5.getString("preference_logged3", "").toString()
				.equals("logged_3")) {

			preferencethree_model = settings5.getString("model_three", "");
			preference_threeresult = settings5.getString(
					"preference_three_result", "");
			if (!(preference_threeresult.length() == 0)) {
				preferences3.setText(preferencethree_model + "  "
						+ preference_threeresult + "   Cars found");
			} else {
				preferences3.setText(preferencethree_model);
			}
			/*System.out.println("this is preference data"
					+ preferencethree_model);*/
		} else {

			preferences3.setText("Preference-3");

		}
		// SharedPreferences 4
		SharedPreferences settings6 = getSharedPreferences(PREFS_NAME, 0);
		if (settings4.getString("preference_logged4", "").toString()
				.equals("logged_4")) {

			preferencefour_model = settings6.getString("model_four", "");
			preference_fourresult = settings6.getString(
					"preference_four_result", "");
			if (!(preference_fourresult.length() == 0)) {
				preferences4.setText(preferencefour_model + "  "
						+ preference_fourresult + "   Cars found");
			} else {
				preferences4.setText(preferencefour_model);
			}

			/*System.out
					.println("this is preference data" + preferencefour_model);*/
		} else {

			preferences4.setText("Preference-4");

		}
		// SharedPreferences 5
		SharedPreferences settings7 = getSharedPreferences(PREFS_NAME, 0);
		if (settings7.getString("preference_logged5", "").toString()
				.equals("logged_5")) {

			preferencefive_model = settings7.getString("model_five", "");
			preference_fiveresult = settings7.getString(
					"preference_five_result", "");
			if (!(preference_fiveresult.length() == 0)) {
				preferences5.setText(preferencefive_model + "  "
						+ preference_fiveresult + "  Cars found");
			} else {
				preferences5.setText(preferencefive_model);
			}
			/*System.out
					.println("this is preference data" + preferencefive_model);*/
		} else {

			preferences5.setText("Preference-5");

		}

		preferencesData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View alet) {

				if (preferenceone_model.equals("")) {
					Intent in = new Intent(getApplicationContext(),
							PreferenceOne.class);
					startActivity(in);

				} else {

					final Dialog dialog = new Dialog(PreferenceView.this);
					dialog.setContentView(R.layout.preferencedialog);
					dialog.setTitle("Preference 1");
					TextView input = (TextView) dialog
							.findViewById(R.id.tv_preferencedialog_input);

					if(preference_oneresult.equals("")){
						//input.setText(preference_oneresult + "  Cars Found");
						System.out.println("this is no cars");
					}else{
					input.setText(preference_oneresult + "  Cars Found");
					}
					// }
					input.setTextColor(Color.RED);
					input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
					input.setPadding(22, 0, 0, 0);

					Button preference_editone = (Button) dialog
							.findViewById(R.id.preferencedialog_edit);
					Button preference_viewone = (Button) dialog
							.findViewById(R.id.preferencedialog_viewcars);
					
					preference_editone
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub

									Intent in = new Intent(
											getApplicationContext(),
											PreferenceOne.class);
									startActivity(in);
									dialog.dismiss();
								}
							});

					if (!(preference_oneresult.length() == 0)) {
						//System.out.println("this is checking condition");
						preference_viewone.setVisibility(alet.VISIBLE);

						preference_viewone
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										Intent in = new Intent(
												getApplicationContext(),
												ViewCarsOne.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(in);
										dialog.dismiss();
									}
								});
					}
					dialog.show();

				}

			}

		});

		// code for preference-2

		preferences2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View alet2) {
				// TODO Auto-generated method stub
				
				if (preferencetwo_model.equals("")) {
					Intent in = new Intent(getApplicationContext(),
							PreferencesTwo.class);
					startActivity(in);

				} else {

					final Dialog dialog = new Dialog(PreferenceView.this);
					dialog.setContentView(R.layout.preferencedialog);
					dialog.setTitle("Preference 2");
					TextView input = (TextView) dialog
							.findViewById(R.id.tv_preferencedialog_input);

				if(preference_tworesult.equals("")){
					
				}else{
					input.setText(preference_tworesult + "  Cars Found");
				}
					// }
					input.setTextColor(Color.RED);
					input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
					input.setPadding(22, 0, 0, 0);

					Button preference_editone = (Button) dialog
							.findViewById(R.id.preferencedialog_edit);
					Button preference_viewone = (Button) dialog
							.findViewById(R.id.preferencedialog_viewcars);
					preference_editone
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub

									Intent in = new Intent(
											getApplicationContext(),
											PreferencesTwo.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
									startActivity(in);
									dialog.dismiss();
								}
							});

					if (!(preference_tworesult.length() == 0)) {
						//System.out.println("this is checking condition");
						preference_viewone.setVisibility(alet2.VISIBLE);

						preference_viewone
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										Intent in = new Intent(
												getApplicationContext(),
												ViewCarsTwo.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
										startActivity(in);
										dialog.dismiss();
									}
								});
					}
					dialog.show();
				}

				/*
				 * SharedPreferences sharedPreferences = getSharedPreferences(
				 * "Mani2", MODE_PRIVATE); String results2 =
				 * sharedPreferences.getString("Mass2", ""); System.out
				 * .println("this is sceond preference value" + results2); //
				 * preferences2.setText("Preference-2"); helper2 = new
				 * com.uce.adapters.MySQLiteHelperTwo( PreferenceView.this);
				 * helper2.open(); cursor = helper2.getCarList();
				 * 
				 * cursor.moveToFirst();
				 * 
				 * System.out.println("Cursor count:" + cursor.getCount());
				 * 
				 * if (cursor.getCount() > 0) { final Dialog dialog2 = new
				 * Dialog(PreferenceView.this);
				 * dialog2.setContentView(R.layout.preferencedialog);
				 * dialog2.setTitle("Preference 2"); TextView input = (TextView)
				 * dialog2 .findViewById(R.id.tv_preferencedialog_input); // int
				 * res=Integer.parseInt(results2); if (results2.equals("1")) {
				 * input.setText(results2.toString() + "  Car Found"); } else {
				 * input.setText(results2.toString() + "  Cars Found"); }
				 * input.setTextColor(Color.RED);
				 * input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); Button
				 * preference_editone = (Button) dialog2
				 * .findViewById(R.id.preferencedialog_edit); Button
				 * preference_viewone = (Button) dialog2
				 * .findViewById(R.id.preferencedialog_viewcars);
				 * preference_editone .setOnClickListener(new OnClickListener()
				 * {
				 * 
				 * @Override public void onClick(View arg0) { // TODO
				 * Auto-generated method stub Intent previewMessage = new
				 * Intent( PreferenceView.this, EditPreferencesTwo.class);
				 * startActivity(previewMessage); dialog2.dismiss(); } });
				 * System.out.println("this is no of cars in preference" +
				 * results + results2); if (!results2.equals("0")) {
				 * System.out.println("this is checking condition");
				 * preference_viewone.setVisibility(alet2.VISIBLE);
				 * 
				 * preference_viewone .setOnClickListener(new OnClickListener()
				 * {
				 * 
				 * @Override public void onClick(View v) { // TODO
				 * Auto-generated method stub Intent previewMessage = new
				 * Intent( PreferenceView.this, ViewCarsTwo.class);
				 * startActivity(previewMessage); dialog2.dismiss(); } }); }
				 * dialog2.show(); } else { AlertDialog.Builder alert = new
				 * AlertDialog.Builder( PreferenceView.this);
				 * 
				 * alert.setTitle("Preference 2");
				 * alert.setIcon(R.drawable.icon);
				 * 
				 * alert.setPositiveButton("Add Preference", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int whichButton) {
				 * 
				 * Intent edit = new Intent( PreferenceView.this,
				 * PreferencesTwo.class); startActivity(edit);
				 * 
				 * } });
				 * 
				 * alert.show(); } helper2.close();
				 */
			}

		});

		
		// code for preference-3

		// preferences3.setText("Preference-3");
		preferences3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View alet3) {
				// TODO Auto-generated method stub
				if (preferencethree_model.equals("")) {
					Intent in = new Intent(getApplicationContext(),
							PreferencesThree.class);
					startActivity(in);

				} else {

					final Dialog dialog = new Dialog(PreferenceView.this);
					dialog.setContentView(R.layout.preferencedialog);
					dialog.setTitle("Preference 3");
					TextView input = (TextView) dialog
							.findViewById(R.id.tv_preferencedialog_input);

					if(preference_threeresult.equals("")){
						
					}else{
					input.setText(preference_threeresult + "  Cars Found");
					}
					// }
					input.setTextColor(Color.RED);
					input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
					input.setPadding(22, 0, 0, 0);

					Button preference_editone = (Button) dialog
							.findViewById(R.id.preferencedialog_edit);
					Button preference_viewone = (Button) dialog
							.findViewById(R.id.preferencedialog_viewcars);
					preference_editone
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub

									Intent in = new Intent(
											getApplicationContext(),
											PreferencesThree.class);
									startActivity(in);
									dialog.dismiss();
								}
							});

					if (!(preference_threeresult.length() == 0)) {
						//System.out.println("this is checking condition");
						preference_viewone.setVisibility(alet3.VISIBLE);

						preference_viewone
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										Intent in = new Intent(
												getApplicationContext(),
												ViewCarsThree.class);
										startActivity(in);
										dialog.dismiss();
									}
								});
					}
					dialog.show();
				}

				
			}
		});
		
		// code for preference-4

		// preferences4.setText("Preference-4");
		preferences4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View alet4) {
				// TODO Auto-generated method stub

				if (preferencefour_model.equals("")) {
					Intent in = new Intent(getApplicationContext(),
							PreferencesFour.class);
					startActivity(in);

				} else {

					final Dialog dialog = new Dialog(PreferenceView.this);
					dialog.setContentView(R.layout.preferencedialog);
					dialog.setTitle("Preference 4");
					TextView input = (TextView) dialog
							.findViewById(R.id.tv_preferencedialog_input);

                   if(preference_fourresult.equals("")){
						
					}else{
					input.setText(preference_fourresult + "  Cars Found");
					}
					// }
					input.setTextColor(Color.RED);
					input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
					input.setPadding(22, 0, 0, 0);

					Button preference_editone = (Button) dialog
							.findViewById(R.id.preferencedialog_edit);
					Button preference_viewone = (Button) dialog
							.findViewById(R.id.preferencedialog_viewcars);
					preference_editone
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub

									Intent in = new Intent(
											getApplicationContext(),
											PreferencesFour.class);
									startActivity(in);
									dialog.dismiss();
								}
							});

					if (!(preference_fourresult.length() == 0)) {
					//	System.out.println("this is checking condition");
						preference_viewone.setVisibility(alet4.VISIBLE);

						preference_viewone
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										Intent in = new Intent(
												getApplicationContext(),
												ViewCarsFour.class);
										startActivity(in);
										dialog.dismiss();
									}
								});
					}
					dialog.show();

				}

				
			}
		});

		
		// code for preference-5

		// preferences5.setText("Preference-5");
		preferences5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View alet5) {
				// TODO Auto-generated method stub
				if (preferencefive_model.equals("")) {
					Intent in = new Intent(getApplicationContext(),
							PreferencesFive.class);
					startActivity(in);

				} else {

					final Dialog dialog = new Dialog(PreferenceView.this);
					dialog.setContentView(R.layout.preferencedialog);
					dialog.setTitle("Preference 5");
					TextView input = (TextView) dialog
							.findViewById(R.id.tv_preferencedialog_input);
					if(preference_fiveresult.equals("")){
					}else{
					
					input.setText(preference_fiveresult + "  Cars Found");
					}
					// }
					input.setTextColor(Color.RED);
					input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
					input.setPadding(22, 0, 0, 0);

					Button preference_editone = (Button) dialog
							.findViewById(R.id.preferencedialog_edit);
					Button preference_viewone = (Button) dialog
							.findViewById(R.id.preferencedialog_viewcars);
					preference_editone
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub

									Intent in = new Intent(
											getApplicationContext(),
											PreferencesFive.class);
									startActivity(in);
									dialog.dismiss();
								}
							});

					if (!(preference_fiveresult.length() == 0)) {
						//System.out.println("this is checking condition");
						preference_viewone.setVisibility(alet5.VISIBLE);

						preference_viewone
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										Intent in = new Intent(
												getApplicationContext(),
												ViewCarsFive.class);
										startActivity(in);
										dialog.dismiss();
									}
								});
					}
					dialog.show();

				}

				
			}
		});
		

	}

	private void loaddata() {
		// TODO Auto-generated method stub
		String url = "http://unitedcarexchange.com/CarService/Service.svc/GetMakes";
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(url);
		//System.out.println("this is url" + url);
		try {
			MakeListObj = json.getJSONArray("GetMakesResult");//.getJSONArray("GetMakesResult");
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
				int k = MakeList_array.length;
			//	System.out.println("this is length of make" + k);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		case R.id.car:
			Intent car_in = new Intent(getApplicationContext(),
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(car_in);
			break;
		default:
			break;
		}
	}

}
