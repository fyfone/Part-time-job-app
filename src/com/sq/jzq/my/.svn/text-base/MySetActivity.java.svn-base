package com.sq.jzq.my;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.User;
import com.sq.jzq.job.JobDetailActivity;
import com.sq.jzq.util.SharedPreferencesUtils;
import com.sq.jzq.util.UpdateVersionService;
import com.sq.jzq.views.DeleteFactory;

public class MySetActivity extends BaseActivity {

	public RelativeLayout my_set_opinion, my_set_Signout, my_set_edition,
			my_set_page,my_set_disclaimer;
	public TextView txvVer;
	public String version;
	private UpdateVersionService updateVersionService;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_my_set);
		my_set_opinion = (RelativeLayout) findViewById(R.id.my_set_opinion);
		my_set_Signout = (RelativeLayout) findViewById(R.id.my_set_Signout);
		my_set_edition = (RelativeLayout) findViewById(R.id.my_set_edition);
		my_set_page = (RelativeLayout) findViewById(R.id.my_set_page);
		my_set_disclaimer = (RelativeLayout) findViewById(R.id.my_set_disclaimer);
		txvVer = (TextView)findViewById(R.id.my_set_ver);
		
		my_set_opinion.setOnClickListener(this);
		my_set_page.setOnClickListener(this);
		my_set_edition.setOnClickListener(new verCheckOnc());
		my_set_disclaimer.setOnClickListener(this);
		my_set_Signout.setOnClickListener(this);
		
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			version = info.versionName;
			txvVer.setText("当前版本号"+version);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.my_set_opinion://
			if (User.isLogin) {
				startActivity(new Intent(MySetActivity.this,
						MySetOpinionActivity.class));
			} else {
				Toast.makeText(MySetActivity.this, "你还没有登陆请登陆",
						Globals.TOAST_SHORT).show();
				Intent intents = new Intent(MySetActivity.this,
						LoginOneActivity.class);
				intents.putExtra("loginInfo", "setOpinion");
				startActivity(intents);
			}
			break;
		case R.id.my_set_page://
			startActivity(new Intent(MySetActivity.this,
					MySetPageActivity.class));
			break;
		case R.id.my_set_Signout://
			if(User.isLogin) {
				DeleteFactory d = new DeleteFactory() {

					@Override
					public void determineButton() {
						SharedPreferencesUtils.saveString(MySetActivity.this,
								Globals.USER_PHONE, "");
						SharedPreferencesUtils.saveString(MySetActivity.this,
								Globals.USER_PASSWORD, "");
						SharedPreferencesUtils.saveString(MySetActivity.this,
								Globals.USER_PASSWORD, "false");
						Intent intent = new Intent();
						intent.setClass(MySetActivity.this, LoginOneActivity.class);
						intent.putExtra("loginInfo", "login");
						startActivity(intent);
						User.setLoginInfo("", false, "", "");
						finish();
					}
				}.deleteDialog(MySetActivity.this, "是否退出登录", "", "");
			}
			break;
		case R.id.my_set_disclaimer:
			startActivity(new Intent(MySetActivity.this,
					MySetDisclaimerActivity.class));
			break;
		default:
			break;
		}

	}
	
	private class verCheckOnc implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			updateVersionService = new UpdateVersionService(Globals.VERSION_XML, MySetActivity.this);// 创建更新业务对象
			updateVersionService.checkUpdateSD();// 调用检查更新的方法,如果可以更新.就更新
		}
		
	}

}
