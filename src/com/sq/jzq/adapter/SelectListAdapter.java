package com.sq.jzq.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sq.jzq.R;
import com.sq.jzq.bean.JobTypeResult.JobType;

public class SelectListAdapter extends BaseAdapter {

	private List<JobType> dataSet;
	private LayoutInflater mInflater;
	private static HashMap<String, Boolean> isSelected; 


	public SelectListAdapter() {
	}

	public void bindData(Context context, List<JobType> dataSet) {
		Log.i("Response", "dataSet="+dataSet.size());
		this.dataSet = dataSet;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		isSelected = new HashMap<String, Boolean>();
		for(int i=0; i<dataSet.size(); i++) {
			isSelected.put(dataSet.get(i).ID, false);
			
		}
	}

	@Override
	public int getCount() {
		return dataSet.size();
	}

	@Override
	public Object getItem(int location) {
		return dataSet.get(location);
	}

	@Override
	public long getItemId(int location) {
		return Long.parseLong(dataSet.get(location).ID);
	}

	@Override
	public View getView(int location, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_select_item, null);
			holder.title = (TextView)convertView.findViewById(R.id.lisla_text);
			holder.select = (CheckBox)convertView.findViewById(R.id.lisla_chk);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(dataSet.get(location).N);
		holder.select.setTag(dataSet.get(location).ID);
		holder.select.setChecked(isSelected.get(dataSet.get(location).ID));

		return convertView;
	}



	public static HashMap<String, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<String, Boolean> isSelected) {
		SelectListAdapter.isSelected = isSelected;
	}



	public class ViewHolder {
		public TextView title;
		public CheckBox select;
	}

}
