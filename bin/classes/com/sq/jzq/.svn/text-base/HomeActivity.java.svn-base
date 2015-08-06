package com.sq.jzq;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sq.jzq.company.MyFragmentCompany;
import com.sq.jzq.home.HomeFragment;
import com.sq.jzq.job.JobFragment;
import com.sq.jzq.my.MyFragment;
import com.sq.jzq.util.UpdateVersionService;
import com.sq.jzq.vip.VipFragment;

public class HomeActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener {

	public static ViewPager viewPager;
	public static ViewPagerAdapter viewAdapter;
	BottomViewItem item;
	public static ArrayList<Fragment> mViewItems;
	public static boolean[] fragmentsUpdateFlag = { false, false, false, false };
	private ViewPagerScroller vpc;
	private LinearLayout homeAll;
	private UpdateVersionService updateVersionService;
	public static String jobType = "";
	private HomeFragment homeFragment;
	private JobFragment jobFragment;
	private VipFragment vipFragment;
	private MyFragment myFragment;
	private MyFragmentCompany myFragmentCompany;

	// public static Activity instance;
	// private String from = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		homeAll = (LinearLayout) findViewById(R.id.home_linearlayout);
		// from = getIntent().getStringExtra("login");
		//
		// instance = this;
		mViewItems = new ArrayList<Fragment>();
		item = BottomViewItem.getInstance();

		initViews();
		setTabSelection(0);
		// }

