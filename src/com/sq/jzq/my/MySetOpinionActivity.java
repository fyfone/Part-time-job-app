package com.sq.jzq.my;

import java.util.HashMap;
import java.util.Map;

import com.baidu.mapapi.utils.e;
import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.R.layout;
import com.sq.jzq.bean.DeleteItemResult;
import com.sq.jzq.bean.MyDate;
import com.sq.jzq.bean.User;
import com.sq.jzq.company.ChangePwdActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.TitleBarView;
import com.sq.jzq.views.TitleBarView.OnClickEnterButtonListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MySetOpinionActivity extends BaseActivity {

	public TitleBarView job_titlebar;
	private EditText et_opinion_ems, ey_opinion_inif;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_my_set_opinion);
		job_titlebar = (TitleBarView) findViewById(R.id.job_titlebar);
		et_opinion_ems = (EditText) findViewById(R.id.et_opinion_ems);
		ey_opinion_inif = (EditText) findViewById(R.id.ey_opinion_inif);
		rightButton();

	}

	@Override
	public void widgetClick(View v) {

	}

	// 处理右键
	public void rightButton() {

		if (job_titlebar != null) {
			job_titlebar
					.setClickEnterButtonListener(new OnClickEnterButtonListener() {

						@Override
						public void onClickEnterButton(View v) {
							if ("".equals(et_opinion_ems.getText().toString())
									&& "".equals(ey_opinion_inif.getText()
											.toString())) {
								Toast.makeText(MySetOpinionActivity.this,
										"您输入的意见内容或名称不能为空！", 0).show();

							} else {

								if (User.isLogin) {

									// 调教反馈
									Map<String, String> params = new HashMap<String, String>();
									params.put(Globals.WS_POST_KEY,
											"{\"Ac\":\"OP\",\"Para\":{\"sid\":\""
													+ User.sessionId
													+ "\";\"on\":\""
													+ ey_opinion_inif.getText()
															.toString()
													+ "\";\"email\":\""
													+ et_opinion_ems.getText()
															.toString()
													+ "\"}}");
									new VolleyUtil() {

										public void analysisData(String response) {

											DeleteItemResult s = GsonUtils
													.json2bean(
															response,
															DeleteItemResult.class);
											if (s == null || !(s.Stu == 1)) {
												Toast.makeText(
														MySetOpinionActivity.this,
														Globals.SER_ERROR,
														Globals.TOAST_SHORT)
														.show();
											} else {

												if (s.Rst.Scd == 1) {
													Toast.makeText(
															MySetOpinionActivity.this,
															s.Rst.Msg, 0)
															.show();
													finish();

												} else {
													Toast.makeText(
															MySetOpinionActivity.this,
															s.Rst.Msg,
															Globals.TOAST_SHORT)
															.show();
												}

											}

										}
									}.volleyStringRequestPost(
											MySetOpinionActivity.this, params);

								} else {
									Toast.makeText(MySetOpinionActivity.this,
											"你还没有登陆请登陆", Globals.TOAST_SHORT)
											.show();
									Intent intents = new Intent(
											MySetOpinionActivity.this,
											LoginOneActivity.class);
									intents.putExtra("loginInfo", "setOpinion");
									startActivity(intents);

								}

							}

						}
					});
		}
	}
}
