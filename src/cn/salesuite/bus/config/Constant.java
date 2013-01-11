/**
 * 
 */
package cn.salesuite.bus.config;

import java.util.ArrayList;
import java.util.List;

import cn.salesuite.bus.domain.BusStation;


/**
 * @author Tony Shen
 *
 */
public class Constant {

	/** salesuit存储目录/文件 **/
	public static final String DIR = "/salesuite";
	public static final String CACHE_DIR = DIR + "/images";
	public static final String CAMERA_TEMP = DIR + "/images/camera_temp.jpg";
	public static final String IMAGE_TEMP = DIR + "/images/temp.jpg";
	public static final String DATABASE_FILE = DIR + "/salesuite.db";
	public static final String UPDATE_APK_DIR = DIR + "/download";
	
	public static boolean largeScreen = true;//判断屏幕是否是高密度
	public static int height;                //屏幕高度
	public static int width;                 //屏幕宽度
	
	public static final String SHARED="SaleSuite";
	
	public static final String NOTICE_CHANGE_PLACE = "notice_change";
	//gps判断位置不正确后，弹出提示，用户点击确定更换城市，发出的通知
	public static final String CHANGE_HOTMOVIE_DATA = "change_hotmovie";
	//在城市列表act中点击任意一个城市，进行切换后
	public static final String CITY_SETTING_CHANGE = "change_setting_change";
	
	// app地理信息常量
	public static final String GPS_CUR_LOCATION = "gps_cur_location";
	public static final String GPS_CITYCODE = "gps_citycode";
	public static final String GPS_CITYNAME = "gps_cityname";
	public static final String CITY_CODE = "city_code";
	public static final String CITY_NAME = "city_name";
	public static final String LAST_CITY_CODE = "last_city_code";
	public static final String CITY_CODE_DEFAULT = "310000";
	public static final String CITY_NAME_DEFAULT = "上海";
	public static final double LATITUDE_DEFAULT = 31.234914894041356;
	public static final double LONGITUDE_DEFAULT = 121.47494494915009;
	
	// 存储app缓存的key
	
	// 请求响应
	public static final int RESULT_SUCCESS = 1;
	public static final int RESULT_FAILURE = -1;
	public static final int RESULT_IOERROR = -2;
	public static final int NETWORK_ERROR = -3;
	
	// baidu map geocode ws api
	public static final String BAIDU_API_GEOCODE = "http://api.map.baidu.com/geocoder";
	
	public static final int BUSLINE_RESULT=6000;
	public static final int BUSLINE_DETAIL_RESULT=6001;
	public static final int BUSLINE_ERROR_RESULT=6002;
	
	public static final int ROUTE_START_SEARCH=2000;//路径规划起点搜索
	public static final int ROUTE_END_SEARCH=2001;//路径规划起点搜索
	public static final int ROUTE_SEARCH_RESULT=2002;//路径规划结果
	public static final int ROUTE_SEARCH_ERROR=2004;//路径规划起起始点搜索异常
	
	// 公交车接口地址
	public static final String GET_BUS_STATUS = "http://202.102.101.74:17016/Service.asmx/GetCarStatus";
	
	// 公交车实时情况h5地址
	public static final String BUS_REAL_TIME_H5 = "http://124.74.19.103:8090/BusSys/list/list2.jsp";
	
