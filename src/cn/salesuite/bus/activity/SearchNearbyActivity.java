/**
 * 
 */
package cn.salesuite.bus.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import cn.salesuite.bus.BaseActivity;

import com.amap.mapapi.poisearch.PoiTypeDef;

/**
 * @author Tony Shen
 *
 */
public class SearchNearbyActivity extends BaseActivity{
	
	private ListView itemlist;
	private Spinner meterSpinner;
	private int meters = 3000;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_nearby);

		findViews();
		initViews();
		
		addFootMenuView();
	}

	private void findViews() {
		meterSpinner = (Spinner)this.findViewById(R.id.meterSpinner);
		itemlist = (ListView) findViewById(R.id.sortlist);
	}

	private void initViews() {
		 meterSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int pos = meterSpinner.getSelectedItemPosition();
				switch (pos) {
				case 0:
					meters = 3000;
					break;
				case 1:
					meters = 1000;
					break;
				case 2:
					meters = 500;
					break;
				default:
					meters = 3000;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			 
		 });
		
		SimpleAdapter adapter = new SimpleAdapter(this, getAllSort(),
				R.layout.nearby_item, new String[] {"Picture","Sort"},
				new int[] {R.id.sort_pic,R.id.sort_name});
		itemlist.setAdapter(adapter);
		itemlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2,
					long arg3) {
				mProgressDialog = getProgressDialog(SearchNearbyActivity.this);
				new Thread() {
					public void run() {
						try {
							Intent i = new Intent(SearchNearbyActivity.this,
									DisplayMapActivity.class);
							if (getLastLocation()!=null) {
								String position = getLastLocation().getLatitude()+" "+getLastLocation().getLongitude();
								i.putExtra("position", position);
							}
							if (arg2 == 0) {
								i.putExtra("poi_type", PoiTypeDef.FoodBeverages);
								i.putExtra("query", "餐饮");
							} else if (arg2 == 1) {
								i.putExtra("poi_type", PoiTypeDef.SportEntertainment);
								i.putExtra("query", "娱乐");
							} else if (arg2 == 2) {
								i.putExtra("poi_type", PoiTypeDef.LifeService);
								i.putExtra("query", "生活");
							} else if (arg2 == 3) {
								i.putExtra("poi_type", PoiTypeDef.PublicTransportation);
								i.putExtra("query", "交通");
							} else if (arg2 == 4) {
								i.putExtra("poi_type", PoiTypeDef.Financial);
								i.putExtra("query", "银行");
							} else if (arg2 == 5) {
								i.putExtra("poi_type", PoiTypeDef.Accommodation);
								i.putExtra("query", "住宿");
							}
							i.putExtra("meters", meters);
							i.putExtra("search_type", DisplayMapActivity.SEARCH_POIS);
							startActivity(i);
						} finally {
							closeProgressDialog(mProgressDialog);
						}
					}
				}.start();	
			}
			
		});

	}
	
	private List<Map<String, Object>> getAllSort() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> item = null;
		item = new HashMap<String, Object>();
		item.put("Picture", R.drawable.nearby_restaurant);
		item.put("Sort", "餐饮");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("Picture", R.drawable.nearby_entertainment);
		item.put("Sort", "娱乐");
		data.add(item);
		
		item = new HashMap<String, Object>();
		item.put("Picture", R.drawable.nearby_life);
		item.put("Sort", "生活");
		data.add(item);
		
		item = new HashMap<String, Object>();
		item.put("Picture", R.drawable.nearby_traffic);
		item.put("Sort", "交通");
		data.add(item);
		
		item = new HashMap<String, Object>();
		item.put("Picture", R.drawable.nearby_bank);
		item.put("Sort", "银行");
		data.add(item);
		
		item = new HashMap<String, Object>();
		item.put("Picture", R.drawable.nearby_lodging);
		item.put("Sort", "住宿");
		data.add(item);
		
		return data;
	}
}
