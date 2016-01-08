package main;
import nodes.NodesControlThread;
import nodes.OnlineNodesMemory;
import gui.GUI;
import server.Publish;
import db.DBCreation;

public class Main {
	public static void main(String[] args) 
	{
		System.out.println("Setting Server up...\n");
		DBCreation.createAdderDB();
		OnlineNodesMemory.getInstance();
		Publish.publishAdder();
		NodesControlThread nodesThread = new NodesControlThread();
		nodesThread.start();
		GUI window = new GUI();
		Thread guiThread = new Thread(window);
		guiThread.start();
	}
}
