package com.sq.jzq.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sq.jzq.Globals;
import com.sq.jzq.HomeActivity;
import com.sq.jzq.R;
import com.sq.jzq.adapter.JobListAdapter;
import com.sq.jzq.bean.JobResult;
import com.sq.jzq.bean.JobResult.Job;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.SharedPreferencesUtils;
import com.sq.jzq.util.VolleyUtil;

public class JobFragment extends Fragment implements OnClickListener,
		IXListViewListener {
	private LinearLayout llSelJob, llSelloca;
	private int page = 1;
	private String id = ""; // 第一条的信息ID
	private XListView xList;
	private Handler mHandler;
	private List<Job> jobs;
	JobListAdapter ja;
	private String jobType="", loca="";

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_job, container, false);

		loca = Globals.LOCACODE;
		llSelJob = (LinearLayout) view.findViewById(R.id.job_sel_job_ll); // 选择职位
		llSelloca = (LinearLayout) view.findViewById(R.id.job_sel_loca_ll); // 选择地区
		llSelJob.setOnClickListener(this);
		llSelloca.setOnClickListener(this);
		xList = (XListView) view.findViewById(R.id.job_xlist);
		xList.setXListViewListener(this);
		xList.setOnItemClickListener(new ListViewItemClickListener());
		xList.setPullLoadEnable(true);

		ja = new JobListAdapter();
		mHandler = new Handler();

		String jt = HomeActivity.jobType;
		if(!jt.equals("")) {
			jobType = jt;
		}
		
		loadData();

		return view;
	}

	






	private void loadData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY,
				"{\"Ac\":\"ZWLB\",\"Para\":{\"D\":\""+loca+"\",\"T\":\""+jobType+"\",\"P\":\""
						+ page + "\",\"I\":\"" + id + "\"}}");

		new VolleyUtil() {

			public void analysisData(String response) {
				JobResult s = GsonUtils.json2bean(response, JobResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(getActivity(), Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();

				} else {
					if (page == 1 && s.Rst.Lst.size() == 0) {
						Toast.makeText(getActivity(), R.string.job_no_info,
								Globals.TOAST_SHORT).show();
						xList.setPullLoadEnable(false);
						if (page == 1) {
							xList.setAdapter(ja);
						}
					} else {
						if(s.Rst.Lst.size() < Globals.COUNT) {
							xList.setPullLoadEnable(false);
						}
						if (page == 1) {
							jobs = s.Rst.Lst;
							id = jobs.get(0).Id;
						} else {
							List<Job> js = s.Rst.Lst;
							for (int i = 0; i < js.size(); i++) {
								jobs.add(js.get(i));
							}
						}

						ja.setData(getActivity(), jobs);
						if (page == 1) {
							xList.setAdapter(ja);
						} else {
							ja.notifyDataSetChanged();
						}
						page++;
					}
				}

			}

		}.volleyStringRequestPost(getActivity(), params);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();

		switch (arg0.getId()) {
		case R.id.job_sel_job_ll: // 选择职位
			intent.putExtra(Globals.K_TYPE, Globals.JOB);
			break;
		case R.id.job_sel_loca_ll: // 选择地区
			intent.putExtra(Globals.K_TYPE, Globals.LOCATION);
			break;
		default:
			break;
		}

		intent.setClass(getActivity(), SelActivity.class);
		startActivityForResult(intent, 0);
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (jobs != null) {
					jobs.clear();
				}
				loca = Globals.LOCACODE;
				id = "";
				page = 1;
				loadData();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadData();
				onLoad();
			}
		}, 2000);
	}

	private void onLoad() {
		xList.stopRefresh();
		xList.stopLoadMore();
		SimpleDateFormat df = new SimpleDateFormat(Globals.DATE_FORMAT);// 设置日期格式
		xList.setRefreshTime(df.format(new Date()));
	}

	private class ListViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), JobDetailActivity.class);
			intent.putExtra(Globals.K_ID, Long.toString(arg3));

			// intent.putExtra(KWV.K_TYPE, KWV.JSON_MODE_JZLIST);
			startActivity(intent);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 1:
			page = 1;
			if(jobs != null) {
				jobs.clear();
			}
			jobType = data.getStringExtra("jts");
			if(data.getStringExtra("ls").equals("")) {
				loca = Globals.LOCACODE;
			}else {
				loca = data.getStringExtra("ls");
			}
			loadData();
			break;

		default:
			break;
		}
	}
	
	
}
