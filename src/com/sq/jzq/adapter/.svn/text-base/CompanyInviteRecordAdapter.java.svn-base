package com.sq.jzq.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import com.sq.jzq.bean.CompanyInviteRecordResult.CompanyInvieRecord;
import com.sq.jzq.bean.InviteRecordResult.InvieRecord;
import com.sq.jzq.bean.InviteRecordResultTwo.InvieRecordTwo;
import com.sq.jzq.bean.User;
import com.sq.jzq.bean.ApplyRecordResult.ApplyRecord;
import com.sq.jzq.bean.CompanyEvaluationResult.CompanyPEvaluation;
import com.sq.jzq.bean.JobResult.Job;
import com.sq.jzq.my.EvaluationActivity;
import com.sq.jzq.my.SqjlActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.DeleteFactory;

public class CompanyInviteRecordAdapter extends BaseAdapter {

	private List<CompanyInvieRecord> dataSet;
	private LayoutInflater mInflater;
	private Context context;
	private AlertDialog dialog;

	public CompanyInviteRecordAdapter() {

	}

	public void getDate(Context context, List<CompanyInvieRecord> dataSet) {
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
			convertView = mInflater.inflate(R.layout.listview_invite_company,
					null);
			holder.sqjl_item_time = (TextView) convertView
					.findViewById(R.id.sqjl_item_time);
			holder.sqjl_item_title = (TextView) convertView
					.findViewById(R.id.sqjl_item_title);
			holder.sqjl_item_unit = (TextView) convertView
					.findViewById(R.id.sqjl_item_unit);
			holder.sqjl_item_loca = (TextView) convertView
					.findViewById(R.id.sqjl_item_loca);
			holder.sqjl_del = (Button) convertView.findViewById(R.id.sqjl_del);
			holder.bt_evaluation = (Button) convertView
					.findViewById(R.id.bt_evaluation);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.sqjl_item_time.setText(dataSet.get(location).IE);
		holder.sqjl_item_title.setText(dataSet.get(location).NE);
		holder.sqjl_item_loca.setText(dataSet.get(location).PT);
		holder.sqjl_item_unit.setText("(" + dataSet.get(location).S + "/"
				+ dataSet.get(location).U + ")");
		holder.sqjl_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DeleteFactory deleteFactory = new DeleteFactory() {

					@Override
					public void determineButton() {// 获取企业列表
						Map<String, String> params = new HashMap<String, String>();
						params.put(Globals.WS_POST_KEY,
								"{\"Ac\":\"DLYY\",\"Para\":{\"Id\":\""
										+ dataSet.get(location).ID
										+ "\",\"Sid\":\"" + User.sessionId
										+ "\",\"S\":\"1\"}}");
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
				}.deleteDialog(context, "是否要删除邀请记录", "确定", "取消");

			}
		});

		// holder.bt_evaluation.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// dialog = new AlertDialog.Builder(context).create();
		// dialog.show();
		// dialog.getWindow().setContentView(R.layout.dialog_evaluation);
		// Button bt_dg_ecaluation_confirm = (Button) dialog
		// .findViewById(R.id.bt_dg_ecaluation_confirm);
		// Button bt_dg_ecaluation_cancel = (Button) dialog
		// .findViewById(R.id.bt_dg_ecaluation_cancel);
		//
		// bt_dg_ecaluation_confirm
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(context,
		// EvaluationActivity.class);
		// intent.putExtra("evaluation_id",
		// dataSet.get(location).CID);
		// intent.putExtra("evaluation_jobid",
		// dataSet.get(location).JID);
		// intent.putExtra("evaluation_text",
		// dataSet.get(location).CE);
		// context.startActivity(intent);
		// dialog.dismiss();
		//
		// }
		// });
		// bt_dg_ecaluation_cancel
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// dialog.dismiss();
		// }
		// });
		//
		// }
		// });
		return convertView;
	}

	private class ViewHolder {
		private TextView sqjl_item_time, sqjl_item_title, sqjl_item_unit,
				sqjl_item_loca;
		private Button sqjl_del, bt_evaluation;
	}

}
