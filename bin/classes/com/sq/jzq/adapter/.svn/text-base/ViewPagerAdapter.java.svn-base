package com.sq.jzq.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;


public class ViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> list;

	public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
		super(fm);
		this.list = list;
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


	
}
