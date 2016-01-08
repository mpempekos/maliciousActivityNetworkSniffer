package awesomeapp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class AvailableNodes implements KvmSerializable {
	String nodes;
	public AvailableNodes(String nodes) {
		// TODO Auto-generated constructor stub
		this.nodes = nodes;
	}
	@Override
	public String getInnerText() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return nodes;
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		if(arg0 == 0)
			arg2.type = PropertyInfo.VECTOR_CLASS;
        	arg2.name = "nodes"; 		
	}
	@Override
	public void setInnerText(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg0 != 0)
			return;
		else
			nodes = (String) arg1;
	}

}
	