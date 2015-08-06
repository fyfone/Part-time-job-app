package com.sq.jzq.util;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class VolleyStringRequest extends StringRequest{

	private Context mContext;
	private AlertUtil alertUtils;
	
	
	public VolleyStringRequest(int method, String url,
			Listener<String> listener, ErrorListener errorListener,Context context ) {
		super(method, url, listener, errorListener);
		this.mContext = context;
		alertUtils = new AlertUtil(context);
	}

	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {
		alertUtils.closeDialog();
		return super.parseNetworkError(volleyError);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		alertUtils.closeDialog();
		return super.parseNetworkResponse(response);
	}

}
