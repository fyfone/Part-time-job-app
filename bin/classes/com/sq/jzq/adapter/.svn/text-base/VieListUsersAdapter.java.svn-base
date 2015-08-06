package com.sq.jzq.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.R;
import com.sq.jzq.bean.CompanyEvaluationResult.CompanyPEvaluation;

public class VieListUsersAdapter extends BaseAdapter {

	private Context context;
	private List<CompanyPEvaluation> dataSet;
	private LayoutInflater mInflater;

	public VieListUsersAdapter() {
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
	public Object getItem(int location) {
		return null;
	}

	@Override
	public long getItemId(int location) {
		return 0;
	}

	@Override
	public View getView(int location, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_vip_company_user,
					null);
			holder.iv_company_evaluation_head = (ImageView) convertView
					.findViewById(R.id.iv_company_evaluation_head);
			holder.sqjl_item_title = (TextView) convertView
					.findViewById(R.id.sqjl_item_title);
			holder.sqjl_item_date = (TextView) convertView
					.findViewById(R.id.sqjl_item_date);
			holder.tv_itme_position = (TextView) convertView
					.findViewById(R.id.tv_itme_position);
			holder.tv_company_content = (TextView) convertView
					.findViewById(R.id.tv_company_content);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.sqjl_item_title.setText(dataSet.get(location).PENAME);
		holder.sqjl_item_date.setText(dataSet.get(location).TS);
		holder.tv_itme_position.setText(dataSet.get(location).T);
		holder.tv_company_content.setText(dataSet.get(location).CS);
		if (dataSet.get(location).TX != null) {
		}
		ImageLoader.getInstance().displayImage(dataSet.get(location).TX,
				holder.iv_company_evaluation_head);

		return convertView;
	}

	public class ViewHolder {
		private TextView sqjl_item_title, sqjl_item_date, tv_itme_position,
				tv_company_content;
		private ImageView iv_company_evaluation_head;
	}

}
