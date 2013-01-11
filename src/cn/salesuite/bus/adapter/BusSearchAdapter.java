/**
 * 
 */
package cn.salesuite.bus.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.salesuite.bus.activity.R;

import com.amap.mapapi.busline.BusLineItem;

/**
 * @author Tony Shen
 *
 */
public class BusSearchAdapter extends BaseAdapter {
	private Context context;
	private List<BusLineItem> busLineItems=null;
	private LayoutInflater mInflater;
	
	public BusSearchAdapter(Context context,List<BusLineItem> busLineItems) {
		this.context=context;
		this.busLineItems=busLineItems;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return busLineItems.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.bus_result_list, null);
		}
		
		TextView PoiName = ((TextView) convertView
				.findViewById(R.id.buslineName));
		TextView poiAddress = (TextView) convertView
				.findViewById(R.id.buslineLength);
		PoiName.setText(busLineItems.get(position).getmName());
		float length=busLineItems.get(position).getmLength();
		poiAddress.setText("全长:"+length+"公里");
		return convertView;
	}
}
