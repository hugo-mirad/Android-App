package com.mobi.sellacar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class VehicleFeatures extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	List<Integer> intList = new ArrayList<Integer>();
	int[] array;
	String[] temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8;

	String confort_items[] = { "A/C: Front", "A/C: Rear", "Cruise Control",
			"Navigation System", "Power Locks", "Power Steering",
			"Remote Keyless Entry", "TV/VCR", "Remote Start", "Tilt",
			"Rearview Camera", "Power Mirrors", "A/C" };
	String confort_items1[] = { "A/C: Front1", "A/C: Rear1", "Cruise Control1",
			"Navigation System1", "Power Locks1", "Power Steering1",
			"Remote Keyless Entry1", "TV/VCR1", "Remote Start1", "Tilt1",
			"Rearview Camera1", "Power Mirrors1", "A/C1" };
	String seats_items[] = { "Bucket Seats", "Leather Interior",
			"Memory Seats", "Power Seats", "Heated Seats", "Vinyl Interior",
			"Cloth Interior" };
	String safety_items[] = { "Airbag: Driver", "Airbag: Passenger",
			"Airbag: Side", "Alarm", "Anti-Lock Brakes", "Fog Lights",
			"Power Brakes" };
	String soundsystem_items[] = { "Cassette Radio", "CD Changer", "CD Player",
			"Premium Sound", "AM/FM", "DVD" };
	String windows_items[] = { "Power Windows", "Rear Window Defroster",
			"Rear Window Wiper", "Tinted Glass" };
	String other_items[] = { "Alloy Wheels", "Sunroof", "Third Row Seats",
			"Tow Package", "Panoramic Roof", "Moon Roof",
			"Dashboard Wood frame" };
	String new_items[] = { "Battery", "Tires" };
	String specials_items[] = { "Garage Kept", "Non Smoking",
			"Records/Receipts Kept", "Well Maintained", "Regular oil changes" };
	ListView lv_comfort, lv_seats, lv_safety, lv_soundsystem, lv_windows,
			lv_other, lv_new1, lv_specials;
	ArrayAdapter<String> adapter, adapter_comfort, adapter_seats, adapter_new,
			adapter_other, adapter_safety, adapter_soundsystem,
			adapter_specials, adapter_windows;
	Button btn_vehiclefeature_back, btn_vehiclefeature_edit,
			btn_vehiclefeature_save, btn_vehiclefeature_cancel;
	TextView tv_make, tv_model, tv_year;
	String make, model, year;
	ArrayList<String> updatedList;

	List list = null, seats_list, new_list, other_list, safety_list,
			sound_list, specials_list, window_list;
	ArrayList s1List_comfort = new ArrayList<String>();
	ArrayList s1List_seats = new ArrayList<String>();
	ArrayList s1List_new = new ArrayList<String>();
	ArrayList other_s1List = new ArrayList<String>();
	ArrayList safety_s1List = new ArrayList<String>();
	ArrayList sound_s1List = new ArrayList<String>();
	ArrayList specials_s1List = new ArrayList<String>();
	ArrayList windows_s1List = new ArrayList<String>();
	String[] comfort_outputStrArr = new String[0];
	String[] comfort_outputStr = new String[0];
	String[] seats_outputStrArr = new String[0];
	String[] seats_outputStr = new String[0];
	String[] outputStrArr_new = new String[0];
	String[] outputStr_new = new String[0];
	String[] other_outputStrArr = new String[0];
	String[] other_outputStr = new String[0];

	String[] safety_outputStrArr = new String[0];
	String[] safety_outputStr = new String[0];
	String[] sound_outputStrArr = new String[0];
	String[] sound_outputStr = new String[0];
	String[] specials_outputStrArr = new String[0];
	String[] specials_outputStr = new String[0];
	String[] windows_outputStrArr = new String[0];
	String[] windows_outputStr = new String[0];
	String[] mStringArray;
	String[] new_comfort = new String[0];
	String[] mStringArray1;// = { "10", "20", "30", "40" };
	String[] seats_mStringArray1, new_mStringArray1, safety_mStringArray1;
	String car_id, url;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	JSONArray contacts = null;
	private static final String TAG_GETCARFEATURES = "GetCarFeaturesResult";
	private Map<String, String> mapFeatures;
	String[] keyVal;
	String res, seats, safety, sound, new1, windows, others, specials;
	CheckedTextView che_tv;
	ArrayList<String> finalList = new ArrayList<String>();
	ArrayList comfort_list = new ArrayList<String>();
	ArrayList seats_list1 = new ArrayList<String>();
	ArrayList safety_newlist = new ArrayList<String>();
	ArrayList new_list1 = new ArrayList<String>();
	ArrayList other_list1 = new ArrayList<String>();
	ArrayList sound_list1 = new ArrayList<String>();
	ArrayList specials_list1 = new ArrayList<String>();
	ArrayList window_list1 = new ArrayList<String>();
	String delimiter = ",";
	String[] finalstring;
	String mynewstring;
	public static final int TABLET_MIN_DP_WEIGHT = 450;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.vehiclefeatures);

			lv_comfort = (ListView) findViewById(R.id.lv_comfort);
			lv_new1 = (ListView) findViewById(R.id.lv_new);
			lv_other = (ListView) findViewById(R.id.lv_other);
			lv_safety = (ListView) findViewById(R.id.lv_safety);
			lv_seats = (ListView) findViewById(R.id.lv_seats);
			lv_soundsystem = (ListView) findViewById(R.id.lv_soundsystem);
			lv_specials = (ListView) findViewById(R.id.lv_specilas);
			lv_windows = (ListView) findViewById(R.id.lv_windows);

			adapter_comfort = new ArrayAdapter<String>(this,
					R.layout.vehiclefeaturs_listitem, confort_items);
			this.lv_comfort.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lv_comfort.setAdapter(adapter_comfort);

			adapter_seats = new ArrayAdapter<String>(this,
					R.layout.vehiclefeaturs_listitem, seats_items);
			lv_seats.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lv_seats.setAdapter(adapter_seats);

			adapter_safety = new ArrayAdapter<String>(this,
					R.layout.vehiclefeaturs_listitem, safety_items);
			lv_safety.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lv_safety.setAdapter(adapter_safety);

			adapter_soundsystem = new ArrayAdapter<String>(this,
					R.layout.vehiclefeaturs_listitem, soundsystem_items);
			lv_soundsystem.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lv_soundsystem.setAdapter(adapter_soundsystem);

			adapter_windows = new ArrayAdapter<String>(this,
					R.layout.vehiclefeaturs_listitem, windows_items);
			lv_windows.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lv_windows.setAdapter(adapter_windows);

			adapter_other = new ArrayAdapter<String>(this,
					R.layout.vehiclefeaturs_listitem, other_items);
			lv_other.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lv_other.setAdapter(adapter_other);

			adapter_new = new ArrayAdapter<String>(this,
					R.layout.vehiclefeaturs_listitem, new_items);
			lv_new1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lv_new1.setAdapter(adapter_new);

			adapter_specials = new ArrayAdapter<String>(this,
					R.layout.vehiclefeaturs_listitem, specials_items);
			lv_specials.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lv_specials.setAdapter(adapter_specials);

			make = SellCarDetailView.sellcardetails_make;
			model = SellCarDetailView.sellcardetails_model;
			year = SellCarDetailView.sellcardetails_year;

			tv_make = (TextView) findViewById(R.id.tv_vehiclefeature_make);
			tv_model = (TextView) findViewById(R.id.tv_vehiclefeature_model);
			tv_year = (TextView) findViewById(R.id.tv_vehiclefeature_year);
			btn_vehiclefeature_back = (Button) findViewById(R.id.btn_vehiclefeature_back);
			btn_vehiclefeature_edit = (Button) findViewById(R.id.btn_vehiclefeature_edit);
			btn_vehiclefeature_save = (Button) findViewById(R.id.btn_vehiclefeature_save);
			btn_vehiclefeature_cancel = (Button) findViewById(R.id.btn_vehiclefeature_cancel);

			tv_make.setText(make);
			tv_model.setText(model);
			tv_year.setText(year);
			car_id = SellCarDetailView.sellcardetails_carid;

			lv_comfort.setEnabled(false);
			lv_new1.setEnabled(false);
			lv_other.setEnabled(false);
			lv_safety.setEnabled(false);
			lv_seats.setEnabled(false);
			lv_soundsystem.setEnabled(false);
			lv_specials.setEnabled(false);
			lv_windows.setEnabled(false);

			url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetCarFeatures/"
					+ car_id + "/" + Uid + "/" + Uno + "/";

			// "http://test1.unitedcarexchange.com/carservice/ServiceMobile.svc/GetCarFeatures/1902/ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654/12345/";
			ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

			// Creating JSON Parser instance
			JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			JSONObject json = jParser.getJSONFromUrl(url);
			try {
				// Getting Array of Contacts
				contacts = json.getJSONArray(TAG_GETCARFEATURES);
				if (contacts.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"No Features for this car", Toast.LENGTH_SHORT)
							.show();
					res = "";
					seats = "";
					safety = "";
					sound = "";
					new1 = "";
					windows = "";
					others = "";
					specials = "";
					// finish();
				} else {
					if (mapFeatures == null) {
						mapFeatures = new HashMap<String, String>();
					}
					for (int i = 0; i < contacts.length(); i++) {
						keyVal = contacts.getString(i).split(",");

						if (keyVal.length == 2) {
							String value = "";
							if (mapFeatures.get(keyVal[0]) != null) {
								value = mapFeatures.get(keyVal[0]) + ", ";
							}
							value = value + keyVal[1];
							mapFeatures.put(keyVal[0], value);
							String comfort = value + keyVal[1];

							if (mapFeatures.get("Comfort") != null) {
								res = (mapFeatures.get("Comfort").trim()
										.toString());

							} else {
								res = "";
							}

							if (mapFeatures.get("Seats") != null) {
								seats = mapFeatures.get("Seats").trim();

							} else {
								seats = "";
							}

							if (mapFeatures.get("Safety") != null) {
								safety = mapFeatures.get("Safety").trim();

							} else {
								safety = "";
							}

							if (mapFeatures.get("Sound System") != null) {
								sound = mapFeatures.get("Sound System").trim();

							} else {
								sound = "";
							}

							if (mapFeatures.get("New") != null) {
								new1 = mapFeatures.get("New").trim();

							} else {
								new1 = "";
							}

							if (mapFeatures.get("Windows") != null) {
								windows = mapFeatures.get("Windows").trim();

							} else {
								windows = "";
							}

							if (mapFeatures.get("Other") != null) {
								others = mapFeatures.get("Other").trim();

							} else {
								others = "";
							}

							if (mapFeatures.get("Specials") != null) {
								specials = mapFeatures.get("Specials").trim();

							} else {
								specials = "";
							}

						}
					}

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			getFeatures();
			btn_vehiclefeature_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			btn_vehiclefeature_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn_vehiclefeature_edit.setVisibility(View.VISIBLE);
					btn_vehiclefeature_cancel.setVisibility(View.INVISIBLE);
					btn_vehiclefeature_back.setVisibility(View.VISIBLE);
					btn_vehiclefeature_save.setVisibility(View.INVISIBLE);
					lv_comfort.setAdapter(adapter_comfort);
					lv_new1.setAdapter(adapter_new);
					lv_other.setAdapter(adapter_other);
					lv_safety.setAdapter(adapter_safety);
					lv_seats.setAdapter(adapter_seats);
					lv_soundsystem.setAdapter(adapter_soundsystem);
					lv_specials.setAdapter(adapter_specials);
					lv_windows.setAdapter(adapter_windows);
					getFeatures();

				}
			});
			btn_vehiclefeature_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					/*System.out.println("this is comfort without one "
							+ Arrays.toString(comfort_outputStrArr));*/

					String[] mStringArray = new String[s1List_comfort.size()];
					mStringArray = (String[]) s1List_comfort
							.toArray(mStringArray);

					/*
					 * System.out.println("this is changeing in string" +
					 * Arrays.toString(mStringArray));
					 */

					list = new ArrayList(Arrays.asList(comfort_outputStr));
					list.addAll(Arrays.asList(mStringArray));
					Object[] c = list.toArray();
					//System.out.println(Arrays.toString(c));

					if (list.contains("A/C: Front1")) {
						list.remove("A/C: Front1");
						// intList.add("11");
						list.add("11");
					}
					if (list.contains("A/C: Front")) {
						list.remove("A/C: Front");
						list.add("10");
					}
					if (list.contains("A/C: Rear1")) {
						list.remove("A/C: Rear1");
						list.add("21");
					}
					if (list.contains("A/C: Rear")) {
						list.remove("A/C: Rear");
						list.add("20");
					}
					if (list.contains("Cruise Control1")) {
						list.remove("Cruise Control1");
						list.add("31");
					}
					if (list.contains("Cruise Control")) {
						list.remove("Cruise Control");
						list.add("30");
					}
					if (list.contains("Navigation System1")) {
						list.remove("Navigation System1");
						list.add("41");
					}
					if (list.contains("Navigation System")) {
						list.remove("Navigation System");
						list.add("40");
					}
					if (list.contains("Power Locks1")) {
						list.remove("Power Locks1");
						list.add("51");
					}
					if (list.contains("Power Locks")) {
						list.remove("Power Locks");
						list.add("50");
					}
					if (list.contains("Power Steering1")) {
						list.remove("Power Steering1");
						list.add("61");
					}
					if (list.contains("Power Steering")) {
						list.remove("Power Steering");
						list.add("60");
					}
					if (list.contains("Remote Keyless Entry1")) {
						list.remove("Remote Keyless Entry1");
						list.add("71");
					}
					if (list.contains("Remote Keyless Entry")) {
						list.remove("Remote Keyless Entry");
						list.add("70");
					}
					if (list.contains("TV/VCR1")) {
						list.remove("TV/VCR1");
						list.add("81");
					}
					if (list.contains("TV/VCR")) {
						list.remove("TV/VCR");
						list.add("80");
					}
					if (list.contains("Tilt1")) {
						list.remove("Tilt1");
						list.add("331");
					}
					if (list.contains("Tilt")) {
						list.remove("Tilt");
						list.add("330");
					}
					if (list.contains("Rearview Camera1")) {
						list.remove("Rearview Camera1");
						list.add("351");
					}
					if (list.contains("Rearview Camera")) {
						list.remove("Rearview Camera");
						list.add("350");
					}
					if (list.contains("Power Mirrors1")) {
						list.remove("Power Mirrors1");
						list.add("361");
					}
					if (list.contains("Power Mirrors")) {
						list.remove("Power Mirrors");
						list.add("360");
					}
					if (list.contains("A/C1")) {
						list.remove("A/C1");
						list.add("511");
					}
					if (list.contains("A/C")) {
						list.remove("A/C");
						list.add("510");
					}
					if (list.contains("Remote Start1")) {
						list.remove("Remote Start1");
						list.add("311");
					}
					if (list.contains("Remote Start")) {
						list.remove("Remote Start");
						list.add("310");
					}
					// System.out.println("this is change index values" + list);

					mStringArray1 = new String[list.size()];
					mStringArray = (String[]) list.toArray(mStringArray1);
					/*
					 * System.out.println("this is changeing in string format" +
					 * Arrays.toString(mStringArray1));
					 */
					if (list.size() == 0) {

						String delimiter;
						delimiter = ",";
						if (res.equals("")) {
							list.add("10");
							list.add("20");
							list.add("30");
							list.add("40");
							list.add("50");

							list.add("60");
							list.add("70");
							list.add("80");
							list.add("300");
							list.add("350");
							list.add("360");
							list.add("500");
						} else {
							String[] temp = res.split(delimiter);

							for (int i = 0; i < temp.length; i++) {
								String comfort_values = temp[i].trim();
								/*
								 * System.out.println("this is new comfort values"
								 * + comfort_values);
								 * System.out.println("this is devideing string"
								 * + temp[i]);
								 */
								comfort_list.add(comfort_values);

								/*
								 * System.out.println("this is devideing string"
								 * + Arrays.toString(temp).trim());
								 */
							}

							if (comfort_list.contains("A/C: Front")) {
								//System.out.println("this is checking in if ");
								list.add("11");
							} else if (!comfort_list.contains("A/C: Front")) {
								list.add("10");
							}
							if (comfort_list.contains("A/C: Rear")) {
								/*System.out
										.println("this is checking if condition");*/
								list.add("21");
							} else {
								/*System.out
										.println("this is checking else condition");*/
								list.add("20");
							}
							if (comfort_list.contains("Cruise Control")) {
								list.add(31);
							} else {
								list.add("30");
							}
							if (comfort_list.contains("Navigation System")) {
								list.add("41");
							} else {
								list.add("40");
							}
							if (comfort_list.contains("Power Locks")) {
								list.add("51");
							} else {
								list.add("50");
							}
							if (comfort_list.contains("Power Steering")) {
								list.add("61");
							} else {
								list.add("60");
							}
							if (comfort_list.contains("Remote Keyless Entry")) {
								list.add("71");
							} else {
								list.add("70");
							}
							if (comfort_list.contains("TV/VCR")) {
								list.add("81");
							} else {
								list.add("80");
							}
							if (comfort_list.contains("Remote Start")) {
								list.add("301");
							} else {
								list.add("300");
							}
							if (comfort_list.contains("Tilt")) {
								list.add("331");
							} else {
								list.add("330");
							}
							if (comfort_list.contains("Rearview Camera")) {
								list.add("351");
							} else {
								list.add("350");
							}
							if (comfort_list.add("Power Mirrors")) {
								list.add("361");
							} else {
								list.add("360");
							}
							if (comfort_list.add("A/C")) {
								list.add("511");
							} else {
								list.add(500);
							}
							/*
							 * System.out
							 * .println("this is change checking value index values"
							 * + list);
							 */
						}
					}

					// code for seats
					String[] seatsStringArray = new String[s1List_seats.size()];
					seatsStringArray = (String[]) s1List_seats
							.toArray(seatsStringArray);

					/*System.out.println("this is seats changeing in string"
							+ Arrays.toString(seatsStringArray));*/

					seats_list = new ArrayList(Arrays.asList(seats_outputStr));
					seats_list.addAll(Arrays.asList(seatsStringArray));
					Object[] seat_c = seats_list.toArray();
				//	System.out.println(Arrays.toString(seat_c));

					if (seats_list.contains("Bucket Seats1")) {
						seats_list.remove("Bucket Seats1");
						seats_list.add("91");
					}
					if (seats_list.contains("Bucket Seats")) {
						seats_list.remove("Bucket Seats");
						seats_list.add("90");
					}
					if (seats_list.contains("Leather Interior1")) {
						seats_list.remove("Leather Interior1");
						seats_list.add("101");
					}
					if (seats_list.contains("Leather Interior")) {
						seats_list.remove("Leather Interior");
						seats_list.add("100");
					}
					if (seats_list.contains("Memory Seats1")) {
						seats_list.remove("Memory Seats1");
						seats_list.add("111");
					}
					if (seats_list.contains("Memory Seats")) {
						seats_list.remove("Memory Seats");
						seats_list.add("110");
					}
					if (seats_list.contains("Power Seats1")) {
						seats_list.remove("Power Seats1");
						seats_list.add("121");
					}
					if (seats_list.contains("Power Seats")) {
						seats_list.remove("Power Seats");
						seats_list.add("120");
					}
					if (seats_list.contains("Heated Seats1")) {
						seats_list.remove("Heated Seats1");
						seats_list.add("321");
					}
					if (seats_list.contains("Heated Seats")) {
						seats_list.remove("Heated Seats");
						seats_list.add("320");
					}
					if (seats_list.contains("Vinyl Interior1")) {
						seats_list.remove("Vinyl Interior1");
						seats_list.add("371");
					}
					if (seats_list.contains("Vinyl Interior")) {
						seats_list.remove("Vinyl Interior");
						seats_list.add("370");
					}
					if (seats_list.contains("Cloth Interior1")) {
						seats_list.remove("Cloth Interior1");
						seats_list.add("381");
					}
					if (seats_list.contains("Cloth Interior")) {
						seats_list.remove("Cloth Interior");
						seats_list.add("380");
					}
					/*System.out.println("this is change index values"
							+ seats_list);*/

					seats_mStringArray1 = new String[seats_list.size()];
					seatsStringArray = (String[]) seats_list
							.toArray(seats_mStringArray1);
					/*
					 * System.out.println("this is changeing in string format" +
					 * Arrays.toString(seats_mStringArray1));
					 */
					if (seats_list.size() == 0) {

						if (seats.equals("")) {
							seats_list.add("90");
							seats_list.add("100");
							seats_list.add("110");
							seats_list.add("120");
							seats_list.add("320");
							seats_list.add("370");
							seats_list.add("380");
						} else {
							String[] temp = seats.split(delimiter);

							for (int i = 0; i < temp.length; i++) {
								String seats_values = temp[i].trim();
								/*
								 * System.out.println("this is new comfort values"
								 * + seats_values);
								 * System.out.println("this is devideing string"
								 * + temp[i]);
								 */
								seats_list1.add(seats_values);

								/*
								 * System.out.println("this is devideing string"
								 * + Arrays.toString(temp).trim());
								 */
							}
							if (seats_list1.contains("Bucket Seats")) {
								seats_list.add("91");
							} else {
								seats_list.add("90");
							}
							if (seats_list1.contains("Leather Interior")) {
								seats_list.add("101");
							} else {
								seats_list.add("100");
							}
							if (seats_list1.contains("Memory Seats")) {
								seats_list.add("111");
							} else {
								seats_list.add("110");
							}
							if (seats_list1.contains("Power Seats")) {
								seats_list.add("121");
							} else {
								seats_list.add("120");
							}
							if (seats_list1.contains("Heated Seats")) {
								seats_list.add("321");
							} else {
								seats_list.add("320");
							}
							if (seats_list1.contains("Vinyl Interior")) {
								seats_list.add("371");
							} else {
								seats_list.add("370");
							}
							if (seats_list1.contains("Cloth Interior")) {
								seats_list.add("381");
							} else {
								seats_list.add("380");
							}
						}

					}
					/*
					 * System.out.println("this is no change index values" +
					 * seats_list);
					 */

					// code for new
					String[] newStringArray = new String[s1List_new.size()];
					newStringArray = (String[]) s1List_new
							.toArray(newStringArray);

					/*
					 * System.out.println("this is changeing in string" +
					 * Arrays.toString(newStringArray));
					 */

					new_list = new ArrayList(Arrays.asList(outputStr_new));
					new_list.addAll(Arrays.asList(newStringArray));
					Object[] new_c = new_list.toArray();
					// System.out.println(Arrays.toString(new_c));
					if (new_list.contains("Battery1")) {
						new_list.remove("Battery1");
						new_list.add("441");
					}
					if (new_list.contains("Battery")) {
						new_list.remove("Battery");
						new_list.add("440");
					}
					if (new_list.contains("Tires1")) {
						new_list.remove("Tires1");
						new_list.add("451");
					}
					if (new_list.contains("Tires")) {
						new_list.remove("Tires");
						new_list.add("450");
					}

					/*
					 * System.out.println("this is new change index values" +
					 * new_list);
					 */
					new_mStringArray1 = new String[new_list.size()];
					newStringArray = (String[]) new_list
							.toArray(new_mStringArray1);
					/*
					 * System.out.println("this is changeing in string format" +
					 * Arrays.toString(new_mStringArray1));
					 */
					if (new_list.size() == 0) {

						if (new1.equals("")) {
							new_list.add("440");
							new_list.add("450");
							new_list.add("520");
							new_list.add("530");
						} else {
							String[] temp = new1.split(delimiter);

							for (int i = 0; i < temp.length; i++) {
								String new1_values = temp[i].trim();
								/*
								 * System.out.println("this is new comfort values"
								 * + new1_values);
								 * System.out.println("this is devideing string"
								 * + temp[i]);
								 */
								new_list1.add(new1_values);

								/*
								 * System.out.println("this is devideing string"
								 * + Arrays.toString(temp).trim());
								 */
							}
							if (new_list1.contains("Battery")) {
								new_list.add("441");
							} else {
								new_list.add("440");
							}
							if (new_list1.contains("Tires")) {
								new_list.add("451");
							} else {
								new_list.add("450");
							}
						}

					}
					/*
					 * System.out.println("this is no new change index values" +
					 * new_list);
					 */
					// code for other
					String[] otherStringArray = new String[other_s1List.size()];
					newStringArray = (String[]) other_s1List
							.toArray(otherStringArray);

					/*
					 * System.out.println("this is new changeing in string" +
					 * Arrays.toString(otherStringArray));
					 */

					other_list = new ArrayList(Arrays.asList(other_outputStr));
					other_list.addAll(Arrays.asList(otherStringArray));
					Object[] other_c = new_list.toArray();
					// System.out.println(Arrays.toString(other_c));
					if (other_list.contains("Alloy Wheels1")) {
						other_list.remove("Alloy Wheels1");
						other_list.add("271");
					}
					if (other_list.contains("Alloy Wheels")) {
						other_list.remove("Alloy Wheels");
						other_list.add("270");

					}
					if (other_list.contains("Sunroof1")) {
						other_list.remove("Sunroof1");
						other_list.add("281");
					}
					if (other_list.contains("Sunroof")) {
						other_list.remove("Sunroof");
						other_list.add("280");
					}
					if (other_list.contains("Third Row Seats1")) {
						other_list.remove("Third Row Seats1");
						other_list.add("291");
					}
					if (other_list.contains("Third Row Seats")) {
						other_list.remove("Third Row Seats");
						other_list.add("290");
					}
					if (other_list.contains("Tow Package1")) {
						other_list.remove("Tow Package1");
						other_list.add("301");
					}
					if (other_list.contains("Tow Package")) {
						other_list.remove("Tow Package");
						other_list.add("300");
					}
					if (other_list.contains("Panoramic Roof1")) {
						other_list.remove("Panoramic Roof1");
						other_list.add("411");
					}
					if (other_list.contains("Panoramic Roof")) {
						other_list.remove("Panoramic Roof");
						other_list.add("410");
					}
					if (other_list.contains("Moon Roof1")) {
						other_list.remove("Moon Roof1");
						other_list.add("421");
					}
					if (other_list.contains("Moon Roof")) {
						other_list.remove("Moon Roof");
						other_list.add("420");
					}
					if (other_list.contains("Dashboard Wood frame1")) {
						other_list.remove("Dashboard Wood frame1");
						other_list.add("431");
					}
					if (other_list.contains("Dashboard Wood frame")) {
						other_list.remove("Dashboard Wood frame");
						other_list.add("430");
					}
					/*
					 * System.out.println("this is only other index" +
					 * other_s1List);
					 */
					if (other_list.size() == 0) {

						if (others.equals("")) {

							other_list.add("270");
							other_list.add("280");
							other_list.add("290");
							other_list.add("300");
							other_list.add("410");
							other_list.add("420");
							other_list.add("430");
						} else {
							String[] temp = others.split(delimiter);

							for (int i = 0; i < temp.length; i++) {
								String other_values = temp[i].trim();
								/*
								 * System.out.println("this is new comfort values"
								 * + other_values);
								 * System.out.println("this is devideing string"
								 * + temp[i]);
								 */
								other_list1.add(other_values);
								/*
								 * System.out.println("this is devideing string"
								 * + Arrays.toString(temp).trim());
								 */
							}
							if (other_list1.contains("Alloy Wheels")) {
								other_list.add("271");
							} else {
								other_list.add("270");
							}
							if (other_list1.contains("Sunroof")) {
								other_list.add("281");
							} else {
								other_list.add("281");
							}
							if (other_list1.contains("Third Row Seats")) {
								other_list.add("291");
							} else {
								other_list.add("290");
							}
							if (other_list1.contains("Tow Package")) {

								other_list.add("301");
							} else {
								other_list.add("300");
							}
							if (other_list1.contains("Panoramic Roof")) {
								other_list.add("411");
							} else {
								other_list.add("410");
							}
							if (other_list1.contains("Moon Roof")) {
								other_list.add("421");
							} else {
								other_list.add("420");
							}
							if (other_list1.contains("Dashboard Wood frame")) {

								other_list.add("431");
							} else {
								other_list.add("430");
							}
						}
					}
					// System.out.println("this is no other index" +
					// other_list);
					// code for safety
					String[] safetyStringArray = new String[safety_s1List
							.size()];
					safetyStringArray = (String[]) safety_s1List
							.toArray(safetyStringArray);

					/*
					 * System.out.println("this is safety changeing in string" +
					 * Arrays.toString(safetyStringArray));
					 */

					safety_list = new ArrayList(Arrays.asList(safety_outputStr));
					safety_list.addAll(Arrays.asList(safetyStringArray));
					Object[] safety_c = safety_list.toArray();
					// System.out.println(Arrays.toString(safety_c));
					if (safety_list.contains("Airbag: Driver1")) {
						safety_list.remove("Airbag: Driver1");
						safety_list.add("131");
					}
					if (safety_list.contains("Airbag: Driver")) {
						safety_list.remove("Airbag: Driver");
						safety_list.add("130");
					}
					if (safety_list.contains("Airbag: Passenger1")) {
						safety_list.remove("Airbag: Passenger1");
						safety_list.add("141");
					}
					if (safety_list.contains("Airbag: Passenger")) {
						safety_list.remove("Airbag: Passenger");
						safety_list.add("140");
					}
					if (safety_list.contains("Airbag: Side1")) {
						safety_list.remove("Airbag: Side1");
						safety_list.add("151");
					}
					if (safety_list.contains("Airbag: Side")) {
						safety_list.remove("Airbag: Side");
						safety_list.add("150");
					}
					if (safety_list.contains("Alarm1")) {
						safety_list.remove("Alarm1");
						safety_list.add("161");
					}
					if (safety_list.contains("Alarm")) {
						safety_list.remove("Alarm");
						safety_list.add("160");
					}
					if (safety_list.contains("Anti-Lock Brakes1")) {
						safety_list.remove("Anti-Lock Brakes1");
						safety_list.add("171");
					}
					if (safety_list.contains("Anti-Lock Brakes")) {
						safety_list.remove("Anti-Lock Brakes");
						safety_list.add("170");
					}
					if (safety_list.contains("Fog Lights1")) {
						safety_list.remove("Fog Lights1");
						safety_list.add("181");
					}
					if (safety_list.contains("Fog Lights")) {
						safety_list.remove("Fog Lights");
						safety_list.add("180");
					}
					if (safety_list.contains("Power Brakes1")) {
						safety_list.remove("Power Brakes1");
						safety_list.add("391");
					}
					if (safety_list.contains("Power Brakes")) {
						safety_list.remove("Power Brakes");
						safety_list.add("390");
					}
					/*
					 * System.out.println("this is safety change index values" +
					 * safety_list);
					 */

					safety_mStringArray1 = new String[safety_list.size()];
					safetyStringArray = (String[]) safety_list
							.toArray(safety_mStringArray1);
					/*
					 * System.out
					 * .println("this is changeing in safety string format" +
					 * Arrays.toString(safety_mStringArray1));
					 */
					if (safety_list.size() == 0) {

						if (safety.equals("")) {
							System.out
									.println("this is checking safety if condition");
							safety_list.add("130");
							safety_list.add("140");
							safety_list.add("150");
							safety_list.add("160");
							safety_list.add("170");
							safety_list.add("180");
							safety_list.add("390");
						} else {
							String[] temp = safety.split(delimiter);
							for (int i = 0; i < temp.length; i++) {
								String safety_values = temp[i].trim();
								/*
								 * System.out.println("this is new comfort values"
								 * + safety_values);
								 * System.out.println("this is devideing string"
								 * + temp[i]);
								 */
								safety_newlist.add(safety_values);
							}
							if (safety_newlist.contains("Airbag: Driver")) {
								safety_list.add("131");
							} else {
								safety_list.add("130");
							}
							if (safety_newlist.contains("Airbag: Passenger")) {
								safety_list.add("141");
							} else {
								safety_list.add("140");
							}
							if (safety_newlist.contains("Airbag: Side")) {
								safety_list.add("151");
							} else {
								safety_list.add("150");
							}
							if (safety_newlist.contains("Alarm")) {

								safety_list.add("161");
							} else {
								safety_list.add("160");
							}
							if (safety_newlist.contains("Anti-Lock Brakes")) {

								safety_list.add("171");
							} else {
								safety_list.add("170");
							}
							if (safety_newlist.contains("Fog Lights")) {

								safety_list.add("181");
							} else {
								safety_list.add("180");
							}
							if (safety_newlist.contains("Power Brakes")) {

								safety_list.add("391");
							} else {
								safety_list.add("390");
							}

							/*
							 * System.out.println("this is devideing string" +
							 * Arrays.toString(temp).trim());
							 */

						}
					}
					/*
					 * System.out.println("this is no safety change index values"
					 * + safety_list);
					 */
					// code for soundsystem
					String[] soundStringArray = new String[sound_s1List.size()];
					soundStringArray = (String[]) sound_s1List
							.toArray(soundStringArray);

					/*
					 * System.out.println("this is sound changeing in string" +
					 * Arrays.toString(soundStringArray));
					 */

					sound_list = new ArrayList(Arrays.asList(sound_outputStr));
					sound_list.addAll(Arrays.asList(soundStringArray));
					Object[] sound_c = sound_list.toArray();
					// System.out.println(Arrays.toString(sound_c));
					if (sound_list.contains("Cassette Radio1")) {
						sound_list.remove("Cassette Radio1");
						sound_list.add("191");
					}
					if (sound_list.contains("Cassette Radio")) {
						sound_list.remove("Cassette Radio");
						sound_list.add("190");
					}
					if (sound_list.contains("CD Changer1")) {
						sound_list.remove("CD Changer1");
						sound_list.add("201");
					}
					if (sound_list.contains("CD Changer")) {
						sound_list.remove("CD Changer");
						sound_list.add("200");
					}
					if (sound_list.contains("CD Player1")) {
						sound_list.remove("CD Player1");
						sound_list.add("211");
					}
					if (sound_list.contains("Premium Sound1")) {
						sound_list.remove("Premium Sound1");
						sound_list.add("221");
					}
					if (sound_list.contains("CD Player")) {
						sound_list.remove("CD Player");
						sound_list.add("210");
					}
					if (sound_list.contains("Premium Sound")) {
						sound_list.remove("Premium Sound");
						sound_list.add("220");
					}
					if (sound_list.contains("AM/FM1")) {
						sound_list.remove("AM/FM1");
						sound_list.add("341");
					}
					if (sound_list.contains("AM/FM")) {
						sound_list.remove("AM/FM");
						sound_list.add("340");
					}
					if (sound_list.contains("DVD1")) {
						sound_list.remove("DVD1");
						sound_list.add("401");
					}
					if (sound_list.contains("DVD")) {
						sound_list.remove("DVD");
						sound_list.add("400");
					}
					/*
					 * System.out.println("this is sound change index values" +
					 * sound_list);
					 */
					String[] sound_mStringArray1 = new String[sound_list.size()];
					soundStringArray = (String[]) sound_list
							.toArray(safety_mStringArray1);
					/*
					 * System.out
					 * .println("this is changeing in safety string format" +
					 * Arrays.toString(soundStringArray));
					 */
					if (sound_list.size() == 0) {
						if (sound.equals("")) {
							sound_list.add("190");
							sound_list.add("200");
							sound_list.add("210");
							sound_list.add("220");
							sound_list.add("340");
							sound_list.add("400");
						} else {
							String[] temp = sound.split(delimiter);
							for (int i = 0; i < temp.length; i++) {
								String sound_values = temp[i].trim();
								/*
								 * System.out.println("this is new sound values"
								 * + sound_values);
								 * System.out.println("this is devideing string"
								 * + temp[i]);
								 */
								sound_list1.add(sound_values);
							}
							if (sound_list1.contains("Cassette Radio")) {
								sound_list.add("191");
							} else {
								sound_list.add("190");
							}
							if (sound_list1.contains("CD Changer")) {
								sound_list.add("201");
							} else {
								sound_list.add("200");
							}
							if (sound_list1.contains("CD Player")) {
								sound_list.add("211");
							} else {
								sound_list.add("210");
							}
							if (sound_list1.contains("Premium Sound")) {
								sound_list.add("221");
							} else {
								sound_list.add("220");
							}
							if (sound_list1.contains("AM/FM")) {
								sound_list.add("341");
							} else {
								sound_list.add("340");
							}
							if (sound_list1.contains("DVD")) {
								sound_list.add("401");
							} else {
								sound_list.add("400");
							}
						}
					}
					/*
					 * System.out.println("this is no sound change index values"
					 * + sound_list);
					 */
					// code for specials
					String[] specialsStringArray = new String[specials_s1List
							.size()];
					specialsStringArray = (String[]) specials_s1List
							.toArray(specialsStringArray);

					/*
					 * System.out.println("this is specials changeing in string"
					 * + Arrays.toString(specialsStringArray));
					 */

					specials_list = new ArrayList(Arrays
							.asList(specials_outputStr));
					specials_list.addAll(Arrays.asList(specialsStringArray));
					Object[] specials_c = specials_list.toArray();
					/*
					 * System.out.println("this is specials list" +
					 * Arrays.toString(specials_c));
					 */

					if (specials_list.contains("Garage Kept1")) {
						specials_list.remove("Garage Kept1");
						specials_list.add("461");
					}
					if (specials_list.contains("Garage Kept")) {
						specials_list.remove("Garage Kept");
						specials_list.add("460");
					}

					if (specials_list.contains("Non Smoking1")) {
						specials_list.remove("Non Smoking1");
						specials_list.add("471");
					}
					if (specials_list.contains("Non Smoking")) {
						specials_list.remove("Non Smoking");
						specials_list.add("470");
					}
					if (specials_list.contains("Records/Receipts Kept1")) {
						specials_list.remove("Records/Receipts Kept1");
						specials_list.add("481");
					}
					if (specials_list.contains("Records/Receipts Kept")) {
						specials_list.remove("Records/Receipts Kept");
						specials_list.add("480");
					}
					if (specials_list.contains("Well Maintained1")) {
						specials_list.remove("Well Maintained1");
						specials_list.add("491");
					}
					if (specials_list.contains("Well Maintained")) {
						specials_list.remove("Well Maintained");
						specials_list.add("490");
					}
					if (specials_list.contains("Regular oil changes1")) {
						specials_list.remove("Regular oil changes1");
						specials_list.add("501");
					}
					if (specials_list.contains("Regular oil changes")) {
						specials_list.remove("Regular oil changes");
						specials_list.add("500");
					}

					/*
					 * System.out.println("this is specials change index values"
					 * + specials_list);
					 */
					String[] specials_mStringArray1 = new String[specials_list
							.size()];
					specialsStringArray = (String[]) specials_list
							.toArray(specials_mStringArray1);
					/*
					 * System.out
					 * .println("this is changeing in specials string format" +
					 * Arrays.toString(specialsStringArray));
					 */
					if (specials_list.size() == 0) {
						if (specials.equals("")) {
							specials_list.add("460");
							specials_list.add("470");
							specials_list.add("480");
							specials_list.add("490");
							specials_list.add("500");
						} else {
							String[] temp = specials.split(delimiter);
							for (int i = 0; i < temp.length; i++) {
								String specials_values = temp[i].trim();
								/*System.out.println("this is new sound values"
										+ specials_values);
								System.out.println("this is devideing string"
										+ temp[i]);*/
								specials_list1.add(specials_values);
							}
							if (specials_list1.contains("Garage Kept")) {
								specials_list.add("461");
							} else {
								specials_list.add("460");
							}
							if (specials_list1.contains("Non Smoking")) {
								specials_list.add("471");
							} else {
								specials_list.add("470");
							}
							if (specials_list1
									.contains("Records/Receipts Kept")) {
								specials_list.add("481");
							} else {
								specials_list.add("480");
							}
							if (specials_list1.contains("Well Maintained")) {
								specials_list.add("491");
							} else {
								specials_list.add("490");
							}
							if (specials_list1.contains("Regular oil changes")) {
								specials_list.add("501");
							} else {
								specials_list.add("500");
							}
						}
					}
					/*
					 * System.out
					 * .println("this is specials no change index values" +
					 * specials_list);
					 */
					// code for window
					String[] windowStringArray = new String[windows_s1List
							.size()];
					windowStringArray = (String[]) windows_s1List
							.toArray(windowStringArray);

					/*
					 * System.out.println("this is window changeing in string" +
					 * Arrays.toString(windowStringArray));
					 */

					window_list = new ArrayList(Arrays
							.asList(windows_outputStr));
					window_list.addAll(Arrays.asList(windowStringArray));
					Object[] window_c = window_list.toArray();
				//	System.out.println(Arrays.toString(window_c));
					if (window_list.contains("Power Windows1")) {
						window_list.remove("Power Windows1");
						window_list.add("231");
					}
					if (window_list.contains("Power Windows")) {
						window_list.remove("Power Windows");
						window_list.add("230");
					}
					if (window_list.contains("Rear Window Defroster1")) {
						window_list.remove("Rear Window Defroster1");
						window_list.add("241");
					}
					if (window_list.contains("Rear Window Defroster")) {
						window_list.remove("Rear Window Defroster");
						window_list.add("240");
					}
					if (window_list.contains("Rear Window Wiper1")) {
						window_list.remove("Rear Window Wiper1");
						window_list.add("251");
					}
					if (window_list.contains("Rear Window Wiper")) {
						window_list.remove("Rear Window Wiper");
						window_list.add("250");
					}
					if (window_list.contains("Tinted Glass1")) {
						window_list.remove("Tinted Glass1");
						window_list.add("261");
					}
					if (window_list.contains("Tinted Glass")) {
						window_list.remove("Tinted Glass");
						window_list.add("260");
					}
					/*
					 * System.out.println("this is window change index values" +
					 * window_list);
					 */
					String[] window_mStringArray1 = new String[window_list
							.size()];
					windowStringArray = (String[]) window_list
							.toArray(window_mStringArray1);
					/*
					 * System.out
					 * .println("this is changeing in window string format" +
					 * Arrays.toString(window_mStringArray1));
					 */
					if (window_list.size() == 0) {
						if (windows.equals("")) {
							window_list.add("230");
							window_list.add("240");
							window_list.add("250");
							window_list.add("260");
						} else {
							String[] temp = windows.split(delimiter);
							for (int i = 0; i < temp.length; i++) {
								String windows_values = temp[i].trim();
								/*
								 * System.out.println("this is new sound values"
								 * + windows_values);
								 * System.out.println("this is devideing string"
								 * + temp[i]);
								 */
								window_list1.add(windows_values);
							}
							if (window_list1.contains("Power Windows")) {
								window_list.add("231");
							} else {
								window_list.add("230");
							}
							if (window_list1.contains("Rear Window Defroster")) {

								window_list.add("241");
							} else {
								window_list.add("240");
							}
							if (window_list1.contains("Rear Window Wiper")) {

								window_list.add("251");
							} else {
								window_list.add("250");
							}
							if (window_list1.contains("Tinted Glass")) {

								window_list.add("261");
							} else {
								window_list.add("260");
							}
						}
					}
					/*
					 * System.out.println("this is window no change index values"
					 * + window_list);
					 */

					finalList.addAll(list);
					finalList.addAll(seats_list);
					finalList.addAll(new_list);
					finalList.addAll(other_list);
					finalList.addAll(safety_list);
					finalList.addAll(sound_list);
					finalList.addAll(specials_list);
					finalList.addAll(window_list);

					// int finalsize = finalList.size();
					// System.out.println("this is final array" + finalList);
					// + finalsize);

					finalstring = new String[finalList.size()];
					String[] arr = finalList.toArray(finalstring);
					finalstring = finalList.toArray(finalstring);
					/*
					 * System.out.println("this is string final array" +
					 * Arrays.toString(arr));
					 */
					StringBuffer result = new StringBuffer();
					// Separator sep = new Separator(", ");
					for (int i = 0; i < finalstring.length; i++) {
						result.append(arr[i]).append(",");
					}
					// result.deleteCharAt(result.length()-1);
					mynewstring = result.deleteCharAt(result.length() - 1)
							.toString();

					System.out.println("thsi is feature string" + mynewstring);

					pDialog = new ProgressDialog(VehicleFeatures.this);
					pDialog.setMessage("Please wait..");
					pDialog.setIndeterminate(true);
					pDialog.setCancelable(false);
					pDialog.show();

					MyTask myTask = new MyTask();
					myTask.execute();

				}
			});
			btn_vehiclefeature_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn_vehiclefeature_edit.setVisibility(View.INVISIBLE);
					btn_vehiclefeature_save.setVisibility(View.VISIBLE);
					btn_vehiclefeature_back.setVisibility(View.INVISIBLE);
					btn_vehiclefeature_cancel.setVisibility(View.VISIBLE);
					lv_comfort.setEnabled(true);
					lv_new1.setEnabled(true);
					lv_other.setEnabled(true);
					lv_safety.setEnabled(true);
					lv_seats.setEnabled(true);
					lv_soundsystem.setEnabled(true);
					lv_specials.setEnabled(true);
					lv_windows.setEnabled(true);
					lv_comfort.setFocusableInTouchMode(true);

				}
			});
			lv_comfort.setOnItemClickListener(new OnItemClickListener() {

				@SuppressLint("NewApi")
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					SparseBooleanArray checked = lv_comfort
							.getCheckedItemPositions();

					ArrayList<String> comfort_selectedItems = new ArrayList<String>();
					for (int i = 0; i < checked.size(); i++) {
						// Item position in adapter

						int position = checked.keyAt(i);
						// Add sport if it is checked i.e.) == TRUE!
						if (checked.valueAt(i)) {
							comfort_selectedItems.add(adapter_comfort.getItem(
									position).toString());

						} else if (!checked.valueAt(i)) {

						}

					}
					comfort_outputStrArr = new String[comfort_selectedItems
							.size()];
					comfort_outputStr = new String[comfort_selectedItems.size()];
					for (int i = 0; i < comfort_selectedItems.size(); i++) {
						comfort_outputStrArr[i] = comfort_selectedItems.get(i);
						comfort_outputStr[i] = comfort_selectedItems.get(i) + 1;

					}

					/*
					 * System.out.println("this is selected array" +
					 * Arrays.toString(comfort_outputStrArr));
					 */
					s1List_comfort = new ArrayList(Arrays.asList(confort_items));
					for (String s : comfort_outputStrArr) {
						if (s1List_comfort.contains(s)) {
							s1List_comfort.remove(s);
						} else {
							s1List_comfort.add(s);

						}
					}
					/*System.out.println("this is unselected array"
							+ s1List_comfort);*/

				}

			});
			lv_seats.setOnItemClickListener(new OnItemClickListener() {

				@SuppressLint("NewApi")
				@Override
				public void onItemClick(AdapterView<?> parent, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					int len = parent.getCount();
					SparseBooleanArray checked = lv_seats
							.getCheckedItemPositions();
					ArrayList<String> seats_selectedItems = new ArrayList<String>();
					for (int i = 0; i < lv_seats.getCount(); i++) {
						// Item position in adapter
						int position = checked.keyAt(i);
						// Add sport if it is checked i.e.) == TRUE!
						if (checked.valueAt(i))
							seats_selectedItems.add(adapter_seats
									.getItem(position));
					}
					seats_outputStrArr = new String[seats_selectedItems.size()];
					seats_outputStr = new String[seats_selectedItems.size()];

					for (int i = 0; i < seats_selectedItems.size(); i++) {
						seats_outputStrArr[i] = seats_selectedItems.get(i);
						seats_outputStr[i] = seats_selectedItems.get(i) + "1";
						int k = seats_outputStrArr.length;
						System.out.println("this is new selected item length"
								+ k);
					}

					System.out.println("this is new selected without"
							+ Arrays.toString(seats_outputStrArr));
					String res = Arrays.toString(seats_outputStr);
					System.out.println("arrrrrrrrrrr: new " + res);
					s1List_seats = new ArrayList(Arrays.asList(seats_items));
					for (String s : seats_outputStrArr) {
						if (s1List_seats.contains(s)) {
							s1List_seats.remove(s);
						} else {
							s1List_seats.add(s);

						}
					}
					System.out.println("new unselected " + s1List_seats);
				}
			});
			lv_new1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					SparseBooleanArray checkedItemPos = lv_new1
							.getCheckedItemPositions();
					// SparseBooleanArray uncheckedItemPositions=lv_specials.
					ArrayList<String> newselectedItems = new ArrayList<String>();
					ArrayList<String> newunselectedItems = new ArrayList<String>();
					for (int i = 0; i < new_items.length; i++) {
						// Item position in adapter

						int position = checkedItemPos.keyAt(i);
						// Add sport if it is checked i.e.) == TRUE!
						if (checkedItemPos.valueAt(i)) {
							newselectedItems.add(adapter_new.getItem(position)
									.toString());

						} else if (!checkedItemPos.valueAt(i)) {
							newunselectedItems.add(adapter_new
									.getItem(position).toString());

						}
					}
					outputStrArr_new = new String[newselectedItems.size()];
					outputStr_new = new String[newselectedItems.size()];

					for (int i = 0; i < newselectedItems.size(); i++) {
						outputStrArr_new[i] = newselectedItems.get(i);

						outputStr_new[i] = newselectedItems.get(i) + "1";

						int k = outputStr_new.length;
						/* String res=Arrays.toString(outputStrArr); */
						/*
						 * System.out.println("this is new selected item length"
						 * + k);
						 * System.out.println("this is new selected without" +
						 * Arrays.toString(outputStrArr_new));
						 */
					}
					String res = Arrays.toString(outputStr_new);
					System.out.println("arrrrrrrrrrr: new " + res);
					s1List_new = new ArrayList(Arrays.asList(new_items));
					for (String s : outputStrArr_new) {
						if (s1List_new.contains(s)) {
							s1List_new.remove(s);
						} else {
							s1List_new.add(s);

						}
					}
					System.out.println("new unselected " + s1List_new);

				}

			});
			lv_other.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					SparseBooleanArray checkedItemPos = lv_other
							.getCheckedItemPositions();
					// SparseBooleanArray uncheckedItemPositions=lv_specials.
					ArrayList<String> otherselectedItems = new ArrayList<String>();
					ArrayList<String> otherunselectedItems = new ArrayList<String>();
					for (int i = 0; i < other_items.length; i++) {
						// Item position in adapter

						int position = checkedItemPos.keyAt(i);
						// Add sport if it is checked i.e.) == TRUE!
						if (checkedItemPos.valueAt(i)) {
							otherselectedItems.add(adapter_other.getItem(
									position).toString());

						} else if (!checkedItemPos.valueAt(i)) {
							otherunselectedItems.add(adapter_other.getItem(
									position).toString());
						}
					}
					other_outputStrArr = new String[otherselectedItems.size()];
					other_outputStr = new String[otherselectedItems.size()];

					for (int i = 0; i < otherselectedItems.size(); i++) {
						other_outputStrArr[i] = otherselectedItems.get(i);

						other_outputStr[i] = otherselectedItems.get(i) + "1";

						int k = other_outputStrArr.length;
						/* String res=Arrays.toString(outputStrArr); */
						/*
						 * System.out.println("this is selected item length" +
						 * k); System.out.println("this is selected without" +
						 * Arrays.toString(other_outputStrArr));
						 */
					}
					String res = Arrays.toString(other_outputStr);

					System.out.println("arrrrrrrrrrr: other" + res);
					other_s1List = new ArrayList(Arrays.asList(other_items));
					for (String s : other_outputStrArr) {
						if (other_s1List.contains(s)) {
							other_s1List.remove(s);
						} else {
							other_s1List.add(s);
						}
					}
				}
			});
			lv_safety.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					int count = lv_safety.getCount();
					SparseBooleanArray checkedItemPositions = lv_safety
							.getCheckedItemPositions();

					ArrayList<String> safety_selectedItems = new ArrayList<String>();
					ArrayList<String> unselectedItems = new ArrayList<String>();
					for (int i = 0; i < safety_items.length; i++) {
						// Item position in adapter
						int position = checkedItemPositions.keyAt(i);
						// Add sport if it is checked i.e.) == TRUE!
						if (checkedItemPositions.valueAt(i)) {
							safety_selectedItems.add(adapter_safety.getItem(
									position).toString());

						} else if (!checkedItemPositions.valueAt(i)) {
							// unselectedItems.add(adapter.getItem(position)
							unselectedItems.add(adapter_safety
									.getItem(position).toString());
							// modified by kp

						}
					}
					safety_outputStrArr = new String[safety_selectedItems
							.size()];
					safety_outputStr = new String[safety_selectedItems.size()];
					String[] unoutputStrArr = new String[unselectedItems.size()];

					for (int i = 0; i < safety_selectedItems.size(); i++) {
						safety_outputStrArr[i] = safety_selectedItems.get(i);

						safety_outputStr[i] = safety_selectedItems.get(i) + "1";

						int k = safety_outputStrArr.length;
						/* String res=Arrays.toString(outputStrArr); */
						System.out.println("this is selected item length" + k);
						System.out.println("this is selected without"
								+ Arrays.toString(safety_outputStrArr));

					}
					String res = Arrays.toString(safety_outputStr);

					System.out.println("arrrrrrrrrrr:sound " + res);

					safety_s1List = new ArrayList(Arrays.asList(safety_items));
					for (String s : safety_outputStrArr) {
						if (safety_s1List.contains(s)) {
							safety_s1List.remove(s);
						} else {
							safety_s1List.add(s);

						}
					}
					System.out.println("this is safety array" + safety_s1List);
				}
			});
			lv_soundsystem.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					int count = lv_soundsystem.getCount();
					SparseBooleanArray checkedItemPositions = lv_soundsystem
							.getCheckedItemPositions();

					ArrayList<String> sound_selectedItems = new ArrayList<String>();
					ArrayList<String> unselectedItems = new ArrayList<String>();
					for (int i = 0; i < soundsystem_items.length; i++) {
						// Item position in adapter
						int position = checkedItemPositions.keyAt(i);
						// Add sport if it is checked i.e.) == TRUE!
						if (checkedItemPositions.valueAt(i)) {
							sound_selectedItems.add(adapter_soundsystem
									.getItem(position).toString());

						} else if (!checkedItemPositions.valueAt(i)) {
							// unselectedItems.add(adapter.getItem(position)
							unselectedItems.add(adapter_soundsystem.getItem(
									position).toString());
							// modified by kp

						}
					}
					sound_outputStrArr = new String[sound_selectedItems.size()];
					sound_outputStr = new String[sound_selectedItems.size()];
					String[] unoutputStrArr = new String[unselectedItems.size()];

					for (int i = 0; i < sound_selectedItems.size(); i++) {
						sound_outputStrArr[i] = sound_selectedItems.get(i);

						sound_outputStr[i] = sound_selectedItems.get(i) + "1";

						int k = sound_outputStrArr.length;
						/* String res=Arrays.toString(outputStrArr); */
						System.out.println("this is selected item length" + k);
						System.out.println("this is selected without"
								+ Arrays.toString(sound_outputStrArr));

					}
					String res = Arrays.toString(sound_outputStr);

					System.out.println("arrrrrrrrrrr:sound " + res);

					sound_s1List = new ArrayList(Arrays
							.asList(soundsystem_items));
					for (String s : sound_outputStrArr) {
						if (sound_s1List.contains(s)) {
							sound_s1List.remove(s);
						} else {
							sound_s1List.add(s);

						}
					}
				}
			});
			lv_specials.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					int count = lv_specials.getCount();
					SparseBooleanArray checkedItemPositions = lv_specials
							.getCheckedItemPositions();
					ArrayList<String> specials_selectedItems = new ArrayList<String>();
					ArrayList<String> unselectedItems = new ArrayList<String>();
					for (int i = 0; i < specials_items.length; i++) {
						// Item position in adapter
						int position = checkedItemPositions.keyAt(i);
						// Add sport if it is checked i.e.) == TRUE!
						if (checkedItemPositions.valueAt(i)) {
							specials_selectedItems.add(adapter_specials
									.getItem(position).toString());

						} else if (!checkedItemPositions.valueAt(i)) {
							// unselectedItems.add(adapter.getItem(position)
							unselectedItems.add(adapter_specials.getItem(
									position).toString());
							// modified by kp

						}
					}
					specials_outputStrArr = new String[specials_selectedItems
							.size()];
					specials_outputStr = new String[specials_selectedItems
							.size()];
					String[] unoutputStrArr = new String[unselectedItems.size()];

					for (int i = 0; i < specials_selectedItems.size(); i++) {
						specials_outputStrArr[i] = specials_selectedItems
								.get(i);

						specials_outputStr[i] = specials_selectedItems.get(i)
								+ "1";

						int k = specials_outputStrArr.length;
						/* String res=Arrays.toString(outputStrArr); */
						/*System.out.println("this is selected item length" + k);
						System.out.println("this is selected without"
								+ Arrays.toString(specials_outputStrArr));*/

					}
					String res = Arrays.toString(specials_outputStr);

					//System.out.println("arrrrrrrrrrr:sound " + res);

					specials_s1List = new ArrayList(Arrays
							.asList(specials_items));
					for (String s : specials_outputStrArr) {
						if (specials_s1List.contains(s)) {
							specials_s1List.remove(s);
						} else {
							specials_s1List.add(s);

						}
					}
					/*System.out.println("this is specials list in click"
							+ specials_s1List);*/
				}
			});

			lv_windows.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					final String TAG = null;
					int count = lv_windows.getCount();
					SparseBooleanArray checkedItemPositions = lv_windows
							.getCheckedItemPositions();
					ArrayList<String> windows_selectedItems = new ArrayList<String>();
					ArrayList<String> unselectedItems = new ArrayList<String>();
					for (int i = 0; i < windows_items.length; i++) {
						// Item position in adapter
						int position = checkedItemPositions.keyAt(i);
						// Add sport if it is checked i.e.) == TRUE!
						if (checkedItemPositions.valueAt(i)) {
							windows_selectedItems.add(adapter_windows.getItem(
									position).toString());

						} else if (!checkedItemPositions.valueAt(i)) {

							unselectedItems.add(adapter_windows.getItem(
									position).toString());
							// modified by kp

						}
					}
					windows_outputStrArr = new String[windows_selectedItems
							.size()];
					windows_outputStr = new String[windows_selectedItems.size()];
					String[] unoutputStrArr = new String[unselectedItems.size()];

					for (int i = 0; i < windows_selectedItems.size(); i++) {
						windows_outputStrArr[i] = windows_selectedItems.get(i);

						windows_outputStr[i] = windows_selectedItems.get(i)
								+ "1";

						int k = windows_outputStrArr.length;

						/*System.out.println("this is selected item length" + k);
						System.out.println("this is selected without"
								+ Arrays.toString(windows_outputStrArr));*/

					}
					String res = Arrays.toString(windows_outputStr);

					//System.out.println("arrrrrrrrrrr:sound " + res);

					windows_s1List = new ArrayList(Arrays.asList(windows_items));
					for (String s : windows_outputStrArr) {
						if (windows_s1List.contains(s)) {
							windows_s1List.remove(s);
						} else {
							windows_s1List.add(s);

						}
					}

				}
			});

		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						VehicleFeatures.this).create();
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

	private void getFeatures() {
		// TODO Auto-generated method stub
		String[] temp = res.split(delimiter);

		for (int i = 0; i < temp.length; i++) {
			String comfort_values1 = temp[i].trim();
			if (comfort_values1.equals("A/C: Front")) {
				this.lv_comfort.setItemChecked(0, true);
			}
			if (comfort_values1.equals("A/C: Rear")) {
				this.lv_comfort.setItemChecked(1, true);
			}
			if (comfort_values1.equals("Cruise Control")) {
				this.lv_comfort.setItemChecked(2, true);
			}
			if (comfort_values1.equals("Navigation System")) {
				this.lv_comfort.setItemChecked(3, true);
			}
			if (comfort_values1.equals("Power Locks")) {
				this.lv_comfort.setItemChecked(4, true);
			}
			if (comfort_values1.equals("Power Steering")) {
				this.lv_comfort.setItemChecked(5, true);
			}
			if (comfort_values1.equals("Remote Keyless Entry")) {
				this.lv_comfort.setItemChecked(6, true);
			}
			if (comfort_values1.equals("TV/VCR")) {
				this.lv_comfort.setItemChecked(7, true);
			}
			if (comfort_values1.equals("Remote Start")) {
				this.lv_comfort.setItemChecked(8, true);
			}
			if (comfort_values1.equals("Tilt")) {
				this.lv_comfort.setItemChecked(9, true);
			}
			if (comfort_values1.equals("Rearview Camera")) {
				this.lv_comfort.setItemChecked(10, true);
			}
			if (comfort_values1.equals("Power Mirrors")) {
				this.lv_comfort.setItemChecked(11, true);
			}
			if (comfort_values1.equals("A/C")) {
				this.lv_comfort.setItemChecked(12, true);
			}

		}
		String[] temp1 = seats.split(delimiter);
		for (int i = 0; i < temp1.length; i++) {
			String seats_values1 = temp1[i].trim();
			if (seats_values1.equals("Bucket Seats")) {
				this.lv_seats.setItemChecked(0, true);
			}
			if (seats_values1.equals("Leather Interior")) {
				this.lv_seats.setItemChecked(1, true);
			}
			if (seats_values1.equals("Memory Seats")) {
				this.lv_seats.setItemChecked(2, true);
			}
			if (seats_values1.equals("Power Seats")) {
				this.lv_seats.setItemChecked(3, true);
			}
			if (seats_values1.equals("Heated Seats")) {
				this.lv_seats.setItemChecked(4, true);
			}
			if (seats_values1.equals("Vinyl Interior")) {
				this.lv_seats.setItemChecked(5, true);
			}
			if (seats_values1.equals("Cloth Interior")) {
				this.lv_seats.setItemChecked(6, true);
			}
		}
		String[] temp2 = safety.split(delimiter);
		for (int i = 0; i < temp2.length; i++) {
			String safety_values1 = temp2[i].trim();
			if (safety_values1.equals("Airbag: Driver")) {
				this.lv_safety.setItemChecked(0, true);
			}
			if (safety_values1.equals("Airbag: Passenger")) {
				this.lv_safety.setItemChecked(1, true);
			}
			if (safety_values1.equals("Airbag: Side")) {
				this.lv_safety.setItemChecked(2, true);
			}
			if (safety_values1.equals("Alarm")) {
				this.lv_safety.setItemChecked(3, true);
			}
			if (safety_values1.equals("Anti-Lock Brakes")) {
				this.lv_safety.setItemChecked(4, true);
			}
			if (safety_values1.equals("Fog Lights")) {
				this.lv_safety.setItemChecked(5, true);
			}
			if (safety_values1.equals("Power Brakes")) {
				this.lv_safety.setItemChecked(6, true);
			}
		}
		String[] temp3 = sound.split(delimiter);
		for (int i = 0; i < temp3.length; i++) {
			String sound_values1 = temp3[i].trim();
			if (sound_values1.equals("Cassette Radio")) {
				this.lv_soundsystem.setItemChecked(0, true);
			}
			if (sound_values1.equals("CD Changer")) {
				this.lv_soundsystem.setItemChecked(1, true);
			}
			if (sound_values1.equals("CD Player")) {
				this.lv_soundsystem.setItemChecked(2, true);
			}
			if (sound_values1.equals("Premium Sound")) {
				this.lv_soundsystem.setItemChecked(3, true);
			}
			if (sound_values1.equals("AM/FM")) {
				this.lv_soundsystem.setItemChecked(4, true);
			}
			if (sound_values1.equals("DVD")) {
				this.lv_soundsystem.setItemChecked(5, true);
			}
		}
		String[] temp4 = new1.split(delimiter);
		for (int i = 0; i < temp4.length; i++) {
			String new_values1 = temp4[i].trim();
			if (new_values1.equals("Battery")) {
				this.lv_new1.setItemChecked(0, true);
			}
			if (new_values1.equals("Tires")) {
				this.lv_new1.setItemChecked(1, true);
			}

		}
		String[] temp5 = windows.split(delimiter);
		for (int i = 0; i < temp5.length; i++) {
			String windows_values1 = temp5[i].trim();
			if (windows_values1.equals("Power Windows")) {
				this.lv_windows.setItemChecked(0, true);
			}
			if (windows_values1.equals("Rear Window Defroster")) {
				this.lv_windows.setItemChecked(1, true);
			}
			if (windows_values1.equals("Rear Window Wiper")) {
				this.lv_windows.setItemChecked(2, true);
			}
			if (windows_values1.equals("Tinted Glass")) {
				this.lv_windows.setItemChecked(3, true);
			}
		}
		String[] temp6 = others.split(delimiter);
		for (int i = 0; i < temp6.length; i++) {
			String others_values1 = temp6[i].trim();
			if (others_values1.equals("Alloy Wheels")) {
				this.lv_other.setItemChecked(0, true);
			}
			if (others_values1.equals("Sunroof")) {
				this.lv_other.setItemChecked(1, true);
			}
			if (others_values1.equals("Third Row Seats")) {
				this.lv_other.setItemChecked(2, true);
			}
			if (others_values1.equals("Tow Package")) {
				this.lv_other.setItemChecked(3, true);
			}
			if (others_values1.equals("Panoramic Roof")) {
				this.lv_other.setItemChecked(4, true);
			}
			if (others_values1.equals("Moon Roof")) {
				this.lv_other.setItemChecked(5, true);
			}
			if (others_values1.equals("Dashboard Wood frame")) {
				this.lv_other.setItemChecked(6, true);
			}
		}
		String[] temp7 = specials.split(delimiter);
		for (int i = 0; i < temp7.length; i++) {
			String specials_values1 = temp7[i].trim().toString();

			if (specials_values1.equals("Well Maintained")) {
				this.lv_specials.setItemChecked(3, true);
			} else if (specials_values1.equals("Regular oil changes")) {
				this.lv_specials.setItemChecked(4, true);
			} else if (specials_values1.equals("Garage Kept")) {
				this.lv_specials.setItemChecked(0, true);
			} else if (specials_values1.equals("Non Smoking")) {
				this.lv_specials.setItemChecked(1, true);
			} else if (specials_values1.equals("Records/Receipts Kept")) {
				this.lv_specials.setItemChecked(2, true);
			}
		}
	}

	public class MyTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			String SOAP_ACTION = "http://tempuri.org/UpdateAllCarFeatures";
			String METHOD_NAME = "UpdateAllCarFeatures";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=UpdateAllCarFeatures";
			String TAG = "HELLO";

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("CarID", SellCarDetailView.sellcardetails_carid);
			/*System.out.println("this is photo"
					+ SellCarDetailView.sellcardetails_carid);*/
			request.addProperty("UID", SellCarDetailView.sellcardetails_uid);
			// String[] array ={"10","12","13"};
			request.addProperty("FeaturesBulk", mynewstring);
			request.addProperty("AuthenticationID", Uid);
			request.addProperty("CustomerID", Uno);
			request.addProperty("SessionID", Seller_Login.SessionID);
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// new MarshalBase64().register(soapEnvelope);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(request);
			HttpTransportSE aht = new HttpTransportSE(URL);
			// System.out.println("this is soap url"+aht);
			try {
				Log.v(TAG, "this is login test 24");
				aht.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				aht.debug = true;
				aht.call(SOAP_ACTION, soapEnvelope);
				SoapPrimitive result1 = (SoapPrimitive) soapEnvelope
						.getResponse();
				// SoapObject result1 = (SoapObject)soapEnvelope.getResponse();
				String soap_res = result1.toString();
				//System.out.println("soap response" + soap_res);
				if (soap_res.equals("Success")) {

					VehicleFeatures.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Thank You, Your modifications are saved",
									Toast.LENGTH_SHORT).show();

							pDialog.dismiss();

							finish();
							pDialog.dismiss();
						}
					});

				} else if (soap_res.equals("Session timed out")) {
					VehicleFeatures.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(
									getApplicationContext(),
									"Your session is timed out, please login again",
									Toast.LENGTH_SHORT).show();
							Intent in = new Intent(getApplicationContext(),
									Seller_Login.class);
							startActivity(in);
						}
					});
				} else if (soap_res.equals("Failed")) {
					VehicleFeatures.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Error occured,Please try again",
									Toast.LENGTH_LONG).show();

						}
					});
				}

			} catch (Exception e) {
				e.printStackTrace();
				pDialog.cancel();
				VehicleFeatures.this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Server Error occured,Please try again",
								Toast.LENGTH_LONG).show();
					}
				});
			}
			finalList.removeAll(list);
			finalList.removeAll(seats_list);
			finalList.removeAll(new_list);
			finalList.removeAll(other_list);
			finalList.removeAll(safety_list);
			finalList.removeAll(sound_list);
			finalList.removeAll(specials_list);
			finalList.removeAll(window_list);
			return null;
		}

	}
}
