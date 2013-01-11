/**
 * 
 */
package cn.salesuite.bus.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Tony Shen
 *
 */
public class BusStationAdapter extends PagerAdapter{
	public List<View> stationViewList;
	public BusStationAdapter(List<View> stationViewList) {
		this.stationViewList = stationViewList;
	}
	@Override
	public int getCount() {
		return stationViewList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == (View)obj;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		System.out.println("ViewPager destroyItem = "+position);
		((ViewPager)container).removeView(stationViewList.get(position));
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		System.out.println("ViewPager:instantiateItem = "+position);
		((ViewPager)container).addView(stationViewList.get(position));
		return stationViewList.get(position);
	}
}
