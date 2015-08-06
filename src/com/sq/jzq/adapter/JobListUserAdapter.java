package com.sq.jzq.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class JobListUserAdapter extends BaseAdapter {

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

		return convertView;
	}

}
