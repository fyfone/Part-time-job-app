package com.sq.jzq.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.GuanZhuResult;
import com.sq.jzq.bean.JobDetailResult;
import com.sq.jzq.bean.JobDetailResult.JobDetail;
import com.sq.jzq.bean.User;
import com.sq.jzq.home.adapter.HorizontalListViewAdapter;
import com.sq.jzq.my.LoginOneActivity;
import com.sq.jzq.my.MyDataActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.DeleteFactory;
import com.sq.jzq.views.HorizontialListView;
import com.sq.jzq.vip.VipCompanyDetailsActivity;

public class JobDetailActivity extends BaseActivity {

	private String id;
	private TextView txvTitle, txvSalary, txvUnit, txvJobType, txvNum, txvDate,
			txvStartTime, txvEndTime, txvLoca, txvDescribe, txvRequire;
	private HorizontialListView horizonList;
	private List<String> names, icons;
	HorizontalListViewAdapter hlAdapter;
	private LinearLayout llGz, llSq, llButtom;
	private ImageView ivGz, ivSq;
	private TextView txvGz, txvSq;
	private JobDetail jd;
	private Boolean isGz = false;
	private String loginInfo = "jobDetail";
	private LinearLayout llTitle;
	private String CId;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_job_detail);

		names = new ArrayList<String>();
		icons = new ArrayList<String>();

		id = this.getIntent().getStringExtra(Globals.K_ID);
		txvTitle = (TextView) findViewById(R.id.jd_title); // 公司名称
		txvSalary = (TextView) findViewById(R.id.jd_salary); // 薪水
		txvUnit = (TextView) findViewById(R.id.jd_unit); // 薪水单位
		txvJobType = (TextView) findViewById(R.id.jd_work_type); // 职位类型
		txvNum = (TextView) findViewById(R.id.jd_num); // 招聘人数
		txvDate = (TextView) findViewById(R.id.jd_date); // 发布时间
		txvStartTime = (TextView) findViewById(R.id.jd_start_date); // 开始时间
		txvEndTime = (TextView) findViewById(R.id.jd_end_date); // 结束时间
		txvLoca = (TextView) findViewById(R.id.jd_loca); // 公司地址
		txvDescribe = (TextView) findViewById(R.id.jd_describe); // 职位描述
		txvRequire = (TextView) findViewById(R.id.jd_require); // 职位要求
		llTitle = (LinearLayout)findViewById(R.id.jd_t);
		horizonList = (HorizontialListView) findViewById(R.id.jd_horizon_listview);
		llGz = (LinearLayout) findViewById(R.id.jd_gz);
		llSq = (LinearLayout) findViewById(R.id.jd_sq);
		llButtom = (LinearLayout)findViewById(R.id.jd_buttom);
		ivGz = (ImageView) findViewById(R.id.jd_gz_icon);
		ivSq = (ImageView) findViewById(R.id.jd_sq_icon);
		txvGz = (TextView) findViewById(R.id.jd_gz_text);
		txvSq = (TextView) findViewById(R.id.jd_sq_text);
		
		if(Globals.USER_TYPE.equals("q")) {
			llButtom.setVisibility(View.VISIBLE);
		}

		hlAdapter = new HorizontalListViewAdapter(this);
		
		llGz.setOnClickListener(this);
		loadData();
	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.jd_gz:
			if (User.isLogin) {// 登录
				if(isGz) {
					guanZhu();
				}else {
					quXiaoGuanZhu();
				}
			}else {
				Intent intent = new Intent();
				intent.putExtra("loginInfo", loginInfo);
				intent.putExtra(Globals.K_ID, id);
				intent.setClass(JobDetailActivity.this, LoginOneActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	private void loadData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"GJID\",\"Para\":{\"Id\":\""
				+ id + "\",\"Sid\":\"" + User.getSessionId() + "\"}}");

		new VolleyUtil() {

			public void analysisData(String response) {
				JobDetailResult s = GsonUtils.json2bean(response,
						JobDetailResult.class);

				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(JobDetailActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {
					jd = s.Rst;
					txvTitle.setText(jd.CN);
					txvSalary.setText(jd.S + "元/");
					txvUnit.setText(jd.U);
					txvJobType.setText(jd.JT);
					txvNum.setText("招" + jd.PC + "人");
					txvDate.setText(jd.RT);
					txvStartTime.setText(jd.BD);
					txvEndTime.setText(jd.ED);
					txvLoca.setText(jd.A);
					txvDescribe.setText(jd.JC);
					txvRequire.setText(jd.R);
					CId = jd.UID;
					llTitle.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(JobDetailActivity.this,
									VipCompanyDetailsActivity.class);
							intent.putExtra("companyID", CId);
							startActivity(intent);
						}
					});
					
					for (int i = 0; i < jd.Lst.size(); i++) {
						names.add(jd.Lst.get(i).NE);
						icons.add(jd.Lst.get(i).PH);
					}
					hlAdapter.setData(names, icons);
					horizonList.setAdapter(hlAdapter);

					if (jd.GZ.equals("0")) {// 已关注
						txvGz.setText("取消关注");
						isGz = false;
					} else {
						txvGz.setText("关注");
						isGz = true;
					}
					if (jd.GC.equals("0")) {// 已申请
						ivSq.setImageDrawable(getResources().getDrawable(
								R.drawable.job_content_shc_2));
						txvSq.setTextColor(getResources().getColor(
								R.color.text_666));
					} else {
						ivSq.setImageDrawable(getResources().getDrawable(
								R.drawable.job_content_shc));
						txvSq.setTextColor(getResources().getColor(
								R.color.text_red));
						llSq.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								if(User.isLogin) {
									isPerfect();
								}else{
									Intent intent = new Intent();
									intent.setClass(JobDetailActivity.this, LoginOneActivity.class);
									intent.putExtra("loginInfo", loginInfo);
									intent.putExtra(Globals.K_ID, id);
									startActivity(intent);
								}
								
							}
						});
					}
				}

			}

		}.volleyStringRequestPost(JobDetailActivity.this, params);
	}

	private void guanZhu() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"TJGZ\",\"Para\":{\"Id\":\""
				+ id + "\",\"Sid\":\"" + User.getSessionId()
				+ "\",\"S\":\"0\"}}");
		new VolleyUtil() {

			@Override
			public <T> void analysisData(String response) {
				GuanZhuResult s = GsonUtils.json2bean(response,
						GuanZhuResult.class);

				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(JobDetailActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {
					if(s.Rst.Scd == 1) {
						Toast.makeText(JobDetailActivity.this, "关注成功",
								Globals.TOAST_SHORT).show();
						isGz = false;
						txvGz.setText("取消关注");
					}else {
						Toast.makeText(JobDetailActivity.this, s.Rst.Msg,
								Globals.TOAST_SHORT).show();
					}
				}
			}
		}.volleyStringRequestPost(JobDetailActivity.this, params);
	}
	
	private void quXiaoGuanZhu() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"QXGZ\",\"Para\":{\"Id\":\""
				+ id + "\",\"Sid\":\"" + User.getSessionId()
				+ "\",\"S\":\"0\"}}");
		new VolleyUtil() {

			@Override
			public <T> void analysisData(String response) {
				GuanZhuResult s = GsonUtils.json2bean(response,
						GuanZhuResult.class);

				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(JobDetailActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {
					if(s.Rst.Scd == 1) {
						Toast.makeText(JobDetailActivity.this, "取消关注成功",
								Globals.TOAST_SHORT).show();
						isGz = true;
						txvGz.setText("关注");
					}else {
						Toast.makeText(JobDetailActivity.this, s.Rst.Msg,
								Globals.TOAST_SHORT).show();
					}
				}
			}
		}.volleyStringRequestPost(JobDetailActivity.this, params);
	}
	
	private void isPerfect() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"CUIS\",\"Para\":{\"Sid\":\"" + User.getSessionId() + "\"}}");
		new VolleyUtil() {

			@Override
			public <T> void analysisData(String response) {
				GuanZhuResult s = GsonUtils.json2bean(response,
						GuanZhuResult.class);

				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(JobDetailActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {
					if(s.Rst.Scd == 0) {//没完善
						Log.i("Response", "mei wan shan");
						DeleteFactory d = new DeleteFactory() {

							@Override
							public void determineButton() {
								Intent intent = new Intent();
								intent.setClass(JobDetailActivity.this, MyDataActivity.class);
								startActivity(intent);
							}
						}.deleteDialog(JobDetailActivity.this, "是否去完善个人资料", "", "");
						
					}else {
						//去申请
						Log.i("Response", "yi wan shan");
						shenQing();
					}
				}
			}
		}.volleyStringRequestPost(JobDetailActivity.this, params);
		
	}

	
	private void shenQing() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"AFJ\",\"Para\":{\"Jid\":\""
				+ id + "\",\"Sid\":\"" + User.getSessionId()
				+ "\"}}");
		new VolleyUtil() {

			@Override
			public <T> void analysisData(String response) {
				GuanZhuResult s = GsonUtils.json2bean(response,
						GuanZhuResult.class);

				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(JobDetailActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {
					if(s.Rst.Scd == 1) {
						Toast.makeText(JobDetailActivity.this, "申请职位成功",
								Globals.TOAST_SHORT).show();
						llSq.setOnClickListener(null);
						ivSq.setImageDrawable(getResources().getDrawable(
								R.drawable.job_content_shc_2));
						txvSq.setTextColor(getResources().getColor(
								R.color.text_666));
					}else {
						Toast.makeText(JobDetailActivity.this, s.Rst.Msg,
								Globals.TOAST_SHORT).show();
					}
				}
			}
		}.volleyStringRequestPost(JobDetailActivity.this, params);
	}
}
