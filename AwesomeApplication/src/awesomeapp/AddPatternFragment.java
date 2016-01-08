package awesomeapp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import awesomeapp.RemoveTerminalFragment.LoadNodesTask;

import com.example.awesomeapp.R;

public class AddPatternFragment extends Fragment{
	private Spinner spinner;
	private Button insert;
	private EditText pattern;
	final Context context = getActivity();
	boolean tableIsReady = false;
	
	View fragmentView;
	private List<String> patternList = new ArrayList<String>();
	
	
	
	//////////////////////////////////
	/// Refresher thread //////////////
	Handler handler = new Handler();
	Runnable patternRefresher = new Runnable(){
	    @Override
	    public void run() {

	    	RetrieveMaliciousAsyncTask retrievePatternTask = new RetrieveMaliciousAsyncTask();
	    	retrievePatternTask.execute();
	    	
	    	PropertyFile prop = new PropertyFile();
	        handler.postDelayed(patternRefresher, Long.valueOf(prop.getRefreshTime()));
	    }};


	
	public void onStart() {
		
		super.onStart();
		handler.post(patternRefresher);
	}
	
    
	@Override
	public void onPause() {
		super.onPause();
		handler.removeCallbacks(patternRefresher);
	}
	
	int i =1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	      //setContentView(R.layout.add_pattern_activity);
	      fragmentView = inflater.inflate(R.layout.add_pattern_fragment,container,false);
	      
	      
	      spinner = (Spinner) fragmentView.findViewById(R.id.spinner1);
	      addOptionsOnSpinner();
	      insert = (Button) fragmentView.findViewById(R.id.insert);
	      pattern   = (EditText) fragmentView.findViewById(R.id.editText2);
	      insert.setOnClickListener(new OnClickListener() {
		            @Override
	            public void onClick(View view) {
		            	
		            	if (spinner.getSelectedItem().toString().equals("select type")) {
		            		Toast.makeText(getActivity(), "Please select the pattern's type", Toast.LENGTH_SHORT).show();
		            	}
		            	else {
		            		
		            		String maliciousPattern = pattern.getText().toString();
		            		spinner.getSelectedItem().toString().equals("maliciousIP");
		            		if(!maliciousPattern.isEmpty()) {
		            			InsertMaliciousAsyncTask insertTask = new InsertMaliciousAsyncTask();
		            			if(spinner.getSelectedItem().toString().equals("maliciousIP"))
		            				insertTask.execute(maliciousPattern,null);
		            			else if(spinner.getSelectedItem().toString().equals("maliciousStringPattern"))
		            				insertTask.execute(null,maliciousPattern);
		            		}
		            	    tableAdd(fragmentView);
		            	}
		            }
			 
			       	});
	      
	      RetrieveMaliciousAsyncTask retrieveMaliciousTask = new RetrieveMaliciousAsyncTask();
	      retrieveMaliciousTask.execute();
	      
