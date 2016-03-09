package com.example.map;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.example.bean.Place;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.GpsSatellite;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	MapView mMapView = null;
	BaiduMap baiduMap;
	
	private List<Place> placeList;//�����б�
	
	XMLReader xmlReader;
	InputSource inputSource;//����������Դ
	GpxFileContentHandler gpxHandler;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				setPos();
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
		// ע��÷���Ҫ��setContentView����֮ǰʵ��
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
		baiduMap = mMapView.getMap();
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		
		placeList = new ArrayList<Place>();
		try {
			SAXParserFactory saxFactory = SAXParserFactory.newInstance(); 
			xmlReader = saxFactory.newSAXParser().getXMLReader();
			gpxHandler = new GpxFileContentHandler();
			xmlReader.setContentHandler(gpxHandler);
			inputSource = new InputSource(getAssets().open("demo.gpx"));
			new Thread(new loadRunnable()).start();
//			Toast.makeText(this, "�ɹ�", 1).show();
		} catch (Exception e) {
			Toast.makeText(this, "ʧ��", 1).show();
			e.printStackTrace();
		}
		
		
	}
	
	
	class loadRunnable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				xmlReader.parse(inputSource);
				placeList = gpxHandler.getLocationList();
				handler.sendEmptyMessage(1);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
	}
	
	
	/**
	 * ����·��
	 */
	private void setPos(){
		List<LatLng> pts = new ArrayList<LatLng>();
		for (int i = 0; i < placeList.size(); i++) {
			LatLng pt = new LatLng(placeList.get(i).lat, placeList.get(i).lon);
			pts.add(pt);
		}
		OverlayOptions doOptions = new PolylineOptions().width(5)
				.color(0xAAFF0000).points(pts);
		baiduMap.addOverlay(doOptions);
		
		LatLng cenpt = new LatLng(placeList.get(placeList.size()/2).lat, placeList.get(placeList.size()/2).lon);
		MapStatus status = new MapStatus.Builder().target(cenpt).zoom(6).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(status);
		baiduMap.setMapStatus(mMapStatusUpdate);
		
	}
	
	
	
	
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}
}
