/**
 * 
 */
package cn.salesuite.bus.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import cn.salesuite.bus.adapter.BusSearchAdapter;
import cn.salesuite.bus.config.Constant;
import cn.salesuite.bus.domain.BusStation;
import cn.salesuite.bus.view.BusLineOverlay;
import cn.salesuite.bus.view.BusStationOverItem;
import cn.salesuite.bus.view.OverItemT;
import cn.salesuite.bus.view.RouteSearchPoiDialog;
import cn.salesuite.saf.app.SAFApp;
import cn.salesuite.saf.utils.SAFUtil;

import com.amap.mapapi.busline.BusLineItem;
import com.amap.mapapi.busline.BusPagedResult;
import com.amap.mapapi.busline.BusQuery;
import com.amap.mapapi.busline.BusSearch;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.PoiItem;
import com.amap.mapapi.map.MapActivity;
import com.amap.mapapi.map.MapController;
import com.amap.mapapi.map.MapView;
import com.amap.mapapi.map.Overlay;
import com.amap.mapapi.map.PoiOverlay;
import com.amap.mapapi.map.RouteMessageHandler;
import com.amap.mapapi.map.RouteOverlay;
import com.amap.mapapi.poisearch.PoiPagedResult;
import com.amap.mapapi.poisearch.PoiSearch;
import com.amap.mapapi.poisearch.PoiTypeDef;
import com.amap.mapapi.poisearch.PoiSearch.Query;
import com.amap.mapapi.route.Route;

/**
 * 
 * @author Tony Shen
 *
 */
public class DisplayMapActivity extends MapActivity implements RouteMessageHandler,BusLineOverlay.BusLineMsgHandler{

	private static SAFApp app;
	private String TAG;
	private ProgressDialog mProgressDialog;
	private MapView mMapView;
	private MapController mMapController;  
	private GeoPoint point;
	private BusLineOverlay busLineOverlay = null;
	private BusPagedResult result = null;
	private BusQuery.SearchType type = null;
	private int curPage = 1;
	
	private String position;
	private String pointType,poiType,query;
	private int meters;
	private Location mLocation;
	private BusStation busStation;
	
	public static int SEARCH_STATIONS = 1;
	public static int SEARCH_ROUTE = 2;
	public static int SEARCH_STATION =3;
	public static int SEARCH_REAL_TIME = 4;
	public static int SEARCH_POIS = 6;
	public static int POINT_START_ON_MAP = 7;
	public static int POINT_END_ON_MAP = 8;
	private int searchType = 0;
	private String strStart,strEnd,routeName,stationName;
	private PoiPagedResult startSearchResult;
	private PoiPagedResult endSearchResult;
	private List<Route> routeResult;
	private GeoPoint startPoint=null;
	private GeoPoint endPoint=null;
	private RouteOverlay ol;
	private MapPointOverlay mapPointOverlay;
	
