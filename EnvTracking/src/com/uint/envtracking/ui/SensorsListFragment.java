package com.uint.envtracking.ui;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.uint.envtracking.R;
import com.uint.envtracking.sensors.SensorConstants;
import com.uint.envtracking.ui.widget.SensorsListAdapter;
import com.uint.envtracking.util.SensorUtils;

public class SensorsListFragment extends ListFragment implements OnClickListener{
	private Button mButtonSave;
	private Button mButtonCancel;
	private SensorsListAdapter mListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		View v = inflater.inflate(R.layout.fragment_sensor_list, container);
		
		mButtonSave = (Button)v.findViewById(R.id.btn_save);
		mButtonCancel = (Button)v.findViewById(R.id.btn_cancel);
		
		mButtonSave.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);
		
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mListAdapter = new SensorsListAdapter(getActivity());
		setListAdapter(mListAdapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		loadSensorListSet();
				
		mButtonSave.setEnabled(mListAdapter.getCount() != 0);
	}

	

	private void loadSensorListSet() {
		SharedPreferences appPrefs = SensorUtils.getSharedPreference(getActivity());
		
		for (int i = 0; i < mListAdapter.getCount(); i++) {
			Sensor sensor = (Sensor) mListAdapter.getItem(i);
			
			String sensorPreferenceKey = SensorConstants.PREF_SENSOR_PREFIX + sensor.getType();
			boolean sensorStatus = appPrefs.getBoolean(sensorPreferenceKey, true);
			getListView().setItemChecked(i, sensorStatus);
			
		}
	}
	
	private void saveSensorListSet() {
		SharedPreferences appPrefs = SensorUtils.getSharedPreference(getActivity());
		Editor editor = appPrefs.edit();
		
		SparseBooleanArray checkedPositions = getListView().getCheckedItemPositions();
		
		for (int i = 0; i < checkedPositions.size(); i++) {
			Sensor sensor = (Sensor) mListAdapter.getItem(i);
			String sensorPreferenceKey = SensorConstants.PREF_SENSOR_PREFIX + sensor.getType();
			boolean sensorStatus = checkedPositions.get(i);
			
			editor.putBoolean(sensorPreferenceKey, sensorStatus);
		}

		editor.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			saveSensorListSet();
			getActivity().finish();
			break;
		case R.id.btn_cancel:
			getActivity().finish();
			break;
			
		default:
			break;
		}
		
	}
	
	

}
