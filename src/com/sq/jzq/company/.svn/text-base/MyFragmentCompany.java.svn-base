package com.sq.jzq.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.R;
import com.sq.jzq.bean.User;
import com.sq.jzq.my.LoginOneActivity;
import com.sq.jzq.my.MyDataActivity;
import com.sq.jzq.my.MyMsgActivity;
import com.sq.jzq.my.MyResumeActivity;
import com.sq.jzq.my.MySetActivity;
import com.sq.jzq.my.SqjlActivity;
import com.sq.jzq.views.TitleBarView;
import com.sq.jzq.views.TitleBarView.OnClickEnterButtonListener;

public class MyFragmentCompany extends Fragment implements OnClickListener {
	View view;
	private RelativeLayout rlData, rlMsg, rlYyjl, rlAccountSecure,
			rlResumeCollect;

	private TitleBarView more_titlebar;
	private ImageView iv_head;
	private TextView tv_login;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_my_company, container, false);

		if (view != null) {

			more_titlebar = (TitleBarView) view
					.findViewById(R.id.more_titlebar);
			rlData = (RelativeLayout) view.findViewById(R.id.mc_data); // 单位资料
			rlYyjl = (RelativeLayout) view.findViewById(R.id.mc_yyjl); // 邀约记录
			rlResumeCollect = (RelativeLayout) view
					.findViewById(R.id.mc_resume_collect); // 简历收藏
			rlMsg = (RelativeLayout) view.findViewById(R.id.mc_msg); // 我的消息
			rlAccountSecure = (RelativeLayout) view
					.findViewById(R.id.mc_account_secure); // 账户与安全
			// login Textview
			tv_login = (TextView) view.findViewById(R.id.tv_login);
			// company head title
			iv_head = (ImageView) view.findViewById(R.id.iv_head);

			tv_login.setOnClickListener(this);
			rlData.setOnClickListener(this);
			rlYyjl.setOnClickListener(this);
			rlMsg.setOnClickListener(this);
			rlResumeCollect.setOnClickListener(this);
			rlAccountSecure.setOnClickListener(this);
			// 设置botton
			if (more_titlebar != null) {
				more_titlebar
						.setClickEnterButtonListener(new OnClickEnterButtonListener() {

							@Override
							public void onClickEnterButton(View v) {
								startActivity(new Intent(getActivity(),
										MySetActivity.class));
							}
						});
			}

		}
		return view;
	}

	@Override
	public void onResume() {
		initView();
		super.onResume();
	}

	public void initView() {

		Log.i("Response", "user:" + User.isLogin);
		if (!User.isLogin) {
			tv_login.setVisibility(View.VISIBLE);
			tv_login.setText("登陆/注销");
			ImageLoader.getInstance().displayImage("", iv_head);
		} else {
			Log.d("这里 ", User.getIconPath() + "就是这个图片");
			ImageLoader.getInstance().displayImage(User.getIconPath(), iv_head);
			tv_login.setVisibility(View.GONE);
		}
	}

	String loginInfoCompany;

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.mc_data:// 单位资料
			intent.setClass(getActivity(), MyDataCompanyActivity.class);
			loginInfoCompany = "data";
			break;
		case R.id.mc_msg:// 我的消息
			intent.setClass(getActivity(), ComanyMsgActivity.class);
			loginInfoCompany = "pwd";
			break;
		case R.id.mc_account_secure:// 账户与安全
			intent.setClass(getActivity(), ChangePwdActivity.class);
			loginInfoCompany = "resume";
			break;
		case R.id.mc_resume_collect:// 简历收藏
			intent.setClass(getActivity(),
					CompanyResumeCollectionActivity.class);
			loginInfoCompany = "evaluate";
			break;
		case R.id.mc_yyjl:// 邀约记录
			intent.setClass(getActivity(), CompanyInviteRecordActivity.class);
			loginInfoCompany = "msg";
			break;
		case R.id.tv_login:// 登录
			intent.setClass(getActivity(), LoginOneActivity.class);
			loginInfoCompany = "login";
			break;
		default:
			break;
		}
		if (User.isLogin == true) {
			startActivity(intent);
		} else if (User.isLogin == false) {
			Intent intents = new Intent(getActivity(), LoginOneActivity.class);
			intents.putExtra("loginInfo", loginInfoCompany);
			startActivity(intents);
		}

	}

}
