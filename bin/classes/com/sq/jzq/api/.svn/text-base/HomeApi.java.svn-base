package com.sq.jzq.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.Toast;

import com.sq.jzq.Globals;
import com.sq.jzq.bean.CompanyResult;
import com.sq.jzq.bean.CompanyResult.Company;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.vip.VipFragment;

public class HomeApi {

	public void getVip(String localCode, final Context context) {

		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"COMS\",\"Para\":{}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				CompanyResult s = GsonUtils.json2bean(response,
						CompanyResult.class);
				if (!s.Stu.equals("1")) {
					Toast.makeText(context, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				}
			}

		}.volleyStringRequestPost(context, params);
		

	}
	
	
}
