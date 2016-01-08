package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class GUI extends JFrame implements Runnable {
		
	public GUI() {
		
		setTitle("Adder - admin window");
		setSize(900, 580);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public void buildGui() {
		JTabbedPane tabsArea = new JTabbedPane(); // creates tab area
		getContentPane().add(tabsArea); // add tabs to frame
		tabsArea.setFont(new Font("Verdana", 1, 15));
		tabsArea.setBackground(Color.WHITE);
		
		tabsArea.addTab("Statistics", new StatisticsPanel());
		tabsArea.addTab("Actions", new ActionPanel());				
		
		/// adding icon in each tab
		ImageIcon statisticsIcon = new ImageIcon("images/statistics.png");
		ImageIcon actionsIcon = new ImageIcon("images/actions.png");
		tabsArea.setIconAt(0, statisticsIcon);
		tabsArea.setIconAt(1, actionsIcon);
		
		/// By closing the window, we must also terminate the GUIrefresher
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				GUIrefresher.setAliveFalse();
			}
		});
		
	}
	
	
	
	
	@Override
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {					
					buildGui();

					//creating the statistics refresher thread
					GUIrefresher refresher = new GUIrefresher();
					Thread refresherThread = new Thread(refresher);
					refresherThread.start();
					

			
			}
		});
	}
	
	
}