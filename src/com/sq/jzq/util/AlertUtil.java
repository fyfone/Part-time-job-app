package com.sq.jzq.util;

import android.app.AlertDialog;
import android.content.Context;

import com.sq.jzq.R;

public class AlertUtil {

	private AlertDialog dialog;
	private Context context;

	public AlertUtil(Context context) {
		this.context = context;
		alert();
	}

	public void alert() {

		dialog = new AlertDialog.Builder(context).create();
		if (context != null) {
			dialog.show();
			dialog.getWindow().setContentView(R.layout.dialog);
		}
	}

	public void closeDialog() {
		if (dialog != null && context != null) {
			dialog.dismiss();
		}

	}

}
