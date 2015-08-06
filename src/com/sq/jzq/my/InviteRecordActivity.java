package com.sq.jzq.my;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.adapter.InviteRecordAdapter;
import com.sq.jzq.adapter.SqjlAdapter;
import com.sq.jzq.bean.ApplyRecordResult;
import com.sq.jzq.bean.ApplyRecordResult.ApplyRecord;
import com.sq.jzq.bean.InviteRecordResult;
import com.sq.jzq.bean.InviteRecordResult.InvieRecord;
import com.sq.jzq.bean.InviteRecordResultTwo;
import com.sq.jzq.bean.InviteRecordResultTwo.InvieRecordTwo;
import com.sq.jzq.bean.User;
import com.sq.jzq.job.JobDetailActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.TitleBarView;

public class InviteRecordActivity extends BaseActivity implements
		IXListViewListener {
	private TitleBarView more_title;
	private XListView jlList; // 记录
	private int page = 1; // 页码
	private InviteRecordAdapter inviteRecordAdapter;
	private Handler mHandler;
	private String id = ""; // 第一条的信息ID

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_invite);
		mHandler = new Handler();
		more_title = (TitleBarView) findViewById(R.id.sqjl_titlebar);

		jlList = (XListView) findViewById(R.id.sqjl_list);
		jlList.setXListViewListener(this);
		jlList.setPullLoadEnable(true);
		jlList.setOnItemClickListener(new ListViewItemClickListener());
		inviteRecordAdapter = new InviteRecordAdapter();
		loadData();
		// initApplyRecordView();
	}

	private List<InvieRecordTwo> applyRecords;

	private void loadData() {

		// 获取企业列表
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"YYJL\",\"Para\":{\"sid\":\""
				+ User.sessionId + "\",\"P\":\"" + page + "\",\"I\":\"" + id
				+ "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				InviteRecordResultTwo s = GsonUtils.json2bean(response,
						InviteRecordResultTwo.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(InviteRecordActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT).show();
				} else {

					if (page == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(InviteRecordActivity.this,
								R.string.job_no_info, Globals.TOAST_SHORT)
								.show();
						jlList.setPullLoadEnable(false);
					} else {
						if(s.Rst.Lst.size() < Globals.COUNT) {
							jlList.setPullLoadEnable(false);
						}
						if (page == 1) {
							applyRecords = s.Rst.Lst;
							id = applyRecords.get(0).ID;
						} else {
							List<InvieRecordTwo> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								applyRecords.add(js.get(i));
							}
						}
						inviteRecordAdapter.getDate(InviteRecordActivity.this,
								applyRecords);
						if (page == 1) {
							jlList.setAdapter(inviteRecordAdapter);
						} else {
							inviteRecordAdapter.notifyDataSetChanged();
						}
						page++;
					}
				}

			}

		}.volleyStringRequestPost(InviteRecordActivity.this, params);

	}

	private void initApplyRecordView() {
		if (applyRecords != null && applyRecords.size() > 0) {
			inviteRecordAdapter
					.getDate(InviteRecordActivity.this, applyRecords);
			jlList.setAdapter(inviteRecordAdapter);
			inviteRecordAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(InviteRecordActivity.this, R.string.job_no_info,
					Globals.TOAST_SHORT);
		}
	}

	@Override
	public void widgetClick(View v) {

	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (applyRecords != null) {
					applyRecords.clear();
				}
				id = "";
				page = 1;
				loadData();
				onLoad();
			}
		}, 2000);

	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadData();
				onLoad();
			}
		}, 2000);

	}

	private void onLoad() {
		jlList.stopRefresh();
		jlList.stopLoadMore();
		SimpleDateFormat df = new SimpleDateFormat(Globals.DATE_FORMAT);// 设置日期格式
		jlList.setRefreshTime(df.format(new Date()));
	}

	private class ListViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {

			Intent intent = new Intent();
			intent.setClass(InviteRecordActivity.this,
					InvitationInterviewActivity.class);
			intent.putExtra(Globals.AN_INTERVIEW, arg3 + "");

			startActivity(intent);
		}

	}
}