package com.example.testpic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.User;
import com.sq.jzq.util.Convert;
import com.sq.jzq.util.DownImage;
import com.sq.jzq.util.RestTask;
import com.sq.jzq.util.RestTask.ResponseCallback;
import com.sq.jzq.util.RestUtil;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.TitleBarView;
import com.sq.jzq.views.TitleBarView.OnClickEnterButtonListener;

public class PublishedActivity extends Activity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private TitleBarView tbTitle;

	public String SDPATH = Environment.getExternalStorageDirectory() + "/"
			+ Globals.SDPATH + "/";
	private ArrayList<Map<String, String>> gridList = new ArrayList<Map<String, String>>();
	private int uploadCount = 0; // 需要上传的张数；

	private DisplayMetrics dm;

	LinearLayout.LayoutParams imagebtn_params = new LinearLayout.LayoutParams(

	LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	private AlertDialog dialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectimg);

		Bimp.drr.clear();
		Bimp.bmp.clear();
		Bimp.max = 0;
		loadData();
		Init();

	}

	private void loadData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"GPS\",\"Para\":{\"Sid\":\""
				+ User.getSessionId() + "\"}}");

		new VolleyUtil() {

			public void analysisData(String response) {

				try {
					JSONObject entity = new JSONObject(response);

					if (!entity.getString("Stu").equals("1")) {
						Log.i(Globals.LOG_TAG, "JsonAdapter Error -- Status:"
								+ entity.getString("Stu") + ",Message:"
								+ entity.getString("Msg"));
						return;
					}

					JSONObject result = entity.getJSONObject("Rst");
					Map<String, String> map;
					JSONArray items = result.getJSONArray("Lst");
					for (int i = 0; i < items.length(); i++) {
						JSONObject item = items.getJSONObject(i);
						map = new HashMap<String, String>();
						map.put("Id", item.getString("ID"));
						map.put("BigImage", item.getString("B"));
						// map.put("Thumbnail", Globals.WS_BASE_URL + "/" +
						// item.getString("S"));
						gridList.add(map);

						File f = new File(SDPATH + Globals.FILE_NAME_PREFIX
								+ item.getString("ID") + ".JPEG");
						if (f.exists()) {
							Bimp.drr.add(f.getPath());

						} else {
							DownImage.setImageForImageView(item.getString("B"),
									item.getString("ID"), adapter);
						}

					}

					adapter.update1();
				} catch (JSONException e) {
					Log.i(Globals.LOG_TAG, e.getMessage());
				}

			}

		}.volleyStringRequestPost(PublishedActivity.this, params);
	}

	public void Init() {
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		// adapter.update1();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					new PopupWindows(PublishedActivity.this, noScrollgridview);
				} else {
					Intent intent = new Intent(PublishedActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		tbTitle = (TitleBarView) findViewById(R.id.select_titlebar);
		tbTitle.setClickEnterButtonListener(new OnClickEnterButtonListener() {

			@Override
			public void onClickEnterButton(View v) {
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < Bimp.drr.size(); i++) {
					String str = Bimp.drr.get(i).substring(
							Bimp.drr.get(i).lastIndexOf("/") + 1,
							Bimp.drr.get(i).lastIndexOf("."));
					File file = new File(SDPATH + str + ".JPEG");

					if (!str.contains(Globals.FILE_NAME_PREFIX)) {
						uploadCount++;
						new uploadQsz().upload(file, str + ".JPEG");
					}

					// list.add(FileUtils.SDPATH + Str + ".JPEG");
				}
				// 高清的压缩图片全部就在 list 路径里面了
				// 高清的压缩过的 bmp 对象 都在 Bimp.bmp里面
				// 完成上传服务器后 .........

				// FileUtils.deleteDir();

			}
		});
	}

	/**
	 * 上传全身照片
	 * 
	 * @author Administrator
	 * 
	 */
	class uploadQsz implements ResponseCallback {
		private AlertDialog adUpudateIcon;

		public void upload(File file, String fileName) {
			uploadCount--;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair(Globals.WS_POST_KEY,
						"{\"Ac\":\"TPSC\",\"Para\":{\"SId\":\""
								+ User.sessionId + "\",\"T\":\"" + 2 + "\"}}"));
				Log.i(Globals.LOG_TAG, params.get(0).getValue());
				RestTask postTask = null;
				postTask = RestUtil.obtainMultipartPostTask(
						Globals.WS_URI_POTO, params, file, fileName);

				postTask.setResponseCallback(this);
				postTask.execute();

				if (uploadCount == 0) {
					adUpudateIcon = new AlertDialog.Builder(
							PublishedActivity.this).create();
					adUpudateIcon.show();
					adUpudateIcon.getWindow().setContentView(R.layout.dialog);
					// TextView title = (TextView)
					// adUpudateIcon.getWindow().findViewById(
					// R.id.dialog_title);
					// title.setText(getResources().getString(R.string.ts));
				}
			} catch (Exception e) {
				Log.i(Globals.LOG_TAG, "Exception:" + e.getMessage());
			}
		}

		@Override
		public void onRequestSuccess(String response) {
			try {
				JSONObject entity = new JSONObject(response);
				if (entity.getString("Stu").equals("1")) {
					JSONObject result = entity.getJSONObject("Rst");
					if (result == null) {
						Log.i(Globals.LOG_TAG, response);
					} else {
						if (result.getString("Scd").equals("1")) {// 成功
							if (uploadCount == 0) {
								Bimp.drr.clear();
								Bimp.bmp.clear();
								Bimp.max = 0;
							}

						} else {// 失败
							;
						}
						Toast.makeText(getApplicationContext(),
								result.getString("Msg"), Globals.TOAST_SHORT)
								.show();
					}
				} else {
					Log.i(Globals.LOG_TAG,
							"Json Error -- Status:" + entity.getString("Stu")
									+ ",Message:" + entity.getString("Msg"));
				}
			} catch (JSONException e) {
				Log.i(Globals.LOG_TAG, e.getMessage());
			}
			if (adUpudateIcon != null) {
				adUpudateIcon.dismiss();
			}
		}

		@Override
		public void onRequestError(Exception error) {
			if (adUpudateIcon != null) {
				adUpudateIcon.dismiss();
			}
			Toast.makeText(PublishedActivity.this, Globals.WLCW, 0).show();
		}
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update1() {
			loading1();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			// final int coord = position;
			ViewHolder holder = null;

			System.out.println("测试下表=" + position);
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				getWidth();
				holder.image.setLayoutParams(imagebtn_params);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.image.setVisibility(View.VISIBLE);

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));

			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			if (position == Globals.QSZ) {
				holder.image.setVisibility(View.GONE);
			}

			return convertView;
		}

		void getWidth() {
			int margin = new Convert().dip2px(PublishedActivity.this, 15 * 4);

			// 获取屏幕尺寸
			dm = new DisplayMetrics();

			getWindowManager().getDefaultDisplay().getMetrics(dm);

			imagebtn_params.height = (dm.widthPixels - margin) / 3;

			imagebtn_params.width = (dm.widthPixels - margin) / 3;

		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading1() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								if (Bimp.max > Bimp.drr.size()) {
									return;
								}
								String path = Bimp.drr.get(Bimp.max);
								System.out.println("loading1中的path=" + path);
								Bitmap bm = Bimp.revitionImageSize(path);
								if (bm != null) {
									Bimp.bmp.add(bm);
									String newStr = path.substring(
											path.lastIndexOf("/") + 1,
											path.lastIndexOf("."));
									FileUtils.saveBitmap(bm, "" + newStr);
									Bimp.max += 1;
								}

								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update1();
		super.onRestart();
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			super(mContext);

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.MATCH_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PublishedActivity.this,
							TestPicActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	}

	public void photo() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			File dir = new File(Environment.getExternalStorageDirectory()
					+ "/myimage/");
			if (!dir.exists())
				dir.mkdirs();

			Intent openCameraIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			File file = new File(dir,
					String.valueOf(System.currentTimeMillis()) + ".jpg");
			path = file.getPath();
			Uri imageUri = Uri.fromFile(file);
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
			startActivityForResult(openCameraIntent, TAKE_PICTURE);
		} else {
			Toast.makeText(PublishedActivity.this, "没有储存卡", Toast.LENGTH_LONG)
					.show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.drr.size() < Globals.QSZ && resultCode == -1) {
				Bimp.drr.add(path);
			}
			break;
		}

	}

}
