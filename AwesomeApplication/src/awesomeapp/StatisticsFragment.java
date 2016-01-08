package awesomeapp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import objects.PcNode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class StatisticsFragment extends Fragment{
	
	
	private AtomicBoolean showAllStatistics = new AtomicBoolean(true);
	
	//////////////////////////////////
	/// Refresher thread //////////////
	Handler handler = new Handler();
	Runnable statisticsRefresher = new Runnable(){
	    @Override
	    public void run() {
	    	if (showAllStatistics.get()) {	   
				button.setText("show mine");
		    	AllNodesStatisticsAsyncTask allStatisticsTask = new AllNodesStatisticsAsyncTask();
				allStatisticsTask.execute();
				button.setOnClickListener(ownTerminalsButtonListener);
				listView.setOnItemClickListener(allTerminalsListtListener);
		    	//Log.e("","///+++SHOWING ALL STATISTICS...");
	    	}
	    	else {
				button.setText("show all");
				OwnNodesStatisticsAsyncTask ownStatisticsTask = new OwnNodesStatisticsAsyncTask();
				ownStatisticsTask.execute();
				button.setOnClickListener(allTerminalsButtonListener);
				listView.setOnItemClickListener(ownTerminalsListListener);
		    	//Log.e("","///+++SHOWING OWN statistics...");	    		
	    	}
	    	PropertyFile prop = new PropertyFile();
	        handler.postDelayed(statisticsRefresher, Long.valueOf(prop.getRefreshTime()));
	    }};

	    
	    
	private Activity activity;
	private ListView listView;
	private Button button;
	private static List<StatisticalReports> allNodesStatistics = null;
	private static List<StatisticalReports> ownNodesStatistics = null;
	private OnClickListener ownTerminalsButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			showAllStatistics.set(false);
			
			button.setText("show all");
			OwnNodesStatisticsAsyncTask ownStatisticsTask = new OwnNodesStatisticsAsyncTask();
			ownStatisticsTask.execute();
			button.setOnClickListener(allTerminalsButtonListener);
			listView.setOnItemClickListener(ownTerminalsListListener);
		}
		
		
	};
	private OnClickListener allTerminalsButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			showAllStatistics.set(true);
			
			button.setText("show mine");
			AllNodesStatisticsAsyncTask allStatisticsTask = new AllNodesStatisticsAsyncTask();
			allStatisticsTask.execute();
			button.setOnClickListener(ownTerminalsButtonListener);
			listView.setOnItemClickListener(allTerminalsListtListener);
		}
	};
	private OnItemClickListener allTerminalsListtListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent,View view,int position,long id) {

			UsersStatisticsFragment.chosenNodeStatisticsList = allNodesStatistics.get(position).getStatisticalReportEntries();
			UsersStatisticsFragment.chosenNodeInterfaces = allNodesStatistics.get(position).getInterfaces();
			UsersStatisticsFragment.interfaces.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,UsersStatisticsFragment.chosenNodeInterfaces));
			if(!UsersStatisticsFragment.chosenNodeStatisticsList.isEmpty() && UsersStatisticsFragment.chosenNodeStatisticsList != null) {
				UsersStatisticsFragment.textView.setText("User's : "+UsersStatisticsFragment.chosenNodeStatisticsList.get(0).getNodeID()+" interfaces");
			}
			if(!AdminTabsFragmentAdapter.isTablet(getActivity().getApplicationContext()))
				TabsActivity.viewPager.setCurrentItem(1);
		}
	};
	private OnItemClickListener ownTerminalsListListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent,View view,int position,long id) {

			UsersStatisticsFragment.chosenNodeStatisticsList = ownNodesStatistics.get(position).getStatisticalReportEntries();
			UsersStatisticsFragment.chosenNodeInterfaces = ownNodesStatistics.get(position).getInterfaces();
			if(ownNodesStatistics.get(position).getInterfaces() == null)
				System.err.println("NULL");
			if(UsersStatisticsFragment.interfaces == null)
				System.err.println("NULL INTERFACES");
			UsersStatisticsFragment.interfaces.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,UsersStatisticsFragment.chosenNodeInterfaces));
			UsersStatisticsFragment.textView.setText("User's : "+UsersStatisticsFragment.chosenNodeStatisticsList.get(0).getNodeID()+" interfaces");
			if(!AdminTabsFragmentAdapter.isTablet(getActivity().getApplicationContext()))
				TabsActivity.viewPager.setCurrentItem(1);
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		activity = getActivity();
		View view = inflater.inflate(com.example.awesomeapp.R.layout.statistics_fragment,container,false);
		listView = (ListView) view.findViewById(com.example.awesomeapp.R.id.statistics_list);
		listView.setOnItemClickListener(allTerminalsListtListener);
		button = (Button) view.findViewById(com.example.awesomeapp.R.id.show_button);
		button.setText("show mine");
		button.setOnClickListener(ownTerminalsButtonListener);
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
		AllNodesStatisticsAsyncTask allStatisticsTask = new AllNodesStatisticsAsyncTask();
		allStatisticsTask.execute();
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
			if(result.booleanValue()) {
				getActivity().finish();
			}
			else 
				Toast.makeText(getActivity(),"Problem detected on log out",Toast.LENGTH_SHORT).show();
		}
		private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	}
	private class OwnNodesStatisticsAsyncTask extends AsyncTask<Void, Integer, List<StatisticalReports>> {
		@Override
		protected List<StatisticalReports> doInBackground(Void... params) {
			List<String> ownNodesIDs = MainActivity.database.pcNodesOP.getAllPcNodeForUser(MainActivity.username);
			ownNodesStatistics = new ArrayList<StatisticalReports>();
			for(String temp : ownNodesIDs) {
				ownNodesStatistics.add(MainActivity.database.statisticsOP.getStatisticsByNodeID(temp));
			}
			return ownNodesStatistics;
		}
		@Override
		protected void onPostExecute(List<StatisticalReports> result) {
			ownNodesStatistics = result;
			listView.setAdapter(new ArrayAdapter<StatisticalReports>(activity,android.R.layout.simple_list_item_1,ownNodesStatistics));
		}
	}
	private class AllNodesStatisticsAsyncTask extends AsyncTask<Void,Integer, List<StatisticalReports>> {

		@Override
		protected List<StatisticalReports> doInBackground(Void... params) {
			if(isNetworkAvailable()) {
				allNodesStatistics = WebServiceConnector.retrieveStatistics(MainActivity.username,MainActivity.password);
				if((allNodesStatistics == null) || (allNodesStatistics.isEmpty()))
					allNodesStatistics = MainActivity.database.statisticsOP.getStatisticsInClientsGroup();
			}
			else
				allNodesStatistics = MainActivity.database.statisticsOP.getStatisticsInClientsGroup();
			return allNodesStatistics;
		}
		@Override
		protected void onPostExecute(List<StatisticalReports> result) {
			allNodesStatistics = result ;
			listView.setAdapter(new ArrayAdapter<StatisticalReports>(activity,android.R.layout.simple_list_item_1,allNodesStatistics));
		}
		private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	}
	@Override
	public void onStart() {
		
		handler.post(statisticsRefresher);
		
		super.onStart();
	}
	@Override
	public void onResume() {
		if(listView.getOnItemClickListener() == allTerminalsListtListener ) {
			AllNodesStatisticsAsyncTask allStatisticsTask = new AllNodesStatisticsAsyncTask();
			allStatisticsTask.execute();
		} 
		else {
			OwnNodesStatisticsAsyncTask ownStatisticsTask = new OwnNodesStatisticsAsyncTask();
			ownStatisticsTask.execute();
		}
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		handler.removeCallbacks(statisticsRefresher);

	}

	
}


