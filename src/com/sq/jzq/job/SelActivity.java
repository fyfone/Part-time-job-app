package com.sq.jzq.job;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.R;
import com.sq.jzq.adapter.SelectListAdapter;
import com.sq.jzq.adapter.SelectListAdapter.ViewHolder;
import com.sq.jzq.adapter.SelectLocaAdapter;
import com.sq.jzq.bean.JobTypeResult;
import com.sq.jzq.bean.JobTypeResult.JobType;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.TitleBarView;
import com.sq.jzq.views.TitleBarView.OnClickEnterButtonListener;

public class SelActivity extends BaseActivity {
	private TitleBarView tbvTopBar;
	private String sCatalog;
	private LinearLayout llSelJob, llSelLoca;
	private ListView lvJobType;      
	private ListView lvLoca;
	private TextView txvXzzw, txvXzdq;        //选择职位、地区
	private ImageView ivXzzw, ivXzdq;  
	private SelectListAdapter jobTypeAdapter;
	private SelectLocaAdapter dqAdapter;
	private Map<String, String> sJobTypes = new HashMap<String, String>(); // 选中职位分类的条目
	private Map<String, String> sLocas = new HashMap<String, String>(); // 选中地区的条目
	
	List<JobType> jobTypes;
	List<JobType> locas;
	
	
	@Override
	public void initWidget() {
		setContentView(R.layout.activity_sel);
		
		tbvTopBar = (TitleBarView) findViewById(R.id.sel_titlebar); 
		llSelJob = (LinearLayout)findViewById(R.id.sel_job_ll);
		llSelLoca = (LinearLayout)findViewById(R.id.sel_loca_ll);
		lvJobType = (ListView)findViewById(R.id.sel_lv_job_type);   //职位list
		lvLoca = (ListView)findViewById(R.id.sel_lv_loca);     //地区list
		txvXzzw = (TextView)findViewById(R.id.sel_xzzw_txv);
		txvXzdq = (TextView)findViewById(R.id.sel_xxdq_txv);
		ivXzzw = (ImageView)findViewById(R.id.sel_xzzw_image);
		ivXzdq = (ImageView)findViewById(R.id.sel_xxdq_image);
		
		
		jobTypeAdapter = new SelectListAdapter();
		dqAdapter = new SelectLocaAdapter();
		
		lvJobType.setOnItemClickListener(new ItemClickListener());
		lvLoca.setOnItemClickListener(new ItemLocaClickListener());
		
		sCatalog = this.getIntent().getStringExtra(Globals.K_TYPE);
		select();
		
		llSelJob.setOnClickListener(this);
		llSelLoca.setOnClickListener(this);
		
		tbvTopBar.setClickEnterButtonListener(new OnClickEnterButtonListener() {
			
			@Override
			public void onClickEnterButton(View v) {
				Intent intent = new Intent();
				String jts = "", ls = "";
//				Iterator<String> keys = sIds.keySet().iterator();
//				while (keys.hasNext()) {
//					String key = keys.next();
//					ids = ids + key + ",";
//					infos = infos + sIds.get(key) + "、";
//				}
				Iterator<String> jobTypes = sJobTypes.keySet().iterator();
				Iterator<String> locas = sLocas.keySet().iterator();
				while(jobTypes.hasNext()) {
					String key = jobTypes.next();
					jts += key+",";
				}
				while(locas.hasNext()) {
					String key = locas.next();
					ls += key+",";
			}
				intent.putExtra("jts", jts);
				intent.putExtra("ls", ls);
				setResult(1, intent);
				finish();
			}
		});
		
		loadData();
	}


