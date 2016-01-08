package nodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class NodesControlThread extends Thread {
	private long sleepTime;
	public NodesControlThread() {
		setSleepTime();
	}
	public void run() {
		while(true) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			OnlineNodesMemory.checkAndRemoveInactiveNodes();
		}
	}
	private void setSleepTime() {
		File propertiesFile = new File("config.properties");
		if(propertiesFile.exists()) {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(propertiesFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
			sleepTime = Long.parseLong(properties.getProperty("SLEEPTIME"));
		} 
		else {
			sleepTime =  100000;
		}
	}
}
