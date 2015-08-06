package com.sq.jzq.vip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.adapter.VipRecruitersAdapter;
import com.sq.jzq.adapter.VipWantedAdapter;
import com.sq.jzq.bean.CompanyResult;
import com.sq.jzq.bean.VIPWantedResult;
import com.sq.jzq.bean.CompanyResult.Company;
import com.sq.jzq.bean.VIPWantedResult.Wanted;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.RequestManager;
import com.sq.jzq.util.VolleyUtil;

public class VipFragment extends Fragment implements OnClickListener {
	private ListView vip_list, vip_list_tow;
	private LinearLayout vip_sel_loca_ll, vip_sel_job_ll;
	private View line_bule_l, line_bule_r;
	List<Company> companys;// 企业
	List<Wanted> wanteds;// 求职
	private VipRecruitersAdapter vipRecruitersAdapter;
	private VipWantedAdapter vipWantedAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_vip, container, false);

		vip_list = (ListView) view.findViewById(R.id.vip_list);
		vip_list_tow = (ListView) view.findViewById(R.id.vip_list_tow);
		vip_sel_loca_ll = (LinearLayout) view
				.findViewById(R.id.vip_sel_loca_ll);
		vip_sel_job_ll = (LinearLayout) view.findViewById(R.id.vip_sel_job_ll);
		line_bule_l = (View) view.findViewById(R.id.line_bule_l);
		line_bule_r = (View) view.findViewById(R.id.line_bule_r);
		vipRecruitersAdapter = new VipRecruitersAdapter();
		vipWantedAdapter = new VipWantedAdapter();
		vip_sel_loca_ll.setOnClickListener(this);
		vip_sel_job_ll.setOnClickListener(this);
		loadData();
		return view;
	}

	private void loadData() {
		// 获取企业列表
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"COMS\",\"Para\":{}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				CompanyResult s = GsonUtils.json2bean(response,
						CompanyResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(getActivity(), Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {
					companys = s.Rst.Lst;
				}
				initEnterpriseView();

			}

		}.volleyStringRequestPost(getActivity(), params);

		// 获取求职列表
		Map<String, String> paramss = new HashMap<String, String>();
		paramss.put(Globals.WS_POST_KEY, "{\"Ac\":\"QZHY\",\"Para\":{}}");
		new VolleyUtil() {

			public void analysisData(String response) {
				VIPWantedResult s = GsonUtils.json2bean(response,
						VIPWantedResult.class);
				Log.i("Responsessssssssssssssssssssssssssssssssssssss", "res:"
						+ response);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(getActivity(), Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				} else {
					wanteds = s.Rst.Lst;
				}
				initWantedView();

			}

		}.volleyStringRequestPost(getActivity(), paramss);
	}

	public void initEnterpriseView() {
		if (companys != null && companys.size() > 0) {
			vipRecruitersAdapter.getDate(getActivity(), companys);

			vip_list.setAdapter(vipRecruitersAdapter);

			vip_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Intent intent = new Intent(getActivity(),
							VipCompanyDetailsActivity.class);
					intent.putExtra("companyID", companys.get(position).ID);
					intent.putExtra("companyImager", companys.get(position).RT);
					startActivity(intent);
				}
			});
		} else {
			Toast.makeText(getActivity(), R.string.job_no_info,
					Globals.TOAST_SHORT);
		}

	}

	public void initWantedView() {
		if (wanteds != null && wanteds.size() > 0) {
			vipWantedAdapter.getDate(getActivity(), wanteds);
			vip_list_tow.setAdapter(vipWantedAdapter);
		} else {
			Toast.makeText(getActivity(), R.string.job_no_info,
					Globals.TOAST_SHORT);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.vip_sel_job_ll:
			vip_list.setVisibility(View.VISIBLE);
			vip_list_tow.setVisibility(View.GONE);
			line_bule_l.setVisibility(View.VISIBLE);
			line_bule_r.setVisibility(View.GONE);
			initEnterpriseView();
			break;
		case R.id.vip_sel_loca_ll:
			vip_list_tow.setVisibility(View.VISIBLE);
			vip_list.setVisibility(View.GONE);
			line_bule_l.setVisibility(View.GONE);
			line_bule_r.setVisibility(View.VISIBLE);
			initWantedView();
			break;
		default:
			break;
		}

	}
}
