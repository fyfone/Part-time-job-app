package com.sq.jzq.adapter;

import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sq.jzq.R;

public class MyMessageInvitationAdapter extends BaseAdapter {
	private Context context;

	public MyMessageInvitationAdapter(Context context) {
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
		return convertView;
	}

}
