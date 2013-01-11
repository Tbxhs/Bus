/**
 * 
 */
package cn.salesuite.bus.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.salesuite.bus.BaseActivity;
import cn.salesuite.bus.adapter.BusStationAdapter;
import cn.salesuite.bus.adapter.RealTimeBusStationAdapter2;
import cn.salesuite.bus.config.Constant;
import cn.salesuite.bus.domain.BusStation;
import cn.salesuite.bus.domain.BusStatus;
import cn.salesuite.bus.view.StationViewPager;
import cn.salesuite.saf.http.CommHttpClient;
import cn.salesuite.saf.http.CommHttpClient.OnResponseReceivedListener;
import cn.salesuite.saf.utils.SAFUtil;
import cn.salesuite.saf.utils.StringHelper;

/**
 * @author Tony Shen
 *
 */
public class SearchRealTimeActivity extends BaseActivity{

	private Button backBtn,nextBtn;
	private TextView topTitle;
	private List<View> stationViewList;
	private StationViewPager viewPager;
	
	private List<BusStation> stations; //上行线路
	private List<BusStation> stations2;//下行线路
	private List<BusStatus> upLineBusStatusList = new ArrayList<BusStatus>();
	private List<BusStatus> downLineBusStatusList = new ArrayList<BusStatus>();
	private List<BusStatus> upLineOutsideList = new ArrayList<BusStatus>();
	private List<BusStatus> downLineOutsideList = new ArrayList<BusStatus>();
	
	private RealTimeBusStationAdapter2 uplineAdapter;
	private RealTimeBusStationAdapter2 downLineAdapter;
	
	private ListView uplineListView;
	private ListView downlineListView;
	public static final long ONE_MINUTE = 60000;
	public static final int REFERSH = 1;
	public static final String IN_STATION = "1";
	public static final String OUTSIDE_STATION = "0";
	
	public static final String UPLINE = "0";
	public static final String DOWNLINE = "1";
	
