package com.sq.jzq;

import com.sq.jzq.util.RequestManager;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements OnClickListener {
	// 点击事件
	public abstract void widgetClick(View v);

	// 初始化view
	public abstract void initWidget();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initWidget();
		// au = VolleyUtil.au;

	}

	@Override
	public void onClick(View v) {
		widgetClick(v);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		RequestManager.cancelAll(this);
	}

}
