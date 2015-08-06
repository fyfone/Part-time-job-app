package com.sq.jzq.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sq.jzq.R;
import com.sq.jzq.bean.CompanyPositionResult.CompanyPosition;

public class VipPositionAdapter extends BaseAdapter {
	private Context context;
	private List<CompanyPosition> dataSet;
	private LayoutInflater mInflater;

	public VipPositionAdapter() {
	}

	public void getDate(Context context, List<CompanyPosition> dataSet) {
		this.dataSet = dataSet;
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

		if (dataSet != null) {
			return dataSet.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return Long.parseLong(dataSet.get(position).JID);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		VipPositionItemView sift;
		if (convertView == null) {
			sift = new VipPositionItemView(context);
		} else {
			sift = (VipPositionItemView) convertView;
		}
		sift.initDate(dataSet.get(position));
		return sift;
	}

	public static class VipPositionItemView extends LinearLayout {
		Context context;
		CompanyPosition companyPositions;
		View view;
		private TextView item_title, item_date, item_loca, item_salary;

		public VipPositionItemView(Context context) {
			super(context);
			this.context = context;
		}

		public void initDate(CompanyPosition companyPosition) {
			this.companyPositions = companyPosition;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.listview_company_position, this);
			item_title = (TextView) view.findViewById(R.id.item_title);
			item_date = (TextView) view.findViewById(R.id.item_date);
			item_loca = (TextView) view.findViewById(R.id.item_loca);
			item_salary = (TextView) view.findViewById(R.id.item_salary);
			putView();
		}

		public void putView() {
			if (companyPositions != null) {
				item_title.setText(companyPositions.T);
				item_date.setText(companyPositions.RE);
				item_loca.setText(companyPositions.A);
				item_salary.setText(companyPositions.S);
			}
		}
	}

}
