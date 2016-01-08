package awesomeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.awesomeapp.R;

import dbOperations.DBOperations;

public class MainActivity extends Activity implements android.view.View.OnClickListener {
	private EditText  usernameText = null;
	private EditText  passwordText = null;
	public static String username = null;
	public static String password = null;
	public static DBOperations database;
	private Button 	login;
	private TextView register;
	int counter = 3;					// when counter reaches 0, login disables
	@Override
	protected void onDestroy() {
		Log.e("","///onDestroy()");
		database.closeDB();
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		Log.e("","///onPause()");
		super.onPause();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("","///onCreate()");
		super.onCreate(savedInstanceState);
		database = new DBOperations(this);
	    database.openDB();
	    database.clientOP.addAdmin();
		setContentView(R.layout.activity_main);
		usernameText = (EditText)findViewById(R.id.editText1);
		passwordText = (EditText)findViewById(R.id.editText2);
		login = (Button)findViewById(R.id.button1);
		register = (TextView)findViewById(R.id.textView6);
		register.setTextColor(getResources().getColorStateList(R.color.on_hover));
		///////////// WHY THE HELL ISN'T THIS WORKING????
		///////////////android:textColor="@color/on_hover"
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textView6:	// register
			Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
			MainActivity.this.startActivity(intent);
			break;
		case R.id.button1:		// login
			username = usernameText.getText().toString();
			if(username.isEmpty()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Invalid username \n Please fill the USERNAME field.");
				builder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.show();
				return;
			}
			password = passwordText.getText().toString();
			if(password.isEmpty()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Invalid password \n Please fill the PASSWORD field.");
				builder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.show();
				return;
			}
			LoginTask loginTask = new LoginTask();
			loginTask.execute(username,password);
			break;
		default:
			break;
		}
	}
	
	private class LoginTask extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {	
			if(isNetworkAvailable())
				return Integer.valueOf(WebServiceConnector.login(params[0], params[1]));
			else {
				Log.i("asda","NO INTERNET");
				database.clientOP.setClientLoggedIn(params[0],params[1]);
				return Integer.valueOf(database.clientOP.getLoggedInLevel());
			}
		}
		@Override
		protected void onPostExecute(Integer result) {
			if(result.intValue() == 0) {
				usernameText.clearComposingText();
				passwordText.clearComposingText();
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Invalid username or password \n Please try again.");
				builder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.show();
			}
			else if(result.intValue() == 1) {
				TabsActivity.loggedIn = true;
				Intent intent = new Intent(MainActivity.this,TabsActivity.class);
				intent.putExtra("USER_TYPE","User");
				MainActivity.this.startActivity(intent);	
			}
			else if(result.intValue() == 2) {
				TabsActivity.loggedIn = true;
				Intent intent = new Intent(MainActivity.this,TabsActivity.class);
				intent.putExtra("USER_TYPE","Admin");
				MainActivity.this.startActivity(intent);
			}
			super.onPostExecute(result);
		}
		private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
		
	}
	@Override
	protected void onStop() {
		Log.e("","///onStop()");
		super.onStop();
	}


}
