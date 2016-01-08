package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import db.DBConnector;
import dbObjects.MaliciousPatternsEntry;

public class ActionRemoveListener implements ActionListener {

	
	@Override
	public void actionPerformed(ActionEvent e) {

		int selectedRow = ActionPanel.table.getSelectedRow();
		int selectedColumn = ActionPanel.table.getSelectedColumn();
		
		
		/// if user did not select anything... lets troll him
		if ((selectedRow<0) || (selectedColumn<0)) {
			final ImageIcon icon1 = new ImageIcon("images/why.png");
			JOptionPane.showMessageDialog(null,
					"Select something to remove!", "Why u no",
					JOptionPane.INFORMATION_MESSAGE, icon1);
			return;
		}
		
		int patternID = (int) ActionPanel.table.getModel().getValueAt(ActionPanel.table.getSelectedRow(), 0);
		
		// deleting selected pattern
		DBConnector.deleteMaliciousPattern(patternID);
		
		int i;

		/// updating table which shows malicious patterns
		ArrayList <MaliciousPatternsEntry> maliciousList = DBConnector.getMaliciousPatternsForGui(); 
		ActionPanel.tableModelAction.setRowCount(0);//reset table to ZERO rows

		for (i=0; i<maliciousList.size(); i++) {
			MaliciousPatternsEntry mal = maliciousList.get(i);
			
			Object[] tableRow = {
					mal.getMaliciousPatternID(),
					mal.getMaliciousPattern(),
					mal.getMaliciousTime()
					
				};
			ActionPanel.tableModelAction.addRow(tableRow);
		}		
		
		final ImageIcon icon = new ImageIcon("images/success.png");

		JOptionPane.showMessageDialog(null,
				"pattern with ID: "+patternID+" has been successfully removed!", "Report",
				JOptionPane.INFORMATION_MESSAGE, icon);

		/// We'll read from report queue and update with the same
		/// message action panel and statistics panel
		String newReport = new String(StatisticsPanel.textArea.getText() + ReportTerminalOperations.getReport());
		StatisticsPanel.textArea.setText(newReport);
		ActionPanel.textArea.setText(newReport);

		
	}	
}


