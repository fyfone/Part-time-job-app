package com.sq.jzq.home;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.Globals;
import com.sq.jzq.HomeActivity;
import com.sq.jzq.R;
import com.sq.jzq.adapter.JobListAdapter;
import com.sq.jzq.bean.HomeImagerResult;
import com.sq.jzq.bean.JobResult;
import com.sq.jzq.bean.JobResult.Job;
import com.sq.jzq.bean.JobTypeResult;
import com.sq.jzq.bean.JobTypeResult.JobType;
import com.sq.jzq.home.adapter.HomeGVAdapter;
import com.sq.jzq.job.JobDetailActivity;
import com.sq.jzq.job.JobFragment;
import com.sq.jzq.util.GsonUtils;
import com.sq.jzq.util.SharedPreferencesUtils;
import com.sq.jzq.util.UpdateVersionService;
import com.sq.jzq.util.VolleyUtil;
import com.sq.jzq.views.ListViewForScrollView;
import com.sq.jzq.views.LoopViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private LinearLayout llHead;
    public ViewPager home_viewpager;
    private ViewPager viewPager;
    private LinearLayout pointGroup;

    private List<String> imgList = new ArrayList<>();
    private ArrayList<ImageView> portImg;
    private LinearLayout llMakeMoney;
    private ListViewForScrollView lvHome;
    private JobListAdapter ja;
    private RelativeLayout rl_home_viewpager;
    private RequestQueue mRequestQueue;
    private ScrollView sv;
    private TextView loca;
    private String latitude;
    private String longitude;

    private UpdateVersionService updateVersionService;

    /**
     * 存储上一个选择项的Index
     */
    private int preSelImgIndex = 0;
    private LinearLayout ll_focus_indicator_container = null;
    View view;
    public GridView gv_houme;
    //    public MyGallery gallery;
    public LocationClient mLocationClient;
    public GeofenceClient mGeofenceClient;
    public MyLocationListener mMyLocationListener;
    // private LocationMode tempMode = LocationMode.Hight_Accuracy;
    public Vibrator mVibrator;
    private static final int AD_TIME = 4000;
    private ArrayList<ImageView> imageList;
    /**
     * 判断是否自动滚动
     */
    private boolean isRunning = false;
    //处理录播
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int p = viewPager.getCurrentItem() + 1;
            if (p == 3) {
                p = 0;
            }
            //让viewPager 滑动到下一页
            viewPager.setCurrentItem(p);
            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, AD_TIME);
            }

        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mRequestQueue = Volley.newRequestQueue(getActivity());

        if (view != null) {

            sv = (ScrollView) view.findViewById(R.id.fh_sv);
            sv.smoothScrollTo(0, 20);
            lvHome = (ListViewForScrollView) view.findViewById(R.id.home_jztj);
            lvHome.setFocusable(false);
            loca = (TextView) view.findViewById(R.id.home_loca_text);
            loca.setText(Globals.LOCA);
//            gallery = (MyGallery) view.findViewById(R.id.gallery);
            rl_home_viewpager = (RelativeLayout) view
                    .findViewById(R.id.rl_home_viewpager);
            LinearLayout button = (LinearLayout) view
                    .findViewById(R.id.bt_login);
            viewPager = (ViewPager) view.findViewById(R.id.vp_home);
            pointGroup = (LinearLayout) view.findViewById(R.id.point_group);


            lvHome.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), JobDetailActivity.class);
                    intent.putExtra(Globals.K_ID, Long.toString(arg3));
                    intent.putExtra(Globals.K_TYPE, Globals.JSON_MODE_JZLIST);
                    startActivity(intent);
                }

            });
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),
                            GetLocaActivity.class));
                    // startActivity(new Intent(getActivity(),
                    // GetLocaActivity.class));

                }
            });
            getHomeImage();

            initGridView();
            // loadData();
            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    updateVersionService = new UpdateVersionService(
                            Globals.VERSION_XML, getActivity());// 创建更新业务对象
                    updateVersionService.checkUpdate();// 调用检查更新的方法,如果可以更新.就更新
                    Log.i("Response", "versionfdsafddfdafdsf");
                }

            });
        }

        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的高度
        int screenWidth = dm.widthPixels;
        ViewGroup.LayoutParams para = rl_home_viewpager.getLayoutParams();
        para.height = screenWidth / 20 * 9;
        para.width = screenWidth;
        rl_home_viewpager.setLayoutParams(para);

        if ("".equals(SharedPreferencesUtils.getString(getActivity(),
                Globals.SHARED_LOCA, ""))
                || "".equals(SharedPreferencesUtils.getString(getActivity(),
                Globals.SHARED_LOCA_CODE, ""))) {
            initLocation();
        } else {
            Globals.LOCA = SharedPreferencesUtils.getString(getActivity(),
                    Globals.SHARED_LOCA, "");
            Globals.LOCACODE = SharedPreferencesUtils.getString(getActivity(),
                    Globals.SHARED_LOCA_CODE, "");
        }

        loca.setText(Globals.LOCA);
        loadData();
        return view;
    }

    public void initviepage() {
//        view_pager.setAdapter(new ImgAdapter(getActivity(), imgList));


    }

    //	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (requestCode) {
