/**
 * 
 */
package cn.salesuite.bus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.salesuite.bus.BaseActivity;

/**
 * @author Tony Shen
 *
 */
public class SearchStationActivity extends BaseActivity{
	
	private Button backBtn,nextBtn;
	private TextView leftText,topTitle;
	private ImageView searchBtn;
	private EditText stationName;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_station);

		findViews();
		initViews();
		
		addFootMenuView();
	}

	private void findViews() {
		backBtn = (Button) findViewById(R.id.btn_back);
		nextBtn = (Button) findViewById(R.id.btn_next);
		leftText = (TextView) findViewById(R.id.left_text);
		topTitle = (TextView) findViewById(R.id.top_title);
		stationName = (EditText) findViewById(R.id.stationName);
		searchBtn = (ImageView) findViewById(R.id.searchBtn);
	}

	private void initViews() {
//		backBtn.setBackgroundResource(R.drawable.head_arrow);
		leftText.setText("按站点查询");
		nextBtn.setVisibility(View.GONE);
		topTitle.setVisibility(View.GONE);
		
		searchBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final String search = stationName.getText().toString().trim();
				if (search == null || search.length()==0) {
					Toast.makeText(SearchStationActivity.this, "请输入站点名称", Toast.LENGTH_SHORT).show();
					return;
				}
				
				mProgressDialog = getProgressDialog(SearchStationActivity.this);
				new Thread() {
					public void run() {
						try {
							Intent i = new Intent(SearchStationActivity.this,
									DisplayMapActivity.class);
							if (getLastLocation()!=null) {
								String position = getLastLocation().getLatitude()+" "+getLastLocation().getLongitude();
								i.putExtra("position", position);
							}
							i.putExtra("search_type", DisplayMapActivity.SEARCH_STATION);
							i.putExtra("station_name", search);
							startActivity(i);
						} finally {
							closeProgressDialog(mProgressDialog);
						}
					}
				}.start();	
			}
			
		});
	}
}
