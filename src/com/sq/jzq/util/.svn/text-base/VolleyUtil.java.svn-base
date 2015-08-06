package com.sq.jzq.util;

import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sq.jzq.Globals;

//import me.huixin.tryst.ui.LoginActivity;

/**
 * 2015年5月5日17:03:46 fyfone
 */
public abstract class VolleyUtil {
	public AlertUtil au;
	private final int SPLASH_DISPLAY_LENGHT = 1500; // 延迟3秒

	// RetryPolicy retryPolicy =
	// post请求封装
	public <T> void volleyStringRequestPost(final Context context,
			final Map<String, String> paramsss) {

		Log.i("Response", "paramsss=" + paramsss);
		// RequestQueue mRequestQueue = Volley.newRequestQueue(context);

		if (context != null) {
			au = new AlertUtil(context);
		}
		StringRequest sr = new StringRequest(Request.Method.POST,
				Globals.WS_URI, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (au != null) {
							au.closeDialog();
						}
						if (response.contains("\"Stu\":0")) {
							Log.i("Response", "res:" + response);
							Toast.makeText(context, Globals.SER_ERROR,
									Globals.TOAST_SHORT).show();
							return;
						}
						if (response != null) {
							Log.i("Response", "res:" + response);
							analysisData(response);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// error
						Log.i("Response", "error" + error.getMessage());

						if (au != null) {
							au.closeDialog();

						}
						if (AppUtil.networkCheck() == false) {
							Toast.makeText(Globals.context, "没有网络", 0).show();
						}
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				return paramsss;
			}
		};
		
		// sr.setRetryPolicy(new DefaultRetryPolicy(15 * 1000,
		// DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
		// DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		sr.setRetryPolicy(new DefaultRetryPolicy(3000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		RequestManager.addRequest(sr, null);
		// new Handler().postDelayed(new Runnable() {
		// public void run() {
		// if (au != null) {
		// au.closeDialog();
		// }
		// }
		// }, SPLASH_DISPLAY_LENGHT);

	}

	public abstract <T> void analysisData(String response);

}
