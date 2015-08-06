package com.sq.jzq;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.sq.jzq.bean.LoginResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.home.MyLocationListener;
import com.sq.jzq.util.AppUtil;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.SharedPreferencesUtils;

public class SplashScreenActivity extends BaseActivity {
	private final int SPLASH_DISPLAY_LENGHT = 1500; // 延迟3秒

	private Button btnJobhunter; // 求职者
	private Button btnRecruiter; // 招聘者
	private SharedPreferences myPrefer;
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		myPrefer = getSharedPreferences(Globals.SPLASH_USERTYPE,
				Activity.MODE_PRIVATE);

		initLocation();
		btnJobhunter = (Button) findViewById(R.id.guide_jobhunter);
		btnRecruiter = (Button) findViewById(R.id.guide_recruiter);
		if (myPrefer.contains(Globals.SPLASH_USERTYPE)) {
			btnJobhunter.setVisibility(View.GONE);
			btnRecruiter.setVisibility(View.GONE);
			Globals.USER_TYPE = myPrefer
					.getString(Globals.SPLASH_USERTYPE, "q");
			if ("".equals(SharedPreferencesUtils.getString(
					SplashScreenActivity.this, Globals.USER_PHONE, null))
					&& "".equals(SharedPreferencesUtils.getString(
							SplashScreenActivity.this, Globals.USER_PASSWORD,
							null))) {

				new Handler().postDelayed(new Runnable() {
					public void run() {

						Intent mainIntent = new Intent(
								SplashScreenActivity.this, HomeActivity.class);
						SplashScreenActivity.this.startActivity(mainIntent);
						SplashScreenActivity.this.finish();
					}
				}, SPLASH_DISPLAY_LENGHT);

			} else {
				initLogin();
			}
		} else {
			btnJobhunter.setOnClickListener(new RecruiterButtonClickListener());
			btnRecruiter.setOnClickListener(new RecruiterButtonClickListener());
		}

	}

	public void initLocation() {
		mLocationClient = new LocationClient(getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
		if (mLocationClient != null) {

			mLocationClient.start();

		}

	}

	public void initLogin() {

		// 登陆
		final Map<String, String> params = new HashMap<String, String>();
		params.put(
				Globals.WS_POST_KEY,
				"{\"Ac\":\"LOGIN\",\"Para\":{\"Mb\":\""
						+ SharedPreferencesUtils.getString(
								SplashScreenActivity.this, Globals.USER_PHONE,
								null)
						+ "\",\"Pwd\":\""
						+ SharedPreferencesUtils.getString(
								SplashScreenActivity.this,
								Globals.USER_PASSWORD, null) + "\"}}");
		RequestQueue mRequestQueue = Volley
				.newRequestQueue(SplashScreenActivity.this);
		Log.i("Response", "paramsss=" + params);
		StringRequest sr = new StringRequest(Request.Method.POST,
				Globals.WS_URI, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("fyfone", "这里打印 首页是否登陆");
						Log.i("fyfone", response);
						if (response != null) {
							LoginResult s = GsonUtils.json2bean(response,
									LoginResult.class);
							if (s == null || !(s.Stu == 1)) {
//								Toast.makeText(SplashScreenActivity.this,
//										Globals.SER_ERROR, Globals.TOAST_SHORT)
//										.show();
							} else if (s.Stu == 1 && s.Rst.Scd == 0) {
//								Toast.makeText(SplashScreenActivity.this,
//										s.Rst.Msg, Globals.TOAST_SHORT).show();
							} else {

								Toast.makeText(SplashScreenActivity.this,
										s.Rst.Msg, Globals.TOAST_SHORT);
								Globals.USER_TYPE = s.Rst.Etype;
								User.setLoginInfo(SharedPreferencesUtils
										.getString(SplashScreenActivity.this,
												Globals.USER_PHONE, null),
										true, s.Rst.Sid + "", s.Rst.PH);

							}
						}
						Intent mainIntent = new Intent(
								SplashScreenActivity.this, HomeActivity.class);
						SplashScreenActivity.this.startActivity(mainIntent);
						SplashScreenActivity.this.finish();

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// error
						Log.i("Response", "error" + error.getMessage());
						Toast.makeText(Globals.context, "您的网络有问题请查证后启动!", 0)
								.show();
						if (AppUtil.networkCheck() == false) {
							Toast.makeText(Globals.context, "没有网络", 0).show();
						}
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				return params;
			}
		};
		sr.setRetryPolicy(new DefaultRetryPolicy(3000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		mRequestQueue.add(sr);
		if (sr.getRetryPolicy().getCurrentTimeout() > 6000) {
			new Handler().postDelayed(new Runnable() {
				public void run() {

					Intent mainIntent = new Intent(SplashScreenActivity.this,
							HomeActivity.class);
					SplashScreenActivity.this.startActivity(mainIntent);
					SplashScreenActivity.this.finish();
				}
			}, sr.getRetryPolicy().getCurrentTimeout());
		}
	}

	private class RecruiterButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View arg) {
			// 记录首次登录状态
			SharedPreferences.Editor edit = myPrefer.edit();
			switch (arg.getId()) {
			case R.id.guide_jobhunter:
				edit.putString(Globals.SPLASH_USERTYPE, "q");
				Globals.USER_TYPE = "q";
				break;
			case R.id.guide_recruiter:
				edit.putString(Globals.SPLASH_USERTYPE, "z");
				Globals.USER_TYPE = "z";
				break;
			default:
				break;
			}
			edit.apply();
			// 进入兼职列表
			Intent mainIntent = new Intent(SplashScreenActivity.this,
					HomeActivity.class);
			SplashScreenActivity.this.startActivity(mainIntent);
			SplashScreenActivity.this.finish();
		}

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub

	}

}