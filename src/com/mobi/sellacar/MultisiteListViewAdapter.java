package com.mobi.sellacar;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobi.mobicarz.R;

public class MultisiteListViewAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	// ImageLoader imageLoader;
	HashMap<String, String> result = new HashMap<String, String>();

	public MultisiteListViewAdapter(Context context,
			MultisiteListing multisiteListing,
			ArrayList<HashMap<String, String>> mulitsitelist) {
		// TODO Auto-generated constructor stub
		this.context = context;
		data = mulitsitelist;
		// imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView multisitename, multisitesurl, posteddate, validdays;
		
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.reports_page_row, parent,false);
		// holder = new ViewHolder();
		result = data.get(position);
		multisitename = (TextView) convertView.findViewById(R.id.textView2_multireport);
		posteddate = (TextView) convertView.findViewById(R.id.textView3_multireport);
		validdays = (TextView) convertView.findViewById(R.id.textView4_multireport);
		multisitesurl = (TextView) convertView.findViewById(R.id.textView5_multireport);
		String dateres=result.get(MultisiteListing.TAG_posteddate);
		  String arr[] = dateres.split(" ");
	        for(int i = 0; i < arr.length; i++){
	          //  System.out.println("arr["+i+"] = " + arr[i].trim());
	            
	        }
		String res=arr[0];
		//System.out.println("this is date only"+res);
		
		multisitename.setText(result.get(MultisiteListing.TAG_multisitename));
		//System.out.println("this is multilist name in row page"+result.get(MultisiteListing.TAG_multisitename));
		multisitesurl.setText(result.get(MultisiteListing.TAG_multisitesurl));
		posteddate.setText(res);
		validdays.setText(result.get(MultisiteListing.TAG_validdays));
		
		
		return convertView;
	}

}
