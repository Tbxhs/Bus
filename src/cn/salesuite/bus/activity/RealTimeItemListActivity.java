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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.salesuite.bus.BaseActivity;

/**
 * @author Tony Shen
 *
 */
public class RealTimeItemListActivity extends BaseActivity{

	private Button backBtn,nextBtn;
	private TextView topTitle;
	private ListView itemlist;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.real_time_item_list);

		findViews();
		initViews();
		
	}

	private void findViews() {
		backBtn = (Button) findViewById(R.id.btn_back);
		nextBtn = (Button) findViewById(R.id.btn_next);
		topTitle = (TextView) findViewById(R.id.top_title);
		itemlist = (ListView) findViewById(R.id.sortlist);
	}

	private void initViews() {
		backBtn.setVisibility(View.GONE);
		nextBtn.setVisibility(View.GONE);
		topTitle.setText("掌上公交");
		
		SimpleAdapter adapter = new SimpleAdapter(this, getAllSort(),
				R.layout.nearby_item, new String[] {"Sort"},
				new int[] {R.id.sort_name});
		itemlist.setAdapter(adapter);
		itemlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2,
					long arg3) {
				if (arg2 == 0) {
					showToast("当前无公交线路更新!");
				} else if (arg2 == 1) {
					Intent i = new Intent(RealTimeItemListActivity.this,SearchRealTimeActivity.class);
					startActivity(i);
					finish();
				}
			}
		});
	}
	
	private List<Map<String, Object>> getAllSort() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> item = null;
		item = new HashMap<String, Object>();
		item.put("Sort", "公交线路更新");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("Sort", "实时车辆情况更新");
		data.add(item);
		
		return data;
	}
}
