package com.sq.jzq.my;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.R.layout;
import com.sq.jzq.adapter.JobAttentionAdapter;
import com.sq.jzq.adapter.MyAttentionCompanyAdapter;
import com.sq.jzq.adapter.MyAttentionIndividualAdapter;
import com.sq.jzq.adapter.VipListUserAdapter;
import com.sq.jzq.adapter.VipRecruitersAdapter;
import com.sq.jzq.adapter.VipWantedAdapter;
import com.sq.jzq.bean.MyAttentionCompanyResult;
import com.sq.jzq.bean.MyAttentionCompanyResult.AttentionCompany;
import com.sq.jzq.bean.MyAttentionIndividualResult;
import com.sq.jzq.bean.MyAttentionIndividualResult.IndividualCompany;
import com.sq.jzq.bean.MyMsgCircle;
import com.sq.jzq.bean.User;
import com.sq.jzq.bean.ApplyRecordResult.ApplyRecord;
import com.sq.jzq.bean.MyMsgCircle.MyCircleResult;
import com.sq.jzq.job.JobDetailActivity;
import com.sq.jzq.util.AppUtil;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.vip.VipCompanyDetailsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyGzActivity extends BaseActivity implements IXListViewListener {
	private XListView xlv_my_company, xlv_my_position;
	private LinearLayout vip_sel_loca_ll, vip_sel_job_ll;
	private View line_bule_l, line_bule_r;
	private String idCompany = "", idIndividual = ""; // 第一条的信息ID
	private int pageCompany = 1, pageIndividual = 1;
	private MyAttentionCompanyAdapter myAttentionCompanyAdapter;
	private MyAttentionIndividualAdapter myAttentionIndividualAdapter;

	private List<AttentionCompany> attentionCompanies;
	private List<IndividualCompany> individualCompanies;

	private Handler attentionCompanyHandler;
	private Handler individualAttentionHandler;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_my_gz);
		xlv_my_company = (XListView) findViewById(R.id.xlv_my_company);
		xlv_my_position = (XListView) findViewById(R.id.xlv_my_position);
		xlv_my_company.setXListViewListener(this);
		xlv_my_company.setPullLoadEnable(true);
		xlv_my_position.setXListViewListener(this);
		xlv_my_position.setPullLoadEnable(true);
		myAttentionCompanyAdapter = new MyAttentionCompanyAdapter();
		myAttentionIndividualAdapter = new MyAttentionIndividualAdapter();

		attentionCompanyHandler = new Handler();
		individualAttentionHandler = new Handler();

		vip_sel_loca_ll = (LinearLayout) findViewById(R.id.vip_sel_loca_ll);
		vip_sel_job_ll = (LinearLayout) findViewById(R.id.vip_sel_job_ll);
		line_bule_l = (View) findViewById(R.id.line_bule_l);
		line_bule_r = (View) findViewById(R.id.line_bule_r);

		xlv_my_company
				.setOnItemClickListener(new ListViewItemAttentionClickListener());
		xlv_my_position.setOnItemClickListener(new ListViewItemClickListener());

		vip_sel_loca_ll.setOnClickListener(this);
		vip_sel_job_ll.setOnClickListener(this);

		loadData();
	}

	int slideSource = 1;

	private void loadData() {
		getAttentionCompanyDate();
		getIndividualAttentionDate();
	}

	public void getAttentionCompanyDate() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"WDGZ\",\"Para\":{\"Sid\":\""
				+ User.sessionId + "\",\"S\":\"1\",\"P\":\"" + pageCompany
				+ "\",\"I\":\"" + idCompany + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				MyAttentionCompanyResult s = GsonUtils.json2bean(response,
						MyAttentionCompanyResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(MyGzActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				} else {

					if (pageCompany == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(MyGzActivity.this, R.string.job_no_info,
								Globals.TOAST_SHORT).show();
						xlv_my_company.setPullLoadEnable(false);
					} else {
						if (s.Rst.Lst.size() < Globals.COUNT) {
							xlv_my_company.setPullLoadEnable(false);
						}
						if (pageCompany == 1) {
							attentionCompanies = s.Rst.Lst;
							idCompany = attentionCompanies.get(0).Id;
						} else {
							List<AttentionCompany> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								attentionCompanies.add(js.get(i));
							}
						}
						myAttentionCompanyAdapter.getDate(MyGzActivity.this,
								attentionCompanies);
						if (pageCompany == 1) {
							xlv_my_company
									.setAdapter(myAttentionCompanyAdapter);
						} else {
							myAttentionCompanyAdapter.notifyDataSetChanged();
						}
						pageCompany++;
					}

				}
			}

		}.volleyStringRequestPost(MyGzActivity.this, params);

	}

	public void getIndividualAttentionDate() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"WDGZ\",\"Para\":{\"Sid\":\""
				+ User.sessionId + "\",\"S\":\"0\",\"P\":\"" + pageIndividual
				+ "\",\"I\":\"" + idIndividual + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				MyAttentionIndividualResult s = GsonUtils.json2bean(response,
						MyAttentionIndividualResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(MyGzActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				} else {

					if (pageIndividual == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(MyGzActivity.this, R.string.job_no_info,
								Globals.TOAST_SHORT).show();
						xlv_my_position.setPullLoadEnable(false);
					} else {
						if (s.Rst.Lst.size() < Globals.COUNT) {
							xlv_my_position.setPullLoadEnable(false);
						}
						if (pageIndividual == 1) {
							individualCompanies = s.Rst.Lst;
							idIndividual = individualCompanies.get(0).Id;
						} else {
							List<IndividualCompany> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								individualCompanies.add(js.get(i));
							}
						}
						myAttentionIndividualAdapter.getDate(MyGzActivity.this,
								individualCompanies);
						if (pageIndividual == 1) {
							xlv_my_position
									.setAdapter(myAttentionIndividualAdapter);
						} else {
							myAttentionIndividualAdapter.notifyDataSetChanged();
						}
						pageIndividual++;
					}

				}
			}

		}.volleyStringRequestPost(MyGzActivity.this, params);

	}

	private void initAttentionIndividualView() {

		if (individualCompanies != null && individualCompanies.size() > 0) {
			myAttentionIndividualAdapter.getDate(MyGzActivity.this,
					individualCompanies);
			xlv_my_position.setAdapter(myAttentionIndividualAdapter);
			myAttentionIndividualAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(MyGzActivity.this, R.string.job_no_info,
					Globals.TOAST_SHORT).show();
		}

	}

	private void initAttentionCompanyView() {

		if (attentionCompanies != null && attentionCompanies.size() > 0) {
			myAttentionCompanyAdapter.getDate(MyGzActivity.this,
					attentionCompanies);
			xlv_my_company.setAdapter(myAttentionCompanyAdapter);
			myAttentionCompanyAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(MyGzActivity.this, R.string.job_no_info,
					Globals.TOAST_SHORT).show();
		}

	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.vip_sel_job_ll:
			line_bule_l.setVisibility(View.VISIBLE);
			line_bule_r.setVisibility(View.GONE);
			initAttentionCompanyView();
			xlv_my_company.setVisibility(View.VISIBLE);
			xlv_my_position.setVisibility(View.GONE);

			break;
		case R.id.vip_sel_loca_ll:
			line_bule_l.setVisibility(View.GONE);
			line_bule_r.setVisibility(View.VISIBLE);
			initAttentionIndividualView();
			xlv_my_company.setVisibility(View.GONE);
			xlv_my_position.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

	}

	@Override
	public void onRefresh() {
		switch (slideSource) {
		case 1:
			attentionCompanyHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (attentionCompanies != null) {
						attentionCompanies.clear();
					}
					idCompany = "";
					pageCompany = 1;
					getAttentionCompanyDate();
					AppUtil.onLoad(xlv_my_company);
				}
			}, 2000);
			break;
		case 2:
			individualAttentionHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (individualCompanies != null) {
						individualCompanies.clear();
					}
					idIndividual = "";
					pageIndividual = 1;
					getIndividualAttentionDate();
					AppUtil.onLoad(xlv_my_position);
				}
			}, 2000);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoadMore() {
		switch (slideSource) {
		case 1:
			attentionCompanyHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					getIndividualAttentionDate();
					AppUtil.onLoad(xlv_my_company);
				}
			}, 2000);
			break;
		case 2:
			individualAttentionHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					getIndividualAttentionDate();
					AppUtil.onLoad(xlv_my_position);
				}
			}, 2000);
			break;
		default:
			break;
		}
	}

	private class ListViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {

			Intent intent = new Intent();
			intent.setClass(MyGzActivity.this, JobDetailActivity.class);
			intent.putExtra(Globals.K_ID, Long.toString(arg3));
			startActivity(intent);
		}

	}

	private class ListViewItemAttentionClickListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {

			Intent intent = new Intent();
			intent.setClass(MyGzActivity.this, VipCompanyDetailsActivity.class);
			intent.putExtra("companyID", Long.toString(arg3));
			startActivity(intent);
		}

	}

}
