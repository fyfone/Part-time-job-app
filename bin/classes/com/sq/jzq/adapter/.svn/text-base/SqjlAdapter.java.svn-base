package com.sq.jzq.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.DeleteItemResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.bean.ApplyRecordResult.ApplyRecord;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.DeleteFactory;

public class SqjlAdapter extends BaseAdapter {

	private List<ApplyRecord> dataSet;
	private LayoutInflater mInflater;
	private Context context;

	public SqjlAdapter() {

	}

	public void getDate(Context context, List<ApplyRecord> dataSet) {
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
		return Long.parseLong(dataSet.get(location).Jid);
	}

	@Override
	public View getView(final int location, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_sqjl_item, null);
			holder.sqjl_item_time = (TextView) convertView
					.findViewById(R.id.sqjl_item_time);
			holder.sqjl_item_title = (TextView) convertView
					.findViewById(R.id.sqjl_item_title);
			holder.sqjl_item_cn = (TextView) convertView
					.findViewById(R.id.sqjl_item_cn);
			holder.sqjl_item_date = (TextView) convertView
					.findViewById(R.id.sqjl_item_date);
			holder.sqjl_item_salary = (TextView) convertView
					.findViewById(R.id.sqjl_item_salary);
			holder.sqjl_item_unit = (TextView) convertView
					.findViewById(R.id.sqjl_item_unit);
			holder.sqjl_item_loca = (TextView) convertView
					.findViewById(R.id.sqjl_item_loca);
			holder.sqjl_del = (Button) convertView.findViewById(R.id.sqjl_del);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.sqjl_item_title.setText(dataSet.get(location).T);
		holder.sqjl_item_cn.setText(dataSet.get(location).Cn);
		holder.sqjl_item_date.setText(dataSet.get(location).Rt);
		holder.sqjl_item_time.setText(dataSet.get(location).ST);

		holder.sqjl_item_loca.setText(dataSet.get(location).A);
		holder.sqjl_item_salary.setText(dataSet.get(location).S);
		holder.sqjl_item_unit.setText("元/" + dataSet.get(location).U);

		holder.sqjl_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DeleteFactory deleteFactory = new DeleteFactory() {

					@Override
					public void determineButton() {
						// 获取企业列表
						Map<String, String> params = new HashMap<String, String>();
						params.put(Globals.WS_POST_KEY,
								"{\"Ac\":\"DJABU\",\"Para\":{\"Jid\":\""
										+ dataSet.get(location).Jid
										+ "\",\"Sid\":\"" + User.sessionId
										+ "\"}}");
						new VolleyUtil() {

							public void analysisData(String response) {
								DeleteItemResult s = GsonUtils.json2bean(
										response, DeleteItemResult.class);
								if (s == null || !(s.Stu == 1)) {
									Toast.makeText(context, Globals.SER_ERROR,
											Globals.TOAST_SHORT).show();
								} else {
									if (s.Rst.Scd == 1) {
										dataSet.remove(location);
										Toast.makeText(context, s.Rst.Msg, 0)
												.show();
										notifyDataSetChanged();

									} else {
										Toast.makeText(context, "删除失败", 0)
												.show();
									}

								}

							}

						}.volleyStringRequestPost(context, params);

					}
				}.deleteDialog(context, "是否删除", "确定", "取消");

			}
		});

		return convertView;
	}

	private class ViewHolder {
		private TextView sqjl_item_time, sqjl_item_title, sqjl_item_cn,
				sqjl_item_date, sqjl_item_salary, sqjl_item_unit,
				sqjl_item_loca;

		private Button sqjl_del;
	}

}
