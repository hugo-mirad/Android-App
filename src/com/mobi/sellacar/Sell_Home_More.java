package com.mobi.sellacar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mobi.mobicarz.R;

public class Sell_Home_More extends Activity {

	ListView sell_home_more_lv;
	TextView tv_sellhomemore;
	Button btn_logout, btn_done;
	String[] values = new String[] { "Registration Information", "Packages",
			"Customer Support"

	};
	public static final int TABLET_MIN_DP_WEIGHT = 450; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sell_home_more);
		
		tv_sellhomemore = (TextView) findViewById(R.id.tv_sellhomemore);
		btn_done = (Button) findViewById(R.id.btn_sellhome_done);
		btn_logout = (Button) findViewById(R.id.btn_sellhomemore_logout);

		tv_sellhomemore.setText(Seller_Login.seller_name);

		sell_home_more_lv = (ListView) findViewById(R.id.list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		sell_home_more_lv.setAdapter(adapter);
		sell_home_more_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				int itemPosition = position;

				// ListView Clicked item value
				String itemValue = (String) sell_home_more_lv
						.getItemAtPosition(position);
			//	System.out.println("this is item" + itemValue);
				if (itemValue.equals("Registration Information")) {
					Intent reg_in = new Intent(getApplicationContext(),
							SellerRegistration_Info.class);
					startActivity(reg_in);
				} else if (itemValue.equals("Packages")) {
					Intent package_in = new Intent(getApplicationContext(),
							Packages.class);
					startActivity(package_in);
				} else if (itemValue.equals("Customer Support")) {
					Intent customer_in = new Intent(getApplicationContext(),
							Customer_Support.class);
					startActivity(customer_in);
				}

			}
		});
		btn_done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(), Sell_Home.class);
				startActivity(in);
			}
		});
		btn_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),
						Seller_Login.class);
				startActivity(in);
			}
		});
	}
	

}
