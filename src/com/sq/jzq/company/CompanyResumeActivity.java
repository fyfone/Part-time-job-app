package com.sq.jzq.company;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.bean.Grjl;
import com.sq.jzq.bean.User;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;

public class CompanyResumeActivity extends BaseActivity {

	private WebView webView;
	private String webJid, userID;

	@Override
	public void initWidget() {
		setContentView(R.layout.activity_my_resume);

		webView = (WebView) findViewById(R.id.mr_web);
		webJid = getIntent().getStringExtra("webJid");
		userID = getIntent().getStringExtra("userID");
		loadData();

	}

	private void loadData() {
		// 获取个人信息
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"JSXX\",\"Para\":{\"Sid\":\""
				+ User.sessionId + "\",\"Jid\":\"" + webJid + "\",\"Uid\":\""
				+ userID + "\"}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				Grjl s = GsonUtils.json2bean(response, Grjl.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(CompanyResumeActivity.this,
							Globals.SER_ERROR, Globals.TOAST_SHORT).show();
				} else {

					webView.getSettings().setJavaScriptEnabled(true);
					webView.loadUrl(s.Rst.U);
					// 设置Web视图
					webView.setWebViewClient(new HelloWebViewClient());
				}

			}
		}.volleyStringRequestPost(CompanyResumeActivity.this, params);
	}

	@Override
	public void widgetClick(View v) {

	}

	// Web视图
	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}