//		case 0:
//			loca.setText(Globals.LOCA);
//			loadData();
//			break;
//
//		default:
//			break;
//		}
//	}
    /**
     * 上一个页面的位置
     */
    protected int lastPosition;

    private void initViewPager() {
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < 3; i++) {
            // 初始化图片资源
            ImageView image = new ImageView(getActivity());
//            image.setBackgroundResource(Integer.parseInt());
            LinearLayout.LayoutParams vpll = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            image.setLayoutParams(vpll);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoader.getInstance().displayImage(imgList.get(i),
                    image);
            imageList.add(image);

            // 添加指示点
            ImageView point = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = 20;
            point.setLayoutParams(params);

            point.setBackgroundResource(R.drawable.point_bg);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }

        viewPager.setAdapter(new MyPagerAdapter());

        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            /**
             * 页面切换后调用
             * position  新的页面位置
             */
            public void onPageSelected(int position) {

                position = position % imageList.size();

                // 改变指示点的状态
                // 把当前点enbale 为true
                pointGroup.getChildAt(position).setEnabled(true);
                // 把上一个点设为false
                pointGroup.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;

            }

            @Override
            /**
             * 页面正在滑动的时候，回调
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             * 当页面状态发生变化的时候，回调
             */
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        /*
         * 自动循环： 1、定时器：Timer 2、开子线程 while true 循环 3、ColckManager 4、 用handler
		 * 发送延时信息，实现循环
		 */
        isRunning = true;
        handler.sendEmptyMessageDelayed(0, AD_TIME);
        super.onResume();
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * 获得页面的总数
         */
        public int getCount() {
            return imageList.size();
        }

        @Override
        /**
         * 销毁对应位置上的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(imageList.get(position % imageList.size()));
        }

        @Override
        /**
         * 获得相应位置上的view
         * container  view的容器，其实就是viewpager自身
         * position 	相应的位置
         */
        public Object instantiateItem(ViewGroup container, int position) {

            // 给 container 添加一个view
            container.removeView(imageList.get(position % imageList.size()));
            try {
                container.addView(imageList.get(position % imageList.size()));
            } catch (Exception e) {
                e.printStackTrace();
            }


            // 返回一个和该view相对的object
            return imageList.get(position % imageList.size());
        }

        @Override
        /**
         * 判断 view和object的对应关系
         */
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }


    }

    public void initLocation() {
        mLocationClient = new LocationClient(getActivity()
                .getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mGeofenceClient = new GeofenceClient(getActivity()
                .getApplicationContext());
        mVibrator = (Vibrator) getActivity().getApplicationContext()
                .getSystemService(Service.VIBRATOR_SERVICE);
        if (mLocationClient != null) {

            mLocationClient.start();
        }

        latitude = SharedPreferencesUtils.getString(getActivity(), "latitude",
                "");
        longitude = SharedPreferencesUtils.getString(getActivity(),
                "longitude", "");

        if (!latitude.equals("0.0") && !longitude.equals("0.0")) {
            getCity();
        }

    }

    private void getCity() {

        Map<String, String> params = new HashMap<String, String>();
        params.put(Globals.WS_POST_KEY, "{\"Ac\":\"POSN\",\"Para\":{\"N\":\""
                + latitude + "\",\"E\":\"" + longitude + "\"}}");

        new VolleyUtil() {

            public void analysisData(String response) {
                JobTypeResult s = GsonUtils.json2bean(response,
                        JobTypeResult.class);
                if (s == null || !(s.Stu == 1)) {
                    Toast.makeText(getActivity(), Globals.SER_ERROR,
                            Globals.TOAST_SHORT).show();
                } else {
                    List<JobType> jobs = s.Rst.Lst;
                    if (jobs.size() > 0) {
                        Globals.LOCA = jobs.get(0).N;
                        Globals.LOCACODE = jobs.get(0).ID;

                        SharedPreferencesUtils.saveString(getActivity(),
                                Globals.SHARED_LOCA, Globals.LOCA);
                        SharedPreferencesUtils.saveString(getActivity(),
                                Globals.SHARED_LOCA, Globals.LOCACODE);

                        loca.setText(Globals.LOCA);
                    } else {
                        Toast.makeText(getActivity(), R.string.job_no_info,
                                Globals.TOAST_SHORT);
                    }
                }

            }

        }.volleyStringRequestPost(getActivity(), params);
    }

    private void loadData() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Globals.WS_POST_KEY, "{\"Ac\":\"ZWTJ\",\"Para\":{\"A\":\""
                + Globals.LOCACODE + "\"}}");

        new VolleyUtil() {

            public void analysisData(String response) {
                JobResult s = GsonUtils.json2bean(response, JobResult.class);
                if (s == null || !(s.Stu == 1)) {
                    Toast.makeText(getActivity(), Globals.SER_ERROR,
                            Globals.TOAST_SHORT).show();
                } else {
                    List<Job> jobs = s.Rst.Lst;
                    if (jobs.size() > 0) {
                        JobListAdapter ja = new JobListAdapter(getActivity(),
                                jobs);
                        lvHome.setAdapter(ja);

                    } else {
                        Toast.makeText(getActivity(), R.string.job_no_info,
                                Globals.TOAST_SHORT);
                        if (jobs != null) {
                            jobs.clear();
                            lvHome.setAdapter(ja);
                        }
                    }
                }

            }

        }.volleyStringRequestPost(getActivity(), params);
    }

    public void getHomeImage() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Globals.WS_POST_KEY, "{\"Ac\":\"GGW\"}}");

        new VolleyUtil() {

            public void analysisData(String response) {
                HomeImagerResult s = GsonUtils.json2bean(response,
                        HomeImagerResult.class);
                if (s == null || !(s.Stu == 1)) {
                    Toast.makeText(getActivity(), Globals.SER_ERROR,
                            Globals.TOAST_SHORT).show();
                } else {
                    imgList = s.Rst.Lst;

                    if (imgList != null) {

                        initViewPager();
                    }
                }

            }

        }.volleyStringRequestPost(getActivity(), params);
    }

    // 处理首页宫格
    public void initGridView() {
        gv_houme = (GridView) view.findViewById(R.id.gv_houme);
        this.gv_houme.setAdapter(new HomeGVAdapter(getActivity()));
        gv_houme.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                HomeActivity.jobType = (arg2 + 1) + ",";
                HomeActivity.viewPager.setCurrentItem(1);
                HomeActivity.mViewItems.set(1, new JobFragment());
                HomeActivity.fragmentsUpdateFlag[1] = true;
                HomeActivity.viewAdapter.notifyDataSetChanged();

            }

        });
    }

