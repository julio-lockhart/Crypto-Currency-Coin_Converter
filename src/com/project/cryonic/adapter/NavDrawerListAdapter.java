/**
 * Class that holds all items for each item in nav drawer
 */

package com.project.cryonic.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.cryonic.R;
import com.project.cryonic.model.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter {
	
	// Variable declaration
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	// Constructor
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	/**
	 * Return size of nav drawer
	 */
	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	/**
	 * Return the item user pressed on nav drawer based on position
	 */
	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	/**
	 * Return the position user pressed on nav drawer
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Populate all the items in the specific nav item
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
         
        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
        txtTitle.setText(navDrawerItems.get(position).getTitle());
        
        // displaying count
        // check whether it set visible or not
        if(navDrawerItems.get(position).getCounterVisibility()){
        	txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
        	// hide the counter view
        	txtCount.setVisibility(View.GONE);
        }
        
        return convertView;
	}

}
