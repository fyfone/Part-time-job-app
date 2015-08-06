package com.sq.jzq.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.sq.jzq.BaseActivity;
import com.sq.jzq.Globals;
import com.sq.jzq.HomeActivity;
import com.sq.jzq.R;
import com.sq.jzq.SplashScreenActivity;
import com.sq.jzq.adapter.getLocaAdapter;
import com.sq.jzq.bean.JobTypeResult;
import com.sq.jzq.bean.JobTypeResult.JobType;
import com.sq.jzq.company.MyFragmentCompany;
import com.sq.jzq.job.JobFragment;
import com.sq.jzq.my.MyFragment;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.SharedPreferencesUtils;
import com.sq.jzq.util.VolleyUtil;

public class GetLocaActivity extends BaseActivity {
	
	List<JobType> locas;
	private getLocaAdapter dqAdapter;
	private ListView lvLoca, lvCity;
	private String id="";
	
	@Override
	public void initWidget() {
		setContentView(R.layout.activity_get_loca);
		lvLoca = (ListView)findViewById(R.id.gl_loca);
		lvCity = (ListView)findViewById(R.id.gl_city);
		dqAdapter = new getLocaAdapter();
		
		lvLoca.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				id = Long.toString(arg0.getItemIdAtPosition(arg2));
				getLoca();
			}
			
		});
		
		lvCity.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Globals.LOCACODE = Long.toString(arg0.getItemIdAtPosition(arg2));
				Globals.LOCA = locas.get(arg2).N;
				SharedPreferencesUtils.saveString(GetLocaActivity.this, Globals.SHARED_LOCA, Globals.LOCA);
				SharedPreferencesUtils.saveString(GetLocaActivity.this, Globals.SHARED_LOCA_CODE, Globals.LOCACODE);
				Globals.FROMSELLOCA = true;
//				HomeActivity.viewPager.setCurrentItem(0);
//				HomeActivity.mViewItems.set(1, new JobFragment());
//				HomeActivity.fragmentsUpdateFlag[1] = true;
//				HomeActivity.viewAdapter.notifyDataSetChanged();
				GetLocaActivity.this.finish();
			}
			
		});
		getLoca();
	}

	
	private void getLoca() {
		Map<String, String> pars = new HashMap<String, String>();
		pars.put(Globals.WS_POST_KEY, "{\"Ac\":\"GSAS\",\"Para\":{\"id\":\""+id+"\"}}");
		
		new VolleyUtil() {
			
			public void analysisData(String response) {
				JobTypeResult s = GsonUtils.json2bean(response, JobTypeResult.class);
				if (s == null || !(s.Stu == 1)) {
					Toast.makeText(GetLocaActivity.this, Globals.SER_ERROR,
							Globals.TOAST_SHORT);
				}else {
					locas = s.Rst.Lst;
					if (locas != null && locas.size() > 0) {
						dqAdapter.bindData(GetLocaActivity.this, locas);
						if(id.equals("")) {
							lvLoca.setAdapter(dqAdapter);
						}else {
							lvLoca.setVisibility(View.GONE);
							lvCity.setAdapter(dqAdapter);
						}
						
					}
//					initLoca();
				}
				
			}

		}.volleyStringRequestPost(GetLocaActivity.this, pars);
	}
	
	
	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.gl_loca:
			
			break;

		default:
			break;
		}
		
	}

}
