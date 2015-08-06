package com.sq.jzq.my;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.HomeActivity;
import com.sq.jzq.R;
import com.sq.jzq.bean.LoginResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.company.ChangePwdActivity;
import com.sq.jzq.company.ComanyMsgActivity;
import com.sq.jzq.company.CompanyInviteRecordActivity;
import com.sq.jzq.company.CompanyResumeCollectionActivity;
import com.sq.jzq.company.MyDataCompanyActivity;
import com.sq.jzq.job.JobDetailActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.SharedPreferencesUtils;
import com.sq.jzq.util.VolleyUtil;

public class LoginOneActivity extends BaseActivity {
	public Button bt_login_login, bt_login_forget;
	public LinearLayout ll_forget_password;
	public EditText et_log_phone, et_log_password;
	String loginInfo;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_login);
		bt_login_login = (Button) findViewById(R.id.bt_login_login); // 登录按钮
		bt_login_forget = (Button) findViewById(R.id.bt_login_forget); // 注册按钮
		ll_forget_password = (LinearLayout) findViewById(R.id.ll_forget_password); // 忘记密码
		et_log_phone = (EditText) findViewById(R.id.et_log_phone); // 忘记密码
		et_log_password = (EditText) findViewById(R.id.et_log_password); // 忘记密码

		loginInfo = getIntent().getStringExtra("loginInfo");
		if (loginInfo == null) {
			loginInfo = "";
		}

		ll_forget_password.setOnClickListener(this);
		bt_login_login.setOnClickListener(this);
		bt_login_forget.setOnClickListener(this);
	}

	public void loadDate() {

		// 登陆
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"LOGIN\",\"Para\":{\"Mb\":\""
				+ et_log_phone.getText().toString() + "\",\"Pwd\":\""
				+ et_log_password.getText().toString() + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				LoginResult s = GsonUtils
						.json2bean(response, LoginResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(LoginOneActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else if (s.Stu == 1 && s.Rst.Scd == 0) {
					Toast.makeText(LoginOneActivity.this, s.Rst.Msg,
							Globals.TOAST_SHORT).show();
				} else {

					Toast.makeText(LoginOneActivity.this, s.Rst.Msg,
							Globals.TOAST_SHORT).show();

					// 保存当前ueer状态值已经账号密码 如果第二次登陆 直接登陆（类似扣扣）就可使用下面账号密码进行登陆
					Globals.USER_TYPE = s.Rst.Etype;
					SharedPreferencesUtils.saveString(LoginOneActivity.this,
							Globals.SPLASH_USERTYPE, s.Rst.Etype);
					SharedPreferencesUtils.saveString(LoginOneActivity.this,
							Globals.USER_PHONE, et_log_phone.getText()
									.toString());
					SharedPreferencesUtils.saveString(LoginOneActivity.this,
							Globals.USER_PASSWORD, et_log_password.getText()
									.toString());
					User.setLoginInfo(et_log_phone.getText().toString(), true,
							s.Rst.Sid + "", s.Rst.PH);

					Globals.FROM = true;
					switch (loginInfo) {
					case "data":// 个人资料
						if ("q".equals(s.Rst.Etype)) {
							startActivity(new Intent(LoginOneActivity.this,
									MyDataActivity.class));
						} else if ("z".equals(s.Rst.Etype)) {
							startActivity(new Intent(LoginOneActivity.this,
									MyDataCompanyActivity.class));
						}
						break;
					case "msg":// 我的消息 /企业端邀约记录

						if ("q".equals(s.Rst.Etype)) {
							startActivity(new Intent(LoginOneActivity.this,
									MyMsgActivity.class));
						} else if ("z".equals(s.Rst.Etype)) {
							startActivity(new Intent(LoginOneActivity.this,
									CompanyInviteRecordActivity.class));
						}

						break;
					case "evaluate":// 我的评价/简历收藏
						if ("q".equals(s.Rst.Etype)) {
							startActivity(new Intent(LoginOneActivity.this,
									MyEvaluateActivity.class));
						} else if ("z".equals(s.Rst.Etype)) {
							startActivity(new Intent(LoginOneActivity.this,
									CompanyResumeCollectionActivity.class));
						}

						break;
					case "pwd":// 修改密码 /企业端消息

						if ("q".equals(s.Rst.Etype)) {
							startActivity(new Intent(LoginOneActivity.this,
									ChagePwdActivity.class));
						} else if ("z".equals(s.Rst.Etype)) {
							startActivity(new Intent(LoginOneActivity.this,
									ComanyMsgActivity.class));
						}

						break;
					case "resume":// 个人简历 /企业端账号安全
						if ("q".equals(s.Rst.Etype)) {

							startActivity(new Intent(LoginOneActivity.this,
									MyResumeActivity.class));
						} else if ("z".equals(s.Rst.Etype)) {
							startActivity(new Intent(LoginOneActivity.this,
									ChangePwdActivity.class));
						}

						break;
					case "sqji":// 申请记录
						startActivity(new Intent(LoginOneActivity.this,
								SqjlActivity.class));
						break;
					case "yyjl":// 邀约记录
						startActivity(new Intent(LoginOneActivity.this,
								SqjlActivity.class));
						break;
					case "gz":// 我的关注
						startActivity(new Intent(LoginOneActivity.this,
								MyGzActivity.class));
						break;
					case "setOpinion":// 意见反馈
						startActivity(new Intent(LoginOneActivity.this,
								MySetOpinionActivity.class));
						break;
					case "login":// 登陆按钮
						Intent i = new Intent();
						// i.putExtra("login", "login");
						i.setClass(LoginOneActivity.this, HomeActivity.class);
						startActivity(i);
						break;
					// case "companyData":// 企业端信息
					// startActivity(new Intent(LoginOneActivity.this,
					// MyDataCompanyActivity.class));
					// break;
					// case "companyInvitation":// 企业端邀约记录
					// startActivity(new Intent(LoginOneActivity.this,
					// CompanyInviteRecordActivity.class));
					// break;
					// case "companyResume":// 企业端简历收藏
					// startActivity(new Intent(LoginOneActivity.this,
					// CompanyResumeCollectionActivity.class));
					// break;
					// case "companyNews":// 企业端消息
					// startActivity(new Intent(LoginOneActivity.this,
					// ComanyMsgActivity.class));
					// break;
					case "jobDetail":// 登陆按钮
						Intent intent = new Intent();
						intent.setClass(LoginOneActivity.this,
								JobDetailActivity.class);
						intent.putExtra(Globals.K_ID, getIntent()
								.getStringExtra(Globals.K_ID));
						startActivity(intent);
						break;
					default:
						break;
					}

					finish();

				}
			}
		}.volleyStringRequestPost(LoginOneActivity.this, params);
	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login_login:
			if ("".equals(et_log_phone.getText().toString())
					&& et_log_phone.getText().toString().length() < 11
					&& "".equals(et_log_password.getText().toString())
					&& et_log_password.getText().toString().length() < 11) {

				Toast.makeText(LoginOneActivity.this, "您输入的手机号、密码有误。",
						Globals.TOAST_SHORT);
			} else {
				loadDate();
			}
			break;
		case R.id.bt_login_forget:
			startActivity(new Intent(LoginOneActivity.this,
					RegistrationActivity.class));
			break;
		case R.id.ll_forget_password:
			Intent intent = new Intent(LoginOneActivity.this,
					RegistrationActivity.class);
			intent.putExtra("forget", "forget");
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
