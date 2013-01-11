package cn.salesuite.bus.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.salesuite.bus.BaseActivity;
import cn.salesuite.bus.config.Constant;
import cn.salesuite.bus.util.AppUtil;
import cn.salesuite.saf.location.LocationUtil;
import cn.salesuite.saf.utils.SAFUtil;

import com.amap.mapapi.poisearch.PoiTypeDef;

/**
 * @author Tony Shen
 *
 */
public class MainActivity extends BaseActivity {

	private Location mLocation;
	private SharedPreferences sp;
	private GridView gridview;
	private Button backBtn,nextBtn;
	private TextView leftText,topTitle;
	private AbsoluteLayout adLayout;
	private ImageView adView,ad_delete;
	private boolean gpsThread = true;
	private String cityname;

	public static final long ONE_MINUTE = 60000;
	public static final long FIVE_SECONDS = 5000;
	
	private int adTag = 0;
	
	private Handler handler = new Handler() {  
		@Override 
        public void handleMessage(Message msg) {  
            if (adTag > 3)  {  
            	adTag = 0;  
            } else {  
                switch (adTag)  
                {  
                case 1:  
                	adView.setImageResource(R.drawable.ad_1);  
                    break;  
                case 2:  
                	adView.setImageResource(R.drawable.ad_2);  
                    break;  
                case 3:  
                	adView.setImageResource(R.drawable.ad_3);  
                    break;  
                default:  
                    break;  
                }  
                adLayout.invalidate();  
            }  
            super.handleMessage(msg);  
        }  
    };  
	
	private Runnable mLocationRunnable = new Runnable() {
		public void run() {
			if (mLocationManager != null) {
				if (getLastLocation() == null) {
//					showToast(R.string.error_get_location);
				}
			}
		}
	};
	
    Timer timer = new Timer();//���ڹ����ʾ
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		if (getLastLocation() == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mLocationManager.push();
			handler.postDelayed(mLocationRunnable, ONE_MINUTE);
		}

		sp = this.getSharedPreferences(Constant.SHARED,Activity.MODE_PRIVATE);
        
		findViews();
		initViews();
		initData();
		
		addFootMenuView();
    }

	private void findViews() {
		gridview = (GridView) findViewById(R.id.gridview);
		backBtn = (Button) findViewById(R.id.btn_back);
		nextBtn = (Button) findViewById(R.id.btn_next);
		leftText = (TextView) findViewById(R.id.left_text);
		topTitle = (TextView) findViewById(R.id.top_title);
		adLayout = (AbsoluteLayout)findViewById(R.id.ad);
		adView = (ImageView) findViewById(R.id.adview);
		ad_delete = (ImageView) findViewById(R.id.ad_delete);
	}

	private void initViews() {
		backBtn.setVisibility(View.GONE);
		nextBtn.setVisibility(View.GONE);
		topTitle.setText("���Ϲ���");
		
		ad_delete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				timer.cancel();
				adLayout.setVisibility(View.GONE);
			}
			
		});
		
		ArrayList<HashMap<String, Object>> imageItemList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		item = new HashMap<String, Object>();
		item.put("ItemImage", R.drawable.search_stations);
		item.put("ItemText", "��վվ��ѯ");
		imageItemList.add(item);
		
		item = new HashMap<String, Object>();
		item.put("ItemImage", R.drawable.search_route);
		item.put("ItemText", "��·�߲�");
		imageItemList.add(item);
		
		item = new HashMap<String, Object>();
		item.put("ItemImage", R.drawable.search_station);
		item.put("ItemText", "��վ���");
		imageItemList.add(item);
		
		item = new HashMap<String, Object>();
		item.put("ItemImage", R.drawable.search_real_time);
		item.put("ItemText", "ʵʱ�������");
		imageItemList.add(item);
		
		item = new HashMap<String, Object>();
		item.put("ItemImage", R.drawable.search_map);
		item.put("ItemText", "���ص��");
		imageItemList.add(item);
		
		item = new HashMap<String, Object>();
		item.put("ItemImage", R.drawable.search_nearby);
		item.put("ItemText", "�ܱ�����");
		imageItemList.add(item);
		
		item = new HashMap<String, Object>();
		item.put("ItemImage", R.drawable.feedback);
		item.put("ItemText", "�������");
		imageItemList.add(item);

		SimpleAdapter saImageItems = new SimpleAdapter(this,
				imageItemList,
				R.layout.grid_item,
				new String[] { "ItemImage", "ItemText" },
				new int[] { R.id.ItemImage, R.id.ItemText });
		gridview.setAdapter(saImageItems);
		
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					Intent searchStations = new Intent(MainActivity.this,SearchStationsActivity.class);
					startActivity(searchStations);
					return;
				case 1:
					Intent searchRoute = new Intent(MainActivity.this,SearchRouteActivity.class);
					startActivity(searchRoute);
					return;
				case 2:
					Intent searchStation = new Intent(MainActivity.this,SearchStationActivity.class);
					startActivity(searchStation);
					return;
				case 3:
					Intent searchRealTime = new Intent(MainActivity.this,SearchRealTime2Activity.class);
					startActivity(searchRealTime);
					return;
				case 4:
					Intent searchMap = new Intent(MainActivity.this,DisplayMapActivity.class);
					if (getLastLocation()!=null) {
						String position = getLastLocation().getLatitude()+" "+getLastLocation().getLongitude();
						searchMap.putExtra("position", position);
					}
					searchMap.putExtra("poi_type", PoiTypeDef.All);
					searchMap.putExtra("query", "������վ");
					searchMap.putExtra("meters", 3000);
					searchMap.putExtra("search_type", DisplayMapActivity.SEARCH_POIS);
					startActivity(searchMap);
					return;
				case 5:
					Intent searchNearby = new Intent(MainActivity.this, SearchNearbyActivity.class);
		            startActivity(searchNearby);
					return;
				case 6:
