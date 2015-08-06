package com.sq.jzq.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.INotificationSideChannel.Stub;
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
import com.sq.jzq.bean.CompanyResumeCollectionResult.ResumeCollection;
import com.sq.jzq.bean.CompanyResumeCollectionResult;
import com.sq.jzq.bean.DeleteItemResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.company.CompanyResumeCollectionActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.DeleteFactory;

public class ComapanyResumeCollectiondapter extends BaseAdapter {

	private List<ResumeCollection> dataSet;
	private LayoutInflater mInflater;
	private Context context;

	public ComapanyResumeCollectiondapter() {

	}

	public void getDate(Context context, List<ResumeCollection> dataSet) {
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
		return Long.parseLong(dataSet.get(location).UID);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int location, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.listview_my_message_invitation, null);
			holder.tv_messg = (TextView) convertView
					.findViewById(R.id.tv_messg);
			holder.sqjl_item_loca = (TextView) convertView
					.findViewById(R.id.sqjl_item_loca);
			holder.tv_msg_invit_time = (TextView) convertView
					.findViewById(R.id.tv_msg_invit_time);
			holder.tv_msg_red = (TextView) convertView
					.findViewById(R.id.tv_msg_red);
			holder.sqjl_del = (Button) convertView.findViewById(R.id.sqjl_del);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.sqjl_item_loca.setText(dataSet.get(location).NE);
		holder.tv_messg.setText("求职意向：" + dataSet.get(location).IN);
		holder.tv_msg_invit_time.setText("兼职时间：" + dataSet.get(location).IT);
		holder.tv_msg_red.setText(dataSet.get(location).TI);
		holder.tv_msg_red.setTextColor(R.color.text_888);
		holder.sqjl_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DeleteFactory deleteFactory = new DeleteFactory() {

					@Override
					public void determineButton() {
						Map<String, String> params = new HashMap<String, String>();
						params.put(Globals.WS_POST_KEY,
								"{\"Ac\":\"NOCOL\",\"Para\":{\"Id\":\""
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

			}
		});
		return convertView;
	}

	private class ViewHolder {
		private TextView sqjl_item_loca, tv_messg, tv_msg_invit_time,
				tv_msg_red;
		private Button sqjl_del, bt_evaluation;
	}

}
