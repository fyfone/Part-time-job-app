package com.sq.jzq.views;

import com.sq.jzq.R;
import com.sq.jzq.my.EvaluationActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * ] 自定义的Dialog工厂类
 * 
 * @author Administrator
 */
public abstract class DeleteFactory {

	/**
	 * 1.确定取消按钮 按钮资字样 已经标题字样可自行填写 。 2.默认字样 确定/取消 。 3.填写string 不可为null 填写""即可。
	 * 
	 * @param context
	 *            上下文
	 * @param tetle
	 *            标题头
	 * @param btDetermine
	 *            确定按钮String
	 * @param btDismiss
	 *            取消按钮String
	 * @return
	 */
	// 删除提示框
	public DeleteFactory deleteDialog(Context context, String tetle,
			String btDetermine, String btDismiss) {

		final Dialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(R.layout.dialog_evaluation);
		Button bt_dg_ecaluation_confirm = (Button) dialog
				.findViewById(R.id.bt_dg_ecaluation_confirm);
		Button bt_dg_ecaluation_cancel = (Button) dialog
				.findViewById(R.id.bt_dg_ecaluation_cancel);

		if (!"".equals(btDetermine) && "".equals(btDismiss)) {
			bt_dg_ecaluation_confirm.setText(btDetermine);
			bt_dg_ecaluation_cancel.setText(btDismiss);
		}
		TextView tv_tetle = (TextView) dialog.findViewById(R.id.tv_tetle);
		tv_tetle.setText(tetle);
		bt_dg_ecaluation_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		bt_dg_ecaluation_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				determineButton();
				dialog.dismiss();

			}
		});
		return null;

	}

	public abstract void determineButton();

}
