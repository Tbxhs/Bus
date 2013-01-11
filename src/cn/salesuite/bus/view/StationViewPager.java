/**
 * 
 */
package cn.salesuite.bus.view;

import cn.salesuite.bus.activity.R;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author Tony Shen
 *
 */
public class StationViewPager extends LinearLayout implements OnPageChangeListener{
	private ViewPager viewPager;
	private LinearLayout dots;
	private int oldPosition;
	
	public StationViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public StationViewPager(Context context) {
		super(context);
		init();
	}

	private void init() {
		setOrientation(VERTICAL);
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.station_view_pager, this, true);
		
		viewPager = (ViewPager)findViewById(R.id.view_pager);
		viewPager.setOnPageChangeListener(this);
		dots = (LinearLayout)findViewById(R.id.dots);
	}
	
	public PagerAdapter getAdapter() {
		return viewPager.getAdapter();
	}
	
	public void setAdapter(PagerAdapter adapter) {
		viewPager.setAdapter(adapter);
		int numberOfDots = adapter.getCount();
		dots.removeAllViews();
		for (int i = 0; i < numberOfDots; i++) {
			dots.addView(newDot());
		}
		if (numberOfDots > 0) {
			((ImageView)dots.getChildAt(0)).setImageResource(R.drawable.dot_selected);
		}
		oldPosition = 0;
	}

	private ImageView newDot() {
		ImageView dot = new ImageView(getContext());
		dot.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		dot.setPadding(0, 0, 15, 0);
		dot.setImageResource(R.drawable.dot_unselected);
		return dot;
	}

	public void onPageScrollStateChanged(int arg0) {
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	public void onPageSelected(int position) {
		ImageView dot = (ImageView)dots.getChildAt(oldPosition);
		dot.setImageResource(R.drawable.dot_unselected);
		
		dot = (ImageView)dots.getChildAt(position);
		dot.setImageResource(R.drawable.dot_selected);
		
		oldPosition = position;		
	}

	public ViewPager getViewPager() {
		return viewPager;
	}
}
