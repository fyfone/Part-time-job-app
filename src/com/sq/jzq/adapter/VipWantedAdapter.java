package com.sq.jzq.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.adapter.VipRecruitersAdapter.ViewHolder;
import com.sq.jzq.bean.CompanyResult.Company;
import com.sq.jzq.bean.VIPWantedResult.Wanted;

public class VipWantedAdapter extends BaseAdapter {
	private Context context;
	private List<Wanted> dataSet;
	private LayoutInflater mInflater;

	public VipWantedAdapter() {
	}

	public void getDate(Context context, List<Wanted> dataSet) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_vip_wanted, null);
			holder.tv_vip_wanted_name = (TextView) convertView
					.findViewById(R.id.tv_vip_wanted_name);
			holder.im_vip_wanted_head = (ImageView) convertView
					.findViewById(R.id.im_vip_wanted_head);
			holder.im_vip_wanted_sex = (ImageView) convertView
					.findViewById(R.id.im_vip_wanted_sex);

			holder.tv_vip_wanted_sex = (TextView) convertView
					.findViewById(R.id.tv_vip_wanted_sex);
			holder.tv_vip_wanted_ambition = (TextView) convertView
					.findViewById(R.id.tv_vip_wanted_ambition);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_vip_wanted_name.setText(dataSet.get(position).RE);
		holder.tv_vip_wanted_sex.setText("性别：" + dataSet.get(position).GR);
		holder.tv_vip_wanted_ambition.setText("意向：" + dataSet.get(position).JN);
		ImageLoader.getInstance().displayImage(dataSet.get(position).RT,
				holder.im_vip_wanted_head);
		if ("男".equals(dataSet.get(position).GR)) {
			holder.im_vip_wanted_sex
					.setBackgroundResource(R.drawable.vip_sex_nan);
		} else if ("女".equals(dataSet.get(position).GR)) {
			holder.im_vip_wanted_sex
					.setBackgroundResource(R.drawable.vip_sex_nv);
		} else {
			holder.im_vip_wanted_sex
					.setBackgroundResource(R.drawable.vip_sex_nv);
		}

		return convertView;
	}

	public class ViewHolder {
		public TextView tv_vip_wanted_name, tv_vip_wanted_sex,
				tv_vip_wanted_ambition;
		public ImageView im_vip_wanted_sex, im_vip_wanted_head;
	}

}
