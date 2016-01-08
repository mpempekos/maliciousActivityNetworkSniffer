package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsSearchListener implements ActionListener {

	
	@Override
	public void actionPerformed(ActionEvent e) {

		boolean terminalReport = true;
		GUIrefresher.showStatistics(terminalReport);

		// read/update terminal panel
		String newReport = new String(StatisticsPanel.textArea.getText() + ReportTerminalOperations.getReport());

		// updating both statistics/action terminal with same message
		StatisticsPanel.textArea.setText(newReport);
		ActionPanel.textArea.setText(newReport);
		
	}
	
}