	      return fragmentView;
	}
	public void addOptionsOnSpinner() { 
		List<String> list = new ArrayList<String>();
		list.add("maliciousIP");
		list.add("maliciousStringPattern");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		
	  }

	 public void tableAdd(View view) {
		 
		 TableLayout stk = (TableLayout) view.findViewById(R.id.table_main);
		 
		 /// if already exists ///
		 if (patternList.contains(pattern.getText().toString()))
		 	return;
		 if ((pattern.getText().toString().equalsIgnoreCase("")) || (pattern.getText().toString().equalsIgnoreCase(" ")))
			 return;
			 
		 if (!tableIsReady) {
		          
	        TableRow row = new TableRow(getActivity());
	        TextView tv0 = new TextView(getActivity());
	        tv0.setText(" PID ");
	        tv0.setTextColor(Color.RED);
	        row.addView(tv0);
	        TextView tv1 = new TextView(getActivity());
	        tv1.setText(" Malicious Pattern ");
	        tv1.setTextColor(Color.RED);
	        row.addView(tv1);
	        TextView tv2 = new TextView(getActivity());
	        tv2.setText(" Type ");
	        tv2.setTextColor(Color.RED);
	        row.addView(tv2);
	        row.setPadding(0, 0, 0, 8);
	        stk.addView(row);
	        
	        TableRow tbrow = new TableRow(getActivity());
	        TextView tv00 = new TextView(getActivity());
	        tv00.setText(" "+i);
	        tv00.setTextColor(Color.BLACK);
	        tbrow.addView(tv00);
	        TextView tv01 = new TextView(getActivity());
	        tv01.setText(pattern.getText().toString());
	        tv01.setTextColor(Color.BLACK);
	        tbrow.addView(tv01);
	        TextView tv02 = new TextView(getActivity());
	        tv02.setText(spinner.getSelectedItem().toString());
	        tv02.setTextColor(Color.BLACK);
	        tbrow.addView(tv02);
	        stk.addView(tbrow);
	        i++;
	        tableIsReady = true;
		 }
		 
		 else {
			 
			 TableRow tbrow0 = new TableRow(getActivity());
	         TextView tv0 = new TextView(getActivity());
	         tv0.setText(" "+i);
	         tv0.setTextColor(Color.BLACK);
	         tbrow0.addView(tv0);
	         TextView tv1 = new TextView(getActivity());
	         tv1.setText(pattern.getText().toString());
	         tv1.setTextColor(Color.BLACK);
	         tbrow0.addView(tv1);
	         TextView tv2 = new TextView(getActivity());
	         tv2.setText(spinner.getSelectedItem().toString());
	         tv2.setTextColor(Color.BLACK);
	         tbrow0.addView(tv2);
	         stk.addView(tbrow0);
			 i++;
		 }
       
	 }
	 

	 public void tableAddFromWeb(View view, String patternFromWeb) {
		 
		 TableLayout stk = (TableLayout) view.findViewById(R.id.table_main);
		 
		 if (!tableIsReady) {
		          
	        TableRow row = new TableRow(getActivity());
	        TextView tv0 = new TextView(getActivity());
	        tv0.setText(" PID ");
	        tv0.setTextColor(Color.RED);
	        row.addView(tv0);
	        TextView tv1 = new TextView(getActivity());
	        tv1.setText(" Malicious Pattern ");
	        tv1.setTextColor(Color.RED);
	        row.addView(tv1);
	        TextView tv2 = new TextView(getActivity());
	        tv2.setText(" Type ");
	        tv2.setTextColor(Color.RED);
	        row.addView(tv2);
	        row.setPadding(0, 0, 0, 8);
	        stk.addView(row);
	        
	        TableRow tbrow = new TableRow(getActivity());
	        TextView tv00 = new TextView(getActivity());
	        tv00.setText(" "+i);
	        tv00.setTextColor(Color.BLACK);
	        tbrow.addView(tv00);
	        TextView tv01 = new TextView(getActivity());
	        tv01.setText(patternFromWeb);
//	        tv01.setText(pattern.getText().toString());
	        tv01.setTextColor(Color.BLACK);
	        tbrow.addView(tv01);
	        TextView tv02 = new TextView(getActivity());
	        tv02.setText("pattern (from Server)");
	        tv02.setTextColor(Color.BLACK);
	        tbrow.addView(tv02);
	        stk.addView(tbrow);
	        i++;
	        tableIsReady = true;
		 }
		 
		 else {
			 
			 TableRow tbrow0 = new TableRow(getActivity());
	         TextView tv0 = new TextView(getActivity());
	         tv0.setText(" "+i);
	         tv0.setTextColor(Color.BLACK);
	         tbrow0.addView(tv0);
	         TextView tv1 = new TextView(getActivity());
	         tv1.setText(patternFromWeb);
	         tv1.setTextColor(Color.BLACK);
	         tbrow0.addView(tv1);
	         TextView tv2 = new TextView(getActivity());
	         tv2.setText("pattern (from Server)");
	         tv2.setTextColor(Color.BLACK);
	         tbrow0.addView(tv2);
	         stk.addView(tbrow0);
			 i++;
		 }
        
	 }

	 
	private class InsertMaliciousAsyncTask extends AsyncTask<String, Integer, Void> {
		private boolean alreadyExists;
		@Override
		protected Void doInBackground(String... params) {
			
			/// adding to privateList new patterns
			if(params[0] == null) {
				if (!patternList.contains(params[1])) {
					patternList.add(params[1]);
					alreadyExists = false;
				}
				else {//if exists 
					alreadyExists = true;
					return null;
				}
			}
			else if(params[1] == null) {
				if (!patternList.contains(params[0])){
					patternList.add(params[0]);
					alreadyExists = false;
				}
				else {//if exists
					alreadyExists = true;
					return null;
				}
			}

			if(isNetworkAvailable())
				WebServiceConnector.insertMaliciousPatterns(MainActivity.username,MainActivity.password,params[0],params[1]); 
			else { 
				if(params[0] == null)
					MainActivity.database.pendingIntertionsOP.addPendingInsertion("stringPattern",params[1]);
				else if(params[1] == null)
					MainActivity.database.pendingIntertionsOP.addPendingInsertion("ip",params[0]);
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			if(alreadyExists)
                Toast.makeText(getActivity(), "Malicious Pattern already exists ", Toast.LENGTH_SHORT).show();
			else
                Toast.makeText(getActivity(), "Malicious Pattern inserted successfully", Toast.LENGTH_SHORT).show();
		}
		private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	}
	
	
	
	
	
	private class RetrieveMaliciousAsyncTask extends AsyncTask<String, Integer, List<String>> {

		@Override
		protected List<String> doInBackground(String... params) {
			List<String> tempList = new ArrayList<String>();
			if(isNetworkAvailable())
				tempList = getMaliciousFromWeb();
			
			return tempList;
		}
		@Override
		protected void onPostExecute(List<String> result) {
			if (!result.isEmpty()) {
				for (int i=0; i<result.size(); i++) {
					tableAddFromWeb(fragmentView, result.get(i));
				}
			}
			
		}
		private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	}
	
	
	private List<String> getMaliciousFromWeb() {
		
		String maliciousString = WebServiceConnector.retrieveMaliciousPatterns(MainActivity.username,MainActivity.password);
		List<String> tempList = new ArrayList<String>();

		//Log.e("", "///string just arrived: " + maliciousString);

		
		/// this is the default return value server /////
		/// sends us when there are not any patterns ///
		if(maliciousString != null) {
			if (maliciousString.equalsIgnoreCase("anyType{}"))
				return tempList;
		}
		
		final String[] tokens = maliciousString.split(Pattern.quote("+"));			

		//Log.e("", "///+++MaliciousString = " + maliciousString);
		for (int j=0; j<tokens.length;j++) {
			if (!patternList.contains(tokens[j])) {
				//Log.e("", "/// inserting :" + tokens[j]);
				patternList.add(tokens[j]);
				tempList.add(tokens[j]);
			}
		}
		
		return tempList;			
	}

	
	
	
}