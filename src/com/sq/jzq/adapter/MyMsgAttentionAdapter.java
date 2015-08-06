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
import com.sq.jzq.bean.ApplyRecordResult;
import com.sq.jzq.bean.DeleteItemResult;
import com.sq.jzq.bean.InviteRecordResult.InvieRecord;
import com.sq.jzq.bean.MyMsgAttention.MyAttentionResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.bean.ApplyRecordResult.ApplyRecord;
import com.sq.jzq.bean.CompanyEvaluationResult.CompanyPEvaluation;
import com.sq.jzq.bean.JobResult.Job;
import com.sq.jzq.my.SqjlActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.DeleteFactory;

public class MyMsgAttentionAdapter extends BaseAdapter {

	private List<MyAttentionResult> dataSet;
	private LayoutInflater mInflater;
	private Context context;

	public MyMsgAttentionAdapter() {

	}

	public void getDate(Context context, List<MyAttentionResult> dataSet) {
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
					R.layout.listview_my_message_invitation, null);
			holder.tv_messg = (TextView) convertView
					.findViewById(R.id.tv_messg);
			holder.sqjl_item_loca = (TextView) convertView
					.findViewById(R.id.sqjl_item_loca);
			holder.tv_msg_invit_time = (TextView) convertView
					.findViewById(R.id.tv_msg_invit_time);
			// holder.tv_msg_red = (TextView) convertView
			// .findViewById(R.id.tv_msg_red);
			holder.sqjl_del = (Button) convertView.findViewById(R.id.sqjl_del);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.sqjl_item_loca.setText(dataSet.get(location).CE);
		holder.tv_messg.setText(dataSet.get(location).JE);
		holder.tv_msg_invit_time.setText(dataSet.get(location).RE);
		holder.sqjl_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DeleteFactory deleteFactory = new DeleteFactory() {

					@Override
					public void determineButton() {// t 1、系统消息 2、邀约消息 3、关注消息
						Map<String, String> params = new HashMap<String, String>();
						params.put(
								Globals.WS_POST_KEY,
								"{\"Ac\":\"XXDL\",\"Para\":{\"sid\":\""
										+ User.sessionId
										+ "\",\"t\":\"3\",\"id\":\""
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
