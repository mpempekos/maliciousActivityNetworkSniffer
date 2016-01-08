package awesomeapp;

import java.util.List;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.awesomeapp.R;

public class RemoveTerminalFragment extends Fragment {
	View view;
	Button deleteButton;
	private ListView listView;
	private static List<String> nodesList ;
	private int clickedItemPosition = -1;

	
	
	//////////////////////////////////
	/// Refresher thead //////////////
	Handler handler = new Handler();
	Runnable terminalRefresher = new Runnable(){
	    @Override
	    public void run() {

			listView = (ListView) view.findViewById(com.example.awesomeapp.R.id.listView3);
			LoadNodesTask loadNodesTask = new LoadNodesTask();
			loadNodesTask.execute();
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			listView.setSelection(clickedItemPosition);
			deleteButton = (Button) view.findViewById(com.example.awesomeapp.R.id.delete);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					clickedItemPosition = position;
			        view.setSelected(true);
				}
			});
			deleteButton.setOnClickListener(deleteButtonListener);

	    	PropertyFile prop = new PropertyFile();
	        handler.postDelayed(terminalRefresher, Long.valueOf(prop.getRefreshTime()));
	    }};


	    public void onStart() {
			super.onStart();
			handler.post(terminalRefresher);
		}
	    
		@Override
		public void onPause() {
			super.onPause();
			handler.removeCallbacks(terminalRefresher);
		}
	
		@Override
		public void onResume() {
			LoadNodesTask loadNodesTask = new LoadNodesTask();
			loadNodesTask.execute();
			super.onResume();
		}
		
			
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

			view = inflater.inflate(R.layout.remove_terminal_fragment,container,false);
			listView = (ListView) view.findViewById(com.example.awesomeapp.R.id.listView3);
			LoadNodesTask loadNodesTask = new LoadNodesTask();
			loadNodesTask.execute();
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			deleteButton = (Button) view.findViewById(com.example.awesomeapp.R.id.delete);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					clickedItemPosition = position;
			        view.setSelected(true);
				}
			});
			deleteButton.setOnClickListener(deleteButtonListener);
			return view;
		}	
		
		@Override
		public void onDestroy() {
			super.onDestroy();
		}
		
		
		private OnClickListener deleteButtonListener = new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	            builder.setTitle("Delete");
	            if(clickedItemPosition == -1) {
	            	builder.setMessage("You must select an item from the list");
	            	builder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
	            	builder.show();
	            	return;
	            }
	            builder.setMessage("Are you sure you want to delete node with UID :"+listView.getItemAtPosition(clickedItemPosition)+ "?");
	            // builder1.setIcon(R.drawable.ic_launcher);
	            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                	
	                	DeleteNodeAsyncTask asyncTask = new DeleteNodeAsyncTask();
	            		asyncTask.execute(nodesList.get(clickedItemPosition));
	            		nodesList.remove(clickedItemPosition);
	            		listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,nodesList));
	                	clickedItemPosition = -1;
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
		
		};
		
		
		
		
		
		
		
		class LoadNodesTask extends AsyncTask<Void, Integer,List<String>> {
			@Override
			protected List<String> doInBackground(Void... params) {
				return MainActivity.database.pcNodesOP.getAllPcNodes();
			}
			@Override
			protected void onPostExecute(List<String> result) {
				if(result.isEmpty())
					System.out.println("EMPTY");
				nodesList = result;
				listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,nodesList));
			}
		}
		class DeleteNodeAsyncTask extends AsyncTask<String ,Integer, Boolean> {
	
			@Override
			protected Boolean doInBackground(String... params) {
				MainActivity.database.pcNodesOP.deletePcNode(params[0]);
				if(isNetworkAvailable()) {
					return Boolean.valueOf(WebServiceConnector.delete(MainActivity.username,MainActivity.password,params[0]));		 
				}
				else {
					MainActivity.database.pendingDeletesOP.addPendingDelete(params[0]);
					return Boolean.valueOf(false);
				}
			}
			private boolean isNetworkAvailable() {
			    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
			}
		}
	
		
		
}
