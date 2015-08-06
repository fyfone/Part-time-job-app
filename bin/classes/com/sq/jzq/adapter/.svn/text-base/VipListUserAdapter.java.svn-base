package com.sq.jzq.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.R;
import com.sq.jzq.bean.CompanyEvaluationResult.CompanyPEvaluation;
import com.sq.jzq.bean.CompanyPositionResult.CompanyPosition;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VipListUserAdapter extends BaseAdapter {
	private Context context;
	private List<CompanyPEvaluation> dataSet;
	private LayoutInflater mInflater;

	public VipListUserAdapter() {
	}

	public void getDate(Context context, List<CompanyPEvaluation> dataSet) {
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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		VipJobinfoidItemView sift;
		if (convertView == null) {
			sift = new VipJobinfoidItemView(context);
		} else {
			sift = (VipJobinfoidItemView) convertView;
		}
		sift.initDate(dataSet.get(position));
		return sift;
	}

	public static class VipJobinfoidItemView extends LinearLayout {
		Context context;
		CompanyPEvaluation companyEvaluation;
		View view;
		private TextView sqjl_item_title, sqjl_item_date, tv_itme_position,
				tv_company_content;
		private ImageView iv_company_evaluation_head;

		public VipJobinfoidItemView(Context context) {
			super(context);
			this.context = context;
		}
		
		public void initDate(CompanyPEvaluation companyEvaluation) {
			this.companyEvaluation = companyEvaluation;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.listview_vip_company_user, this);
			iv_company_evaluation_head = (ImageView) view
					.findViewById(R.id.iv_company_evaluation_head);
			sqjl_item_title = (TextView) view
					.findViewById(R.id.sqjl_item_title);
			sqjl_item_date = (TextView) view.findViewById(R.id.sqjl_item_date);
			tv_itme_position = (TextView) view
					.findViewById(R.id.tv_itme_position);
			tv_company_content = (TextView) view
					.findViewById(R.id.tv_company_content);
			putView();
		}

		public void putView() {
			if (companyEvaluation != null) {
				sqjl_item_title.setText(companyEvaluation.PENAME);
				sqjl_item_date.setText(companyEvaluation.TS);
				tv_itme_position.setText(companyEvaluation.T);
				tv_company_content.setText(companyEvaluation.CS);
				if (companyEvaluation.TX != null) {
				}
				ImageLoader.getInstance().displayImage(companyEvaluation.TX,
						iv_company_evaluation_head);
			}
		}
	}
}
