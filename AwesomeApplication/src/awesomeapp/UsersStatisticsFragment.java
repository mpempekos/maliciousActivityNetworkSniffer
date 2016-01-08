package awesomeapp;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class UsersStatisticsFragment extends Fragment{
	public static ListView interfaces;
	public static TextView textView;
	public static List<StatisticsEntry> chosenNodeStatisticsList = null;
	public static List<String> chosenNodeInterfaces = null;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(com.example.awesomeapp.R.layout.users_statistics_fragment,container,false);
		textView = (TextView) view.findViewById(com.example.awesomeapp.R.id.users_id_interfaces);
		textView.setText("Please select an item from Statistics list");	
		interfaces = (ListView) view.findViewById(com.example.awesomeapp.R.id.listView2);
		interfaces.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Info");
                builder.setMessage(interfaceInfo(chosenNodeInterfaces.get(position)));
                builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                builder.show();
			}
		});	
		final Button settingsButton = (Button) view.findViewById(com.example.awesomeapp.R.id.settings);
		settingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupMenu popup = new PopupMenu(getActivity(),settingsButton);
				popup.getMenuInflater().inflate(com.example.awesomeapp.R.menu.pop_up_menu, popup.getMenu());
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						if(item.getTitle().equals("Logout")) {
							AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
							builder.setTitle("Logout");
							builder.setMessage("Are you sure you want to logout ?");
			                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
			                    @Override
			                    public void onClick(DialogInterface dialog, int which) {
			                    	LogoutAsyncTask asyncTask = new LogoutAsyncTask();
			                		asyncTask.execute();
			                    }
			                });
			                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
			                builder.show();
						}
						return true;
					}
				});
                popup.show();
			}
		});
		return view; 
	}
	private class LogoutAsyncTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			if(isNetworkAvailable()) {
				Boolean result = Boolean.valueOf(WebServiceConnector.logout(MainActivity.username,MainActivity.password));
				return result;
			}
			return Boolean.valueOf(false);
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if(result.booleanValue())
				getActivity().finish();
			else 
				Toast.makeText(getActivity(),"Problem detected on log out",Toast.LENGTH_SHORT).show();
		}
		private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	}
	
	private String interfaceInfo(String interfaceName) {
		StringBuilder builder = new StringBuilder();
		builder.append("User's ID : ");
		builder.append(chosenNodeStatisticsList.get(0).getNodeID());
		builder.append("\n");
		builder.append(interfaceName);
		builder.append("\n");
		builder.append("Statistics from "+interfaceName+" : "+"\n");
		List<String> patterns = new ArrayList<String>();
		List<Integer> patternsFreq = new ArrayList<Integer>();
		for(StatisticsEntry entry : chosenNodeStatisticsList) {
			if(entry.getInterfaceName().equals(interfaceName)) {
				if(!patterns.contains(entry.getMaliciousPattern())) {
					patterns.add(entry.getMaliciousPattern());
					patternsFreq.add(Integer.valueOf(entry.getFrequency()));
				} 
				else {
					int position = patterns.indexOf(entry.getMaliciousPattern());
					int sumFreq = entry.getFrequency() + patternsFreq.get(position).intValue();
					patternsFreq.add(Integer.valueOf(sumFreq));
				}
			}	
		}
		for(int i = 0 ; i < patterns.size() ; i++) {
			builder.append(patterns.get(i));
			builder.append(" ");
			builder.append(patternsFreq.get(i)); 
			builder.append(" times found \n");
		}
		return builder.toString();
	}
} 
