package awesomeapp;

public class Device {
	
	 String name = null;
	 boolean selected = false;
	  
	 public Device(String name, boolean selected) {
	  super();
	  this.name = name;
	  this.selected = selected;
	 }
	 
	 public String getName() {
	  return name;
	 }
	 public void setName(String name) {
	  this.name = name;
	 }
	 
	 public boolean isSelected() {
	  return selected;
	 }
	 public void setSelected(boolean selected) {
	  this.selected = selected;
	 }

}
