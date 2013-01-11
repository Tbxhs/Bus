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
	 * 保存当前城市信息，记录城市名称
	 * @param context
	 * @param app
	 */
	public static void saveCityInfo(SharedPreferences sp,SAFApp app) {
		Editor editor = sp.edit();
		editor.putString(Constant.CITY_NAME, (String)app.session.get(Constant.GPS_CITYNAME));
		editor.commit();
	}
	
	/**
	 * 根据location解析出城市的名称
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
				if(address.contains("省")){
					int p=address.indexOf("省");
					city=address.substring(p+1, address.indexOf("市"));
					return city;
				}else if(address.contains("市")){
					city=address.substring(0, address.indexOf("市"));
					return city;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return city;
	}
}
