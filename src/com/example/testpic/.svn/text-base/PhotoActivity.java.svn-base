package com.example.testpic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.User;
import com.sq.jzq.util.DownImage;
import com.sq.jzq.util.RestTask;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.util.RestTask.ResponseCallback;
import com.sq.jzq.util.RestUtil;

public class PhotoActivity extends Activity {

	private ArrayList<View> listViews = null;
	private ViewPager pager;
	private MyPageAdapter adapter;
	private int count;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();
	public int max;

	RelativeLayout photo_relativeLayout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		photo_relativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
		photo_relativeLayout.setBackgroundColor(0x70000000);

		for (int i = 0; i < Bimp.bmp.size(); i++) {
			bmp.add(Bimp.bmp.get(i));
		}
		for (int i = 0; i < Bimp.drr.size(); i++) {
			drr.add(Bimp.drr.get(i));
		}
		max = Bimp.max;

		Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
		photo_bt_exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();
			}
		});
		Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
		photo_bt_del.setOnClickListener(new delOnc());


		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < bmp.size(); i++) {
			initListViews(bmp.get(i));//
		}

		adapter = new MyPageAdapter(listViews);// 构造adapter
		pager.setAdapter(adapter);// 设置适配器
		Intent intent = getIntent();
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}

	public class delOnc implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			String newStr = drr.get(count).substring(
					drr.get(count).lastIndexOf("/") + 1,
					drr.get(count).lastIndexOf("."));
			System.out.println("newstr="+newStr);
			String id = newStr.substring(Globals.FILE_NAME_PREFIX.length(), newStr.length());
			System.out.println("id"+id);
			
			if(newStr.contains(Globals.FILE_NAME_PREFIX)) {
				new delQsz().del(id);
			}
			
			if (listViews.size() == 1) {
				bmp.remove(count);
				drr.remove(count);
				del.add(newStr);
				max--;
//				Bimp.bmp.clear();
//				Bimp.drr.clear();
//				Bimp.max = 0;
//				listViews.remove(count);
//				FileUtils.deleteDir();
//				finish();
				Bimp.bmp = bmp;
				Bimp.drr = drr;
				Bimp.max = max;
				for (int i = 0; i < del.size(); i++)
				{
					FileUtils.delFile(del.get(i) + ".JPEG");
				}
				finish();
			} else {
				bmp.remove(count);
				drr.remove(count);
				del.add(newStr);
				max--;
				pager.removeAllViews();
				listViews.remove(count);
				adapter.setListViews(listViews);
				adapter.notifyDataSetChanged();
				Bimp.bmp = bmp;
				Bimp.drr = drr;
				Bimp.max = max;
				for (int i = 0; i < del.size(); i++)
				{
					FileUtils.delFile(del.get(i) + ".JPEG");
				}
			}
			
			
		}
		
	}
	
	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		ImageView img = new ImageView(this);// 构造textView对象
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listViews.add(img);// 添加view
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {// 页面选择响应函数
			count = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

		}

		public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

		}
	};

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;// content

		private int size;// 页数

		public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
															// 初始化viewpager的时候给的一个页面
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {// 返回数量
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
			((ViewPager) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {// 返回view对象
			try {
				((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
	
	

	/**
	 * 删除全身照片
	 * 
	 * @author Administrator
	 * 
	 */
	class delQsz{
//		private AlertDialog adUpudateIcon;

		public void del(String id) {
			
			Map<String, String> params = new HashMap<String, String>();
			params.put(Globals.WS_POST_KEY, "{\"Ac\":\"RPS\",\"Para\":{\"Sid\":\"" + User.getSessionId()+"\",\"Id\":\"" + id + "\"}}");

			new VolleyUtil() {

				public void analysisData(String response) {
					try {
						JSONObject entity = new JSONObject(response);
						if (entity.getString("Stu").equals("1")) {
							JSONObject result = entity.getJSONObject("Rst");
							if (result == null) {
								Log.i(Globals.LOG_TAG, response);
							} else {
								if (result.getString("Scd").equals("1")) {// 成功

								} else {// 失败
									;
								}
								Toast.makeText(getApplicationContext(),
										result.getString("Msg"), Globals.TOAST_SHORT).show();
							}
						} else {
							Log.i(Globals.LOG_TAG,
									"Json Error -- Status:" + entity.getString("Stu")
											+ ",Message:" + entity.getString("Msg"));
						}
					} catch (JSONException e) {
						Log.i(Globals.LOG_TAG, e.getMessage());
					}

				}

			}.volleyStringRequestPost(PhotoActivity.this, params);
			
		}
//			try {
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//				params.add(new BasicNameValuePair(Globals.WS_POST_KEY,
//						"{\"Ac\":\"RPS\"," + "\"Sid\":\"" + User.getSessionId()
//								+ "\",\"Para\":{\"Id\":\"" + id + "\"}}"));
//				Log.i(Globals.LOG_TAG, params.get(0).getValue());
//				RestTask postTask = RestUtil.obtainFormPostTask(Globals.WS_URI_POTO,
//						params);
//				postTask.setResponseCallback(this);
//				postTask.execute();

//				adUpudateIcon = new AlertDialog.Builder(PhotoActivity.this)
//						.create();
//				adUpudateIcon.show();
//				adUpudateIcon.getWindow().setContentView(R.layout.dialog);
//				TextView title = (TextView) adUpudateIcon.getWindow()
//						.findViewById(R.id.dialog_title);
//				title.setText(getResources().getString(R.string.ts));
//				TextView content = (TextView)adUpudateIcon.getWindow().findViewById(R.id.dialog_content);
//				content.setText(getResources().getString(R.string.del));
//			} catch (Exception e) {
//				Log.i(Globals.LOG_TAG, "Exception:" + e.getMessage());
//			}
//		}

//		@Override
//		public void onRequestSuccess(String response) {
//			try {
//				JSONObject entity = new JSONObject(response);
//				if (entity.getString("Stu").equals("1")) {
//					JSONObject result = entity.getJSONObject("Rst");
//					if (result == null) {
//						Log.i(Globals.LOG_TAG, response);
//					} else {
//						if (result.getString("Scd").equals("1")) {// 成功
//
//						} else {// 失败
//							;
//						}
//						Toast.makeText(getApplicationContext(),
//								result.getString("Msg"), Globals.TOAST_SHORT).show();
//					}
//				} else {
//					Log.i(Globals.LOG_TAG,
//							"Json Error -- Status:" + entity.getString("Stu")
//									+ ",Message:" + entity.getString("Msg"));
//				}
//			} catch (JSONException e) {
//				Log.i(Globals.LOG_TAG, e.getMessage());
//			}
////			if (adUpudateIcon != null) {
////				adUpudateIcon.dismiss();
////			}
//		}
//
//		@Override
//		public void onRequestError(Exception error) {
////			if (adUpudateIcon != null) {
////				adUpudateIcon.dismiss();
////			}
//			Toast.makeText(PhotoActivity.this,
//					Globals.WLCW, 0).show();
//		}
	}

}
