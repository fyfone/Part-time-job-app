package com.sq.jzq;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sq.jzq.R;

public class BottomViewItem {

	public static BottomViewItem instance;

	public static BottomViewItem getInstance() {
		if (instance == null) {
			instance = new BottomViewItem();
			
		}
		return instance;
	}

	public int viewNum = 4;
	public ImageView[] images = new ImageView[viewNum];
	public TextView[] texts = new TextView[viewNum];
	public LinearLayout[] linears = new LinearLayout[viewNum];
	//ImageView控件数组
	public int[] images_id = new int[] { R.id.home_image, R.id.job_image, R.id.vip_image, R.id.my_image };
	//TextView控件数组
	public int[] texts_id = new int[] { R.id.home_text, R.id.job_text, R.id.vip_text, R.id.my_text };
	//LinearLayout控件数组
	public int[] linears_id = new int[] { R.id.home_layout, R.id.job_layout, R.id.vip_layout, R.id.my_layout };
	//图片被选中时的数组
	public int[] images_selected = new int[] { R.drawable.home_2, R.drawable.job_2, R.drawable.vip_2, R.drawable.my_2};
	//图片数组
	public int[] images_unselected = new int[] { R.drawable.home_1, R.drawable.job_1, R.drawable.vip_1, R.drawable.my_1};
	//布局数组
//	public int[] layouts_id = new int[] { R.layout.make_money, R.layout.job_list, R.layout.member_center};

}
