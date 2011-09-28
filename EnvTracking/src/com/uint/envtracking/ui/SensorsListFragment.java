package com.uint.envtracking.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.uint.envtracking.R;
import com.uint.envtracking.ui.widget.SensorsListAdapter;

public class SensorsListFragment extends ListFragment implements OnClickListener{
	private Button mButtonSave;
	private Button mButtonCancel;

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
		
		SensorsListAdapter adapter = new SensorsListAdapter(getActivity());
		setListAdapter(adapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				
		mButtonSave.setEnabled(adapter.getCount() != 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			
			break;
		case R.id.btn_cancel:
			getActivity().finish();
			break;
			
		default:
			break;
		}
		
	}
	
	

}