//					Intent exchangeCity = new Intent(MainActivity.this, ExchangeCityActivity.class);
//		            startActivity(exchangeCity);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Arashmen@163.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "�������");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    emailIntent.setType("text/html");
                    startActivity(Intent.createChooser(emailIntent, "��ѡ��Email�ͻ������"));  
					return;
				}
			}
			
		});
	}

	private void initData() {
		timer.scheduleAtFixedRate(new TimerTask()  
        {  
            @Override  
            public void run(){  
                adTag++;  
                Message mesasge = new Message();  
                mesasge.what = adTag;  
                handler.sendMessage(mesasge);  
            }  
        }, 0, FIVE_SECONDS);  
		
		mLocation = getLastLocation();
		
		cityname = (String) app.session.get(Constant.CITY_NAME);

		if (mLocation != null)
			app.session.put(Constant.GPS_CUR_LOCATION, mLocation);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (gpsThread) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					mLocation = (Location) app.session.get(Constant.GPS_CUR_LOCATION);

					final String gpsCityName = getCurrentCityName(mLocation);
					System.out.println("cityname : " + cityname);
					System.out.println("mLocation : " + mLocation);
					System.out.println("gpsCityName : " + gpsCityName);
					if (gpsCityName != null) {
						app.session.put(Constant.GPS_CITYNAME, gpsCityName);
//						app.session.put(Constant.GPS_CITYCODE, cityCode);
						app.session.put(Constant.CITY_NAME, gpsCityName);
//						app.session.put(Constant.CITY_CODE, cityCode);
						
						handler.post(new Runnable() {  
			                    public void run() {  
			                    	leftText.setText(gpsCityName);
			                    }  
			                });  
						gpsThread = false;
					}
				}
			}
		}).start();
	}
	
	public void onLocationChanged(final Location location) {
		if (location != null) {
			// λ�ò����й�������λ�ñ仯
			if (!LocationUtil.positionInChina(location)) {
				return;
			}
			
			app.session.put(Constant.GPS_CUR_LOCATION, location);
			
			new Thread() {
				public void run() {
					String gpsCityName = getCurrentCityName(location);
					String cityName = (String) app.session.get(Constant.CITY_NAME);
					System.out.println("mLocation : " + location);
					System.out.println("gpsCityName : " + gpsCityName);
					if (gpsCityName != null) {
						app.session.put(Constant.GPS_CITYNAME, gpsCityName);
//						app.session.put(Constant.GPS_CITYCODE,cityName2Code(gpsCityName));
					}
				}
			}.start();
		}
	}
	
	private String getCurrentCityName(Location mLocation) {
		String curCityName = null;
		if (mLocation != null) {
			try{
				Class.forName("com.google.android.maps.MapActivity");
				return AppUtil.getCityFromLocation(mLocation, this);
			}catch(Exception e){
				// ����֧��google��Geocoderʱ,ʹ�ðٶȵ�geocode api
				curCityName = geocodeUseBaiduAPI(mLocation.getLatitude()+"",mLocation.getLongitude()+"");
			}
		}
		return curCityName;
	}

	protected void onResume() {
		super.onRestart();
		menuSelceted(1);
	}

	protected void onRestart() {
		super.onRestart();
		if (SAFUtil.isAppRunning(this, getPackageName())) {
			if (mLocationManager != null)
				mLocationManager.resume();
		}
		menuSelceted(1);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (!SAFUtil.isAppRunning(this, getPackageName())) {
			if (mLocationManager != null)
				mLocationManager.pause();
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		mLocationManager.destroy(); // ������MainActivity�е��ø÷���,�����activity������ø÷���

		gpsThread = false;          // �˳�ʱ,�ر��߳�
		handler.removeCallbacks(mLocationRunnable);
		timer.cancel();
		clearApplicationCache();    // ɾ����������
        
		AppUtil.saveCityInfo(sp, app);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			confirmQuit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void confirmQuit() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage(getString(R.string.confirm_to_quit));
		builder.setTitle(getString(R.string.confirm));
		builder.setPositiveButton(getString(R.string.ok),
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (dialog != null) {
							dialog.dismiss();
						}
						clearApplicationCache();
						backToMainActivity();// ����������activity,�����MainActivity,��ֹmLocationManager����ָ���쳣
						MainActivity.this.finish();
					}
				});

		builder.setNegativeButton(getString(R.string.cancel),
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}
	
	/**
	 * �˳�ʱ���app��������
	 */
	private void clearApplicationCache() {
	}
	
	private String geocodeUseBaiduAPI(String latitude,String longitude) {
		String city = null;
		String url = Constant.BAIDU_API_GEOCODE +"?location=" + latitude + "," + longitude + "&output=json&key=8cb976834235d8cbcde2dce4835ae191";
		try {
			byte[] b = LocationUtil.postViaHttpConnection(null,url);
			if (b!=null) {
				String response = new String(b);
				JSONObject json = new JSONObject(response);
				JSONObject result = json.getJSONObject("result");
				JSONObject addressComponent = result.getJSONObject("addressComponent");
				city = addressComponent.getString("city");
				if (city.indexOf("��")>=0) {
					city = city.substring(0,city.indexOf("��"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			city = Constant.CITY_NAME_DEFAULT;
		} 
		return city;
	}
}