	private Handler routeHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Constant.ROUTE_START_SEARCH) {
				mProgressDialog.dismiss();
				try {
					List<PoiItem> poiItems;
					if (startSearchResult != null && (poiItems = startSearchResult.getPage(1)) != null 
							&& poiItems.size() > 0) {
						RouteSearchPoiDialog dialog = new RouteSearchPoiDialog(
								DisplayMapActivity.this, poiItems);

						dialog.setTitle("您要找的起点是:");
						dialog.show();
						dialog.setOnListClickListener(new cn.salesuite.bus.view.RouteSearchPoiDialog.OnListItemClick() {
							@Override
							public void onListItemClick(
									RouteSearchPoiDialog dialog,
									PoiItem startpoiItem) {
								startPoint = startpoiItem.getPoint();
								strStart = startpoiItem.getTitle();
//								startTextView.setText(strStart);
								endSearchResult();
							}
						});
					} else {
						showToast("无搜索起点结果,建议重新设定...");
					}
				} catch (AMapException e) {
					e.printStackTrace();
				}

			} else if (msg.what == Constant.ROUTE_END_SEARCH) {
				mProgressDialog.dismiss();
				try {
					List<PoiItem> poiItems;
					if (endSearchResult != null && (poiItems = endSearchResult.getPage(1)) != null 
							&& poiItems.size() > 0) {
						RouteSearchPoiDialog dialog = new RouteSearchPoiDialog(
								DisplayMapActivity.this, poiItems);
						dialog.setTitle("您要找的终点是:");
						dialog.show();
						dialog.setOnListClickListener(new cn.salesuite.bus.view.RouteSearchPoiDialog.OnListItemClick() {
							@Override
							public void onListItemClick(
									RouteSearchPoiDialog dialog,
									PoiItem endpoiItem) {
								endPoint = endpoiItem.getPoint();
								strEnd = endpoiItem.getTitle();
//								endTextView.setText(strEnd);
								searchRouteResult(startPoint, endPoint);
							}

						});
					} else {
						showToast("无搜索起点结果,建议重新设定...");
					}
				} catch (AMapException e) {
					e.printStackTrace();
				}

			} else if (msg.what == Constant.ROUTE_SEARCH_RESULT) {
				mProgressDialog.dismiss();
				if (routeResult != null && routeResult.size()>0) {
					Route route = routeResult.get(0);
					if (route != null) {
						if (ol != null) {
							ol.removeFromMap(mMapView);
						}
						ol = new RouteOverlay(DisplayMapActivity.this, route);
						ol.registerRouteMessage(DisplayMapActivity.this); // 注册消息处理函数
						ol.addToMap(mMapView); // 加入到地图
						ArrayList<GeoPoint> pts = new ArrayList<GeoPoint>();
						pts.add(route.getLowerLeftPoint());
						pts.add(route.getUpperRightPoint());
						mMapView.getController().setFitView(pts);//调整地图显示范围
						mMapView.invalidate();
					}
				}
			} else if (msg.what == Constant.ROUTE_SEARCH_ERROR) {
				mProgressDialog.dismiss();
				showToast((String)msg.obj);
			}
		}
	};
	
	private Handler buslineHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Constant.BUSLINE_RESULT) {
				mProgressDialog.dismiss();
				if (busLineOverlay != null) {
					busLineOverlay.removeFromMap(mMapView);
				}
				List<BusLineItem> items;
				try {
					if (result == null || (items = result.getPage(curPage)) == null || items.size() == 0) {
						Toast.makeText(getApplicationContext(), "没有找到！",
								Toast.LENGTH_SHORT).show();
					} else {
						Log.d("AMAP busline search", "item number of 1st page = " + items.size());
						Log.d("AMAP busline search", items.toString());

						showResultList(items);
					}
				} catch (AMapException e) {
					e.printStackTrace();
				}
			}
			else if (msg.what == Constant.BUSLINE_DETAIL_RESULT) {
				mProgressDialog.dismiss();
				List<BusLineItem> list;
				try {
					if(result != null) {
						list = result.getPage(1);
						if (list != null && list.size() > 0) {
							drawBusLine(list.get(0));
						}
					}
				} catch (AMapException e) {
					e.printStackTrace();
				}
			}
			else if(msg.what == Constant.BUSLINE_ERROR_RESULT){
				mProgressDialog.dismiss();
				Toast.makeText(getApplicationContext(), (String)msg.obj,
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	protected void onCreate(Bundle  savedInstanceState) {   
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.mapview);

		TAG = SAFUtil.makeLogTag(this.getClass());
		app = (SAFApp) this.getApplication();
		
		findViews();
		initViews();
		initData();
				
		addActivityToManager(this);
	} 

	private void findViews() {
		mMapView = (MapView) findViewById(R.id.mapView);
	}
	
	private void initViews() {
		mapPointOverlay = new MapPointOverlay(this);
	}

	private void initData() {
		double lat=0,lon=0;
		if (getIntent()!=null) {
			position = getIntent().getStringExtra("position");
			String[] positions = null;
			if (position!=null) {
				positions = position.split("\\s+");
			}
			if (positions!=null && positions.length>0) {
				lat = Double.parseDouble(positions[0]);
				lon = Double.parseDouble(positions[1]);
			}
			searchType = getIntent().getIntExtra("search_type", 0);
			// 站站查询相关
			strStart = getIntent().getStringExtra("start_station");
			strEnd = getIntent().getStringExtra("end_station");
			
			if (getIntent().getStringExtra("start_point")!=null) {
				startPoint = (GeoPoint) getIntent().getSerializableExtra("start_point");
			}
			
			if (getIntent().getStringExtra("end_point")!=null) {
				endPoint = (GeoPoint) getIntent().getSerializableExtra("end_point");
			}
			
			// 线路查询相关
			routeName = getIntent().getStringExtra("route_name");
			
			// 站点查询相关
			stationName = getIntent().getStringExtra("station_name");
			
			// 实时车辆情况相关
			busStation = (BusStation)getIntent().getSerializableExtra("bus_station");
			
			// 周边生活相关
			poiType = getIntent().getStringExtra("poi_type");
			meters = getIntent().getIntExtra("meters", 3000);
			query = getIntent().getStringExtra("query");
		}
		
//		mMapView.setVectorMap(true);                 //设置地图为矢量模式
		mMapView.setBuiltInZoomControls(true);       //设置启用内置的缩放控件   
		mMapController =  mMapView.getController();  // 得到mMapView 的控制权,可以用它控制和驱动平移和缩放
		
		if (lat==0 && lon==0) {
			lat = Constant.LATITUDE_DEFAULT;
			lon = Constant.LONGITUDE_DEFAULT;
		}
		
		point = new GeoPoint((int) (lat *  1E6),  
				(int) (lon * 1E6));       //用给定的经纬度构造一个GeoPoint，单位是微度(度* 1E6)  
	    mMapController.setCenter(point);  //设置地图中心点   
		mMapController.setZoom(20);       //设置地图zoom 级别

		mLocation = (Location)app.session.get(Constant.GPS_CUR_LOCATION);
		if (mLocation!=null) {
			GeoPoint point = new GeoPoint((int)(mLocation.getLatitude()*1E6), (int)(mLocation.getLongitude()*1E6));
			Drawable marker = getResources().getDrawable(R.drawable.location_android);
			marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
					.getIntrinsicHeight());
			mMapView.getOverlays().add(new OverItemT(marker,DisplayMapActivity.this,point));
		}
		
		switch(searchType) {
		case 1:
			searchStations(strStart,strEnd);
			break;
		case 2:
			searchRouteName(routeName);
			break;
		case 3:
			searchStation(stationName);
			break;
		case 4:
			searchRealtime(busStation);
			break;
		case 6:
			searchPOIS(poiType,query,meters);
			break;
		case 7:
			pointStartOnMap();
			break;
		case 8:
			pointEndOnMap();
			break;
		case 0:
			break;
		default:
			break;
		}
	}
	
	/**
	 * 创建菜单
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "定位").setIcon(android.R.drawable.ic_menu_search);
		return true;
	}
	
	/**
	* 菜单点击事件
    */
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case 1:
    		mMapView.getOverlays().clear();
    		mLocation = (Location)app.session.get(Constant.GPS_CUR_LOCATION);
    		if (mLocation!=null) {
    			GeoPoint point = new GeoPoint((int)(mLocation.getLatitude()*1E6), (int)(mLocation.getLongitude()*1E6));
    			Drawable marker = getResources().getDrawable(R.drawable.location_android);
    			marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
    					.getIntrinsicHeight());
    			mMapView.getOverlays().add(new OverItemT(marker,DisplayMapActivity.this,point));
    			mMapController.animateTo(point);
    		} else {
				Toast.makeText(this, "定位失败！", Toast.LENGTH_SHORT).show();
			}
	        
    		break;
    	}
    	return true;
    }

	/**
	 * 站站查询
	 * @param strStart
	 * @param strEnd
	 */
	private void searchStations(String strStart,String strEnd) {
		if(startPoint!=null && strStart.equals("地图上的点")){
			endSearchResult();
		} else{
			final Query startQuery = new Query(strStart, PoiTypeDef.All, "021");
			mProgressDialog = getProgressDialog(this);
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					// 调用搜索POI方法
					PoiSearch poiSearch = new PoiSearch(DisplayMapActivity.this, startQuery); // 设置搜索字符串
					try {
						startSearchResult = poiSearch.searchPOI();
						if(mProgressDialog.isShowing()){
							routeHandler.sendMessage(Message.obtain(routeHandler,
									Constant.ROUTE_START_SEARCH));
					    }
					} catch (AMapException e) {
						Message msg = new Message();
						msg.what = Constant.ROUTE_SEARCH_ERROR;
						msg.obj =  e.getErrorMessage();
						routeHandler.sendMessage(msg);
					} 
				}

			});
			t.start();			
		}
	}
	
	// 查询路径规划终点
	public void endSearchResult() {
		if(endPoint!=null&&strEnd.equals("地图上的点")){
			searchRouteResult(startPoint,endPoint);
		} else {
			final Query endQuery = new Query(strEnd, PoiTypeDef.All, "021");
			mProgressDialog = getProgressDialog(this);
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					PoiSearch poiSearch = new PoiSearch(DisplayMapActivity.this,endQuery); // 设置搜索字符串
					try {
						endSearchResult = poiSearch.searchPOI();
						if(mProgressDialog.isShowing()){
						 routeHandler.sendMessage(Message.obtain(routeHandler,
								Constant.ROUTE_END_SEARCH));
						}
					} catch (AMapException e) {
						Message msg = new Message();
						msg.what = Constant.ROUTE_SEARCH_ERROR;
						msg.obj =  e.getErrorMessage();
						routeHandler.sendMessage(msg);
					} 
				}

			});
			t.start();
		}
	}
	
	public void searchRouteResult(GeoPoint startPoint, GeoPoint endPoint) {
		mProgressDialog = getProgressDialog(this);
		final Route.FromAndTo fromAndTo = new Route.FromAndTo(startPoint,
				endPoint);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					routeResult = Route.calculateRoute(DisplayMapActivity.this,
							fromAndTo, Route.BusSaveMoney);
					if(mProgressDialog.isShowing()){
						if(routeResult!=null||routeResult.size()>0)
						routeHandler.sendMessage(Message
								.obtain(routeHandler, Constant.ROUTE_SEARCH_RESULT));
					}
				} catch (AMapException e) {
					Message msg = new Message();
					msg.what = Constant.ROUTE_SEARCH_ERROR;
					msg.obj =  e.getErrorMessage();
					routeHandler.sendMessage(msg);
				}
			}
		});
		t.start();

	}
	
	/**
	 * 线路查询
	 * @param routeName
	 */
	private void searchRouteName(final String routeName) {
		mProgressDialog = getProgressDialog(DisplayMapActivity.this);
		new Thread(new Runnable(){

			@Override
			public void run() {
				type = BusQuery.SearchType.BY_LINE_NAME;
				try {
					curPage = 1;
					BusSearch busSearch = new BusSearch(DisplayMapActivity.this,
							new BusQuery(routeName, type, "021")); // 设置搜索字符串
					busSearch.setPageSize(4);
					result = busSearch.searchBusLine();
					Log.d("AMAP POI search", "poi search page count = " + result.getPageCount());
					buslineHandler.sendEmptyMessage(Constant.BUSLINE_RESULT);
				} catch (AMapException e) {
					Message msg = new Message();
					msg.what = Constant.BUSLINE_ERROR_RESULT;
					msg.obj =  e.getErrorMessage();
					buslineHandler.sendMessage(msg);
				}
			}
			
		}).start();
	}
	
	/**
	 * 站点查询
	 * @param stationName
	 */
	private void searchStation(final String stationName) {
		mProgressDialog = getProgressDialog(DisplayMapActivity.this);
		new Thread(new Runnable(){

			@Override
			public void run() {
				type = BusQuery.SearchType.BY_STATION_NAME;
				try {
					curPage = 1;
					BusSearch busSearch = new BusSearch(DisplayMapActivity.this,
							new BusQuery(stationName, type, "021")); // 设置搜索字符串
					busSearch.setPageSize(4);
					result = busSearch.searchBusLine();
					Log.d("AMAP POI search", "poi search page count = " + result.getPageCount());
					buslineHandler.sendEmptyMessage(Constant.BUSLINE_RESULT);
				} catch (AMapException e) {
					Message msg = new Message();
					msg.what = Constant.BUSLINE_ERROR_RESULT;
					msg.obj =  e.getErrorMessage();
					buslineHandler.sendMessage(msg);
				}
			}
		}).start();
	}
	
	/**
	 * 实时车辆情况
	 * @param busStation
	 */
	private void searchRealtime(BusStation busStation) {
		GeoPoint point = new GeoPoint((int)(busStation.latitude*1E6), (int)(busStation.longitude*1E6));
		Drawable marker = getResources().getDrawable(R.drawable.bus_point);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
				.getIntrinsicHeight());
		mMapView.getOverlays().add(new BusStationOverItem(marker,DisplayMapActivity.this,point));
		
		mMapController.setCenter(point);  //设置地图中心点   
	}
	
	/**
	 * 查询附近的poi
	 * @param poiType
	 * @param query
	 * @param meters
	 */
	private void searchPOIS(String poiType, String query, int meters) {
		if (poiType!=null) {
			try{
                PoiSearch poiSearch = new PoiSearch(DisplayMapActivity.this,
						 new PoiSearch.Query(query ,poiType, "021"));
				PoiSearch.SearchBound bound=new PoiSearch.SearchBound(point,meters);
				poiSearch.setBound(bound);
				PoiPagedResult result = poiSearch.searchPOI();
				if(result.getPage(1).size()==0){
					Toast.makeText(
							getApplicationContext(), "没有找到！",
							Toast.LENGTH_SHORT).show();
				}
				PoiOverlay overlays = new PoiOverlay(null,result.getPage(1));
				overlays.addToMap(mMapView);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void pointStartOnMap() {
		showToast("在地图上点击您的起点");
		pointType = "startPoint";
		mMapView.getOverlays().add(mapPointOverlay);
	}
	
	private void pointEndOnMap() {
		showToast("在地图上点击您的终点");
		pointType="endPoint";
		mMapView.getOverlays().add(mapPointOverlay);
	}

	private  void addActivityToManager(Activity act) {
    	Log.i(TAG, "addActivityToManager");
        if (!app.activityManager.contains(act)) {
        	 Log.i(TAG , "addActivityToManager, packagename = " + act.getClass().getName()) ;
        	 app.activityManager.add(act);
	    }
	}
	
	private  void delActivityFromManager(Activity act) {
    	Log.i(TAG,"delActivityFromManager") ;
        if (app.activityManager.contains(act)) {
        	app.activityManager.remove(act);
        }
	}
	
	@Override
	protected void onDestroy() {
		if (busLineOverlay != null) {
			busLineOverlay.removeFromMap(mMapView);
		}
		super.onDestroy();
		delActivityFromManager(this);
	}
	
	private void showResultList(List<BusLineItem> list) {
		BusSearchDialog dialog = new BusSearchDialog(
				DisplayMapActivity.this, list);
		
		dialog.setTitle("搜索结果:");
		dialog.setOnListClickListener(new OnListItemClick() {
			@Override
			public void onListItemClick(
					BusSearchDialog dialog,
					final BusLineItem busLineItem) {
				mProgressDialog = ProgressDialog.show(DisplayMapActivity.this, null,
							"正在搜索...", true, false);
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						String lineId = busLineItem.getmLineId();
						BusSearch busSearch = new BusSearch(DisplayMapActivity.this,
								new BusQuery(lineId, BusQuery.SearchType.BY_ID, "021")); // 设置搜索字符串
						try {
							result = busSearch.searchBusLine();
							buslineHandler.sendEmptyMessage(Constant.BUSLINE_DETAIL_RESULT);
						} catch (AMapException e) {
							Message msg = new Message();
							msg.what = Constant.BUSLINE_ERROR_RESULT;
							msg.obj =  e.getErrorMessage();
							buslineHandler.sendMessage(msg);
						}
					}
					
				});
				t.start();
			}

		});
		dialog.show();
	}
	
	private void drawBusLine(BusLineItem busLine) {
		if (busLineOverlay != null) {
			busLineOverlay.removeFromMap(mMapView);
		}
		busLineOverlay = new BusLineOverlay(this, busLine);
		busLineOverlay.registerBusLineMessage(DisplayMapActivity.this);
		busLineOverlay.addToMap(mMapView);
		ArrayList<GeoPoint> pts = new ArrayList<GeoPoint>();
		pts.add(busLine.getLowerLeftPoint());
		pts.add(busLine.getUpperRightPoint());
		mMapView.getController().setFitView(pts);//调整地图显示范围
		mMapView.invalidate();
	}
	
	public ProgressDialog getProgressDialog(Context context) {
		return ProgressDialog.show(context,
				getString(R.string.load_title), getString(R.string.load_message));
	}
	
	public void closeProgressDialog(ProgressDialog mProgressDialog) {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	
	public void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
    interface OnListItemClick {
        /**
         * This method will be invoked when the dialog is canceled.
         * 
         * @param dialog The dialog that was canceled will be passed into the
         *            method.
         */
        public void onListItemClick(BusSearchDialog dialog,BusLineItem item);
    }
	
	public class BusSearchDialog extends Dialog implements OnItemClickListener, OnItemSelectedListener{
		private List<BusLineItem> busLineItems;
		private Context context;
		private BusSearchAdapter adapter;
		protected OnListItemClick mOnClickListener;
		private Button preButton, nextButton;
		
		public BusSearchDialog(Context context) {
			this(context,android.R.style.Theme_Dialog);
			this.context=context;
		}
		public BusSearchDialog(Context context,int theme) {
			super(context,theme);
			this.context=context;
		}
		
		public BusSearchDialog(Context context,
				List<BusLineItem> busLineItems) {
			this(context,android.R.style.Theme_Dialog);
			this.busLineItems=busLineItems;
			this.context=context;
			adapter=new BusSearchAdapter(context,busLineItems);
		}
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.navsearch_list_busline);
			ListView listView=(ListView) findViewById(R.id.ListView_busline);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, 
						int position, long id) {
					dismiss();
					mOnClickListener.onListItemClick(BusSearchDialog.this,busLineItems.get(position));
				}
			});

			onButtonClick listener = new onButtonClick();
			preButton = (Button) findViewById(R.id.preButton);
			if (curPage <= 1) {
				preButton.setEnabled(false);
			}
			preButton.setOnClickListener(listener);
			nextButton = (Button) findViewById(R.id.nextButton);
			if (curPage >= result.getPageCount()) {
				nextButton.setEnabled(false);
			}
			nextButton.setOnClickListener(listener);
		}
		
		class onButtonClick implements View.OnClickListener {

			@Override
			public void onClick(View v) {
				BusSearchDialog.this.dismiss();
				if (v.equals(preButton)) {
					curPage--;
				} else if (v.equals(nextButton)) {
					curPage++;
				}

				mProgressDialog = ProgressDialog.show(DisplayMapActivity.this, null,
							"正在搜索...", true, false);
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							result.getPage(curPage);
							buslineHandler.sendEmptyMessage(Constant.BUSLINE_RESULT);
						} catch (AMapException e) {
							Message msg = new Message();
							msg.what = Constant.BUSLINE_ERROR_RESULT;
							msg.obj =  e.getErrorMessage();
							buslineHandler.sendMessage(msg);
						}
					}
					
				});
				t.start();
			}
		}
		
		@Override
		public void onItemClick(AdapterView<?> view, View view1, int arg2, long arg3) {
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	   
	    public void setOnListClickListener(OnListItemClick l) {
	        mOnClickListener = l;
	    }
	}
	
	@Override
	public boolean onStationClickEvent(MapView mapView, BusLineOverlay overlay,
			int index) {
		return false;
	}

	@Override
	public void onDrag(MapView arg0, RouteOverlay arg1, int arg2, GeoPoint arg3) {
	}

	@Override
	public void onDragBegin(MapView arg0, RouteOverlay arg1, int arg2,
			GeoPoint arg3) {
	}

	@Override
	public void onDragEnd(MapView mapView, RouteOverlay overlay, int index,
			GeoPoint pos) {
		try {
			startPoint = overlay.getStartPos();
			endPoint = overlay.getEndPos();
//			overlay.renewOverlay(mapView);
			searchRouteResult(startPoint, endPoint);
		} catch (IllegalArgumentException e) {
			ol.restoreOverlay(mMapView);
			overlayToBack(ol, mMapView);
		} catch (Exception e1) {
			overlay.restoreOverlay(mMapView);
			overlayToBack(ol, mMapView);
		}
	}
	
	private void overlayToBack(RouteOverlay overlay, MapView mapView) {
		startPoint = overlay.getStartPos();
		endPoint = overlay.getEndPos();
	}

	@Override
	public boolean onRouteEvent(MapView arg0, RouteOverlay arg1, int arg2,
			int arg3) {
		return false;
	}
	
	public class MapPointOverlay extends Overlay{
	    private Context context;
	    private LayoutInflater inflater;
	    private View popUpView;
	    public MapPointOverlay(Context context){
	    	this.context=context;
	    	inflater = (LayoutInflater)context.getSystemService(
	    	        Context.LAYOUT_INFLATER_SERVICE);
	    }
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			// TODO Auto-generated method stub
			super.draw(canvas, mapView, shadow);                
		}

		@Override
		public boolean onTap(final GeoPoint point, final MapView view) {
			if(popUpView!=null){
				view.removeView(popUpView);
			}
		   // Projection接口用于屏幕像素点坐标系统和地球表面经纬度点坐标系统之间的变换
		    popUpView=inflater.inflate(R.layout.popup, null);
			TextView textView=(TextView) popUpView.findViewById(R.id.PoiName);
			textView.setText("点击即可选择此点");
			MapView.LayoutParams lp;
			lp = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
					MapView.LayoutParams.WRAP_CONTENT,
					point,0,0,
					MapView.LayoutParams.BOTTOM_CENTER);
				view.addView(popUpView,lp);
			popUpView.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View v) {
					if(pointType.equals("startPoint")){
						startPoint = point;
						view.removeView(popUpView);
						view.getOverlays().remove(mapPointOverlay);
						
		                Intent i = new Intent();  
		                i.putExtra("station_name","地图上的点");
		                i.putExtra("start_point",point);
		                setResult(Activity.RESULT_OK,i);  
		                finish();  
					}
					
					if(pointType.equals("endPoint")){
						endPoint = point;
						view.removeView(popUpView);
						view.getOverlays().remove(mapPointOverlay);
						
		                Intent i = new Intent();  
		                i.putExtra("station_name","地图上的点");
		                i.putExtra("end_point",point);
		                setResult(Activity.RESULT_OK,i);  
		                finish(); 
					}

				}
			});
	        return super.onTap(point, view);
		}
	}
}
