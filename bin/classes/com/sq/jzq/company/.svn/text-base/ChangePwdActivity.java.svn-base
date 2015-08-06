package com.sq.jzq.company;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.CompanyResult;
import com.sq.jzq.bean.DeleteItemResult;
import com.sq.jzq.bean.MyDate;
import com.sq.jzq.bean.User;
import com.sq.jzq.bean.Yyzz;
import com.sq.jzq.my.MyDataActivity.UpdateIcon;
import com.sq.jzq.my.ChagePwdActivity;
import com.sq.jzq.my.MyDataActivity;
import com.sq.jzq.my.SelectPicActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.RestTask;
import com.sq.jzq.util.RestUtil;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.util.RestTask.ResponseCallback;

public class ChangePwdActivity extends BaseActivity {
	private LinearLayout company_change_pwd_ll, company_info_ll, change_pwd,
			company_info;
	private TextView company_changepwd_tag, company_info_tag;
	private View line_bule_l, line_bule_r;
	private String UploadData[] = new String[] { "从图库中选择", "拍照", "取消" };
	private ImageView tax_text_view, business_text_view;
	private static final int NONE = 0;
	private static final int PHOTO_GRAPH = 1;// 拍照
	private static final int PHOTO_ZOOM = 2; // 缩放
	private static final int PHOTO_RESOULT = 3;// 结果
	private EditText et_uppass_pwd, et_uppass_nwd, et_uppass_nwd_two;
	private Button bt_registration;
	private String type = "6";

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_change_pwd);
		this.company_change_pwd_ll = (LinearLayout) findViewById(R.id.company_change_pwd_ll);
		this.company_info_ll = (LinearLayout) findViewById(R.id.company_info_ll);
		this.change_pwd = (LinearLayout) findViewById(R.id.change_pwd);
		this.company_info = (LinearLayout) findViewById(R.id.company_info);
		this.company_changepwd_tag = (TextView) findViewById(R.id.company_changepwd_tag);
		this.company_info_tag = (TextView) findViewById(R.id.company_info_tag);
		this.line_bule_l = (View) findViewById(R.id.line_bule_l);
		this.line_bule_r = (View) findViewById(R.id.line_bule_r);
		this.tax_text_view = (ImageView) findViewById(R.id.tax_text_view);
		this.business_text_view = (ImageView) findViewById(R.id.business_text_view);

		et_uppass_pwd = (EditText) findViewById(R.id.et_uppass_pwd);
		et_uppass_nwd = (EditText) findViewById(R.id.et_uppass_nwd);
		et_uppass_nwd_two = (EditText) findViewById(R.id.et_uppass_nwd_two);
		bt_registration = (Button) findViewById(R.id.bt_registration);

		this.company_change_pwd_ll.setOnClickListener(this);
		this.bt_registration.setOnClickListener(this);
		this.company_info_ll.setOnClickListener(this);
		this.tax_text_view.setOnClickListener(this);
		this.business_text_view.setOnClickListener(this);

		loadData();

	}

	private void loadData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"QYZS\",\"Para\":{\"Sid\":\""
				+ User.sessionId + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				Yyzz s = GsonUtils.json2bean(response, Yyzz.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(ChangePwdActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {
					if (!"".equals(s.Rst.S)) {
						ImageLoader.getInstance().displayImage(s.Rst.S,
								tax_text_view);
					}
					if (!"".equals(s.Rst.Y)) {
						ImageLoader.getInstance().displayImage(s.Rst.Y,
								business_text_view);
					}
				}

			}
		}.volleyStringRequestPost(ChangePwdActivity.this, params);
	}

	private void changePasswordDate() {

		// 修改密码
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"MPwd\",\"Para\":{\"Pwd\":\""
				+ et_uppass_pwd.getText().toString() + "\",\"NPwd\":\""
				+ et_uppass_nwd_two.getText().toString() + "\",\"Sid\":\""
				+ User.sessionId + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {

				DeleteItemResult s = GsonUtils.json2bean(response,
						DeleteItemResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(ChangePwdActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {

					if (s.Rst.Scd == 1) {
						Toast.makeText(ChangePwdActivity.this, s.Rst.Msg, 0)
								.show();
						finish();

					} else {
						Toast.makeText(ChangePwdActivity.this, s.Rst.Msg,
								Globals.TOAST_SHORT).show();
					}

				}

			}

		}.volleyStringRequestPost(ChangePwdActivity.this, params);

	}

	@Override
	public void widgetClick(View v) {
		// 标签选项
		switch (v.getId()) {
		case R.id.company_change_pwd_ll:
			this.line_bule_l.setVisibility(View.VISIBLE);
			this.line_bule_r.setVisibility(View.GONE);
			this.change_pwd.setVisibility(View.VISIBLE);
			this.company_info.setVisibility(View.GONE);
			break;
		case R.id.company_info_ll:
			this.line_bule_l.setVisibility(View.GONE);
			this.line_bule_r.setVisibility(View.VISIBLE);
			this.change_pwd.setVisibility(View.GONE);
			this.company_info.setVisibility(View.VISIBLE);
			break;
		case R.id.bt_registration:
			if ("".equals(et_uppass_pwd.getText().toString())
					&& "".equals(et_uppass_nwd.getText().toString())
					&& "".equals(et_uppass_nwd_two.getText().toString())) {
				Toast.makeText(ChangePwdActivity.this, "您输入的密码不能为空",
						Globals.TOAST_SHORT).show();

			} else {
				if (et_uppass_nwd.getText().toString()
						.equals(et_uppass_nwd_two.getText().toString())) {
					changePasswordDate();
				} else {
					Toast.makeText(ChangePwdActivity.this, "您输入的两次密码不匹配",
							Globals.TOAST_SHORT).show();
				}

			}
			break;
		case R.id.tax_text_view:
			// this.tax_text_view.setTag("true");
			// this.business_text_view.setTag("false");
			// this.chooseDialog();
			Intent intent = new Intent();
			intent.setClass(ChangePwdActivity.this, SelectPicActivity.class);
			intent.putExtra("type", "icon");
			startActivityForResult(intent, 6);
			break;
		case R.id.business_text_view:
			// this.tax_text_view.setTag("true");
			// this.business_text_view.setTag("false");
			// this.chooseDialog();
			Intent intent1 = new Intent();
			intent1.setClass(ChangePwdActivity.this, SelectPicActivity.class);
			intent1.putExtra("type", "icon");
			startActivityForResult(intent1, 5);
			break;
		default:
			break;
		}
		// // 判断点击那个文本框
		// if (v == this.tax_text_view) {
		// this.tax_text_view.setTag("true");
		// this.business_text_view.setTag("false");
		// this.chooseDialog();
		// } else if (v == this.business_text_view) {
		// this.tax_text_view.setTag("false");
		// this.business_text_view.setTag("true");
		// this.chooseDialog();
		// }
	}

	private void chooseDialog() {
		Dialog dialog = new AlertDialog.Builder(ChangePwdActivity.this)
				.setTitle("编辑")
				.setItems(ChangePwdActivity.this.UploadData,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == 0) { // 从相册获取图片
									Intent intent = new Intent(
											Intent.ACTION_PICK, null);
									intent.setDataAndType(
											MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											"image/*");
									startActivityForResult(intent, PHOTO_ZOOM);
								} else if (which == 1) { // 从拍照获取图片
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									intent.putExtra(
											MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(
													Environment
															.getExternalStorageDirectory(),
													"temp_enterprise.jpg")));
									startActivityForResult(intent, PHOTO_GRAPH);
								} else if (which == 2) {// 取消
									dialog.cancel();
								}
							}
						}).create();
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		type = requestCode + "";
		switch (resultCode) {
		case 1:
			if (data != null) {
				// 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
				Uri mImageCaptureUri = data.getData();
				// 返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
				if (mImageCaptureUri != null) {
					Bitmap image;
					try {
						// 这个方法是根据Uri获取Bitmap图片的静态方法
						image = MediaStore.Images.Media.getBitmap(
								this.getContentResolver(), mImageCaptureUri);
						if (image != null) {
							// CacheImage.setImageForImageView(User.IconPath,
							// ivIcon);
							if ("5".equals(type)) {
								business_text_view.setImageBitmap(image);
							} else if ("6".equals(type)) {
								tax_text_view.setImageBitmap(image);
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Bundle extras = data.getExtras();
					if (extras != null) {
						// 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
						Bitmap image = extras.getParcelable("data");
						if (image != null) {
							if ("5".equals(type)) {
								business_text_view.setImageBitmap(image);
							} else if ("6".equals(type)) {
								tax_text_view.setImageBitmap(image);
							}
						}
					}
				}

			}
			new UpdateIcon().update(type);
			break;
		default:
			break;
		}

	}

	public class UpdateIcon implements ResponseCallback {
		private AlertDialog adUpudateIcon;
		Bitmap img;

		public void update(String t) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				String req = "{\"Ac\":\"TPSC\",\"Para\":{\"SId\":\""
						+ User.sessionId + "\",\"T\":\"" + t + "\"}}";
				params.add(new BasicNameValuePair(Globals.WS_POST_KEY, req));
				Log.i(Globals.LOG_TAG, params.get(0).getValue());
				RestTask postTask = null;
				if ("5".equals(type)) {
					business_text_view.setDrawingCacheEnabled(true);
					img = Bitmap.createBitmap(business_text_view
							.getDrawingCache());
					business_text_view.setDrawingCacheEnabled(false);
				} else if ("6".equals(type)) {
					tax_text_view.setDrawingCacheEnabled(true);
					img = Bitmap.createBitmap(tax_text_view.getDrawingCache());
					tax_text_view.setDrawingCacheEnabled(false);
				}
				File imgFile = new File(getExternalCacheDir(),
						Globals.CACHE_FILE_NAME);
				FileOutputStream fos = new FileOutputStream(imgFile);
				img.compress(CompressFormat.PNG, 0, fos);
				fos.flush();
				fos.close();
				postTask = RestUtil.obtainMultipartPostTask(
						Globals.WS_URI_POTO, params, imgFile,
						Globals.CACHE_FILE_NAME);

				postTask.setResponseCallback(this);
				postTask.execute();

				adUpudateIcon = new AlertDialog.Builder(ChangePwdActivity.this)
						.create();
				adUpudateIcon.show();
				adUpudateIcon.getWindow().setContentView(R.layout.dialog);
				// TextView title = (TextView)
				// adUpudateIcon.getWindow().findViewById(
				// R.id.dialog_title);
				// title.setText(getResources().getString(R.string.ts));
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
							if ("1".equals(type)) {
								// User.setIconPath(result.getString("Po"));
							}

							// User.saveLoginInfo(MyDataActivity.this);
							// Toast.makeText(getApplicationContext(),
							// result.getString("Msg"), 1).show();
						} else {// 失败
							;
						}
						Toast.makeText(getApplicationContext(),
								result.getString("Msg"), 1).show();
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
			Toast.makeText(ChangePwdActivity.this, "没有网络", 0).show();
		}
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// if (resultCode == NONE)
	// return;
	// // 拍照
	// if (requestCode == PHOTO_GRAPH) {
	// // 设置文件保存路径
	// File picture = new File(Environment.getExternalStorageDirectory()
	// + "/temp.jpg");
	// startPhotoZoom(Uri.fromFile(picture));
	// }
	// if (data == null)
	// return;
	// // 读取相册缩放图片
	// if (requestCode == PHOTO_ZOOM) {
	// startPhotoZoom(data.getData());
	// }
	// // 处理结果
	// if (requestCode == PHOTO_RESOULT) {
	// Bundle extras = data.getExtras();
	// if (extras != null) {
	// Bitmap photo = extras.getParcelable("data");
	// ByteArrayOutputStream stream = new ByteArrayOutputStream();
	// photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
	// // 此处可以把Bitmap保存到sd卡中
	// if (this.tax_text_view.getTag().toString().equals("true")) {
	// this.tax_text_view.setVisibility(View.GONE);
	// this.tax_text_view.setVisibility(View.VISIBLE);// 打开imageview
	// this.tax_text_view.setImageBitmap(photo); // 把图片显示在ImageView控件上
	// }
	// if (this.business_text_view.getTag().toString().equals("true")) {
	// this.business_text_view.setVisibility(View.GONE);
	// this.business_text_view.setVisibility(View.VISIBLE);// 打开imageview
	// this.business_text_view.setImageBitmap(photo); // 把图片显示在ImageView控件上
	// }
	// }
	// }
	// super.onActivityResult(requestCode, resultCode, data);
	// }

	/**
	 * 裁切图片
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");// 调用Android系统自带的一个图片剪裁页面
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");// 进行修剪
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 130);
		intent.putExtra("outputY", 130);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_RESOULT);
	}
}