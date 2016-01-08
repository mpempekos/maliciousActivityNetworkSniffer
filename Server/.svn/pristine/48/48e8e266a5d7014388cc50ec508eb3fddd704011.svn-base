package gui;

import dbObjects.ClientsEntry;

import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import db.DBConnector;

public class StatisticsResultsListener implements MouseListener {
		
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// if user double clicks a row
		if (e.getClickCount() == 2) {

			// we take the selected row
			String nodeID = (String) StatisticsPanel.table.getModel().getValueAt(StatisticsPanel.table.getSelectedRow(), 0);
			
			// and create a popup window with infos of this node

			ClientsEntry myNode = DBConnector.getNodeInfo(nodeID);
			if (myNode.getType().equalsIgnoreCase("")) {

				// if for some reason this node is not registered
				// we'll only show NodeID, maliciousHits
				// (if everything works correctly this will never happen!)
				JOptionPane.showMessageDialog(null,
						"nodeID: "+nodeID+"\nMalicious hits: "+myNode.getNumOfMalicious()+"\n\nSeems like this node is not Registered!\n", "Node info",
						JOptionPane.INFORMATION_MESSAGE);				
			}
			else {
				JOptionPane.showMessageDialog(null,
						"nodeID: "+nodeID+"\nType: "+myNode.getType()+"\nLast Request: "+myNode.getLastReport()+"\nMalicious hits: "+myNode.getNumOfMalicious(), "Node info",
						JOptionPane.INFORMATION_MESSAGE);
	

			}
		}	
		
		// updating both statistics/action terminal with same message
		String newReport = new String(StatisticsPanel.textArea.getText() + ReportTerminalOperations.getReport());
		StatisticsPanel.textArea.setText(newReport);
		ActionPanel.textArea.setText(newReport);

	}
	
	
	// some methods must be overrided...
	@Override	public void mousePressed(java.awt.event.MouseEvent e) {	}
	@Override	public void mouseEntered(java.awt.event.MouseEvent e) {	}
	@Override	public void mouseReleased(java.awt.event.MouseEvent e) { }
	@Override	public void mouseExited(java.awt.event.MouseEvent e) { }

}

