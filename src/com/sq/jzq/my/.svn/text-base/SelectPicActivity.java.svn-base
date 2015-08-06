package com.sq.jzq.my;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sq.jzq.R;




public class SelectPicActivity extends Activity implements OnClickListener {
	private LinearLayout pickPhot; // 从相册选择
	private LinearLayout photograph; // 拍照
	private LinearLayout cancel; // 取消
//	private TextView txvTitle;   //头部
	private String type;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_pic);
		intent = getIntent();
		type = intent.getStringExtra("type");

		pickPhot = (LinearLayout) findViewById(R.id.update_icon_pick_photo);
		photograph = (LinearLayout) findViewById(R.id.update_icon_photograph);
		cancel = (LinearLayout) findViewById(R.id.update_icon_cancle);
//		txvTitle = (TextView)findViewById(R.id.spinner_dialog_title);
//		if(type.equals("qsz")) {
//			txvTitle.setText("上传全身照");
//		}

		pickPhot.setOnClickListener(this);
		photograph.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent;
		switch (arg0.getId()) {
		// 选择照片
		case R.id.update_icon_pick_photo:
			intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, 1);
			break;
		// 拍照
		case R.id.update_icon_photograph:
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, 2);
			break;
		case R.id.update_icon_cancle:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case 1:
				if(type.equals("icon")) {
					startPhotoZoom(data.getData());
				}else {
					if (data != null) {
						if (data.getExtras() != null) {
							intent.putExtras(data.getExtras());
						} else if (data.getData() != null) {
							intent.setData(data.getData());
						}
						setResult(1, intent);
						finish();
					}
				}
				break;
			case 2:
				if (data != null) {
					if (data.getExtras() != null) {
						intent.putExtras(data.getExtras());
					} else if (data.getData() != null) {
						intent.setData(data.getData());
					}
					setResult(1, intent);
					finish();
				}
				break;
			case 3:
				if (data != null) {
					if (data.getExtras() != null)
						intent.putExtras(data.getExtras());
					if (data.getData() != null)
						intent.setData(data.getData());
					setResult(1, intent);
					finish();
				}
				break;
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

}
