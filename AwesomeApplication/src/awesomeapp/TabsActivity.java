package awesomeapp;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.awesomeapp.R;

@SuppressLint("NewApi")
public class TabsActivity extends FragmentActivity {
	public static ViewPager viewPager = null;
	public static boolean loggedIn;
	private Intent workerService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabs);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setId(R.id.pager);
		viewPager.setPageMargin(3);
		viewPager.setPageMarginDrawable(com.example.awesomeapp.R.color.my_holo_light_blue);
		FragmentManager fragmentManager = getSupportFragmentManager();
		Intent intent = getIntent();
		String userType = intent.getStringExtra("USER_TYPE");
		if(userType.equals("Admin")) 
			viewPager.setAdapter(new AdminTabsFragmentAdapter(fragmentManager,this));
		else 
			viewPager.setAdapter(new SimpleUserTabsFragmentAdapter(fragmentManager,this));
		workerService = new Intent(this,WorkerService.class);
		startService(workerService);
	}
	@Override
	protected void onDestroy() {
		stopService(workerService);
		super.onDestroy();
	}
}

class AdminTabsFragmentAdapter extends FragmentStatePagerAdapter {
	public static boolean itemSelected = false;
	private Activity activity;
	FragmentManager fm;
	public AdminTabsFragmentAdapter(FragmentManager fm,Activity activity) {
		super(fm);
		this.fm = fm;
		this.activity = activity;
	}
	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		if(arg0 == 0) {
			fragment = new StatisticsFragment();
		}
		else if(arg0 == 1) {
			fragment = new UsersStatisticsFragment();
		}
		else if(arg0 == 2) {
			fragment = new AddPatternFragment();
		}
		else if(arg0 == 3) {
			fragment = new RemoveTerminalFragment();
		}
		return fragment;
	}
	@Override
	public int getCount() {
		return 4;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		if(position == 0)
			return "Statistics";
		if(position == 1)
			return "User's Statistics";
		if(position == 2)
			return "Insert Pattern";
		if(position == 3)
			return "Delete Node";
		return null;
	}
	@Override
    public float getPageWidth(int position) {
        float nbPages;
        // Check the device
        if(isTablet(activity.getApplicationContext())) {
            nbPages = 2;     // 2 fragments / pages
        } else {
            nbPages = 1;     // 1 fragment / pages
        }
        return (1 / nbPages);
    }  
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}  
}

class SimpleUserTabsFragmentAdapter extends FragmentStatePagerAdapter {
	Activity activity;
	public SimpleUserTabsFragmentAdapter(FragmentManager fm,Activity activity) {
		super(fm);
		this.activity = activity;
	}
	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		if(arg0 == 0) {
			fragment = new StatisticsFragment();
		}
		else if(arg0 == 1) {
			fragment = new UsersStatisticsFragment();
		}
		return fragment;
	}
	@Override
	public int getCount() {
		return 2;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		if(position == 0)
			return "Statistics";
		if(position == 1)
			return "User's Statistics";
		return null;
	}
	@Override
    public float getPageWidth(int position) {
        float nbPages;
        if(isTablet(activity.getApplicationContext())) {
            nbPages = 2;     // 2 fragments / pages
        } else {
            nbPages = 1;     // 1 fragment / pages
        }
        return (1 / nbPages);
    }  
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
}