package com.sq.jzq.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

public class RequestUtils {

	public RequestUtils() {
	}

	public static void init(Context context) {
		RequestManager.init(context);
	}

	public static RequestQueue getRequestQueue() {
		return RequestManager.getRequestQueue();
	}

	public static void addRequsetQueue(Request<?> request, Object tag) {
		
		RequestManager.addRequest(request, tag);
	}

	public static void addRequstQueue(Request<?> request) {
		RequestManager.addRequest(request, null);
	}

	public static void cancelAll(Object obj) {
		RequestManager.cancelAll(obj);
	}

	public static void cancelAll() {
		RequestManager.cancelAll(null);
	}

}