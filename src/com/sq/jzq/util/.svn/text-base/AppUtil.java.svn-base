package com.sq.jzq.util;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.maxwin.view.XListView;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;

import com.sq.jzq.Globals;
//import org.apache.log4j.Logger;

//import me.huixin.tryst.ui.LoginActivity;

/**
 * Created by Administrator on 13-12-16.
 */
public class AppUtil {
	// private static Logger logger = LogHelper.getLogger(AppUtil.class);
	private static String DeviceId;

	// /**
	// * 安装快捷图标
	// */
	// public static void installShortCut(Context activity, int shortCutIcon) {
	//
	// Intent shortcutIntent = new Intent();
	// Intent intent;
	//
	// //创建快捷方式的Intent
	// shortcutIntent = new Intent(
	// "com.android.launcher.action.INSTALL_SHORTCUT");
	// //不允许重复创建
	// shortcutIntent.putExtra("duplicate", false);
	// //需要现实的名称
	// shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "面具");
	// Parcelable shortIcon = Intent.ShortcutIconResource.fromContext(
	// activity, shortCutIcon);
	//
	// shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, shortIcon);
	//
	// intent = new Intent();
	// intent.setClass(activity.getApplicationContext(), LoginActivity.class);
	// intent.setAction(Intent.ACTION_MAIN);
	// intent.addCategory("android.intent.category.LAUNCHER");
	//
	// //点击快捷图片，运行的程序主入口
	// shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
	// //发送广播。OK
	// activity.sendBroadcast(shortcutIntent);
	// }

	public static void onLoad(XListView job_list) {
		job_list.stopRefresh();
		job_list.stopLoadMore();
		SimpleDateFormat df = new SimpleDateFormat(Globals.DATE_FORMAT);// 设置日期格式
		job_list.setRefreshTime(df.format(new Date()));
	}

	/**
	 * 获取应用程序的版本号
	 * 
	 * @return
	 */

