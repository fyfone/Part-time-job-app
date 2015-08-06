package com.sq.jzq.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.testpic.Bimp;
import com.example.testpic.PublishedActivity.GridAdapter;
import com.sq.jzq.Globals;

public class DownImage {
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/" + Globals.SDPATH + "/";
	static String path = "";

	public static void setImageForImageView(final String imageUrl, final String id, final GridAdapter ga) {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.obj == null) {
					return;
				}
				path = (String) msg.obj;
				Bimp.drr.add(path);
				ga.update1();
			}
		};

		// 子线程不能更新UI,又不然会报错
		new Thread() {
			public void run() {
				String p = "";
				Bitmap bitmap = loadImageFromUrl(imageUrl);
				if (bitmap == null) {
					Log.i(Globals.LOG_TAG, "single imageview of drawable is null");
				} else {
					// 把已经读取到的图片存入本地
					try {
						p = saveOnlinePictureToCard(bitmap, id);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Message message = handler.obtainMessage(0, p);
				handler.sendMessage(message);
			};
		}.start();

	}

	/**
	 * 通过图片地址,返回drawable
	 * 
	 * @param url
	 * @return
	 */
	private static Bitmap loadImageFromUrl(String url) {
		ByteArrayOutputStream out = null;
		Bitmap bitmap = null;
		int BUFFER_SIZE = 1024 * 16;
		InputStream inputStream = null;
		try {
			inputStream = new URL(url).openStream();
			BufferedInputStream in = new BufferedInputStream(inputStream,
					BUFFER_SIZE);
			out = new ByteArrayOutputStream(BUFFER_SIZE);
			int length = 0;
			byte[] tem = new byte[BUFFER_SIZE];
			length = in.read(tem);
			while (length != -1) {
				out.write(tem, 0, length);
				length = in.read(tem);
			}
			in.close();
			bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0,
					out.toByteArray().length);

		} catch (Exception e) {
			Log.i(Globals.LOG_TAG, "Exception:" + e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					Log.i(Globals.LOG_TAG, "Exception:" + e.getMessage());
				}
			}
		}
		return bitmap;
	}

	private static String saveOnlinePictureToCard(Bitmap bm, String fileName)
			throws IOException {
		File dirFile = new File(SDPATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		String onlineFilePath = SDPATH + Globals.FILE_NAME_PREFIX + fileName + ".JPEG";
		File myOnlineFile = new File(onlineFilePath);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myOnlineFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		bos.flush();
		bos.close();
		return onlineFilePath;
	}
}
