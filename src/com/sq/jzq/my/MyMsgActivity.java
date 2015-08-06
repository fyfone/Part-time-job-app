package com.sq.jzq.my;

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
import com.sq.jzq.adapter.MyMsgAttentionAdapter;
import com.sq.jzq.adapter.MyMsgCircleAdapter;
import com.sq.jzq.adapter.MyMsgInvitationAdapter;
import com.sq.jzq.bean.InviteRecordResult;
import com.sq.jzq.bean.InviteRecordResult.InvieRecord;
import com.sq.jzq.bean.MyMsgAttention;
import com.sq.jzq.bean.MyMsgAttention.MyAttentionResult;
import com.sq.jzq.bean.MyMsgCircle;
import com.sq.jzq.bean.MyMsgCircle.MyCircleResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyMsgActivity extends BaseActivity implements IXListViewListener {

	private XListView job_list_invitation, job_list_attention, job_list_circle;
	private LinearLayout vip_sel_loca_ll, vip_sel_job_ll, vip_sel_loca_circle;
	private View line_bule_l, line_bule_r, line_bule_middle;
	private Handler mHandler;
	private Handler mAttentionHandler;
	private Handler mCircleHandler;
	private String idInvitation = "", idAttention = "", idCircle = ""; // 第一条的信息ID
	private int pageInvitation = 1, pageAttention = 1, pageCircle = 1;
	List<InvieRecord> myMsgInvitation;// 评价
	List<MyAttentionResult> myMsgAttention;// 评价
	List<MyCircleResult> myMsgCircle;// 圈圈信使
	private MyMsgInvitationAdapter inviteRecordAdapter;
	private MyMsgAttentionAdapter messageAttentionAdapter;
	private MyMsgCircleAdapter msgCircleAdapter;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_my_msg);
		mHandler = new Handler();
		mAttentionHandler = new Handler();
		mCircleHandler = new Handler();
		job_list_invitation = (XListView) findViewById(R.id.job_list_invitation);
		job_list_attention = (XListView) findViewById(R.id.job_list_attention);
		job_list_circle = (XListView) findViewById(R.id.job_list_circle);
		job_list_invitation.setXListViewListener(this);
		job_list_invitation.setPullLoadEnable(true);
		job_list_attention.setXListViewListener(this);
		job_list_attention.setPullLoadEnable(true);
		job_list_circle.setXListViewListener(this);
		job_list_circle.setPullLoadEnable(true);
		inviteRecordAdapter = new MyMsgInvitationAdapter();
		messageAttentionAdapter = new MyMsgAttentionAdapter();
		msgCircleAdapter = new MyMsgCircleAdapter();

		vip_sel_loca_ll = (LinearLayout) findViewById(R.id.vip_sel_loca_ll);
		vip_sel_job_ll = (LinearLayout) findViewById(R.id.vip_sel_job_ll);
		vip_sel_loca_circle = (LinearLayout) findViewById(R.id.vip_sel_loca_circle);
		line_bule_l = (View) findViewById(R.id.line_bule_l);
		line_bule_r = (View) findViewById(R.id.line_bule_r);
		line_bule_middle = (View) findViewById(R.id.line_bule_middle);
		vip_sel_loca_ll.setOnClickListener(this);
		vip_sel_job_ll.setOnClickListener(this);
		vip_sel_loca_circle.setOnClickListener(this);
		loadData();

	}

	private void loadData() {
		getInvitationDate();
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
					Toast.makeText(MyMsgActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				} else {

					if (pageCircle == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(MyMsgActivity.this,
								R.string.job_no_info, Globals.TOAST_SHORT)
								.show();
						job_list_attention.setPullLoadEnable(false);
					} else {
						if(s.Rst.Lst.size() < Globals.COUNT) {
							job_list_attention.setPullLoadEnable(false);
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
						msgCircleAdapter.getDate(MyMsgActivity.this,
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

		}.volleyStringRequestPost(MyMsgActivity.this, params);

	}

	public void getAttentionDate() {
		// 获取邀请提醒信息列表
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"GZTX\",\"Para\":{\"Sid\":\""
				+ User.sessionId + "\",\"P\":\"" + pageAttention
				+ "\",\"I\":\"" + idAttention + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				MyMsgAttention s = GsonUtils.json2bean(response,
						MyMsgAttention.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(MyMsgActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				} else {

					if (pageAttention == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(MyMsgActivity.this,
								R.string.job_no_info, Globals.TOAST_SHORT)
								.show();
						job_list_attention.setPullLoadEnable(false);
					} else {
						if(s.Rst.Lst.size() < Globals.COUNT) {
							job_list_attention.setPullLoadEnable(false);
						}
						if (pageAttention == 1) {
							myMsgAttention = s.Rst.Lst;
							idAttention = myMsgAttention.get(0).ID;
						} else {
							List<MyAttentionResult> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								myMsgAttention.add(js.get(i));
							}
						}
						messageAttentionAdapter.getDate(MyMsgActivity.this,
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

		}.volleyStringRequestPost(MyMsgActivity.this, params);

	}

	public void getInvitationDate() {
		// 获取邀请提醒信息列表
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"YQTX\",\"Para\":{\"Sid\":\""
				+ User.sessionId + "\",\"P\":\"" + pageInvitation
				+ "\",\"I\":\"" + idInvitation + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				InviteRecordResult s = GsonUtils.json2bean(response,
						InviteRecordResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(MyMsgActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				} else {

					if (pageInvitation == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(MyMsgActivity.this,
								R.string.job_no_info, Globals.TOAST_SHORT)
								.show();
						job_list_invitation.setPullLoadEnable(false);
					} else {
						if(s.Rst.Lst.size() < Globals.COUNT) {
							job_list_invitation.setPullLoadEnable(false);
						}
						if (pageInvitation == 1) {
							myMsgInvitation = s.Rst.Lst;
							idInvitation = myMsgInvitation.get(0).ID;
						} else {
							List<InvieRecord> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								myMsgInvitation.add(js.get(i));
							}
						}
						inviteRecordAdapter.getDate(MyMsgActivity.this,
								myMsgInvitation);
						if (pageInvitation == 1) {
							job_list_invitation.setAdapter(inviteRecordAdapter);
						} else {
							inviteRecordAdapter.notifyDataSetChanged();
						}
						pageInvitation++;
					}

				}
			}

		}.volleyStringRequestPost(MyMsgActivity.this, params);

	}

	private void initInviteView() {

		if (myMsgInvitation != null && myMsgInvitation.size() > 0) {
			inviteRecordAdapter.getDate(MyMsgActivity.this, myMsgInvitation);
			job_list_invitation.setAdapter(inviteRecordAdapter);
			inviteRecordAdapter.notifyDataSetChanged();
			job_list_invitation
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							Intent intent = new Intent(MyMsgActivity.this,
									InvitationInterviewActivity.class);
							intent.putExtra("inviteID",
									myMsgInvitation.get(position).ID);
							startActivity(intent);
						}
					});

		} else {
			Toast.makeText(MyMsgActivity.this, R.string.job_no_info,
					Globals.TOAST_SHORT).show();
		}

	}

	private void initAttentionView() {

		if (myMsgAttention != null && myMsgAttention.size() > 0) {
			messageAttentionAdapter.getDate(MyMsgActivity.this, myMsgAttention);
			job_list_attention.setAdapter(messageAttentionAdapter);
			messageAttentionAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(MyMsgActivity.this, R.string.job_no_info,
					Globals.TOAST_SHORT).show();
		}

	}

	private void initCircleView() {

		if (myMsgCircle != null && myMsgCircle.size() > 0) {
			msgCircleAdapter.getDate(MyMsgActivity.this, myMsgCircle);
			job_list_circle.setAdapter(msgCircleAdapter);
			msgCircleAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(MyMsgActivity.this, R.string.job_no_info,
					Globals.TOAST_SHORT).show();
		}

	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.vip_sel_loca_ll:
			line_bule_l.setVisibility(View.VISIBLE);
			line_bule_r.setVisibility(View.GONE);
			line_bule_middle.setVisibility(View.GONE);
			job_list_invitation.setVisibility(View.VISIBLE);
			job_list_circle.setVisibility(View.GONE);
			job_list_attention.setVisibility(View.GONE);
			initInviteView();
			slideSource = 1;
			break;
		case R.id.vip_sel_job_ll:
			line_bule_l.setVisibility(View.GONE);
			line_bule_r.setVisibility(View.GONE);
			line_bule_middle.setVisibility(View.VISIBLE);
			job_list_invitation.setVisibility(View.GONE);
			job_list_circle.setVisibility(View.GONE);
			job_list_attention.setVisibility(View.VISIBLE);
			initAttentionView();
			slideSource = 2;
			break;
		case R.id.vip_sel_loca_circle:
			line_bule_l.setVisibility(View.GONE);
			line_bule_r.setVisibility(View.VISIBLE);
			line_bule_middle.setVisibility(View.GONE);
			job_list_invitation.setVisibility(View.GONE);
			job_list_circle.setVisibility(View.VISIBLE);
			job_list_attention.setVisibility(View.GONE);
			slideSource = 3;
			initCircleView();
			break;
		default:
			break;
		}

	}

	int slideSource = 1;

	@Override
	public void onRefresh() {
		switch (slideSource) {
		case 1:
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (myMsgInvitation != null) {
						myMsgInvitation.clear();
					}
					idInvitation = "";
					pageInvitation = 1;
					getInvitationDate();
					onLoad(job_list_invitation);
				}
			}, 2000);
			break;
		case 2:
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
		case 3:

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

	@Override
	public void onLoadMore() {

		switch (slideSource) {
		case 1:
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					getInvitationDate();
					onLoad(job_list_invitation);
				}
			}, 2000);
			break;
		case 2:
			mAttentionHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					getInvitationDate();
					onLoad(job_list_attention);
				}
			}, 2000);
			break;
		case 3:

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

}
