package com.sq.jzq.job;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sq.jzq.Globals;
import com.sq.jzq.R;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.Toast;

public class MapActivity extends Activity implements
		OnGetGeoCoderResultListener {
	MapView mMapView = null;
	private BaiduMap mBaiduMap;
	GeoCoder mSearch = null;
	String addressMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		addressMap = getIntent().getStringExtra(Globals.MAP_BAIDU_ADDRESS);
		if (addressMap != null && !"".equals(addressMap)) {
			getlocalIfon();
		}
	}

	public void getlocalIfon() {
		mSearch.geocode(new GeoCodeOption().city("").address(addressMap));
	}

	// public void localInfo() {
	// LocalSearchInfo info = new LocalSearchInfo();
	// info.ak = "B266f735e43ab207ec152deff44fec8b";
	// info.geoTableId = 31869;
	// info.tags = "";
	// info.q = "天安门";
	// info.region = "北京市";
	// CloudManager.getInstance().localSearch(info);
	//
	// }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		if (arg0 == null || arg0.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(arg0.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_gcoding)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(arg0
				.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",
				arg0.getLocation().latitude, arg0.getLocation().longitude);
		float f = mBaiduMap.getMaxZoomLevel();// 19.0

		// float m = mBaiduMap.getMinZoomLevel();//3.0

		MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(arg0.getLocation(), f - 2);

		mBaiduMap.animateMapStatus(u);

		// Toast.makeText(MapActivity.this, strInfo, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		if (arg0 == null || arg0.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(arg0.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_gcoding)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(arg0
				.getLocation()));
		float f = mBaiduMap.getMaxZoomLevel();// 19.0
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(
				arg0.getLocation(), f - 2);

		mBaiduMap.animateMapStatus(u);
		// float f = mBaiduMap.getMaxZoomLevel();// 19.0
		//
		// // float m = mBaiduMap.getMinZoomLevel();//3.0
		//
		// MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, f - 2);
		//
		// mBaiduMap.animateMapStatus(u);
		// Toast.makeText(MapActivity.this, arg0.getAddress(),
		// Toast.LENGTH_LONG)
		// .show();

	}

}