	public static List<BusStation> getWanZhouBusLine() {
		List<BusStation> stations = new ArrayList<BusStation>();
		BusStation station1 = new BusStation();
		station1.stationName = "周祝公路(上海医药园区)";
		station1.longitude = 121.606;
		station1.latitude = 31.10558333;
		stations.add(station1);
		
		BusStation station2 = new BusStation();
		station2.stationName = "牛桥";
		station2.longitude = 121.60186667;
		station2.latitude = 31.10501667;
		stations.add(station2);
		
		BusStation station3 = new BusStation();
		station3.stationName = "塘东";
		station3.longitude = 121.58423333;
		station3.latitude = 31.10226667;
		stations.add(station3);
		
		BusStation station4 = new BusStation();
		station4.stationName = "康沈路繁荣路";
		station4.longitude = 121.57745;
		station4.latitude = 31.10686667;
		stations.add(station4);
		
		BusStation station5 = new BusStation();
		station5.stationName = "南八灶";
		station5.longitude = 121.57615;
		station5.latitude = 31.1115;
		stations.add(station5);
		
		BusStation station6 = new BusStation();
		station6.stationName = "康沈路年家浜路";
		station6.longitude = 121.5742;
		station6.latitude = 31.11645;
		stations.add(station6);
		
		BusStation station7 = new BusStation();
		station7.stationName = "周浦";
		station7.longitude = 121.5719333;
		station7.latitude = 31.1191;
		stations.add(station7);
		
		BusStation station8 = new BusStation();
		station8.stationName = "镇北轻纺市场";
		station8.longitude = 121.56925;
		station8.latitude = 31.12093333;
		stations.add(station8);
		
		BusStation station9 = new BusStation();
		station9.stationName = "八一";
		station9.longitude = 121.5647667;
		station9.latitude = 31.11993333;
		stations.add(station9);
		
		BusStation station10 = new BusStation();
		station10.stationName = "吴迅中学";
		station10.longitude = 121.5582;
		station10.latitude = 31.12081667;
		stations.add(station10);

		BusStation station11 = new BusStation();
		station11.stationName = "赵家宅";
		station11.longitude = 121.5505333;
		station11.latitude = 31.12135;
		stations.add(station11);
		
		BusStation station12 = new BusStation();
		station12.stationName = "南华城";
		station12.longitude = 121.5457167;
		station12.latitude = 31.12133333;
		stations.add(station12);
		
		BusStation station13 = new BusStation();
		station13.stationName = "百曲";
		station13.longitude = 121.5380833;
		station13.latitude = 31.12188333;
		stations.add(station13);
		
		BusStation station14 = new BusStation();
		station14.stationName = "闵家宅";
		station14.longitude = 121.5305667;
		station14.latitude = 31.12555;
		stations.add(station14);
		
		BusStation station15 = new BusStation();
		station15.stationName = "友谊";
		station15.longitude = 121.5162667;
		station15.latitude = 31.12973333;
		stations.add(station15);

		BusStation station16 = new BusStation();
		station16.stationName = "上南路外环线";
		station16.longitude = 121.5127667;
		station16.latitude = 31.13533333;
		stations.add(station16);
		
		BusStation station17 = new BusStation();
		station17.stationName = "上南路永泰路";
		station17.longitude = 121.5092667;
		station17.latitude = 31.14118333;
		stations.add(station17);
		
		BusStation station18 = new BusStation();
		station18.stationName = "上南路三林路";
		station18.longitude = 121.5043333;
		station18.latitude = 31.1478;
		stations.add(station18);
		
		BusStation station19 = new BusStation();
		station19.stationName = "上南路华夏西路";
		station19.longitude = 121.5015333;
		station19.latitude = 31.1524;
		stations.add(station19);
		
		BusStation station20 = new BusStation();
		station20.stationName = "上南路海阳路";
		station20.longitude = 121.4972;
		station20.latitude = 31.15705;
		stations.add(station20);
		
		BusStation station21 = new BusStation();
		station21.stationName = "上南路杨思路";
		station21.longitude = 121.49335;
		station21.latitude = 31.16325;
		stations.add(station21);
		
		BusStation station22 = new BusStation();
		station22.stationName = "上南路德州路";
		station22.longitude = 121.4921167;
		station22.latitude = 31.17203333;
		stations.add(station22);
		
		BusStation station23 = new BusStation();
		station23.stationName = "上南路昌里路";
		station23.longitude = 121.4912167;
		station23.latitude = 31.1751;
		stations.add(station23);
		
		BusStation station24 = new BusStation();
		station24.stationName = "耀华路历城路";
		station24.longitude = 121.48655;
		station24.latitude = 31.17988333;
		stations.add(station24);
		
		BusStation station25 = new BusStation();
		station25.stationName = "中山南二路大木桥路";
		station25.longitude = 121.4595167;
		station25.latitude = 31.1935;
		stations.add(station25);
		
		BusStation station26 = new BusStation();
		station26.stationName = "中山南二路船厂路";
		station26.longitude = 121.4529333;
		station26.latitude = 31.18933333;
		stations.add(station26);
		
		BusStation station27 = new BusStation();
		station27.stationName = "上海游泳馆";
		station27.longitude = 121.4375667;
		station27.latitude = 31.18078333;
		stations.add(station27);
		
		BusStation station28 = new BusStation();
		station28.stationName = "漕溪公交枢纽";
		station28.longitude = 121.4319833;
		station28.latitude = 31.18171667;
		stations.add(station28);
		
		return stations;
	}
	
