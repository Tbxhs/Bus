/**
 * 
 */
package cn.salesuite.bus.activity;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import cn.salesuite.bus.BaseActivity;
import cn.salesuite.bus.config.Constant;


/**
 * @author Tony Shen
 *
 */
public class SplashScreen extends BaseActivity{
	
	private ProgressBar pb;
	
	private Location mLocation;
	private Handler handler = new Handler();
	
	private static final int GPS_REQUEST_CODE = 1;
	private static final int NETWORK_REQUEST_CODE = 2;
	
	public static final long ONE_MINUTE = 60000;
	
	private Runnable mLocationRunnable = new Runnable() {
		public void run() {
			if(mLocationManager != null){
				if(getLastLocation()==null){
//					showToast(R.string.error_get_location);
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		init();

		islargeScreen();
		findViews();
		initData();
	}
	
	private void init() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File sdcard = Environment.getExternalStorageDirectory();
			if (sdcard.exists()) {
				app.root = sdcard.toString();
				File busDirectory = new File(sdcard + Constant.DIR);
				if (!busDirectory.exists())
					busDirectory.mkdirs();
				File cacheDirectory = new File(sdcard + Constant.CACHE_DIR);
				if (!cacheDirectory.exists())
					cacheDirectory.mkdirs();
				File updateApkDirectory = new File(sdcard
						+ Constant.UPDATE_APK_DIR);
				if (!updateApkDirectory.exists())
					updateApkDirectory.mkdirs();
			} else {
				Log.e(TAG, "sdcard not use!");
			}
		} else {
			File flash = new File("/flash");
			if (flash.exists()) {
				app.root = flash.toString();
				File gewaraDirectory = new File(flash + Constant.DIR);
				if (!gewaraDirectory.exists())
					gewaraDirectory.mkdirs();
				File cacheDirectory = new File(flash + Constant.CACHE_DIR);
				if (!cacheDirectory.exists())
					cacheDirectory.mkdirs();
				File updateApkDirectory = new File(flash
						+ Constant.UPDATE_APK_DIR);
				if (!updateApkDirectory.exists())
					updateApkDirectory.mkdirs();
			} else {
				Log.e(TAG, "/false not use!");
			}
		}
	}
	
	/**
	 * 判断屏幕是否是高密度
	 */
	private void islargeScreen(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  	// 屏幕宽度（像素）
        int height = metric.heightPixels;  	// 屏幕高度（像素）
        float density = metric.density;  	// 屏幕密度（0.75 / 1.0 / 1.5）
		Log.i(TAG, "device screen: " + width + "*" + height + " desity: "
				+ density);
		Constant.height = height;
		Constant.width = width;
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        if(densityDpi < 240){
        	Constant.largeScreen = false;
        }else{
        	Constant.largeScreen = true;
        }
	}
	
	private void findViews() {
		pb = (ProgressBar) findViewById(R.id.progressbar);
	}
	
	private void initData() {
		
		Location defaultloc = new Location("");
		defaultloc.setLatitude(Constant.LATITUDE_DEFAULT);
		defaultloc.setLongitude(Constant.LONGITUDE_DEFAULT);
		app.session.put(Constant.GPS_CUR_LOCATION, defaultloc);         //设置gps的默认城市经纬度
		
		app.session.put(Constant.CITY_NAME, Constant.CITY_NAME_DEFAULT);//设置默认城市名称
		app.session.put(Constant.CITY_CODE, Constant.CITY_CODE_DEFAULT);//设置默认城市code
		
		startTracking();
		// TODO:稍后打开
//		if (checkNetwork()) {
			pb.setVisibility(View.GONE);
			loadingNext();
//		}
	}
	
	/**
	 * 开始定位
	 */
	public void startTracking() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mLocation = getLastLocation();
		if(mLocation == null){
			mLocationManager.push();
		}else{
			onLocationChanged(mLocation);
		}
		
		handler.postDelayed(mLocationRunnable,ONE_MINUTE);
	}
	
	public void onLocationChanged(Location location) {
		if (location != null) {
			app.session.put(Constant.GPS_CUR_LOCATION, location);
		}
	}
	
	/**
	 * 测试手机端是否能上网
	 * @return
	 */
	private boolean checkNetwork(){
		boolean isNetwork=false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null ||!networkinfo.isAvailable()) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle(getString(R.string.checknetwork_title));
        	builder.setMessage(getString(R.string.checknetwork_message))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS),NETWORK_REQUEST_CODE);
                }
            })
            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                     dialog.cancel();
                     finish();
                }
            }).create().show();
        }else {
        	isNetwork=true;
        }

        return isNetwork;
    }
	
	/**
	 * 跳转到主页面
	 */
	private void loadingNext() {
		handler.postDelayed(new Runnable() {
			public void run() {
				Intent i = new Intent();
				i.setClass(SplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}, 2000);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "requestCode=" + requestCode + ",resultCode="+ resultCode);
		if(NETWORK_REQUEST_CODE == requestCode || GPS_REQUEST_CODE == requestCode){
			initData();
		}
	}
}
