package com.sq.jzq;

import java.io.File;

import android.app.Application;
import android.app.Service;
import android.graphics.Bitmap;
import android.os.Vibrator;

import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.sq.jzq.home.MyLocationListener;
import com.sq.jzq.util.RequestManager;

/**
 * 首次启动初始化数据类
 * 
 * 2015年4月16日13:14:10 fyf
 */

public class GuideApplication extends Application {
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;

	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		RequestManager.init(getApplicationContext());
		// 百度地图初始化
		// PropertyConfigurator.configure("log4j.properties");
		// // 给全局上下文赋值
		Globals.context = getApplicationContext();
		if (Globals.context != null) {
			initImageConfigure();
		}
		RequestManager.init(this);
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());

		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);

		// initMap();

	}

	public void initMap() {

		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
	}

	private static File cacheDir;

	public static File getCacheFile(String fileName) {
		File file = new File(cacheDir, fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		return file;
	}

	/**
	 * 初始化ImageLoader 设置了内存已经磁盘缓存 可以通过uri获取缓存图片
	 * 
	 * 
	 * 
	 * */
	public static ImageLoaderConfiguration getImageLoaderConfiguration() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.logo_c) // resource or
														// drawable
//				.showImageForEmptyUri(R.drawable.logo_c) // resource or
															// drawable
				.showImageOnFail(R.drawable.logo_c) // resource or drawable
				.cacheInMemory(true) // default
				.cacheOnDisk(true) // default
				.imageScaleType(ImageScaleType.EXACTLY)
				// .preProcessor(...)
				// .postProcessor(...)
				// .extraForDownloader(...)
				.considerExifParams(false) // default
				// .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //
				// default
				.bitmapConfig(Bitmap.Config.ARGB_8888) // default
				// .decodingOptions(...)
				// .displayer(new SimpleBitmapDisplayer()) // default
				// .handler(new Handler()) // default
				.build();

		return new ImageLoaderConfiguration.Builder(Globals.context)
				// .memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				// .diskCacheExtraOptions(480, 800, null)
				// .taskExecutor(...)
				// .taskExecutorForCachedImages(...)
				.threadPoolSize(2)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				// default
				// .diskCache(new UnlimitedDiscCache(cacheDir))
				.diskCacheSize(50 * 1024 * 1024)
				// .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.imageDownloader(new BaseImageDownloader(Globals.context)) // default
				// .imageDecoder(new BaseImageDecoder()) // default
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.defaultDisplayImageOptions(options)//
				.writeDebugLogs().build();
	}

	public static void initImageConfigure() {
		ImageLoader.getInstance().init(getImageLoaderConfiguration());
	}

}
