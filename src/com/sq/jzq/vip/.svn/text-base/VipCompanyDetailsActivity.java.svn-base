package com.sq.jzq.vip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.adapter.VieListUsersAdapter;
import com.sq.jzq.adapter.VipListUserAdapter;
import com.sq.jzq.adapter.VipPositionAdapter;
import com.sq.jzq.bean.CompanyEvaluationResult;
import com.sq.jzq.bean.CompanyEvaluationResult.CompanyPEvaluation;
import com.sq.jzq.bean.CompanyPositionResult;
import com.sq.jzq.bean.CompanyPositionResult.CompanyPosition;
import com.sq.jzq.bean.EnterpriseResult;
import com.sq.jzq.bean.EnterpriseResult.EnterpriseDetails;
import com.sq.jzq.company.ComanyMsgActivity;
import com.sq.jzq.company.CompanyResumeActivity;
import com.sq.jzq.job.JobDetailActivity;
import com.sq.jzq.job.MapActivity;
import com.sq.jzq.my.SqjlActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VipCompanyDetailsActivity extends BaseActivity implements
		IXListViewListener {

	private XListView job_list;
	private ListView job_list_position;
	private LinearLayout vip_sel_loca_ll, vip_sel_job_ll, vip_sel_loca_circle;
	private View line_bule_l, line_bule_r, line_bule_middle;
	private ScrollView sv_vip_compan;
	private TextView tv_vip_compan_details, tv_vip_compan_time,
			tv_vip_compan_address, tv_vip_compan_scale, tv_vip_compan_type,
			tv_vip_compan_nature, tv_vip_compan_website, tv_vip_compan_mobile,
			tv_vip_compan_bruef;
	private ImageView iv_vip_map, iv_vip_title;
	List<EnterpriseDetails> wanteds;// 企业
	List<CompanyPosition> companyPosition;// 职位
	List<CompanyPEvaluation> companyPEvaluations;// 评价
	String companyID;
	private Handler mHandler;
	private VipPositionAdapter vipPositionAdapter;
	private VieListUsersAdapter vipListUserAdapter;
	private String id = ""; // 第一条的信息ID
	private int page = 1;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_vip_compan);
		job_list = (XListView) findViewById(R.id.job_list);
		job_list_position = (ListView) findViewById(R.id.job_list_position);
		vip_sel_loca_ll = (LinearLayout) findViewById(R.id.vip_sel_loca_ll);
		vip_sel_job_ll = (LinearLayout) findViewById(R.id.vip_sel_job_ll);
		vip_sel_loca_circle = (LinearLayout) findViewById(R.id.vip_sel_loca_circle);
		sv_vip_compan = (ScrollView) findViewById(R.id.sv_vip_compan);
		line_bule_l = (View) findViewById(R.id.line_bule_l);
		line_bule_r = (View) findViewById(R.id.line_bule_r);
		line_bule_middle = (View) findViewById(R.id.line_bule_middle);
		iv_vip_map = (ImageView) findViewById(R.id.iv_vip_map);
		iv_vip_title = (ImageView) findViewById(R.id.iv_vip_title);
		// 公司详情
		tv_vip_compan_details = (TextView) findViewById(R.id.tv_vip_compan_details);
		tv_vip_compan_time = (TextView) findViewById(R.id.tv_vip_compan_time);
		tv_vip_compan_address = (TextView) findViewById(R.id.tv_vip_compan_address);
		tv_vip_compan_scale = (TextView) findViewById(R.id.tv_vip_compan_scale);
		tv_vip_compan_type = (TextView) findViewById(R.id.tv_vip_compan_type);
		tv_vip_compan_nature = (TextView) findViewById(R.id.tv_vip_compan_nature);
		tv_vip_compan_website = (TextView) findViewById(R.id.tv_vip_compan_website);
		tv_vip_compan_mobile = (TextView) findViewById(R.id.tv_vip_compan_mobile);
		tv_vip_compan_bruef = (TextView) findViewById(R.id.tv_vip_compan_bruef);
		mHandler = new Handler();
		job_list.setXListViewListener(this);
		job_list.setPullLoadEnable(true);

		job_list_position
				.setOnItemClickListener(new ListViewItemClickListener());

		vipPositionAdapter = new VipPositionAdapter();
		vipListUserAdapter = new VieListUsersAdapter();

		vip_sel_loca_ll.setOnClickListener(this);
		vip_sel_job_ll.setOnClickListener(this);
		vip_sel_loca_circle.setOnClickListener(this);
		iv_vip_map.setOnClickListener(this);

		companyID = getIntent().getStringExtra("companyID");
		loadData();
	}

	private void loadData() {
		// 企业信息数据
		Map<String, String> params = new HashMap<String, String>();
		if (companyID != null) {
			params.put(Globals.WS_POST_KEY,
					"{\"Ac\":\"COMD\",\"Para\":{\"id\":\"" + companyID
							+ "\",\"sid\":\"\"}}");
		}
		new VolleyUtil() {

			public void analysisData(String response) {
				EnterpriseResult s = GsonUtils.json2bean(response,
						EnterpriseResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(VipCompanyDetailsActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT);
				} else {
					wanteds = s.Rst.Lst;
				}
				initDetailsView();
			}

		}.volleyStringRequestPost(VipCompanyDetailsActivity.this, params);
		// 公司发布职位
		Map<String, String> positionParam = new HashMap<String, String>();
		if (companyID != null) {
			positionParam.put(Globals.WS_POST_KEY,
					"{\"Ac\":\"COMZ\",\"Para\":{\"sid\":\"" + companyID
							+ "\"}}");
		}
		new VolleyUtil() {

			public void analysisData(String response) {
				CompanyPositionResult s = GsonUtils.json2bean(response,
						CompanyPositionResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(VipCompanyDetailsActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT);
				} else {
					companyPosition = s.Rst.Lst;
				}
				initPositionView();
			}

		}.volleyStringRequestPost(VipCompanyDetailsActivity.this, positionParam);
		getEvaluationDate();
	}

	public void getEvaluationDate() {
		// 企业评论
		Map<String, String> params = new HashMap<String, String>();
		if (companyID != null) {
			params.put(Globals.WS_POST_KEY,
					"{\"Ac\":\"COMP\",\"Para\":{\"sid\":\"" + companyID
							+ "\",\"P\":\"" + page + "\",\"I\":\"" + id
							+ "\"}}");
		}
		new VolleyUtil() {

			public void analysisData(String response) {
				CompanyEvaluationResult s = GsonUtils.json2bean(response,
						CompanyEvaluationResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(VipCompanyDetailsActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT).show();
				} else {

					if (page == 1 && s.Rst.Lst.size() == 0) {
//						Toast.makeText(VipCompanyDetailsActivity.this,
//								R.string.job_no_info, Globals.TOAST_SHORT)
//								.show();
						job_list.setPullLoadEnable(false);
					} else {
						if (s.Rst.Lst.size() < Globals.COUNT) {
							job_list.setPullLoadEnable(false);
						}
						if (page == 1) {
							companyPEvaluations = s.Rst.Lst;
							id = companyPEvaluations.get(0).ID;
						} else {
							List<CompanyPEvaluation> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								companyPEvaluations.add(js.get(i));
							}
						}
						vipListUserAdapter.getDate(
								VipCompanyDetailsActivity.this,
								companyPEvaluations);
						if (page == 1) {
							job_list.setAdapter(vipListUserAdapter);
						} else {
							vipListUserAdapter.notifyDataSetChanged();
						}
						page++;
					}

				}
			}

		}.volleyStringRequestPost(VipCompanyDetailsActivity.this, params);

	}

	private void initEvaluationView() {

		if (companyPEvaluations != null && companyPEvaluations.size() > 0) {
			vipListUserAdapter.getDate(VipCompanyDetailsActivity.this,
					companyPEvaluations);
			job_list.setAdapter(vipListUserAdapter);
			vipListUserAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(VipCompanyDetailsActivity.this,
					R.string.job_no_info, Globals.TOAST_SHORT).show();
		}

	}

	private void initPositionView() {
		if (companyPosition != null && companyPosition.size() > 0) {
			vipPositionAdapter.getDate(VipCompanyDetailsActivity.this,
					companyPosition);
			job_list_position.setAdapter(vipPositionAdapter);
		} else {
			Toast.makeText(VipCompanyDetailsActivity.this,
					R.string.job_no_info, Globals.TOAST_SHORT);
		}
	}

	// 公司详情view
	public void initDetailsView() {
		if (wanteds != null) {
			tv_vip_compan_details.setText(wanteds.get(0).CE);
			tv_vip_compan_time.setText("注册时间： " + wanteds.get(0).RE);
			tv_vip_compan_address.setText(wanteds.get(0).CT);
			tv_vip_compan_scale.setText(wanteds.get(0).SE + "人");
			tv_vip_compan_type.setText(wanteds.get(0).CTYPE);
			tv_vip_compan_nature.setText(wanteds.get(0).CNATURE);
			tv_vip_compan_website.setText(wanteds.get(0).CNET);
			tv_vip_compan_mobile.setText(wanteds.get(0).ME);
			tv_vip_compan_bruef.setText(wanteds.get(0).INN);
			ImageLoader.getInstance().displayImage(wanteds.get(0).LO,
					iv_vip_title);
		}

	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.vip_sel_loca_ll:
			line_bule_l.setVisibility(View.VISIBLE);
			line_bule_r.setVisibility(View.GONE);
			line_bule_middle.setVisibility(View.GONE);
			job_list.setVisibility(View.GONE);
			job_list_position.setVisibility(View.GONE);
			sv_vip_compan.setVisibility(View.VISIBLE);
			break;
		case R.id.vip_sel_job_ll:
			line_bule_l.setVisibility(View.GONE);
			line_bule_r.setVisibility(View.GONE);
			line_bule_middle.setVisibility(View.VISIBLE);
			sv_vip_compan.setVisibility(View.GONE);
			job_list.setVisibility(View.GONE);
			job_list_position.setVisibility(View.VISIBLE);
			initPositionView();

			break;
		case R.id.vip_sel_loca_circle:
			job_list.setVisibility(View.VISIBLE);
			sv_vip_compan.setVisibility(View.GONE);
			line_bule_l.setVisibility(View.GONE);
			line_bule_middle.setVisibility(View.GONE);
			line_bule_r.setVisibility(View.VISIBLE);
			job_list_position.setVisibility(View.GONE);
			initEvaluationView();
			break;
		case R.id.iv_vip_map:
			if (wanteds != null) {
				Intent intent = new Intent(VipCompanyDetailsActivity.this,
						MapActivity.class);
				intent.putExtra(Globals.MAP_BAIDU_ADDRESS, wanteds.get(0).CT);
				startActivity(intent);
			} else {
				Toast.makeText(VipCompanyDetailsActivity.this,
						"没有公司详细地址,无法开启地图", 0).show();
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (companyPEvaluations != null) {
					companyPEvaluations.clear();
				}
				id = "";
				page = 1;
				getEvaluationDate();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getEvaluationDate();
				onLoad();
			}
		}, 2000);

	}

	private void onLoad() {
		job_list.stopRefresh();
		job_list.stopLoadMore();
		SimpleDateFormat df = new SimpleDateFormat(Globals.DATE_FORMAT);// 设置日期格式
		job_list.setRefreshTime(df.format(new Date()));
	}

	private class ListViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {

			Intent intent = new Intent();
			intent.setClass(VipCompanyDetailsActivity.this,
					JobDetailActivity.class);
			intent.putExtra(Globals.K_ID, Long.toString(arg3));
			startActivity(intent);
		}

	}
}
