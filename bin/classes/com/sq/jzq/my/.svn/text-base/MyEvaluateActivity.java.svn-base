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
import com.sq.jzq.adapter.MyEvaluationAdapter;
import com.sq.jzq.bean.CompanyEvaluationResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.bean.ApplyRecordResult.ApplyRecord;
import com.sq.jzq.bean.CompanyEvaluationResult.CompanyPEvaluation;
import com.sq.jzq.bean.MyEvaluationResult;
import com.sq.jzq.bean.MyEvaluationResult.MyEvaluation;
import com.sq.jzq.util.AppUtil;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.vip.VipCompanyDetailsActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class MyEvaluateActivity extends BaseActivity implements
		IXListViewListener {
	private XListView jlList; // 记录
	private int page = 1; // 页码
	private String id = ""; // 第一条的信息ID
	private MyEvaluationAdapter myEvaluationAdapter;
	private Handler mHandler;
	private List<MyEvaluation> evaluationRecords;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_my_evaluate);
		jlList = (XListView) findViewById(R.id.sqjl_list);
		jlList.setXListViewListener(this);
		jlList.setPullLoadEnable(true);
		mHandler = new Handler();
		myEvaluationAdapter = new MyEvaluationAdapter();
		getEvaluationDate();
	}

	@Override
	public void widgetClick(View v) {

	}

	private void initEvaluationView() {
		if (evaluationRecords != null && evaluationRecords.size() > 0) {
			myEvaluationAdapter.getDate(MyEvaluateActivity.this,
					evaluationRecords);
			jlList.setAdapter(myEvaluationAdapter);
			myEvaluationAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(MyEvaluateActivity.this, R.string.job_no_info,
					Globals.TOAST_SHORT).show();
		}
	}

	public void getEvaluationDate() {
		// 获取评价
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"WDPJ\",\"Para\":{\"sid\":\""
				+ User.sessionId + "\",\"P\":\"" + page + "\",\"I\":\"" + id
				+ "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				MyEvaluationResult s = GsonUtils.json2bean(response,
						MyEvaluationResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(MyEvaluateActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				} else {

					if (page == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(MyEvaluateActivity.this,
								R.string.job_no_info, Globals.TOAST_SHORT)
								.show();
						jlList.setPullLoadEnable(false);
					} else {
						if(s.Rst.Lst.size() < Globals.COUNT) {
							jlList.setPullLoadEnable(false);
						}
						if (page == 1) {
							evaluationRecords = s.Rst.Lst;
							id = evaluationRecords.get(0).ID;
						} else {
							List<MyEvaluation> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								evaluationRecords.add(js.get(i));
							}
						}
						myEvaluationAdapter.getDate(MyEvaluateActivity.this,
								evaluationRecords);
						if (page == 1) {
							jlList.setAdapter(myEvaluationAdapter);
						} else {
							myEvaluationAdapter.notifyDataSetChanged();
						}
						page++;
					}

				}
			}

		}.volleyStringRequestPost(MyEvaluateActivity.this, params);

	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (evaluationRecords != null) {
					evaluationRecords.clear();
				}
				id = "";
				page = 1;
				getEvaluationDate();
				AppUtil.onLoad(jlList);
			}
		}, 2000);

	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getEvaluationDate();
				AppUtil.onLoad(jlList);
			}
		}, 2000);
	}

}
