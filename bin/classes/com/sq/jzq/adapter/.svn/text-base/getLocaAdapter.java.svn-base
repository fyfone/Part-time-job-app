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

public class getLocaAdapter extends BaseAdapter {

	private List<JobType> dataSet;
	private LayoutInflater mInflater;


	public getLocaAdapter() {
	}

	public void bindData(Context context, List<JobType> dataSet) {
		this.dataSet = dataSet;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			convertView = mInflater.inflate(R.layout.loca_item, null);
			holder.title = (TextView)convertView.findViewById(R.id.lisla_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(dataSet.get(location).N);

		return convertView;
	}


	public class ViewHolder {
		public TextView title;
	}

}
