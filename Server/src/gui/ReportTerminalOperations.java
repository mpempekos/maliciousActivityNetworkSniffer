package gui;

import java.util.ArrayList;


public class ReportTerminalOperations {

	
	///////////////////////////////////////////////////////////////
	/// reports insertion in a queue //////////////////////////////
	/// and in every action the report terminal will be updated ///
	private static ArrayList<String> reportsQueue = new ArrayList<String>();

	public static synchronized void addReport(String report) {
	// ^ add reports mostly from db package
		reportsQueue.add(report);
	}
	
	public static synchronized String getReport() {
	// ^ get reports ONLY from listeners of this (gui) package
		StringBuilder report = new StringBuilder();
			for (int i=0; i<reportsQueue.size(); i++) {		
				report.append("\n");
				report.append(reportsQueue.get(i));								
			}
			reportsQueue.clear();			
		return report.toString();
	}

}



