package com.sq.jzq.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sq.jzq.R;
import com.sq.jzq.bean.MyEvaluationResult.MyEvaluation;

public class MyEvaluationAdapter extends BaseAdapter {

	private List<MyEvaluation> dataSet;
	private LayoutInflater mInflater;
	private Context context;

	public MyEvaluationAdapter() {

	}

	public void getDate(Context context, List<MyEvaluation> dataSet) {
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
		return Long.parseLong(dataSet.get(location).ID);
	}

	@Override
	public View getView(final int location, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.listview_my_evaluation_item, null);
			holder.tv_evaluation_title = (TextView) convertView
					.findViewById(R.id.tv_evaluation_title);
			holder.sqjl_item_title = (TextView) convertView
					.findViewById(R.id.sqjl_item_title);
			holder.tv_my_evaluation_time = (TextView) convertView
					.findViewById(R.id.tv_my_evaluation_time);
			holder.sqjl_item_loca = (TextView) convertView
					.findViewById(R.id.sqjl_item_loca);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_evaluation_title.setText("已对" + dataSet.get(location).CE);
		holder.sqjl_item_loca.setText("职位：" + dataSet.get(location).T);
		holder.sqjl_item_loca.setText("职位：" + dataSet.get(location).TS);
		holder.sqjl_item_title.setText("    " + dataSet.get(location).CS);

		return convertView;
	}

	private class ViewHolder {
		private TextView tv_evaluation_title, sqjl_item_loca,
				tv_my_evaluation_time, sqjl_item_title;
	}

}
