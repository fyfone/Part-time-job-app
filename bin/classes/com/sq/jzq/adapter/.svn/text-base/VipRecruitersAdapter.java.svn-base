package com.sq.jzq.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.R;
import com.sq.jzq.bean.User;
import com.sq.jzq.bean.CompanyResult.Company;

@SuppressLint("ResourceAsColor")
public class VipRecruitersAdapter extends BaseAdapter {
	private List<Company> dataSet;
	private LayoutInflater mInflater;

	public VipRecruitersAdapter(Context context, List<Company> dataSet) {
		this.dataSet = dataSet;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public VipRecruitersAdapter() {

	}

	public void getDate(Context context, List<Company> dataSet) {
		this.dataSet = dataSet;
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

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int location, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_vip_enterprise,
					null);
			holder.title = (TextView) convertView
					.findViewById(R.id.vip_item_title);
			holder.loca = (TextView) convertView
					.findViewById(R.id.vip_item_loca);
			holder.date = (TextView) convertView
					.findViewById(R.id.vip_item_date);
			holder.tv_vip_authentication = (TextView) convertView
					.findViewById(R.id.tv_vip_authentication);
			holder.vip_item_icon = (ImageView) convertView
					.findViewById(R.id.vip_item_icon);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if ("未认证".equals(dataSet.get(location).ZS)) {

			holder.tv_vip_authentication.setText(dataSet.get(location).ZS);
			holder.tv_vip_authentication
					.setBackgroundResource(R.drawable.text_certified_guide);
			holder.tv_vip_authentication.setTextColor(R.color.alert_title);

		} else if ("已认证".equals(dataSet.get(location).ZS)) {
			holder.tv_vip_authentication.setText(dataSet.get(location).ZS);
			holder.tv_vip_authentication
					.setBackgroundResource(R.drawable.text_certified_red);
			holder.tv_vip_authentication.setTextColor(R.color.background_white);
		}

		holder.title.setText(dataSet.get(location).CE);
		holder.date.setText(dataSet.get(location).RE);
		holder.loca.setText(dataSet.get(location).CT);

		ImageLoader.getInstance().displayImage(dataSet.get(location).RT,
				holder.vip_item_icon);
		return convertView;
	}

	public class ViewHolder {
		public TextView title, date, loca, tv_vip_authentication;
		public ImageView vip_item_icon;
		public LinearLayout ll_vip_Enterprise;
	}

}
