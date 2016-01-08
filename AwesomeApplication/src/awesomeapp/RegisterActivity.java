package awesomeapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.awesomeapp.R;
	 
public class RegisterActivity	 extends Activity {
	MyCustomAdapter dataAdapter = null;
	EditText usernameText;
	EditText passwordText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.last_try_register);
		usernameText = (EditText) findViewById(R.id.editText1);
		passwordText = (EditText) findViewById(R.id.editText2);
		displayList();
		checkButtonClick();
	}
	 
	 private void displayList() {
		 
		 LoadPcNodesFromStatisticsAsyncTask loadNodes = new LoadPcNodesFromStatisticsAsyncTask();
		 loadNodes.execute();
		 
		 /*
		  dataAdapter = new MyCustomAdapter(this,R.layout.device_info, deviceList);
		  ListView listView = (ListView) findViewById(R.id.listView1);
		 
		  // Assign adapter to ListView
		  listView.setAdapter(dataAdapter);
		 
		 
		  listView.setOnItemClickListener(new OnItemClickListener() {
		   public void onItemClick(AdapterView<?> parent, View view,
		     int position, long id) {
			   // When clicked, show a toast with the TextView text
			   // Device dev = (Device) parent.getItemAtPosition(position);
			   // Toast.makeText(getApplicationContext(),
			   //  "Clicked now: " + dev.getName(),
			   //Toast.LENGTH_LONG).show();
		   }
		  });
		 */
		 }
	 
	 private class MyCustomAdapter extends ArrayAdapter<Device> {
		 
		  private ArrayList<Device> deviceList;
		 
		  public MyCustomAdapter(Context context, int textViewResourceId,
		    ArrayList<Device> deviceList) {
		   super(context, textViewResourceId, deviceList);
		   this.deviceList = new ArrayList<Device>();
		   this.deviceList.addAll(deviceList);
		  }
		 
		  private class ViewHolder {
		   CheckBox name;
		  }
		 
		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		 
		   ViewHolder holder = null;
		  // Log.v("ConvertView", String.valueOf(position));
		 
		   if (convertView == null) {
		   LayoutInflater vi = (LayoutInflater)getSystemService(
		     Context.LAYOUT_INFLATER_SERVICE);
		   convertView = vi.inflate(R.layout.device_info, null);
		 
		   holder = new ViewHolder();
		 //  holder.code = (TextView) convertView.findViewById(R.id.code);
		   holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
		   convertView.setTag(holder);
		 
		    holder.name.setOnClickListener( new View.OnClickListener() {
		    	
		    	public void onClick(View v) { 
		    	      CheckBox cb = (CheckBox) v ; 
		    	      Device dev = (Device) cb.getTag(); 
		    	      
		    	      /**** comment below code if u want *****/
		    	      
		    	      Toast.makeText(getApplicationContext(),
		    	       "On Click: " + cb.getText() +
		    	       " is " + cb.isChecked(),
		    	       Toast.LENGTH_LONG).show();
		    	      dev.setSelected(cb.isChecked());
		    	     } 
		    	    }); 
		    	   }
		    	   else {
		    	    holder = (ViewHolder) convertView.getTag();
		    	   }
		    	 
		   		   Device dev = deviceList.get(position);
		    	  
		    	   holder.name.setText(dev.getName());
		    	   holder.name.setChecked(dev.isSelected());
		    	   holder.name.setTag(dev);
		    	 
		    	   return convertView;
		    	 
		    	  }
		    	 
		    	 }
		    	 
		    	 private void checkButtonClick() {
		    	 
		    	  Button myButton = (Button) findViewById(R.id.register);
		    	  myButton.setOnClickListener(new OnClickListener() {
		    		  
		    		  @Override
		    		   public void onClick(View v) {
		    			  
		     		    StringBuffer finalList = new StringBuffer();
		    		    //finalList.append("Follwing devices were selected: \n \n");
		    		 
		    		    ArrayList<Device> countryList = dataAdapter.deviceList;
		    		    for(int i=0;i<countryList.size();i++){
		    		     Device country = countryList.get(i);
		    		     if(country.isSelected()){
		    		      finalList.append(country.getName() + "+");
		    		     }
		    		    }
		    		    if(usernameText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
		    		    	showAlertBox("You must fill username and password fieds","Register - Error");
			                return;
		    		    }
		    		    if(finalList.toString().isEmpty()) {
		    		    	showAlertBox("You must select your nodes form the list","Register - Error");
		    		    	return;
		    		    }
		    		    RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask();
		    		    registerAsyncTask.execute(usernameText.getText().toString(),passwordText.getText().toString(),finalList.toString());
		    		   }
		    		  });
		    		 
		    		 }
		    		 
		    	private class RegisterAsyncTask extends AsyncTask<String, Integer, Boolean> {
		    		@Override
		    		protected Boolean doInBackground(String... params) {
		    			String[] nodesArray = params[2].split("\\+");
		    	    	for(String nodeID : Arrays.asList(nodesArray))
		    	    		MainActivity.database.pcNodesOP.setPcNodeOwner(nodeID,params[0]);
		    			AvailableNodes availableNodes = new AvailableNodes(params[2]);
		    			return Boolean.valueOf(WebServiceConnector.register(params[0],params[1],availableNodes));
		    		}		    	
		    		@Override
		    		protected void onPostExecute(Boolean result) {
		    			if(result.booleanValue()) {
		    				ImageView image = new ImageView(RegisterActivity.this);
		    				image.setImageResource(R.drawable.done);
		    				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
		    		        alertDialogBuilder.setTitle("Registration completed!"); //CustomTitle
		    		        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
		    		        	public void onClick(DialogInterface dialog,int id) {
		    		        		finish();
		    		        	}
		    		        }).setView(image);
		    		        AlertDialog alert= alertDialogBuilder.create();
		    		        alert.show();
		    			}
		    			else
		    				showAlertBox("A problem detected.Please try again.","Register - Error");
		    		}
		    	}
		    	private void showAlertBox(String message,String title) {
		    		AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
					builder.setTitle(title);
					builder.setMessage(message);
	                builder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
	                	@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
	                });
	                builder.show();
		    	}
		    	private class LoadPcNodesFromStatisticsAsyncTask extends AsyncTask<Void,Void,ArrayList<Device>> {
					@Override
					protected ArrayList<Device> doInBackground(Void... params) {
						if(isNetworkAvailable()) {
							List<StatisticalReports> reports = WebServiceConnector.retrieveStatistics("admin","1234");
							if(reports != null) {
								if((!reports.isEmpty())) 
									MainActivity.database.statisticsOP.addAllStatisticsFromNet(reports);
							}
							ArrayList<Device> deviceList = new ArrayList<Device>();
							List<String> availableNodes = MainActivity.database.pcNodesOP.getAllPcNodes();
							for(String node : availableNodes) {
								deviceList.add(new Device(node,false));
							}
							return deviceList;
						}
						else {
							ArrayList<Device> deviceList = new ArrayList<Device>();
							List<String> availableNodes = MainActivity.database.pcNodesOP.getAllPcNodes();
							for(String node : availableNodes) {
								deviceList.add(new Device(node,false));
							}
							return deviceList;
						}
					}
					@Override
					protected void onPostExecute(ArrayList<Device> result) {
						if(result != null) {
							dataAdapter = new MyCustomAdapter(RegisterActivity.this,R.layout.device_info,result);
							ListView listView = (ListView) findViewById(R.id.listView1);
							// Assign adapter to ListView
							listView.setAdapter(dataAdapter);
						}
					}
					private boolean isNetworkAvailable() {
					    ConnectivityManager connectivityManager = (ConnectivityManager) RegisterActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
					    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
					    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
					}
				}
		 }