	public static String getVersion() {
		try {
			return Globals.context.getPackageManager().getPackageInfo(
					Globals.context.getPackageName(), 0).versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 安装应用程序
	 * 
	 * @param t
	 */
	public static void installApk(File t, Context context) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(t),
				"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(intent);

		// try {
		// Runtime.getRuntime().exec("pm install " + t.getAbsolutePath());
		// Log.i(TAG,"install success");
		// } catch (IOException e) {
		//
		// Log.e(TAG,e.toString());
		// }

	}

	/**
	 * 获取sim序列号
	 * 
	 * @return 返回sim序列号
	 */

	public static String getSimIMSI() {
		TelephonyManager telManager = (TelephonyManager) Globals.context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telManager.getSubscriberId();
		return imsi;
	}

	public static String getMobile() {
		TelephonyManager telephonyManager = (TelephonyManager) Globals.context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String mobile = telephonyManager.getLine1Number();
		return mobile;
	}

	/**
	 * 获取手机设备号
	 * 
	 * @return 手机设备号
	 */

	public static String getDeviceId() {
		if (DeviceId == null) {
			final TelephonyManager tm = (TelephonyManager) Globals.context
					.getSystemService(Context.TELEPHONY_SERVICE);
			final String tmDevice, tmSerial, androidId;
			tmDevice = "" + tm.getDeviceId();
			tmSerial = "" + tm.getSimSerialNumber();
			androidId = ""
					+ android.provider.Settings.Secure.getString(
							Globals.context.getContentResolver(),
							android.provider.Settings.Secure.ANDROID_ID);
			UUID deviceUuid = new UUID(androidId.hashCode(),
					((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
			DeviceId = deviceUuid.toString();
		}
		return DeviceId;
	}

	public static boolean isTopActivty(String packageNameFlg) {
		ActivityManager activityManager = (ActivityManager) Globals.context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager
				.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			ComponentName cn = tasksInfo.get(0).topActivity;
			if (cn.getClassName().indexOf(packageNameFlg) >= 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTopActivty(Class<?> cls) {
		ActivityManager activityManager = (ActivityManager) Globals.context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager
				.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			ComponentName cn = tasksInfo.get(0).topActivity;
			Class<?> clsName = cls;
			int i = 0;
			while (clsName != Object.class && i < 20) {
				i++;
				boolean f = cn.getClassName().equals(clsName.getName());
				if (f) {
					return true;
				}
				clsName = cls.getSuperclass();
			}
		}
		return false;
	}

	/**
	 * 判断网络状态
	 * 
	 * @return
	 */
	public static boolean networkCheck() {
		// WIFI处于连接
		boolean isWIFI = isWIFIConnectivity();
		// Mobile连接
		boolean isMobile = isMobileConnectivity(Globals.context);
		return isWIFI || isMobile;
	}

	/**
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnectivity(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * WIFI是否处于连接
	 * 
	 * @param
	 * @return
	 */
	public static boolean isWIFIConnectivity() {
		ConnectivityManager manager = (ConnectivityManager) Globals.context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	public static String getTopActivityName() {
		ActivityManager activityManager = (ActivityManager) Globals.context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager
				.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			return tasksInfo.get(0).topActivity.getClassName();
		}
		return "";
	}

	public static boolean isTopActivity() {
		ActivityManager activityManager = (ActivityManager) Globals.context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager
				.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			if (Globals.context.getPackageName().equals(
					tasksInfo.get(0).topActivity.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	public static ComponentName getTopActivity() {
		ActivityManager activityManager = (ActivityManager) Globals.context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager
				.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			return tasksInfo.get(0).topActivity;
		}
		return null;
	}

	/**
	 * 收集设备信息.
	 * 
	 * @return
	 */
	public static String collectDeviceInfo(char collChar) {
		boolean encode = collChar == '&';
		StringBuffer sb = new StringBuffer();
		try {
			PackageManager pm = Globals.context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(
					Globals.context.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				sb.append("versionName=").append(
						pi.versionName == null ? "null" : pi.versionName);
				sb.append(collChar).append("versionCode=")
						.append(pi.versionCode);
			}
			// 获取渠道名
			ApplicationInfo appInfo = pm.getApplicationInfo(
					Globals.context.getPackageName(),
					PackageManager.GET_META_DATA);
			if (appInfo != null) {
				String channel = appInfo.metaData.getString("UMENG_CHANNEL");
				if (channel != null) {
					sb.append(collChar).append("channel=").append(channel);
				}
			}
		} catch (PackageManager.NameNotFoundException e) {
			// logger.error("an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object obj = field.get(null);
				if (obj != null) {
					String v = obj.toString();
					v = encode ? java.net.URLEncoder.encode(v, "utf-8") : v;
					sb.append(collChar).append(field.getName()).append("=")
							.append(v);
				}
			} catch (Exception e) {
				// logger.error("an error occured when collect crash info", e);
			}
		}
		sb.append(collChar).append("deviceId=").append(AppUtil.getDeviceId());
		sb.append(collChar).append("w=").append(Globals.screenWidth);
		sb.append(collChar).append("h=").append(Globals.screenHeight);
		return sb.toString();
	}

	// public static boolean isWorked(Class<?> cls) {
	// ActivityManager myManager = (ActivityManager) BaseApplication.context
	// .getSystemService(Context.ACTIVITY_SERVICE);
	// ArrayList<RunningServiceInfo> runningService =
	// (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
	// for (int i = 0; i < runningService.size(); i++) {
	// if (runningService.get(i).service.getClass().equals(cls)) {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * 把uri地址转化为文件路径
	 * 
	 * @param
	 * @return
	 */
	public static String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		if (contentUri.getScheme() == null
				|| !contentUri.getScheme().equals("content")) {
			return contentUri.getPath();
		}
		ContentResolver contentResolver = Globals.context.getContentResolver();
		Cursor cursor = contentResolver.query(contentUri, proj, null, null,
				null);
		int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String res = cursor.getString(index);
		cursor.close();
		return res;
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(float pxValue) {
		final float scale = Globals.context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(float dpValue) {
		final float scale = Globals.context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 验证手机号：
	 * */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
		Matcher m = p.matcher(mobiles);
		System.out.println(m.matches() + "---");
		return m.matches();
	}

	/**
	 * 
	 * 
	 * 验证邮箱：
	 * */
	public static boolean isEmail(String strEmail) {
//		String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		String strPattern = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	/**
	 * 身份证验证：
	 * */
	public static boolean isID(String s) {
		String strPattern = "^\\d{15}|^\\d{17}([0-9]|X|x)$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(s);
		return m.matches();
	}
}
