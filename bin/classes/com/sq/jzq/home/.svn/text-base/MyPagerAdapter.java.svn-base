package com.sq.jzq.home;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyPagerAdapter extends PagerAdapter {
	ImageView[] list;

    public MyPagerAdapter(ImageView[] list) {
//        this.list = list;
    }
    public MyPagerAdapter(){
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager pViewPager = ((ViewPager) container);
        pViewPager.removeView(list[position]);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return list.length;
    }
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ViewPager pViewPager = ((ViewPager) arg0);
        ImageView view = list[arg1];
        if (pViewPager != null && view != null)
            pViewPager.addView(view);
        return view;
    }
}
