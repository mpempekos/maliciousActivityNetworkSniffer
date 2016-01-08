package exchangeableObjects;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AvailableNodes {
	@XmlElement
	//private String[] nodes;
	String nodes;
	/*
	public AvailableNodes() {
		// TODO Auto-generated constructor stub
		nodes = new String[64];
	}
	public AvailableNodes(int size) {
		nodes = new String[size];
	}
	*/
    public List<String> getNodes() {
    	String[] nodesArray = nodes.split("\\+");
    	return Arrays.asList(nodesArray);
    }
    /*
	public void print() {
		int i;
		for(i = 0 ; i<nodes.length ;i++)
			System.out.println(nodes[i]);
	}
	public void setNodes(String[] nodes) {
		this.nodes = nodes;
	}
	*/
}