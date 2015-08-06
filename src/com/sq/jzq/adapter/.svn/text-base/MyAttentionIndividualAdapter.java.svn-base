package com.sq.jzq.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sq.jzq.R;
import com.sq.jzq.bean.MyAttentionIndividualResult.IndividualCompany;

public class MyAttentionIndividualAdapter extends BaseAdapter {

	private List<IndividualCompany> dataSet;
	private LayoutInflater mInflater;
	private Context context;

	public MyAttentionIndividualAdapter() {

	}

	public void getDate(Context context, List<IndividualCompany> dataSet) {
		this.dataSet = dataSet;
		this.context = context;
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
		return Long.parseLong(dataSet.get(location).Id);
	}

	@Override
	public View getView(final int location, View convertView, ViewGroup parent) {

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

		// holder.title.setText(dataSet.get(location).ZN);
		holder.cN.setText(dataSet.get(location).N);
		holder.date.setText(dataSet.get(location).Rt);
		holder.salary.setText(dataSet.get(location).S + "元/");
		holder.unit.setText(dataSet.get(location).U);
		holder.loca.setText(dataSet.get(location).A);
		return convertView;
	}

	private class ViewHolder {
		public TextView title, cN, date, salary, unit, loca;
	}

}
