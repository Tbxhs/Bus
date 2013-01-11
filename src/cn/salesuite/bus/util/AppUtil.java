/**
 * 
 */
package cn.salesuite.bus.util;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import cn.salesuite.bus.config.Constant;
import cn.salesuite.saf.app.SAFApp;

/**
 * @author Tony Shen
 *
 */
public class AppUtil {

	/**
	 * ���浱ǰ������Ϣ����¼��������
	 * @param context
	 * @param app
	 */
	public static void saveCityInfo(SharedPreferences sp,SAFApp app) {
		Editor editor = sp.edit();
		editor.putString(Constant.CITY_NAME, (String)app.session.get(Constant.GPS_CITYNAME));
		editor.commit();
	}
	
	/**
	 * ����location���������е�����
	 * @param location
	 * @param context
	 * @return
	 */
	public static String getCityFromLocation(Location location, Context context) {
		List<Address> places = null;
	    Geocoder geocoder = new Geocoder(context , Locale.CHINA);
	    String city=Constant.CITY_NAME_DEFAULT;
	    try {
			places = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			if(null!=places && places.size()>0){
				String address=((Address) places.get(0)).getAddressLine(1);
				if(address.contains("ʡ")){
					int p=address.indexOf("ʡ");
					city=address.substring(p+1, address.indexOf("��"));
					return city;
				}else if(address.contains("��")){
					city=address.substring(0, address.indexOf("��"));
					return city;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return city;
	}
}
