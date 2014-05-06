/**
 * Class that initializes all of the elements of the nav item
 * which will be added via the adapter
 */

package com.project.cryonic.model;

public class NavDrawerItem {
	
	private String title;
	private int icon;
	private String symbol = "";
	// boolean to set visiblity of the counter
	private boolean isCounterVisible = false;
	private String image;
	
	public NavDrawerItem(String title, String symbol, boolean isCounterVisible, int icon) {
		this.title = title;
		this.symbol = symbol;
		this.isCounterVisible = isCounterVisible;
		this.icon = icon;
	}

	public String getTitle(){
		return this.title;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public String getCount(){
		return this.symbol;
	}
	
	public boolean getCounterVisibility(){
		return this.isCounterVisible;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
	public void setCount(String count){
		this.symbol = count;
	}
	
	public void setCounterVisibility(boolean isCounterVisible){
		this.isCounterVisible = isCounterVisible;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