//    // 处理轮播图
//    public void initCarousel() {
//        ll_focus_indicator_container = (LinearLayout) view
//                .findViewById(R.id.ll_focus_indicator_container);
//        InitFocusIndicatorContainer();
//        gallery = (MyGallery) view.findViewById(R.id.gallery);
//        gallery.setAdapter(new ImgAdapter(getActivity(), imgList));
//        gallery.setFocusable(true);
//        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int selIndex, long arg3) {
//                selIndex = selIndex % imgList.size();
//                // 修改上一次选中项的背景
//                portImg.get(preSelImgIndex).setImageResource(
//                        R.drawable.ic_focus);
//                // 修改当前选中项的背景
//                portImg.get(selIndex).setImageResource(
//                        R.drawable.ic_focus_select);
//                preSelImgIndex = selIndex;
//            }
//
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
//    }

//    // 处理底部轮播点
//    private void InitFocusIndicatorContainer() {
//        portImg = new ArrayList<ImageView>();
//        for (int i = 0; i < imgList.size(); i++) {
//            ImageView localImageView = new ImageView(getActivity());
//            localImageView.setId(i);
//            ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
//            localImageView.setScaleType(localScaleType);
//            LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
//                    24, 24);
//            localImageView.setLayoutParams(localLayoutParams);
//            localImageView.setPadding(5, 5, 5, 5);
//            localImageView.setImageResource(R.drawable.ic_focus);
//            portImg.add(localImageView);
//            this.ll_focus_indicator_container.addView(localImageView);
//        }
//    }

}
