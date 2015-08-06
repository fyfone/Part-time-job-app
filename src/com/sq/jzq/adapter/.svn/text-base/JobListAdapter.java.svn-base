package com.sq.jzq.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sq.jzq.R;
import com.sq.jzq.bean.JobResult.Job;

public class JobListAdapter extends BaseAdapter {

	private List<Job> dataSet;
	private LayoutInflater mInflater;

	public JobListAdapter() {
	}

	public JobListAdapter(Context context, List<Job> dataSet) {
		this.dataSet = dataSet;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(Context context, List<Job> dataSet) {
		this.dataSet = dataSet;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount() {
		if(dataSet != null) {
			return dataSet.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int location) {
		return dataSet.get(location);
	}

	@Override
	public long getItemId(int location) {
		return Long.parseLong(dataSet.get(location).Id);
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int location, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_jzl_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_title);
			holder.cN = (TextView) convertView.findViewById(R.id.item_cn);
			holder.date = (TextView) convertView.findViewById(R.id.item_date);
			holder.salary = (TextView) convertView
					.findViewById(R.id.item_salary);
			holder.unit = (TextView) convertView.findViewById(R.id.item_unit);
			holder.loca = (TextView) convertView.findViewById(R.id.item_loca);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(dataSet.get(location).ZN);
		holder.cN.setText(dataSet.get(location).N);
		holder.date.setText(dataSet.get(location).Rt);
		holder.salary.setText(dataSet.get(location).S+"元/");
		holder.unit.setText(dataSet.get(location).U);
		holder.loca.setText(dataSet.get(location).A);

		return convertView;
	}

	public class ViewHolder {
		public TextView title;
		public TextView cN;
		public TextView date;
		public TextView salary;
		public TextView unit;
		public TextView loca;
	}

}
