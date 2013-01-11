package cn.salesuite.bus.view;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import cn.salesuite.bus.activity.R;
import cn.salesuite.bus.adapter.RouteSearchAdapter;

import com.amap.mapapi.core.PoiItem;

public class RouteSearchPoiDialog extends Dialog implements OnItemClickListener, OnItemSelectedListener{

	
	private List<PoiItem> poiItems;
	private Context context;
	private RouteSearchAdapter adapter;
	protected OnListItemClick mOnClickListener;
	 
	public RouteSearchPoiDialog(Context context) {
		this(context,android.R.style.Theme_Dialog);
		// TODO Auto-generated constructor stub
	}
	public RouteSearchPoiDialog(Context context,int theme) {
		super(context,theme);
		// TODO Auto-generated constructor stub
	}
	
	public RouteSearchPoiDialog(Context context,
			List<PoiItem> poiItems) {
		this(context,android.R.style.Theme_Dialog);
		this.poiItems=poiItems;
		this.context=context;
		adapter=new RouteSearchAdapter(context,poiItems);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navsearch_list_poi);
		ListView listView=(ListView) findViewById(R.id.ListView_nav_search_list_poi);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				// TODO Auto-generated method stub
				dismiss();
				mOnClickListener.onListItemClick(RouteSearchPoiDialog.this,poiItems.get(position));
				}
		});
		
		
	}
	
	
	
	@Override
	public void onItemClick(AdapterView<?> view, View view1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	}
	
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
    public interface OnListItemClick {
        /**
         * This method will be invoked when the dialog is canceled.
         * 
         * @param dialog The dialog that was canceled will be passed into the
         *            method.
         */
        public void onListItemClick(RouteSearchPoiDialog dialog,PoiItem item);
    }
   
    
    public void setOnListClickListener(OnListItemClick l) {
        mOnClickListener = l;
    }

	

}
