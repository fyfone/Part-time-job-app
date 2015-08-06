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
import com.sq.jzq.bean.MyMsgCircle.MyCircleResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.DeleteFactory;

public class MyMsgCircleAdapter extends BaseAdapter {

	private List<MyCircleResult> dataSet;
	private LayoutInflater mInflater;
	private Context context;

	public MyMsgCircleAdapter() {

	}

	public void getDate(Context context, List<MyCircleResult> dataSet) {
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
					R.layout.listview_my_message_circle, null);
			holder.tv_my_circle_time = (TextView) convertView
					.findViewById(R.id.tv_my_circle_time);
			holder.sqjl_item_loca = (TextView) convertView
					.findViewById(R.id.sqjl_item_loca);
			holder.tv_my_circle_content = (TextView) convertView
					.findViewById(R.id.tv_my_circle_content);

			holder.sqjl_del = (Button) convertView.findViewById(R.id.sqjl_del);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.sqjl_item_loca.setText("发  送  人：" + dataSet.get(location).SR);
		holder.tv_my_circle_time.setText("发布时间：" + dataSet.get(location).SE);
		holder.tv_my_circle_content.setText(dataSet.get(location).CT);
		holder.sqjl_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DeleteFactory deleteFactory = new DeleteFactory() {

					@Override
					public void determineButton() {
						Map<String, String> params = new HashMap<String, String>();
						params.put(
								Globals.WS_POST_KEY,
								"{\"Ac\":\"XXDL\",\"Para\":{\"sid\":\""
										+ User.sessionId
										+ "\",\"t\":\"1\",\"id\":\""
										+ dataSet.get(location).ID + "\"}}");
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

				// t 1、系统消息 2、邀约消息 3、关注消息

			}
		});
		// holder.bt_evaluation.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// }
		// });
		return convertView;
	}

	private class ViewHolder {
		private TextView sqjl_item_loca, tv_my_circle_time,
				tv_my_circle_content;
		private Button sqjl_del, bt_evaluation;
	}

}
