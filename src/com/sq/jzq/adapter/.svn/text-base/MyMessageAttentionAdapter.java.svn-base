package com.sq.jzq.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sq.jzq.R;

public class MyMessageAttentionAdapter extends BaseAdapter {
	private Context context;

	public MyMessageAttentionAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context,
				R.layout.listview_my_message_invitation, null);
		TextView sqjl_item_loca = (TextView) convertView
				.findViewById(R.id.sqjl_item_loca);
		sqjl_item_loca.setText("北京艺术钱箱文化传播有限公司");
		TextView tv_messg = (TextView) convertView.findViewById(R.id.tv_messg);
		sqjl_item_loca.setText("模特");
		TextView tv_msg_red = (TextView) convertView
				.findViewById(R.id.tv_msg_red);
		sqjl_item_loca.setText("有心职位更新");
		return convertView;
	}

}
