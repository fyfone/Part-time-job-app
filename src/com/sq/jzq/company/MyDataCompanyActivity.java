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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.MyCompanyDateResult;
import com.sq.jzq.bean.MyCompanyDateResult.CompanyDateResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.my.MyDataActivity.UpdateIcon;
import com.sq.jzq.my.MyDataActivity;
import com.sq.jzq.my.SelectPicActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.RestTask;
import com.sq.jzq.util.RestUtil;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.util.RestTask.ResponseCallback;
import com.sq.jzq.views.TitleBarView;

public class MyDataCompanyActivity extends BaseActivity {
	CompanyDateResult myInfo;
	private ImageView iv_my_head;
	private EditText et_my_name, et_my_sex, et_my_age, et_my_ems, et_my_qq,
			et_my_website, et_my_contact, et_my_contacts_two;
	public TitleBarView my_titlebar;
	private LinearLayout llQsz;
	private TextView ey_opinion_inif;
	private String type = "7";

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_my_company_data);
		my_titlebar = (TitleBarView) findViewById(R.id.my_titlebar);
		iv_my_head = (ImageView) findViewById(R.id.iv_my_head);

		et_my_name = (EditText) findViewById(R.id.et_my_name);
		et_my_sex = (EditText) findViewById(R.id.et_my_sex);
		et_my_age = (EditText) findViewById(R.id.et_my_age);
		et_my_ems = (EditText) findViewById(R.id.et_my_ems);
		et_my_qq = (EditText) findViewById(R.id.et_my_qq);
		et_my_website = (EditText) findViewById(R.id.et_my_website);
		et_my_contact = (EditText) findViewById(R.id.et_my_contact);
		et_my_contacts_two = (EditText) findViewById(R.id.et_my_contacts_two);
		ey_opinion_inif = (TextView) findViewById(R.id.ey_opinion_inif);
		// llQsz = (LinearLayout) findViewById(R.id.my_data_qsz);

		iv_my_head.setOnClickListener(this);
		// llQsz.setOnClickListener(this);
		getDate();
	}

	// 处理右键

	public void getDate() {
		// 获取个人信息
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"CUI\",\"Para\":{\"Sid\":\""
				+ User.sessionId + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				MyCompanyDateResult s = GsonUtils.json2bean(response,
						MyCompanyDateResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(MyDataCompanyActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT).show();
				} else {
					if (!"".equals(s.Rst)) {
						myInfo = s.Rst.get(0);
					}
				}
				initMyInfoView();

			}
		}.volleyStringRequestPost(MyDataCompanyActivity.this, params);

	}

	private void initMyInfoView() {
		if (myInfo != null) {
			if (myInfo.LO != null && !"".equals(myInfo.LO)) {
				ImageLoader.getInstance().displayImage(myInfo.LO, iv_my_head);
			}
			// ImageLoader.getInstance().displayImage(myInfo.IT,
			// iv_my_comapany_date_tax); //公司税务照
			// ImageLoader.getInstance().displayImage(myInfo.IH,
			// iv_my_comapany_date_license);//公司营业执照
			et_my_name.setText(myInfo.CN);
			et_my_sex.setText(myInfo.CU);
			et_my_age.setText(myInfo.CE);
			et_my_ems.setText(myInfo.CNE);
			et_my_qq.setText(myInfo.SE);
			et_my_website.setText(myInfo.CT);
			et_my_contact.setText(myInfo.PE);
			et_my_contacts_two.setText(myInfo.ME);
			ey_opinion_inif.setText(myInfo.INS);
		}
	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.iv_my_head:
			Intent intent = new Intent();
			intent.setClass(MyDataCompanyActivity.this, SelectPicActivity.class);
			intent.putExtra("type", "icon");
			startActivityForResult(intent, 7); 
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		type = requestCode+"";
		switch (resultCode) {  
			case 1:
				if (data != null) {  
		            //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意  
		            Uri mImageCaptureUri = data.getData(); 
		            //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取  
		            if (mImageCaptureUri != null) {  
		                Bitmap image;  
		                try {  
		                    //这个方法是根据Uri获取Bitmap图片的静态方法  
		                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);  
		                    if (image != null) {  
//		                    	CacheImage.setImageForImageView(User.IconPath, ivIcon);
		                    	if("7".equals(type)) {
		                    		iv_my_head.setImageBitmap(image); 
		                    	}
		                    }  
		                } catch (Exception e) {  
		                    e.printStackTrace();  
		                }  
		            } else {  
		                Bundle extras = data.getExtras();  
		                if (extras != null) {  
		                    //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片  
		                    Bitmap image = extras.getParcelable("data");  
		                    if (image != null) { 
		                    	if("7".equals(type)) {
		                    		iv_my_head.setImageBitmap(image); 
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
	
	public class UpdateIcon implements  ResponseCallback{
		private AlertDialog adUpudateIcon;
		Bitmap img;
		
		public void update(String t) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				String req = "{\"Ac\":\"TPSC\",\"Para\":{\"SId\":\""
						+ User.sessionId + "\",\"T\":\""+t+"\"}}";
				params.add(new BasicNameValuePair(Globals.WS_POST_KEY, req));
				Log.i(Globals.LOG_TAG, params.get(0).getValue());
				RestTask postTask = null;
				if("7".equals(type)) {
					iv_my_head.setDrawingCacheEnabled(true);
					img = Bitmap.createBitmap(iv_my_head.getDrawingCache());
					iv_my_head.setDrawingCacheEnabled(false);
				}
				
				File imgFile = new File(getExternalCacheDir(), Globals.CACHE_FILE_NAME);
				FileOutputStream fos = new FileOutputStream(imgFile);
				img.compress(CompressFormat.PNG, 0, fos);
				fos.flush();
				fos.close();
				postTask = RestUtil.obtainMultipartPostTask(Globals.WS_URI_POTO, params, imgFile, Globals.CACHE_FILE_NAME);
				
	            postTask.setResponseCallback(this);
	            postTask.execute();
	            
	            adUpudateIcon = new AlertDialog.Builder(MyDataCompanyActivity.this).create();
	            adUpudateIcon.show();
	            adUpudateIcon.getWindow().setContentView(R.layout.dialog);
//				TextView title = (TextView) adUpudateIcon.getWindow().findViewById(
//						R.id.dialog_title);
//				title.setText(getResources().getString(R.string.ts));
			} catch (Exception e) {
				Log.i(Globals.LOG_TAG, "Exception:" + e.getMessage());
			}
		}
		@Override
		public void onRequestSuccess(String response) {
			try {
				JSONObject entity = new JSONObject(response);
				if(entity.getString("Stu").equals("1")){
					JSONObject result = entity.getJSONObject("Rst");
					if(result == null){
						Log.i(Globals.LOG_TAG, response);
					}else{
						if(result.getString("Scd").equals("1")){//成功
							if("7".equals(type)) {
								User.setIconPath(result.getString("Po"));
							}
							
//							User.saveLoginInfo(MyDataActivity.this);
//							Toast.makeText(getApplicationContext(), result.getString("Msg"), 1).show();
						}else{//失败
							;
						}
						Toast.makeText(getApplicationContext(), result.getString("Msg"), 1).show();
					}
				}else{
					Log.i(Globals.LOG_TAG, "Json Error -- Status:" + entity.getString("Stu") + ",Message:" + entity.getString("Msg"));
				}
			} catch (JSONException e) {
				Log.i(Globals.LOG_TAG, e.getMessage());
			}
			if(adUpudateIcon != null) {
				adUpudateIcon.dismiss();
	        }
		}
		
		@Override
		public void onRequestError(Exception error) {
			if (adUpudateIcon != null) {
				adUpudateIcon.dismiss();
			}
			Toast.makeText(MyDataCompanyActivity.this,
					"没有网络", 0).show();
		}
	}

}
