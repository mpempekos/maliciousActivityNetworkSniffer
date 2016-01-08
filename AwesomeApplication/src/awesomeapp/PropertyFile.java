package awesomeapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.util.Log;


public class PropertyFile {

	
	public String getServerIP() {

		Properties props=new Properties();
		
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		if (inputStream == null) {
			Log.e("", "/// file: config.properties cannot be found!!!");
			return "0.0.0.0";
		}

		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String property = new String(props.getProperty("serverIP"));
		return property;
	}

	
	public String getAdminUsername() {

		Properties props=new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		if (inputStream == null) {
			Log.e("", "/// file: config.properties cannot be found!!!");
			return "admin";
		}
		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String property = new String(props.getProperty("adminUsername"));
		return property;
	}

	
	public String getAdminPassword() {

		Properties props=new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		if (inputStream == null) {
			Log.e("", "/// file: config.properties cannot be found!!!");
			return "1234";
		}
		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String property = new String(props.getProperty("adminPassword"));
		return property;
	}

	
	public String getRefreshTime() {

		Properties props=new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		if (inputStream == null) {
			Log.e("", "/// file: config.properties cannot be found!!!");
			return "2000";
		}
		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String property = new String(props.getProperty("refreshTime"));
		return property;
	}


}