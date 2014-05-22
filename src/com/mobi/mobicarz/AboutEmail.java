package com.mobi.mobicarz;

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
import android.widget.EditText;
import android.widget.TextView;

public class AboutEmail extends Activity {
	Button buttonSend;
	TextView textTo;
	EditText textSubject;
	EditText textMessage;

	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.aboutemail);

			buttonSend = (Button) findViewById(R.id.buttonSend);
			textTo = (TextView) findViewById(R.id.editTextTo);
			textSubject = (EditText) findViewById(R.id.editTextSubject);
			textMessage = (EditText) findViewById(R.id.editTextMessage);

			buttonSend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					String to = textTo.getText().toString();
					String subject = textSubject.getText().toString();
					String message = textMessage.getText().toString();

					Intent email = new Intent(Intent.ACTION_SEND);
					email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
					System.out.println("this is sending email");
					// email.putExtra(Intent.EXTRA_CC, new String[]{ to});
					// email.putExtra(Intent.EXTRA_BCC, new String[]{to});
					email.putExtra(Intent.EXTRA_SUBJECT, subject);
					email.putExtra(Intent.EXTRA_TEXT, message);

					// need this to prompts email client only
					email.setType("message/rfc822");

					startActivity(Intent.createChooser(email,
							"Choose an Email client :"));
					finish();

				}
			});
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext())
						.create();
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
