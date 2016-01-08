package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import db.DBConnector;

public class GUIrefresher implements Runnable {
	protected static boolean isAlive;
	private int refreshTime ;
	
	public GUIrefresher() {
		setAliveTrue();
		setRefershTime();
	}
	private void setRefershTime() {
		File propertiesFile = new File("guiConfig.properties");
		if(propertiesFile.exists()) {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(propertiesFile));
				Integer rTime = new Integer(properties.getProperty("REFRESHTIME"));
				refreshTime = rTime.intValue();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
			refreshTime = 3000;
	}
	
	protected void setAliveTrue() {
		isAlive = true;
	}
	
	protected static void setAliveFalse() {
		isAlive = false;
	}
	
	/// print statistics to Statistics panel
	protected static synchronized void showStatistics(boolean toReport) {
		int i;
		/// getting user selected choices
		String category = new String(StatisticsPanel.dropDown.getSelectedItem().toString());
		String filter = new String(StatisticsPanel.searchField.getText());
		ArrayList< ArrayList<String> > statistics = DBConnector.getStatistics(category, filter, toReport);

		StatisticsPanel.tableModel.setRowCount(0);//reset table to ZERO rows
		
		for (i=0; i<statistics.size(); i++) {
			ArrayList <String> statisticRow = statistics.get(i);
			
			Object[] tableRow = {
					statisticRow.get(0), //nodeID
					statisticRow.get(1), //interfaceName
					statisticRow.get(2), //interfaceIP
					statisticRow.get(3), //maliciousPattern
					statisticRow.get(4), //frequency
					
				};
			StatisticsPanel.tableModel.addRow(tableRow);			
		}
		
	}
	
	
	@Override
	public void run() {
		boolean toReportToTerminal = false;
		

		while (isAlive) {
			try {
				Thread.sleep(refreshTime);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			showStatistics(toReportToTerminal);

		}
		
		
	}
	
}
