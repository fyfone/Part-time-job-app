package com.sq.jzq.my;

import java.util.HashMap;
import java.util.Map;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.DeleteItemResult;
import com.sq.jzq.bean.InvitationInterviewResult;
import com.sq.jzq.bean.InvitationInterviewResult.InvitationInterviewResults;
import com.sq.jzq.bean.User;
import com.sq.jzq.company.ChangePwdActivity;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.TitleBarView;
import com.sq.jzq.views.TitleBarView.OnClickEnterButtonListener;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InvitationInterviewActivity extends BaseActivity {
	private String inviteID;
	private InvitationInterviewResults invitationInterviews;
	private TitleBarView job_titlebar;
	private TextView tv_interview_title, tv_interview_position,
			tv_invitation_delivery_time, tv_invitation_contact,
			tv_invitation_phone, tv_invitation_loce;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_invitation_interview);
		job_titlebar = (TitleBarView) findViewById(R.id.job_titlebar);
		inviteID = getIntent().getStringExtra(Globals.AN_INTERVIEW);
		tv_interview_title = (TextView) findViewById(R.id.tv_interview_title);
		tv_interview_position = (TextView) findViewById(R.id.tv_interview_position);
		tv_invitation_delivery_time = (TextView) findViewById(R.id.tv_invitation_delivery_time);
		tv_invitation_contact = (TextView) findViewById(R.id.tv_invitation_contact);
		tv_invitation_phone = (TextView) findViewById(R.id.tv_invitation_phone);
		tv_invitation_loce = (TextView) findViewById(R.id.tv_invitation_loce);

		if (!"".equals(inviteID)) {
			upPasswordDate();
		}

		// 设置botton
		if (job_titlebar != null) {
			job_titlebar
					.setClickEnterButtonListener(new OnClickEnterButtonListener() {

						@Override
						public void onClickEnterButton(View v) {
							ReceiveInvitationDate();
							Log.i("面试地址的id", inviteID + "");
						}
					});
		}

	}

	public void upPasswordDate() {

		// 邀约详情
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY,
				"{\"Ac\":\"INVIS\",\"Para\":{\"Sid\":\"" + User.sessionId
						+ "\",\"id\":\"" + inviteID + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				InvitationInterviewResult s = GsonUtils.json2bean(response,
						InvitationInterviewResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(InvitationInterviewActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT).show();
				} else {
					invitationInterviews = s.Rst;
				}
				initInvitationInterviewView();

			}

		}.volleyStringRequestPost(InvitationInterviewActivity.this, params);

	}

	public void ReceiveInvitationDate() {

		// 接收邀请
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"JSYY\",\"Para\":{\"Sid\":\""
				+ User.sessionId + "\",\"id\":\"" + inviteID + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {

				DeleteItemResult s = GsonUtils.json2bean(response,
						DeleteItemResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(InvitationInterviewActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT).show();
				} else {

					if (s.Rst.Scd == 1) {
						Toast.makeText(InvitationInterviewActivity.this,
								s.Rst.Msg, 0).show();
						finish();

					} else {
						Toast.makeText(InvitationInterviewActivity.this,
								s.Rst.Msg, Globals.TOAST_SHORT).show();
					}

				}

			}

		}.volleyStringRequestPost(InvitationInterviewActivity.this, params);

	}

	protected void initInvitationInterviewView() {
		tv_interview_title.setText(invitationInterviews.CE);
		tv_interview_position.setText(invitationInterviews.JE + "("
				+ invitationInterviews.S + "/" + invitationInterviews.U + ")");
		tv_invitation_delivery_time.setText("投递时间：" + invitationInterviews.IE);
		tv_invitation_contact.setText(invitationInterviews.PE);
		tv_invitation_phone.setText(invitationInterviews.ME);
		// 地址
		// tv_invitation_loce.setText(invitationInterviews.ME);
		// TODO 邀请面试 赋值

	}

	@Override
	public void widgetClick(View v) {

	}

}
