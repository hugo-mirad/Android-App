package com.mobi.sellacar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebView;

import com.mobi.mobicarz.R;

public class MultisiteListingWebView extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	String webviewurl;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.multisitelistingwebview);
			Bundle b = this.getIntent().getExtras();
			webviewurl = b.getString("product");
		//	System.out.println("this is product" + webviewurl);
			WebView web = new WebView(this);
			// web = (WebView) findViewById(R.id.webView1);
			web = (WebView) findViewById(R.id.webView);

			web.getSettings().setJavaScriptEnabled(true);
			// web.loadUrl("http://www.gmail.com");
			web.loadUrl("http://" + webviewurl);
			/*
			 * Uri uri = Uri.parse("http://javatechig.com"); Intent intent = new
			 * Intent(Intent.ACTION_VIEW, uri); startActivity(intent);
			 */

		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						MultisiteListingWebView.this).create();
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
