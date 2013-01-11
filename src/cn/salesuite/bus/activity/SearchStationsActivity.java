/**
 * 
 */
package cn.salesuite.bus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.salesuite.bus.BaseActivity;

import com.amap.mapapi.core.GeoPoint;

/**
 * @author Tony Shen
 *
 */
public class SearchStationsActivity extends BaseActivity{
	
	private Button backBtn,nextBtn;
	private TextView leftText,topTitle;
	private EditText startStation,endStation;
	private ImageView startOnMap,endOnMap,searchBtn;
	
	private String strStart,strEnd;
	private GeoPoint startPoint=null;
	private GeoPoint endPoint=null;
	
	private int POINT_START_CODE = 1;
	private int POINT_END_CODE = 2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_stations);

		findViews();
		initViews();
		
		addFootMenuView();
	}

	private void findViews() {
		backBtn = (Button) findViewById(R.id.btn_back);
		nextBtn = (Button) findViewById(R.id.btn_next);
		leftText = (TextView) findViewById(R.id.left_text);
		topTitle = (TextView) findViewById(R.id.top_title);
		startStation = (EditText) findViewById(R.id.startStation);
		endStation = (EditText) findViewById(R.id.endStation);
		startOnMap = (ImageView) findViewById(R.id.startOnMap);
		endOnMap = (ImageView) findViewById(R.id.endOnMap);
		searchBtn = (ImageView) findViewById(R.id.searchBtn);
	}

	private void initViews() {
//		backBtn.setBackgroundResource(R.drawable.head_arrow);
		leftText.setText("按站站查询");
		nextBtn.setVisibility(View.GONE);
		topTitle.setVisibility(View.GONE);
		
		startOnMap.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mProgressDialog = getProgressDialog(SearchStationsActivity.this);
				new Thread() {
					public void run() {
						try {
							Intent i = new Intent(SearchStationsActivity.this,
									DisplayMapActivity.class);
							if (getLastLocation()!=null) {
								String position = getLastLocation().getLatitude()+" "+getLastLocation().getLongitude();
								i.putExtra("position", position);
							}
							i.putExtra("search_type", DisplayMapActivity.POINT_START_ON_MAP);
							startActivityForResult(i, POINT_START_CODE); 
						} finally {
							closeProgressDialog(mProgressDialog);
						}
					}
				}.start();	
			}
			
		});
		
		endOnMap.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mProgressDialog = getProgressDialog(SearchStationsActivity.this);
				new Thread() {
					public void run() {
						try {
							Intent i = new Intent(SearchStationsActivity.this,
									DisplayMapActivity.class);
							if (getLastLocation()!=null) {
								String position = getLastLocation().getLatitude()+" "+getLastLocation().getLongitude();
								i.putExtra("position", position);
							}
							i.putExtra("search_type", DisplayMapActivity.POINT_END_ON_MAP);
							startActivityForResult(i, POINT_END_CODE); 
						} finally {
							closeProgressDialog(mProgressDialog);
						}
					}
				}.start();	
			}
			
		});
		
		searchBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				strStart = startStation.getText().toString().trim();
				strEnd = endStation.getText().toString().trim();
				if (strStart==null||strStart.length()==0) {
					showToast("请选择乘车站");
					return;
				}
				if (strEnd==null||strEnd.length()==0) {
					showToast("请选择目的站");
					return;
				}
				
				mProgressDialog = getProgressDialog(SearchStationsActivity.this);
				new Thread() {
					public void run() {
						try {
							Intent i = new Intent(SearchStationsActivity.this,
									DisplayMapActivity.class);
							if (getLastLocation()!=null) {
								String position = getLastLocation().getLatitude()+" "+getLastLocation().getLongitude();
								i.putExtra("position", position);
							}
							i.putExtra("search_type", DisplayMapActivity.SEARCH_STATIONS);
							i.putExtra("start_station", strStart);
							i.putExtra("end_station", strEnd);
							if (startPoint!=null) {
								i.putExtra("start_point", startPoint);
							}
							if (endPoint!=null) {
								i.putExtra("end_point", endPoint);
							}
							startActivity(i);
						} finally {
							closeProgressDialog(mProgressDialog);
						}
					}
				}.start();	
			}
			
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == POINT_START_CODE && resultCode == Activity.RESULT_OK) {
			if (data != null) {
				startStation.setText(data.getStringExtra("station_name"));
				startPoint = (GeoPoint) data.getSerializableExtra("start_point");
			}
		}

		if (requestCode == POINT_END_CODE && resultCode == Activity.RESULT_OK) {
			if (data != null) {
				endStation.setText(data.getStringExtra("station_name"));
				endPoint = (GeoPoint) data.getSerializableExtra("end_point");
			}
		}
	}
}