	Timer timer = new Timer();

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFERSH:
				resetData();
	        	getAllBusStatus(Constant.getBusKeyId());
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_real_time);

		findViews();
		initViews();
		initData();
		
		addFootMenuView();
	}

	private void findViews() {
		backBtn = (Button) findViewById(R.id.btn_back);
		nextBtn = (Button) findViewById(R.id.btn_next);
		topTitle = (TextView) findViewById(R.id.top_title);
		viewPager = (StationViewPager)findViewById(R.id.station_pager);
	}

	private void initViews() {
		backBtn.setVisibility(View.GONE);
		nextBtn.setVisibility(View.GONE);
		topTitle.setText("掌上公交");
		stationViewList = new ArrayList<View>();
		for(int i=0;i<2;i++) {
			View view = mInflater.inflate(R.layout.station_pager_item, null);
			TextView busLine = (TextView) view.findViewById(R.id.busline);
			if (i==0) {
				busLine.setText("周祝公路->漕溪公交枢纽");
			} else if(i==1) {
				busLine.setText("漕溪公交枢纽->周祝公路");
			}
			
			if (i==0) {
				uplineListView = (ListView) view.findViewById(R.id.stationlist);
				uplineAdapter = new RealTimeBusStationAdapter2(this,getAllStations(i));
				uplineListView.setAdapter(uplineAdapter);
//				uplineListView.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2,
//							long arg3) {
//						mProgressDialog = getProgressDialog(SearchRealTimeActivity.this);
//						new Thread() {
//							public void run() {
//								try {
//									Intent i = new Intent(SearchRealTimeActivity.this,
//											DisplayMapActivity.class);
//									if (getLastLocation() != null) {
//										String position = getLastLocation().getLatitude() + " " + getLastLocation().getLongitude();
//										i.putExtra("position", position);
//									}
//									i.putExtra("bus_station", (Serializable) stations.get(arg2));
//									i.putExtra("search_type", DisplayMapActivity.SEARCH_REAL_TIME);
//									startActivity(i);
//								} finally {
//									closeProgressDialog(mProgressDialog);
//								}
//							}
//						}.start();
//					}
//				});
			} else if (i==1) {
				downlineListView = (ListView) view.findViewById(R.id.stationlist);
				downLineAdapter = new RealTimeBusStationAdapter2(this,getAllStations(i));
				downlineListView.setAdapter(downLineAdapter);
//				downlineListView.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2,
//							long arg3) {
//						mProgressDialog = getProgressDialog(SearchRealTimeActivity.this);
//						new Thread() {
//							public void run() {
//								try {
//									Intent i = new Intent(SearchRealTimeActivity.this,
//											DisplayMapActivity.class);
//									if (getLastLocation() != null) {
//										String position = getLastLocation().getLatitude() + " " + getLastLocation().getLongitude();
//										i.putExtra("position", position);
//									}
//									i.putExtra("bus_station", (Serializable) stations2.get(arg2));
//									i.putExtra("search_type", DisplayMapActivity.SEARCH_REAL_TIME);
//									startActivity(i);
//								} finally {
//									closeProgressDialog(mProgressDialog);
//								}
//							}
//						}.start();
//					}
//				});
			}
	        
			stationViewList.add(view);
		}
		
		viewPager.setAdapter(new BusStationAdapter(stationViewList));
	}
	
	private void initData() {
		timer.scheduleAtFixedRate(new TimerTask() {  
            @Override  
            public void run(){  
                Message mesasge = new Message();  
                mesasge.what = REFERSH;  
                mHandler.sendMessage(mesasge);  
            }  
        }, ONE_MINUTE/2, ONE_MINUTE);  
	}
	
	protected void onResume() {
		super.onResume();
	}
	
	protected void onPause() {
		super.onPause();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}
	
	/**
	 * 需要清空upLineBusStatusList、upLineBusStatusList<br>
	 * 重置stations、stations2数据
	 */
	private void resetData() {
    	upLineBusStatusList.clear();
    	downLineBusStatusList.clear();
    	upLineOutsideList.clear();
    	downLineOutsideList.clear();
    	stations = Constant.getWanZhouBusLine();
    	stations2 = Constant.getWanZhouDownBusLine();
	}
	
	private List<Map<String, Object>> getAllStations(int i) {
		stations = Constant.getWanZhouBusLine();
		if (i==0) {
			return getUpLineBusStationData(stations);
		} else {
			stations2 = Constant.getWanZhouDownBusLine();
			return getDownLineBusStationData(stations2);
		}
	}
	
	private List<Map<String, Object>> getUpLineBusStationData(
			List<BusStation> stations) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> item = null;
		
		for (BusStation station : stations) {
			item = new HashMap<String, Object>();
			if (station.uplineHasBus) {
				item.put("Icon", R.drawable.online_car_icon);
			}
			if (station.middleStatus) {
				item.put("MiddleStatus", "true");
			}
			item.put("Station", station.stationName);
			data.add(item);
		}
		Log.i(TAG,"upline data="+SAFUtil.printObject(data));
		return data;
	}
	
	private List<Map<String, Object>> getDownLineBusStationData(
			List<BusStation> stations2) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> item = null;

		for (BusStation station : stations2) {
			item = new HashMap<String, Object>();
			if (station.downLineHasBus) {
				item.put("Icon", R.drawable.online_car_icon);
			}
			if (station.middleStatus) {
				item.put("MiddleStatus", "true");
			}
			item.put("Station", station.stationName);
			data.add(item);
		}
		Log.i(TAG,"down data="+SAFUtil.printObject(data));
		return data;
	}

	/**
	 * 获取当前所有车辆的运行情况
	 * @param keyids
	 */
	private void getAllBusStatus(List<String> keyids) {
		String[] keyidStrs = keyids.toArray(new String[] {});
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		for (String keyid : keyidStrs) {
			Future<String> future = (Future<String>) threadPool.submit(new GetBusStatusTask(keyid));
			resultList.add(future);
		}
		for (Future<String> fs : resultList) {
			try {
				fs.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} finally {
				threadPool.shutdown();
			}
		}
		
		drawUpLine();
		drawDownLine();
	}

	private void drawUpLine() {
		if (upLineBusStatusList.size()>0) {
			for(BusStatus busStatus:upLineBusStatusList) {
				String stationName = busStatus.station;
				for(BusStation station:stations){
					if (station.stationName.contains(stationName)){
						station.uplineHasBus = true;
					}
				}
			}
		}
		
		if (upLineOutsideList.size()>0) {
			for (BusStatus busStatus : upLineOutsideList) {
				if (busStatus!=null) {
					String stationName = busStatus.station;
					for (BusStation station : stations) {
						if (station.stationName.contains(stationName)) {
							station.middleStatus = true;
						}
					}
				}
			}
		}

		uplineAdapter = new RealTimeBusStationAdapter2(this,getUpLineBusStationData(stations));
		uplineListView.setAdapter(uplineAdapter);
		uplineAdapter.notifyDataSetChanged();
	}
	
	
	private void drawDownLine() {
		if (downLineBusStatusList.size()>0) {
			for(BusStatus busStatus:downLineBusStatusList) {
				String stationName = busStatus.station;
				for(BusStation station:stations2){
					if (station.stationName.contains(stationName)){
						station.downLineHasBus = true;
					}
				}
			}
		}
		
		if (downLineOutsideList.size()>0) {
			for (BusStatus busStatus : downLineOutsideList) {
				String stationName = busStatus.station;
				for (BusStation station : stations2) {
					if (station.stationName.contains(stationName)) {
						station.middleStatus = true;
					}
				}
			}
		}

		downLineAdapter = new RealTimeBusStationAdapter2(this,getDownLineBusStationData(stations2));
		downlineListView.setAdapter(downLineAdapter);
		downLineAdapter.notifyDataSetChanged();
	}

	class GetBusStatusTask implements Runnable {
		private String keyid;
	    
	    public GetBusStatusTask(String keyid) {
	        this.keyid = keyid;
	    }

		@Override
		public void run() {
			CommHttpClient httpUtil = new CommHttpClient();
			HashMap<String, String> postdata = new HashMap<String, String>();
			postdata.put("keyid", keyid);
			
			try {
				httpUtil.makeHTTPRequest(Constant.GET_BUS_STATUS,
						postdata, null, new OnResponseReceivedListener() {
							@Override
							public void onResponseReceived(InputStream response) {
								// 解析数据
								if (response == null)
									return;
								try {
									String jsonString = StringHelper.inputStream2String(response)
											.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "")
											.replace("<string xmlns=\"http://tempuri.org/\">","")
											.replace("</string>", "");
									JSONObject json = new JSONObject(jsonString);
									if ("true".equals(json.getString("success"))) {
										JSONObject result = json.getJSONObject("result");
										BusStatus busStatus = new BusStatus();
										busStatus.carid = result.getString("CARID");
										busStatus.keyid = result.getString("KEYID");
										busStatus.station = result.getString("SATATIONNAME");
										busStatus.direction = result.getString("DIRECTION");
										busStatus.instation = result.getString("INSTATION");
										Log.i(TAG,"direction="+busStatus.direction);
										if (UPLINE.equals(busStatus.direction)) {
											if (IN_STATION.equals(busStatus.instation)){
												upLineBusStatusList.add(busStatus);
											} else if (OUTSIDE_STATION.equals(busStatus.instation)) {
												upLineOutsideList.add(busStatus);
											}
										} else if (DOWNLINE.equals(busStatus.direction)) {
											if (IN_STATION.equals(busStatus.instation)){
												downLineBusStatusList.add(busStatus);
											} else if (OUTSIDE_STATION.equals(busStatus.instation)) {
												downLineOutsideList.add(busStatus);
											}
										}
									}
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}