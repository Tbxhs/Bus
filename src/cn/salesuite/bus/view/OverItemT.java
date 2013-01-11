/**
 * 
 */
package cn.salesuite.bus.view;

import java.util.ArrayList;
import java.util.List;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.OverlayItem;
import com.amap.mapapi.map.ItemizedOverlay;
import com.amap.mapapi.map.MapView;
import com.amap.mapapi.map.Projection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;

/**
 * @author Tony Shen
 *
 */
public class OverItemT extends ItemizedOverlay<OverlayItem>{
	private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
	private Drawable marker;
	private Context mContext;

	public OverItemT(Drawable marker, Context context,GeoPoint pos) {
		super(boundCenterBottom(marker));

		this.marker = marker;
		this.mContext = context;

		// �ø����ľ�γ�ȹ���GeoPoint����λ��΢�� (�� * 1E6)
		GeoPoint p = pos;

		// ����OverlayItem��������������Ϊ��item��λ�ã������ı�������Ƭ��
		GeoList.add(new OverlayItem(p, "",""));	
		populate();  //createItem(int)��������item��һ���������ݣ��ڵ�����������ǰ�����ȵ����������
	}

	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

		// Projection�ӿ�������Ļ���ص�����ϵͳ�͵�����澭γ�ȵ�����ϵͳ֮��ı任
		Projection projection = mapView.getProjection(); 
		for (int index = size() - 1; index >= 0; index--) { // ����GeoList
			OverlayItem overLayItem = getItem(index); // �õ�����������item

			String title = overLayItem.getTitle();
			// �Ѿ�γ�ȱ任�������MapView���Ͻǵ���Ļ��������
			Point point = projection.toPixels(overLayItem.getPoint(), null); 

			Paint paintText = new Paint();
			paintText.setColor(Color.BLACK);
			paintText.setTextSize(15);
			canvas.drawText(title, point.x, point.y - 25, paintText); // �����ı�
			
			Paint mCirclePaint = new Paint();
			mCirclePaint.setAntiAlias(true);
			mCirclePaint.setColor(Color.BLUE);
			mCirclePaint.setAlpha(50);
			mCirclePaint.setStyle(Style.FILL);
			canvas.drawCircle(point.x, point.y, 50, mCirclePaint);
		}

		super.draw(canvas, mapView, shadow);
		//����һ��drawable�߽磬ʹ�ã�0��0�������drawable�ײ����һ�����ĵ�һ������
		boundCenterBottom(marker);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return GeoList.get(i);
	}

	@Override
	public int size() {
		return GeoList.size();
	}

	@Override
	// ��������¼�
	protected boolean onTap(int i) {
		return true;
	}
}
