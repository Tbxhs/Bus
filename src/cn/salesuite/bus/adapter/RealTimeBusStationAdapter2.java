/**
 * 
 */
package cn.salesuite.bus.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.salesuite.bus.activity.R;

/**
 * @author Tony Shen
 *
 * 
 */
public class RealTimeBusStationAdapter2 extends BaseAdapter{

	private Context mContext;
	private LayoutInflater inflater;
	private List<Map<String, Object>> data;
	private ViewHolder holder;
	
	public RealTimeBusStationAdapter2(Context context,List<Map<String, Object>> data) {
		mContext = context;
		this.data = data;
		inflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		if (data.size()>0)
			return data.size();
		else 
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		if(convertView != null){
//			holder = (ViewHolder) convertView.getTag();
//		} else {
			convertView = inflater.inflate(R.layout.station_list_item2, parent, false);
		    holder = new ViewHolder();
		    holder.stationLayout = (LinearLayout)convertView.findViewById(R.id.station_layout);
		    holder.bus = (ImageView)convertView.findViewById(R.id.bus);
		    holder.busstationTxt = (TextView)convertView.findViewById(R.id.station_name);
		    holder.middleStatusLayout = (LinearLayout)convertView.findViewById(R.id.middleStatusLayout);
		    holder.busOutsideStation = (ImageView)convertView.findViewById(R.id.bus_outside_the_station);
		    convertView.setTag(holder);
//		}
		
		if (data.get(position)!=null && data.get(position).get("Icon")!=null) {
			holder.stationLayout.setBackgroundResource(R.drawable.bus_arrive_station_list_item_bg);
			holder.bus.setVisibility(View.VISIBLE);
		}
		holder.busstationTxt.setText((String)data.get(position).get("Station"));
		
		if (position == getCount()-1) {
	    	holder.middleStatusLayout.setVisibility(View.GONE);
	    } else {
	    	if (data.get(position).get("MiddleStatus")!=null && "true".equals(data.get(position).get("MiddleStatus").toString())) {
	    		holder.middleStatusLayout.setBackgroundResource(R.drawable.bus_not_the_site_bg);
    	        holder.busOutsideStation.setBackgroundResource(R.drawable.outside_station_car_icon);
    	    }
	    }
		return convertView;
	}

	class ViewHolder {
		LinearLayout stationLayout;
		ImageView bus;
		TextView busstationTxt;
		LinearLayout middleStatusLayout;
		ImageView busOutsideStation;
	}
}
