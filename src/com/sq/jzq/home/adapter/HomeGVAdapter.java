package com.sq.jzq.home.adapter;

import com.sq.jzq.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeGVAdapter extends BaseAdapter {
	Context context;
	private Integer[] imgs = { R.drawable.home_dcwj, R.drawable.home_hcxz,
			R.drawable.home_fwy, R.drawable.home_ly, R.drawable.home_pfy,
			R.drawable.home_mt, R.drawable.home_dhxs, R.drawable.home_cxy };
	private Integer[] texts = { R.string.home_gv_questionnaire,
			R.string.home_gv_assist, R.string.home_gv_waiter,
			R.string.home_gv_ceremony, R.string.home_gv_distributed,
			R.string.home_gv_model, R.string.home_gv_phone,
			R.string.home_gv_promotion };

	public HomeGVAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 8;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.gv_houme, null);
		}
		ImageView im_houme_gv = (ImageView) convertView
				.findViewById(R.id.im_houme_gv);
		im_houme_gv.setBackgroundResource(imgs[position]);
		TextView tv_home_gv = (TextView) convertView
				.findViewById(R.id.tv_home_gv);
		tv_home_gv.setText(texts[position]);
		return convertView;
	}

}