		// new Handler().postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// updateVersionService = new UpdateVersionService(
		// Globals.VERSION_XML, HomeActivity.this);// 创建更新业务对象
		// updateVersionService.checkUpdate();// 调用检查更新的方法,如果可以更新.就更新
		// }
		// }, 1);

	}

	@Override
	protected void onResume() {
		jobType = "";
		if (Globals.FROM) {
			viewPager.setCurrentItem(3);
			if (Globals.USER_TYPE.equals("q")) {
				HomeActivity.mViewItems.set(3, new MyFragment());
			} else {
				HomeActivity.mViewItems.set(3, new MyFragmentCompany());
			}
			HomeActivity.mViewItems.set(1, new JobFragment());
			HomeActivity.fragmentsUpdateFlag[1] = true;
			HomeActivity.fragmentsUpdateFlag[3] = true;
			HomeActivity.viewAdapter.notifyDataSetChanged();
			Globals.FROM = false;
		}
		else if (Globals.FROMSELLOCA) {

//			viewPager.setCurrentItem(0);
			mViewItems.set(0, new HomeFragment());
			mViewItems.set(1, new JobFragment());
			fragmentsUpdateFlag[0] = true;
			fragmentsUpdateFlag[1] = true;
			viewAdapter.notifyDataSetChanged();
			Globals.FROMSELLOCA = false;
		}
		super.onResume();
	}

	/**
	 * 控件初始化
	 */
	private void initViews() {
		viewPager = (ViewPager) findViewById(R.id.home_viewpager);

		homeFragment = new HomeFragment();
		jobFragment = new JobFragment();
		vipFragment = new VipFragment();
		myFragment = new MyFragment();
		myFragmentCompany = new MyFragmentCompany();

		mViewItems.add(homeFragment);
		mViewItems.add(jobFragment);
		mViewItems.add(vipFragment);
		if (Globals.USER_TYPE.equals("q")) {
			mViewItems.add(myFragment);
		} else {
			mViewItems.add(myFragmentCompany);
		}

		viewAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				mViewItems);

		// homeFragment.sFragment(viewAdapter);
		viewPager.setAdapter(viewAdapter);
		if (Globals.FROM) {
			viewPager.setCurrentItem(3);
		} else {
			viewPager.setCurrentItem(0);
		}

		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnPageChangeListener(this);
		// viewPager.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View arg0, MotionEvent arg1) {
		// // TODO Auto-generated method stub
		// return true;
		// }
		// });
		for (int i = 0; i < item.viewNum; i++) {
			item.linears[i] = (LinearLayout) findViewById(item.linears_id[i]);
			item.linears[i].setOnClickListener(this);
			item.images[i] = (ImageView) findViewById(item.images_id[i]);
			item.texts[i] = (TextView) findViewById(item.texts_id[i]);
		}
	}

	public ViewPagerAdapter getViewAdapter() {
		return viewAdapter;
	}

	public void setViewAdapter(ViewPagerAdapter viewAdapter) {
		this.viewAdapter = viewAdapter;
	}

	/**
	 * @param index
	 *            根据索引值切换fragment
	 */
	private void setTabSelection(int index) {
		clearSelection();
		item.images[index].setImageResource(item.images_selected[index]);
		item.texts[index].setTextColor(getResources().getColor(
				R.color.bottom_text_selected));
	}

	/**
	 * 清空所有图标和文字状态
	 */
	private void clearSelection() {
		for (int i = 0; i < item.viewNum; i++) {
			item.images[i].setImageResource(item.images_unselected[i]);
			item.texts[i].setTextColor(getResources().getColor(
					R.color.bottom_text_unselected));
		}
	}

	@Override
	public void onClick(View v) {
		vpc = new ViewPagerScroller(HomeActivity.this);
		vpc.setScrollDuration(0);
		vpc.initViewPagerScroll(viewPager);
		for (int i = 0; i < item.linears_id.length; i++)
			if (v.getId() == item.linears_id[i]) {
				viewPager.setCurrentItem(i);
				setTabSelection(i);
			}
		vpc.setScrollDuration(1000);
		vpc.initViewPagerScroll(viewPager);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setTabSelection(arg0);
	}

	private static Boolean isQuit = false;
	Timer timer = new Timer();

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(getBaseContext(), "再按一次返回键退出程序",
						Toast.LENGTH_SHORT).show();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						isQuit = false;
					}
				};
				timer.schedule(task, 2000);
			} else {
				finish();
				System.exit(0);
			}
		}
		return false;
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// switch (keyCode) {
	// case KeyEvent.KEYCODE_BACK:
	// long secondTime = System.currentTimeMillis();
	// if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
	// Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
	// firstTime = secondTime;// 更新firstTime
	// return true;
	// } else { // 两次按键小于2秒时，退出应用
	// System.exit(0);
	// }
	// break;
	// }
	// return super.onKeyDown(keyCode, event);
	// }

	// if(timer != null) {
	// if(task != null) {
	// task.cancel();
	// }
	// }
	// task = new MyTimerTask(alert);
	// timer.schedule(task, 1000);

	// private boolean isAppInstalled(Context context,String packagename)
	// {
	// PackageInfo packageInfo;
	// try {
	// packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
	// }catch (NameNotFoundException e) {
	// packageInfo = null;
	// e.printStackTrace();
	// }
	// if(packageInfo ==null){
	// System.out.println("没有安装");
	// return false;
	// }else{
	// System.out.println("已经安装");
	// return true;
	// }
	// }

	// public void refFragment(String jobType, int currentItem) {
	// HomeActivity.jobType = jobType;
	//
	// viewPager.setCurrentItem(currentItem);
	// mViewItems.set(1, new JobFragment());
	// fragmentsUpdateFlag[currentItem] = true;
	// viewAdapter.notifyDataSetChanged();
	// }

	public class ViewPagerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> list;
		FragmentManager fm;
		FragmentTransaction ft;

		public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
			super(fm);
			this.list = list;
			this.fm = fm;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			if (list == null || list.size() == 0)
				return 1;
			return list.size();
		}

		// public void setFragments(ArrayList fragments) {
		// if(this.list != null){
		// FragmentTransaction ft = fm.beginTransaction();
		// for(Fragment f:this.list){
		// ft.remove(f);
		// }
		// ft.commit();
		// ft=null;
		// fm.executePendingTransactions();
		// }
		// this.list = fragments;
		// notifyDataSetChanged();
		// }

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ft = fm.beginTransaction();
			Fragment f = (Fragment) super.instantiateItem(container, position);

			String fragmentTag = f.getTag();
			if (fragmentsUpdateFlag[position]) {
				ft.remove(f);
				f = mViewItems.get(position);
				ft.add(container.getId(), f, fragmentTag);
				ft.attach(f);
//				ft.commit();
				ft.commitAllowingStateLoss();
				fragmentsUpdateFlag[position] = false;
			}
			return f;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

	}

}
