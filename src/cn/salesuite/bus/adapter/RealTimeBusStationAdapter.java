/**
 * 
 */
package cn.salesuite.bus.adapter;

import java.util.List;
import java.util.Map;

import cn.salesuite.bus.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Tony Shen
 *
 */
public class RealTimeBusStationAdapter extends BaseAdapter{
	
	private Context mContext;
	private LayoutInflater inflater;
	private List<Map<String, Object>> data;
	
	private ViewHolder1 holder1;
	private ViewHolder2 holder2;
	
	private final int TYPE_1 = 1;//有公交车停靠的站台
	private final int TYPE_2 = 2;//无公交车停靠的站台
	
	public RealTimeBusStationAdapter(Context context,List<Map<String, Object>> data) {
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
	public int getViewTypeCount() {
		return 3;
	}
	
	//每个convert view都会调用此方法，获得当前所需要的view样式
	@Override
	public int getItemViewType(int position) {
		if (data.get(position)!=null && data.get(position).get("Icon")!=null) {
			return TYPE_1;
		} else {
			return TYPE_2;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int type = getItemViewType(position);
		
		if(convertView != null){
			switch(type)
			{
			case TYPE_1:
				holder1 = (ViewHolder1) convertView.getTag();
			    break;
			case TYPE_2:
				holder2 = (ViewHolder2) convertView.getTag();
			    break;
			default:
				break;
			}
		} else {
			switch(type)
			{
			case TYPE_1:
				convertView = inflater.inflate(R.layout.bus_arrive_station_list_item, parent, false);
			    holder1 = new ViewHolder1();
			    holder1.bus = (ImageView)convertView.findViewById(R.id.bus);
			    holder1.busstationTxt = (TextView)convertView.findViewById(R.id.station_name);
			    holder1.middleStatusLayout = (LinearLayout)convertView.findViewById(R.id.middleStatusLayout);
			    holder1.busOutsideStation = (ImageView)convertView.findViewById(R.id.bus_outside_the_station);
			    convertView.setTag(holder1);
			    break;
			case TYPE_2:
			    convertView = inflater.inflate(R.layout.station_list_item, parent, false);
			    holder2 = new ViewHolder2();
			    holder2.busstationTxt = (TextView)convertView.findViewById(R.id.station_name);
			    holder2.middleStatusLayout = (LinearLayout)convertView.findViewById(R.id.middleStatusLayout);
			    holder2.busOutsideStation = (ImageView)convertView.findViewById(R.id.bus_outside_the_station);
			    convertView.setTag(holder2);
			    break;
			default:
				break;
			}
		}
		
		//设置资源
		switch(type)
		{
		case TYPE_1:
			holder1.bus.setBackgroundResource(R.drawable.online_car_icon);
		    holder1.busstationTxt.setText((String)data.get(position).get("Station"));
		    if (position == getCount()-1) {
		    	holder1.middleStatusLayout.setVisibility(View.GONE);
		    }
		break;
		case TYPE_2:
		    holder2.busstationTxt.setText((String)data.get(position).get("Station"));
		    if (position == getCount()-1) {
		    	holder2.middleStatusLayout.setVisibility(View.GONE);
		    }
		break;			
		default:
			break;
		}
		return convertView;
	}
		
	class ViewHolder1 {
		ImageView bus;
		TextView busstationTxt;
		LinearLayout middleStatusLayout;
		ImageView busOutsideStation;
	}
		
	class ViewHolder2 {
		TextView busstationTxt;
		LinearLayout middleStatusLayout;
		ImageView busOutsideStation;
	}
}