package com.uint.envtracking.ui.widget;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.uint.envtracking.util.SensorUtils;

public class SensorsListAdapter extends BaseAdapter {
	private SensorManager mSensorManager;
	private Context mContext;
	private List<Sensor> mSensorList;
	private LayoutInflater mLayoutInflater;
	
	public SensorsListAdapter(Context context){
		mContext = context;
		mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		mSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//		mSensorList = new ArrayList<Sensor>();
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return mSensorList.size();
	}

	@Override
	public Object getItem(int position) {
		return mSensorList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Sensor sensor = mSensorList.get(position);
		View v = convertView;
		
		if(v == null){
			v = mLayoutInflater.inflate(android.R.layout.simple_list_item_multiple_choice, null);
		}
		
		if(sensor != null){
			CheckedTextView textView = (CheckedTextView)v.findViewById(android.R.id.text1);
			textView.setText(SensorUtils.typeToName(sensor.getType()));
			textView.toggle();
		}
		
		return v;
	}

}
