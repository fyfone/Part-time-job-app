package com.sq.jzq.my;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.R;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

public class MySetPageActivity extends BaseActivity {

	private TextView txvVersion;
	private String version;
	@Override
	public void initWidget() {
		setContentView(R.layout.activity_my_set_page);

		txvVersion = (TextView)findViewById(R.id.msp_version);
		
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			version = info.versionName;
			txvVersion.setText(version);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void widgetClick(View v) {

	}
}
