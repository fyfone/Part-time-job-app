package com.sq.jzq.home.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImgAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context _context;
	private List<String> imgList;

	public ImgAdapter(Context context, List<String> imgList) {
		_context = context;
		this.imgList = imgList;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return Integer.MAX_VALUE;
	}

	public Object getItem(int position) {

		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listvie_gallery,
					null);
			viewHolder.iv_gallery = (ImageView) convertView
					.findViewById(R.id.iv_gallery);
			// imageView.setScaleType(ScaleType.FIT_XY);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(
				imgList.get(position % imgList.size()), viewHolder.iv_gallery);

		return convertView;
	}

	private class ViewHolder {
		ImageView iv_gallery;
	}
}