	private void loadData() {
		//获取职位类别
		Map<String, String> params = new HashMap<String, String>();
		params.put(Globals.WS_POST_KEY, "{\"Ac\":\"GJTS\",\"Para\":{}}");
		
		new VolleyUtil() {
			
			public void analysisData(String response) {
				JobTypeResult s = GsonUtils.json2bean(response, JobTypeResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(SelActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT).show();
				}else {
					jobTypes = s.Rst.Lst;
//					initJobType();
					if (jobTypes != null && jobTypes.size() > 0) {
						jobTypeAdapter.bindData(SelActivity.this, jobTypes);
						lvJobType.setAdapter(jobTypeAdapter);
					}
				}
				
			}

		}.volleyStringRequestPost(SelActivity.this, params);
		
		//获取地区列表
			Map<String, String> pars = new HashMap<String, String>();
			pars.put(Globals.WS_POST_KEY, "{\"Ac\":\"GSAS\",\"Para\":{\"id\":\""+Globals.LOCACODE+"\"}}");
			
			new VolleyUtil() {
				
				public void analysisData(String response) {
					JobTypeResult s = GsonUtils.json2bean(response, JobTypeResult.class);
					if (s == null || !(s.Stu == 1)) {
						Toast.makeText(SelActivity.this, Globals.SER_ERROR,
								Globals.TOAST_SHORT);
					}else {
						locas = s.Rst.Lst;
						if (locas != null && locas.size() > 0) {
							dqAdapter.bindData(SelActivity.this, locas);
							lvLoca.setAdapter(dqAdapter);
						}
//						initLoca();
					}
					
				}

			}.volleyStringRequestPost(SelActivity.this, pars);
		
	}

	private void initJobType() {
//		if (jobTypes != null && jobTypes.size() > 0) {
//			jobTypeAdapter.bindData(SelActivity.this, jobTypes);
//			lvJobType.setAdapter(jobTypeAdapter);
//		}
		jobTypeAdapter.notifyDataSetChanged();
		select();
		
	}

	private void initLoca() {
//		if (locas != null && locas.size() > 0) {
//			dqAdapter.bindData(SelActivity.this, locas);
//			lvLoca.setAdapter(dqAdapter);
//		}
		select();
		
	}
	
	
	private void select() {
		if(sCatalog.equals(Globals.JOB)) {
			txvXzzw.setTextColor(getResources().getColor(R.color.background_blue));
			txvXzdq.setTextColor(getResources().getColor(R.color.text_888));
			ivXzzw.setImageDrawable(getResources().getDrawable(R.drawable.job_select_sjx));
			ivXzdq.setImageDrawable(getResources().getDrawable(R.drawable.job_left_sjx));
			lvJobType.setVisibility(View.VISIBLE);
			lvLoca.setVisibility(View.GONE);
		}
		if(sCatalog.equals(Globals.LOCATION)) {
			txvXzzw.setTextColor(getResources().getColor(R.color.text_888));
			txvXzdq.setTextColor(getResources().getColor(R.color.background_blue));
			ivXzzw.setImageDrawable(getResources().getDrawable(R.drawable.job_left_sjx));
			ivXzdq.setImageDrawable(getResources().getDrawable(R.drawable.job_select_sjx));
			lvJobType.setVisibility(View.GONE);
			lvLoca.setVisibility(View.VISIBLE);
		}
	}
	
	
	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.sel_job_ll:    //职位选择
			sCatalog = Globals.JOB;
			jobTypeAdapter.notifyDataSetChanged();
			select();
//			initJobType();
			break;
		case R.id.sel_loca_ll:    //地区选择
			sCatalog = Globals.LOCATION;
			dqAdapter.notifyDataSetChanged();
			select();
//			initLoca();
			break;
		default:
			break;
		}
		
	}

	/**
	 * 点击item
	 * 
	 * @author Administrator
	 * 
	 */
	private class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			ViewHolder vh = (ViewHolder) arg1.getTag();
			vh.select.toggle();
			if(sCatalog.equals(Globals.JOB)) {
				SelectListAdapter.getIsSelected().put(jobTypes.get(arg2).ID,
						vh.select.isChecked());

				if (vh.select.isChecked()) {
					sJobTypes.put(jobTypes.get(arg2).ID,
							jobTypes.get(arg2).N);
				} else {
					sJobTypes.remove(jobTypes.get(arg2).ID);
				}
			}
			
		}

	}
	
	private class ItemLocaClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			com.sq.jzq.adapter.SelectLocaAdapter.ViewHolder vh = (com.sq.jzq.adapter.SelectLocaAdapter.ViewHolder) arg1.getTag();
			vh.select.toggle();
			if(sCatalog.equals(Globals.LOCATION)) {
				SelectLocaAdapter.getIsSelected().put(locas.get(arg2).ID,
						vh.select.isChecked());

				if (vh.select.isChecked()) {
					sLocas.put(locas.get(arg2).ID,
							locas.get(arg2).N);
				} else {
					sLocas.remove(locas.get(arg2).ID);
				}
			}
			
		}

	}
	
}
