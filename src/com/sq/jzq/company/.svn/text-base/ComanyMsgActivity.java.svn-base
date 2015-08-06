package com.sq.jzq.company;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.adapter.ComapanyReceiveCollectiondapter;
import com.sq.jzq.adapter.MyMsgCircleAdapter;
import com.sq.jzq.bean.CompanyReceiveCollectionResult;
import com.sq.jzq.bean.CompanyReceiveCollectionResult.ReceiveCollection;
import com.sq.jzq.bean.MyMsgCircle;
import com.sq.jzq.bean.MyMsgCircle.MyCircleResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.my.MyResumeActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ComanyMsgActivity extends BaseActivity implements
		IXListViewListener {

	private XListView job_list_attention, job_list_circle;
	private LinearLayout vip_sel_job_ll, vip_sel_loca_circle;
	private View line_bule_r, line_bule_middle;
	private Handler mAttentionHandler;
	private Handler mCircleHandler;
	private String idAttention = "", idCircle = ""; // 第一条的信息ID
	private int pageAttention = 1, pageCircle = 1;
	List<ReceiveCollection> myMsgAttention;// 接受itne
	List<MyCircleResult> myMsgCircle;// 圈圈信使
	private ComapanyReceiveCollectiondapter messageAttentionAdapter;
	private MyMsgCircleAdapter msgCircleAdapter;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_my_msg_comany);
		mAttentionHandler = new Handler();
		mCircleHandler = new Handler();
		job_list_attention = (XListView) findViewById(R.id.job_list_attention);
		job_list_circle = (XListView) findViewById(R.id.job_list_circle);
		job_list_attention.setXListViewListener(this);
		job_list_attention.setPullLoadEnable(true);
		job_list_circle.setXListViewListener(this);
		job_list_circle.setPullLoadEnable(true);
		vip_sel_job_ll = (LinearLayout) findViewById(R.id.vip_sel_job_ll);
		vip_sel_loca_circle = (LinearLayout) findViewById(R.id.vip_sel_loca_circle);
		line_bule_r = (View) findViewById(R.id.line_bule_r);
		line_bule_middle = (View) findViewById(R.id.line_bule_middle);
		messageAttentionAdapter = new ComapanyReceiveCollectiondapter();
		msgCircleAdapter = new MyMsgCircleAdapter();
		job_list_attention
				.setOnItemClickListener(new ListViewItemClickListener());

		vip_sel_job_ll.setOnClickListener(this);
		vip_sel_loca_circle.setOnClickListener(this);
		loadData();

	}

	private void loadData() {
		getAttentionDate();
		getCircleDate();
	}

	public void getCircleDate() {
		// 获取邀请提醒信息列表
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"ME\",\"Para\":{\"sid\":\""
				+ User.sessionId + "\",\"P\":\"" + pageCircle + "\",\"I\":\""
				+ idCircle + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				MyMsgCircle s = GsonUtils
						.json2bean(response, MyMsgCircle.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(ComanyMsgActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				} else {

					if (pageCircle == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(ComanyMsgActivity.this,
								R.string.job_no_info, Globals.TOAST_SHORT)
								.show();
						job_list_circle.setPullLoadEnable(false);
					} else {
						if (s.Rst.Lst.size() < Globals.COUNT) {
							job_list_circle.setPullLoadEnable(false);
						}
						if (pageCircle == 1) {
							myMsgCircle = s.Rst.Lst;
							idCircle = myMsgCircle.get(0).ID;
						} else {
							List<MyCircleResult> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								myMsgCircle.add(js.get(i));
							}
						}
						msgCircleAdapter.getDate(ComanyMsgActivity.this,
								myMsgCircle);
						if (pageCircle == 1) {
							job_list_circle.setAdapter(msgCircleAdapter);
						} else {
							msgCircleAdapter.notifyDataSetChanged();
						}
						pageCircle++;
					}

				}
			}

		}.volleyStringRequestPost(ComanyMsgActivity.this, params);

	}

	public void getAttentionDate() {
		// 获取邀请提醒信息列表
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"SCJL\",\"Para\":{\"sid\":\""
				+ User.sessionId + "\",\"P\":\"" + pageAttention
				+ "\",\"I\":\"" + idAttention + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				CompanyReceiveCollectionResult s = GsonUtils.json2bean(
						response, CompanyReceiveCollectionResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(ComanyMsgActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				} else {

					if (pageAttention == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(ComanyMsgActivity.this,
								R.string.job_no_info, Globals.TOAST_SHORT)
								.show();
						job_list_attention.setPullLoadEnable(false);
					} else {
						if (s.Rst.Lst.size() < Globals.COUNT) {
							job_list_attention.setPullLoadEnable(false);
						}
						if (pageAttention == 1) {
							myMsgAttention = s.Rst.Lst;
							idAttention = myMsgAttention.get(0).ID;
						} else {
							List<ReceiveCollection> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								myMsgAttention.add(js.get(i));
							}
						}
						messageAttentionAdapter.getDate(ComanyMsgActivity.this,
								myMsgAttention);
						if (pageAttention == 1) {
							job_list_attention
									.setAdapter(messageAttentionAdapter);
						} else {
							messageAttentionAdapter.notifyDataSetChanged();
						}
						pageAttention++;
					}

				}
			}

		}.volleyStringRequestPost(ComanyMsgActivity.this, params);

	}

	private void initAttentionView() {

		if (myMsgAttention != null && myMsgAttention.size() > 0) {
			messageAttentionAdapter.getDate(ComanyMsgActivity.this,
					myMsgAttention);
			job_list_attention.setAdapter(messageAttentionAdapter);
			messageAttentionAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(ComanyMsgActivity.this, R.string.job_no_info,
					Globals.TOAST_SHORT).show();
		}

	}

	private void initCircleView() {

		if (myMsgCircle != null && myMsgCircle.size() > 0) {
			msgCircleAdapter.getDate(ComanyMsgActivity.this, myMsgCircle);
			job_list_circle.setAdapter(msgCircleAdapter);
			msgCircleAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(ComanyMsgActivity.this, R.string.job_no_info,
					Globals.TOAST_SHORT).show();
		}

	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {

		case R.id.vip_sel_job_ll:
			line_bule_r.setVisibility(View.GONE);
			line_bule_middle.setVisibility(View.VISIBLE);
			job_list_circle.setVisibility(View.GONE);
			job_list_attention.setVisibility(View.VISIBLE);
			initAttentionView();
			slideSource = 1;
			break;
		case R.id.vip_sel_loca_circle:
			line_bule_r.setVisibility(View.VISIBLE);
			line_bule_middle.setVisibility(View.GONE);
			job_list_circle.setVisibility(View.VISIBLE);
			job_list_attention.setVisibility(View.GONE);
			slideSource = 2;
			initCircleView();
			break;
		default:
			break;
		}

	}

	int slideSource = 1;

	public void onRefresh() {
		switch (slideSource) {
		case 1:
			mAttentionHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (myMsgAttention != null) {
						myMsgAttention.clear();
					}
					idAttention = "";
					pageAttention = 1;
					getAttentionDate();
					onLoad(job_list_attention);
				}
			}, 2000);
			break;
		case 2:

			mCircleHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (myMsgCircle != null) {
						myMsgCircle.clear();
					}
					idCircle = "";
					pageCircle = 1;
					getCircleDate();
					onLoad(job_list_circle);
				}
			}, 2000);

			break;
		default:
			break;
		}

	}

	public void onLoadMore() {
		switch (slideSource) {
		case 1:
			mAttentionHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					getAttentionDate();
					onLoad(job_list_attention);
				}
			}, 2000);
			break;
		case 2:

			mCircleHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					getCircleDate();
					onLoad(job_list_circle);
				}
			}, 2000);
			break;
		default:
			break;
		}

	}

	public void onLoad(XListView job_list) {
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
			intent.setClass(ComanyMsgActivity.this, CompanyResumeActivity.class);
			intent.putExtra("webJid", arg3 + "");
			intent.putExtra("userID", myMsgAttention.get(arg2-1).UID);
			startActivity(intent);
		}

	}

}
