package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


public class StatisticsPanel extends JPanel{

//	JPanel mainPanel;
//	JPanel upperPanel;
//	JPanel reportPanel;
	
	//static entities so that the listeners can modify them
	protected static JTextField searchField; 
	protected static JButton searchButton;
	protected static JComboBox dropDown;
	public static JTextArea textArea;

	static String[] header = {
			"<html><b><font size='3'>NodeID</font></b></html>",
			"<html><b><font size='3'>InterfaceName</font></b></html>",
			"<html><b><font size='3'>InterfaceIP</font></b></html>",
			"<html><b><font size='3'>Pattern</font></b></html>",
			"<html><b><font size='3'>Frequency</font></b></html>" };
	protected static DefaultTableModel tableModel;
	protected static JTable table;
	protected static JScrollPane scrollPane;
	protected ActionListener listener = new StatisticsSearchListener();
	protected MouseListener mouseListener = new StatisticsResultsListener();
	
	
	public StatisticsPanel() {
		initUI();
	}
	
	
	private void initUI() {
		add(makeMainPanel());
	}


	
	//////////////////////////////////////////////////////////////////
	//// Creating Main panel which contains all panels ///////////////
	//////////////////////////////////////////////////////////////////

	private JPanel makeMainPanel() {
		JPanel panel = new JPanel();
		GridBagLayout panelLayout = new GridBagLayout();
		panel.setLayout(panelLayout);
		GridBagConstraints constraints = new GridBagConstraints();

		/// setting search panel layout
		constraints.insets = new Insets(40, 15, 10, 10);
		constraints.weightx = 0.7;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(makeSearchPanel(), constraints);
		
		
		/// setting results panel layout
		constraints.insets = new Insets(20, 5, 0, 10);
		constraints.weightx = 0.3;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridheight = 2;
		panel.add(makeResultPanel(), constraints);
		
		
		/// setting report panel layout
        constraints.insets = new Insets(25, 15, 5, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(makeReportPanel(), constraints);
        
		return panel;
	}
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//// Creating Search panel //////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	
	private JPanel makeSearchPanel() {
		JPanel panel = new JPanel();
		

		GridBagLayout panelLayout = new GridBagLayout();
		panel.setLayout(panelLayout);
		GridBagConstraints constraints = new GridBagConstraints();
		
		String[] options = { "(all)", "nodeID", "InterfaceIP","InterfaceName", "maliciousPattern"};
		dropDown = new JComboBox(options);
		dropDown.setSelectedIndex(0);
		
		JTextField searchField = new JTextField(13);
		searchField.setLayout(new BorderLayout());
		
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED)," Search ");
		panel.setBorder(title);
		
		constraints.insets = new Insets(20, 10, 5, 10);
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(new JLabel("<html><b><font size='4'>Select category to  get stats: "), constraints);
		
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridx = 0;
		constraints.gridy = 1;		
		panel.add(dropDown, constraints);
		
		constraints.insets = new Insets(20, 10, 10, 10);
		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(new JLabel("<html><b><font size='4'>Filter results (optional) "), constraints);

		constraints.insets = new Insets(0, 10, 10, 10);
		constraints.gridx = 0;
		constraints.gridy = 3;
		panel.add(makeSearchField(), constraints);
		
		constraints.insets = new Insets(20, 10, 20, 10);
		constraints.gridx = 0;
		constraints.gridy = 4;
		panel.add(makeSearchButton(), constraints);
		panel.add(new JPanel());

		return panel;
	}
	

	private JPanel makeSearchButton() {
		JPanel panel = new JPanel();
		ImageIcon settingsIcon = new ImageIcon("images/search.png");

		searchButton = new JButton("Search");
		searchButton.setToolTipText("click to Search or Refresh!");
		searchButton.setIcon(settingsIcon);
		searchButton.addActionListener(listener);
		panel.add(searchButton);
		return panel;
	}


	private JPanel makeSearchField() {
		JPanel panel = new JPanel();		
		
		searchField = new JTextField(13);
		searchField.setToolTipText("Leave empty to get All statistics!");
		searchField.setLayout(new BorderLayout());
		searchField.addActionListener(listener);
		panel.add(searchField);
		return panel;
	}

	

	/////////////////////////////////////////////////////////////////
	//// Creating Results panel /////////////////////////////////////
	/////////////////////////////////////////////////////////////////

	private JPanel makeResultPanel() {
		JPanel panel = new JPanel();
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED)," Results ");

		panel.setBorder(title);
		panel.add(makeResultTable());
		return panel;
	}

	
	private JScrollPane makeResultTable() {		
		tableModel = new DefaultTableModel(null, header) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.addMouseListener(mouseListener);
		scrollPane.setPreferredSize(new Dimension(550, 300));
		return scrollPane;
	}
	
	

	//////////////////////////////////////////////////////////////////////////
	//// Creating Report panel ///////////////////////////////////////////////
	/// (Different instance from Action report teminal ///////////////////////
	/// (REASON: we may want to report something only in Statistics panel) ///
	//////////////////////////////////////////////////////////////////////////

	public JPanel makeReportPanel() {

		JPanel panel = new JPanel();
		textArea = new JTextArea(5,70);
		StatisticsPanel.textArea.setMaximumSize(new Dimension(5, 70));
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createEtchedBorder());
		textArea.setAutoscrolls(true);
		textArea.setText("> Adder program is ready!");
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.GREEN);
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED)," Report Panel ");
		panel.setBorder(title);		
		panel.add(scrollPane);

		return panel;
	}
	
	
}

