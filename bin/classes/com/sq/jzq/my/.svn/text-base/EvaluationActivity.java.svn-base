package com.sq.jzq.my;

import java.util.HashMap;
import java.util.Map;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.CompanyResult;
import com.sq.jzq.bean.User;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class EvaluationActivity extends BaseActivity {
	private EditText ey_opinion_inif;
	private RatingBar rb_evaluation_meet, rb_evaluation_work,
			rb_evaluation_deduction, rb_evaluation_grant;
	private Button bt_evaluation_submit;
	private String evaluation_id, evaluation_jobid, evaluation_text;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_evaluation);
		ey_opinion_inif = (EditText) findViewById(R.id.ey_opinion_inif);
		rb_evaluation_meet = (RatingBar) findViewById(R.id.rb_evaluation_meet);
		rb_evaluation_work = (RatingBar) findViewById(R.id.rb_evaluation_work);
		rb_evaluation_deduction = (RatingBar) findViewById(R.id.rb_evaluation_deduction);
		rb_evaluation_grant = (RatingBar) findViewById(R.id.rb_evaluation_grant);
		bt_evaluation_submit = (Button) findViewById(R.id.bt_evaluation_submit);
		evaluation_id = getIntent().getStringExtra("evaluation_id");
		evaluation_jobid = getIntent().getStringExtra("evaluation_jobid");
		evaluation_text = getIntent().getStringExtra("evaluation_text");
		bt_evaluation_submit.setOnClickListener(this);
	}

	public void evaluationDate() {
		/*
		 * on 评价内容 jobid 职位 ID id 公司ID sid 用户ID je 工作信息与实际工作是否相符 we 是否拖延下班时间
		 * dy是否无故克扣工资 gy 是否按时发放工资
		 */
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"APJ\",\"Para\":{\"on\":\""
				+ ey_opinion_inif.getText().toString() + "\",\"id\":\""
				+ evaluation_id + "\",\"sid\":\"" + User.sessionId
				+ "\",\"jobid\":\"" + evaluation_jobid + "\",\"je\":\""
				+ rb_evaluation_meet.getRating() + "\",\"we\":\""
				+ rb_evaluation_work.getRating() + "\",\"dy\":\""
				+ rb_evaluation_deduction.getRating() + "\",\"gy\":\""
				+ bt_evaluation_submit.getRight() + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {

				if (response.contains("评价成功")) {
					Toast.makeText(EvaluationActivity.this, "评价成功",
							Globals.TOAST_SHORT).show();
					finish();
				} else if (response.contains("已经评价")) {
					Toast.makeText(EvaluationActivity.this, "已经评价过该公司",
							Globals.TOAST_SHORT).show();
				} else {
					Toast.makeText(EvaluationActivity.this, "评价失败",
							Globals.TOAST_SHORT).show();
				}
				// initEvaluationView();

			}

		}.volleyStringRequestPost(EvaluationActivity.this, params);
	}

	@Override
	public void widgetClick(View v) {

		switch (v.getId()) {
		case R.id.bt_evaluation_submit:
			evaluationDate();

			break;

		default:
			break;
		}

	}

}
