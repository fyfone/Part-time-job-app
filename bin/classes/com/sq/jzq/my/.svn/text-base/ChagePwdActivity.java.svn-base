package com.sq.jzq.my;

import java.util.HashMap;
import java.util.Map;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.R.layout;
import com.sq.jzq.bean.CompanyResult;
import com.sq.jzq.bean.DeleteItemResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.DeleteFactory;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChagePwdActivity extends BaseActivity {
	private EditText et_uppass_pwd, et_uppass_nwd, et_uppass_nwd_two;
	private Button bt_registration;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_chage_pwd);
		et_uppass_pwd = (EditText) findViewById(R.id.et_uppass_pwd);
		et_uppass_nwd = (EditText) findViewById(R.id.et_uppass_nwd);
		et_uppass_nwd_two = (EditText) findViewById(R.id.et_uppass_nwd_two);
		bt_registration = (Button) findViewById(R.id.bt_registration);
		bt_registration.setOnClickListener(this);

	}

	public void upPasswordDate() {

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
					Toast.makeText(ChagePwdActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {

					if (s.Rst.Scd == 1) {
						Toast.makeText(ChagePwdActivity.this, s.Rst.Msg, 0)
								.show();
						finish();

					} else {
						Toast.makeText(ChagePwdActivity.this, s.Rst.Msg,
								Globals.TOAST_SHORT).show();
					}

				}

			}

		}.volleyStringRequestPost(ChagePwdActivity.this, params);

	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.bt_registration:
			if ("".equals(et_uppass_pwd.getText().toString())
					&& "".equals(et_uppass_nwd.getText().toString())
					&& "".equals(et_uppass_nwd_two.getText().toString())) {
				Toast.makeText(ChagePwdActivity.this, "您输入的账号或者密码不能为空",
						Globals.TOAST_SHORT).show();

			} else {
				if (et_uppass_nwd.getText().toString()
						.equals(et_uppass_nwd_two.getText().toString())) {
					upPasswordDate();
				} else {
					Toast.makeText(ChagePwdActivity.this, "您输入的两次密码不匹配",
							Globals.TOAST_SHORT).show();
				}

			}

			break;
		default:
			break;
		}

	}

}
