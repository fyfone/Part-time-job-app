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
import com.sq.jzq.bean.InviteRecordResult.InvieRecord;
import com.sq.jzq.bean.MyAttentionCompanyResult.AttentionCompany;
import com.sq.jzq.bean.MyMsgAttention.MyAttentionResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.bean.ApplyRecordResult.ApplyRecord;
import com.sq.jzq.bean.CompanyEvaluationResult.CompanyPEvaluation;
import com.sq.jzq.bean.JobResult.Job;
import com.sq.jzq.my.SqjlActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;

public class MyAttentionCompanyAdapter extends BaseAdapter {

	private List<AttentionCompany> dataSet;
	private LayoutInflater mInflater;
	private Context context;

	public MyAttentionCompanyAdapter() {

	}

	public void getDate(Context context, List<AttentionCompany> dataSet) {
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

	@Override
	public View getView(final int location, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.listview_my_attention_wanted, null);
			holder.tv_my_attrtion_title = (TextView) convertView
					.findViewById(R.id.tv_my_attrtion_title);
			holder.sqjl_item_loca = (TextView) convertView
					.findViewById(R.id.sqjl_item_loca);
			holder.tv_my_attrtion_loca = (TextView) convertView
					.findViewById(R.id.tv_my_attrtion_loca);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_my_attrtion_title.setText(dataSet.get(location).CE);
		holder.sqjl_item_loca.setText(dataSet.get(location).T);
		holder.tv_my_attrtion_loca.setText(dataSet.get(location).A);
		return convertView;
	}

	private class ViewHolder {
		private TextView tv_my_attrtion_title, sqjl_item_loca,
				tv_my_attrtion_loca;
	}

}