	public static List<BusStation> getWanZhouDownBusLine(){
		List<BusStation> stations = new ArrayList<BusStation>();
		
		BusStation station1 = new BusStation();
		station1.stationName = "漕溪公交枢纽";
		station1.longitude = 121.4328167;
		station1.latitude = 31.18103333;
		stations.add(station1);
		
		BusStation station2 = new BusStation();
		station2.stationName = "上海游泳馆";
		station2.longitude = 121.4385833;
		station2.latitude = 31.18093333;
		stations.add(station2);
		
		BusStation station3 = new BusStation();
		station3.stationName = "中山南二路船厂路";
		station3.longitude = 121.45515;
		station3.latitude = 31.19018333;
		stations.add(station3);
		
		BusStation station4 = new BusStation();
		station4.stationName = "中山南二路大木桥路";
		station4.longitude = 121.4619667;
		station4.latitude = 31.1946;
		stations.add(station4);
		
		BusStation station5 = new BusStation();
		station5.stationName = "耀华路长清路";
		station5.longitude = 121.484;
		station5.latitude = 31.17795;
		stations.add(station5);
		
		BusStation station6 = new BusStation();
		station6.stationName = "上南路浦东南路";
		station6.longitude = 121.4902333;
		station6.latitude = 31.17936667;
		stations.add(station6);
		
		BusStation station7 = new BusStation();
		station7.stationName = "上南路昌里路";
		station7.longitude = 121.4908667;
		station7.latitude = 31.17563333;
		stations.add(station7);
		
		BusStation station8 = new BusStation();
		station8.stationName = "上南路德州路";
		station8.longitude = 121.4918833;
		station8.latitude = 31.17198333;
		stations.add(station8);
		
		BusStation station9 = new BusStation();
		station9.stationName = "上南路杨思路";
		station9.longitude = 121.4931;
		station9.latitude = 31.16316667;
		stations.add(station9);
		
		BusStation station10 = new BusStation();
		station10.stationName = "上南路海阳路";
		station10.longitude = 121.4967833;
		station10.latitude = 31.1573;
		stations.add(station10);
		
		BusStation station11 = new BusStation();
		station11.stationName = "上南路华夏西路";
		station11.longitude = 121.5010167;
		station11.latitude = 31.15285;
		stations.add(station11);
		
		BusStation station12 = new BusStation();
		station12.stationName = "上南路三林路";
		station12.longitude = 121.5041667;
		station12.latitude = 31.14768333;
		stations.add(station12);
		
		BusStation station13 = new BusStation();
		station13.stationName = "上南路永泰路";
		station13.longitude = 121.5092;
		station13.latitude = 31.14085;
		stations.add(station13);
		
		BusStation station14 = new BusStation();
		station14.stationName = "上南路外环线";
		station14.longitude = 121.51255;
		station14.latitude = 31.13603333;
		stations.add(station14);
		
		BusStation station15 = new BusStation();
		station15.stationName = "友谊";
		station15.longitude = 121.5183;
		station15.latitude = 31.12875;
		stations.add(station15);
		
		BusStation station16 = new BusStation();
		station16.stationName = "闵家宅";
		station16.longitude = 121.5294;
		station16.latitude = 31.12616667;
		stations.add(station16);
		
		BusStation station17 = new BusStation();
		station17.stationName = "百曲";
		station17.longitude = 121.5375;
		station17.latitude = 31.12213333;
		stations.add(station17);
		
		BusStation station18 = new BusStation();
		station18.stationName = "南华城";
		station18.longitude = 121.5417667;
		station18.latitude = 31.1212;
		stations.add(station18);
		
		BusStation station19 = new BusStation();
		station19.stationName = "赵家宅";
		station19.longitude = 121.5505333;
		station19.latitude = 31.12121667;
		stations.add(station19);
		
		BusStation station20 = new BusStation();
		station20.stationName = "吴迅中学";
		station20.longitude = 121.5568;
		station20.latitude = 31.12083333;
		stations.add(station20);
		
		BusStation station21 = new BusStation();
		station21.stationName = "八一";
		station21.longitude = 121.5650333;
		station21.latitude = 31.11988333;
		stations.add(station21);
		
		BusStation station22 = new BusStation();
		station22.stationName = "镇北轻纺市场";
		station22.longitude = 121.5682333;
		station22.latitude = 31.12063333;
		stations.add(station22);
		
		BusStation station23 = new BusStation();
		station23.stationName = "周浦";
		station23.longitude = 121.5712333;
		station23.latitude = 31.12001667;
		stations.add(station23);
		
		BusStation station24 = new BusStation();
		station24.stationName = "康沈路年家浜路";
		station24.longitude = 121.5738833;
		station24.latitude = 31.11668333;
		stations.add(station24);
		
		BusStation station25 = new BusStation();
		station25.stationName = "南八灶";
		station25.longitude = 121.5758;
		station25.latitude = 31.11258333;
		stations.add(station25);
		
		BusStation station26 = new BusStation();
		station26.stationName = "康沈路繁荣路";
		station26.longitude = 121.5777167;
		station26.latitude = 31.10591667;
		stations.add(station26);
		
		BusStation station27 = new BusStation();
		station27.stationName = "塘东";
		station27.longitude = 121.5845;
		station27.latitude = 31.10213333;
		stations.add(station27);
		
		BusStation station28 = new BusStation();
		station28.stationName = "牛桥";
		station28.longitude = 121.6042;
		station28.latitude = 31.10523333;
		stations.add(station28);
		
		BusStation station29 = new BusStation();
		station29.stationName = "周祝公路(上海医药园区)";
		station29.longitude = 121.6072167;
		station29.latitude = 31.10556667;
		stations.add(station29);
		
		return stations;
	}
	
	public static List<String> getBusKeyId() {
		List<String> result = new ArrayList<String>();
		result.add("13301859065");
		result.add("18019020324");
		result.add("18019020334");
		result.add("18918347505");
		result.add("18918347176");
		result.add("18918347493");
		result.add("18918348637");
		result.add("18918348714");
		result.add("18918348365");
		result.add("18918348330");
		result.add("18918347331");
		result.add("18918348329");
		result.add("18918347506");
		result.add("18918348620");
		result.add("18918348895");
		result.add("18918347296");
		result.add("18918347175");
		result.add("18918344551");
		result.add("18918347503");
		result.add("18918347544");
		result.add("18918348617");
		result.add("18918347489");
		result.add("18918347371");
		result.add("18918348607");
		result.add("18918348619");
		result.add("18918347491");
		result.add("18918347492");
		result.add("18918347785");
		result.add("18918348139");
		result.add("18918347871");
		return result;
	}
}
