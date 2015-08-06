package com.sq.jzq.my;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;
import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.HomeActivity;
import com.sq.jzq.R;
import com.sq.jzq.bean.CompanyResult;
import com.sq.jzq.bean.DeleteItemResult;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.TitleBarView;

public class RegistrationActivity extends BaseActivity {
	public Button bt_registration, bt_send_code, bt_registration_q;
	public TitleBarView more_titlebar;
	public TextView agreement_click;
	public EditText et_registration_phone, et_send_code, et_send_password_one,
			et_send_password_two;
	String RegisteredOrForge = "registered";
	String regRoFPwd = "Reg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_registration);
		more_titlebar = (TitleBarView) findViewById(R.id.more_titlebar);
		bt_registration = (Button) findViewById(R.id.bt_registration);
		bt_registration_q = (Button) findViewById(R.id.bt_registration_q);
		bt_send_code = (Button) findViewById(R.id.bt_send_code);
		et_registration_phone = (EditText) findViewById(R.id.et_registration_phone);
		et_send_code = (EditText) findViewById(R.id.et_send_code);
		et_send_password_one = (EditText) findViewById(R.id.et_send_password_one);
		et_send_password_two = (EditText) findViewById(R.id.et_send_password_two);
		agreement_click = (TextView) findViewById(R.id.agreement_click);
		if ("forget".equals(getIntent().getStringExtra("forget"))
				&& more_titlebar != null && bt_registration != null) {
			RegisteredOrForge = "forget";
			more_titlebar.setText("忘记密码");
			bt_registration.setText("完成");
		}
		bt_registration.setOnClickListener(this);
		bt_registration_q.setOnClickListener(this);
		bt_send_code.setOnClickListener(this);

		agreement_click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(RegistrationActivity.this,
						RegistrationAgreementActivity.class));
			}
		});

	}

	public void getCodeRequest() {
		if ("registered".equals(RegisteredOrForge)) {
			regRoFPwd = "Reg";
		} else if ("forget".equals(RegisteredOrForge)) {
			regRoFPwd = "FPwd";
		}
		// 验证码
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"SCD\",\"Para\":{\"Mb\":\""
				+ et_registration_phone.getText().toString() + "\",\"T\":\""
				+ regRoFPwd + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				DeleteItemResult s = GsonUtils.json2bean(response,
						DeleteItemResult.class);
				if (s == null || !(s.Stu == 1)) {

					Toast.makeText(RegistrationActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT).show();

				} else {
					if (s.Rst.Scd == 1) {
						Toast.makeText(RegistrationActivity.this, s.Rst.Msg, 0)
								.show();
					}
					Toast.makeText(RegistrationActivity.this, s.Rst.Msg, 0)
							.show();
				}
			}

		}.volleyStringRequestPost(RegistrationActivity.this, params);
	}

	String userType = null;// 企业类型

	public void regRequest() {
		// 注册
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"REG\",\"Para\":{\"Mb\":\""
				+ et_registration_phone.getText().toString() + "\",\"Pwd\":\""
				+ et_send_password_two.getText().toString() + "\",\"Cod\":\""
				+ et_send_code.getText().toString() + "\",\"Type\":\""
				+ userType + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				DeleteItemResult s = GsonUtils.json2bean(response,
						DeleteItemResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(RegistrationActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT).show();
				} else {
					if (s.Rst.Scd == 1) {
						Toast.makeText(RegistrationActivity.this, s.Rst.Msg, 0)
								.show();
						startActivity(new Intent(RegistrationActivity.this,
								LoginOneActivity.class));
					} else {
						Toast.makeText(RegistrationActivity.this, s.Rst.Msg,
								Globals.TOAST_SHORT).show();
					}

				}
			}

		}.volleyStringRequestPost(RegistrationActivity.this, params);
	}

	public void FpwodRequest() {
		// 忘记
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"FPwd\",\"Para\":{\"Mb\":\""
				+ et_registration_phone.getText().toString() + "\",\"Pwd\":\""
				+ et_send_password_two.getText().toString() + "\",\"Cod\":\""
				+ et_send_code.getText().toString() + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				DeleteItemResult s = GsonUtils.json2bean(response,
						DeleteItemResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(RegistrationActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT).show();
				} else {

					if (s.Rst.Scd == 1) {
						Toast.makeText(RegistrationActivity.this, s.Rst.Msg, 0)
								.show();
						startActivity(new Intent(RegistrationActivity.this,
								LoginOneActivity.class));

					} else {
						Toast.makeText(RegistrationActivity.this, s.Rst.Msg,
								Globals.TOAST_SHORT).show();
					}

				}
			}

		}.volleyStringRequestPost(RegistrationActivity.this, params);

	}

	CountDownTimer mCountDownTimer = null;

	public void getTime() {

		mCountDownTimer = new CountDownTimer(60000, 1000) {
			public void onTick(long millisUntilFinished) {
				bt_send_code.setClickable(false);
				bt_send_code.setText("重新获取" + millisUntilFinished / 1000 + "");
			}

			public void onFinish() {
				bt_send_code.setClickable(true);
				bt_send_code.setText("重新获取");
			}
		};
		if (mCountDownTimer != null) {
			mCountDownTimer.start();
		}

	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.bt_registration:
			userType = "z";
			if ("".equals(et_registration_phone.getText().toString())
					|| "".equals(et_send_code.getText().toString())
					|| "".equals(et_send_password_one.getText().toString())
					|| "".equals(et_send_password_two.getText().toString())) {

				Toast.makeText(RegistrationActivity.this, "您输入的信息不全，请查证后在确定",
						Globals.TOAST_SHORT).show();

			} else {

				if (et_send_password_one.getText().toString()
						.equals(et_send_password_two.getText().toString())) {
					if ("registered".equals(RegisteredOrForge)) {
						regRequest();
					} else if ("forget".equals(RegisteredOrForge)) {
						FpwodRequest();
					}

				} else {
					Toast.makeText(RegistrationActivity.this, "您输入的两次密码不匹配",
							Globals.TOAST_SHORT).show();
				}
			}
			break;
		case R.id.bt_send_code:
			getTime();
			getCodeRequest();
			break;
		case R.id.bt_registration_q:
			userType = "q";
			if ("".equals(et_registration_phone.getText().toString())
					|| "".equals(et_send_code.getText().toString())
					|| "".equals(et_send_password_one.getText().toString())
					|| "".equals(et_send_password_two.getText().toString())) {

				Toast.makeText(RegistrationActivity.this, "您输入的信息不全，请查证后在确定",
						Globals.TOAST_SHORT).show();

			} else {

				if (et_send_password_one.getText().toString()
						.equals(et_send_password_two.getText().toString())) {
					if ("registered".equals(RegisteredOrForge)) {
						regRequest();
					} else if ("forget".equals(RegisteredOrForge)) {
						FpwodRequest();
					}

				} else {
					Toast.makeText(RegistrationActivity.this, "您输入的两次密码不匹配",
							Globals.TOAST_SHORT).show();
				}
			}
			break;
		default:
			break;
		}

	}
}
