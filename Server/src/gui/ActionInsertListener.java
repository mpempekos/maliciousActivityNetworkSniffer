package gui;

import dbObjects.MaliciousPatternsEntry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import nodes.OnlineNodesMemory;
import db.DBConnector;

public class ActionInsertListener implements ActionListener {

	
	@Override
	public void actionPerformed(ActionEvent e) {

		/// getting user selections
		String type = new String(ActionPanel.dropDown.getSelectedItem().toString());
		String pattern = new String(ActionPanel.insertionField.getText());

		/// if user did not select anything... lets troll him
		if (pattern.equalsIgnoreCase("")) {
			final ImageIcon icon = new ImageIcon("images/serious.jpg");
			JOptionPane.showMessageDialog(null,
					"You have to type something before adding it...", "Seriously now",
					JOptionPane.INFORMATION_MESSAGE, icon);
			return;
		}
		
		DBConnector.insertMaliciousPattern(pattern, type);
	
		if (type.equalsIgnoreCase("IP"))
			OnlineNodesMemory.addMaliciousIp(pattern);
		else
			OnlineNodesMemory.addMaliciousStringPattern(pattern);
		
		/// updating table which shows malicious patterns
		ArrayList <MaliciousPatternsEntry> maliciousList = DBConnector.getMaliciousPatternsForGui(); 
		ActionPanel.tableModelAction.setRowCount(0);//reset table to ZERO rows
		int i;
		for (i=0; i<maliciousList.size(); i++) {
			MaliciousPatternsEntry mal = maliciousList.get(i);
			
			Object[] tableRow = {
					mal.getMaliciousPatternID(),
					mal.getMaliciousPattern(),
					mal.getMaliciousTime()
					
				};
			ActionPanel.tableModelAction.addRow(tableRow);
		}

		
		/// We'll read from report queue and update with the same
		/// message action panel and statistics panel
		String newReport = new String(StatisticsPanel.textArea.getText() + ReportTerminalOperations.getReport());

		StatisticsPanel.textArea.setText(newReport);
		ActionPanel.textArea.setText(newReport);

	}	
	public static void addFromWebService() {
		ArrayList <MaliciousPatternsEntry> maliciousList = DBConnector.getMaliciousPatternsForGui(); 
		ActionPanel.tableModelAction.setRowCount(0);//reset table to ZERO rows
		int i;
		for (i=0; i<maliciousList.size(); i++) {
			MaliciousPatternsEntry mal = maliciousList.get(i);
			
			Object[] tableRow = {
					mal.getMaliciousPatternID(),
					mal.getMaliciousPattern(),
					mal.getMaliciousTime()
					
				};
			ActionPanel.tableModelAction.addRow(tableRow);
		}
	}
}


