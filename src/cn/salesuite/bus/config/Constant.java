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

	/** salesuit�洢Ŀ¼/�ļ� **/
	public static final String DIR = "/salesuite";
	public static final String CACHE_DIR = DIR + "/images";
	public static final String CAMERA_TEMP = DIR + "/images/camera_temp.jpg";
	public static final String IMAGE_TEMP = DIR + "/images/temp.jpg";
	public static final String DATABASE_FILE = DIR + "/salesuite.db";
	public static final String UPDATE_APK_DIR = DIR + "/download";
	
	public static boolean largeScreen = true;//�ж���Ļ�Ƿ��Ǹ��ܶ�
	public static int height;                //��Ļ�߶�
	public static int width;                 //��Ļ���
	
	public static final String SHARED="SaleSuite";
	
	public static final String NOTICE_CHANGE_PLACE = "notice_change";
	//gps�ж�λ�ò���ȷ�󣬵�����ʾ���û����ȷ���������У�������֪ͨ
	public static final String CHANGE_HOTMOVIE_DATA = "change_hotmovie";
	//�ڳ����б�act�е������һ�����У������л���
	public static final String CITY_SETTING_CHANGE = "change_setting_change";
	
	// app������Ϣ����
	public static final String GPS_CUR_LOCATION = "gps_cur_location";
	public static final String GPS_CITYCODE = "gps_citycode";
	public static final String GPS_CITYNAME = "gps_cityname";
	public static final String CITY_CODE = "city_code";
	public static final String CITY_NAME = "city_name";
	public static final String LAST_CITY_CODE = "last_city_code";
	public static final String CITY_CODE_DEFAULT = "310000";
	public static final String CITY_NAME_DEFAULT = "�Ϻ�";
	public static final double LATITUDE_DEFAULT = 31.234914894041356;
	public static final double LONGITUDE_DEFAULT = 121.47494494915009;
	
	// �洢app�����key
	
	// ������Ӧ
	public static final int RESULT_SUCCESS = 1;
	public static final int RESULT_FAILURE = -1;
	public static final int RESULT_IOERROR = -2;
	public static final int NETWORK_ERROR = -3;
	
	// baidu map geocode ws api
	public static final String BAIDU_API_GEOCODE = "http://api.map.baidu.com/geocoder";
	
	public static final int BUSLINE_RESULT=6000;
	public static final int BUSLINE_DETAIL_RESULT=6001;
	public static final int BUSLINE_ERROR_RESULT=6002;
	
	public static final int ROUTE_START_SEARCH=2000;//·���滮�������
	public static final int ROUTE_END_SEARCH=2001;//·���滮�������
	public static final int ROUTE_SEARCH_RESULT=2002;//·���滮���
	public static final int ROUTE_SEARCH_ERROR=2004;//·���滮����ʼ�������쳣
	
	// �������ӿڵ�ַ
	public static final String GET_BUS_STATUS = "http://202.102.101.74:17016/Service.asmx/GetCarStatus";
	
	// ������ʵʱ���h5��ַ
	public static final String BUS_REAL_TIME_H5 = "http://124.74.19.103:8090/BusSys/list/list2.jsp";
	
	public static List<BusStation> getWanZhouBusLine() {
		List<BusStation> stations = new ArrayList<BusStation>();
		BusStation station1 = new BusStation();
		station1.stationName = "��ף��·(�Ϻ�ҽҩ԰��)";
		station1.longitude = 121.606;
		station1.latitude = 31.10558333;
		stations.add(station1);
		
		BusStation station2 = new BusStation();
		station2.stationName = "ţ��";
		station2.longitude = 121.60186667;
		station2.latitude = 31.10501667;
		stations.add(station2);
		
		BusStation station3 = new BusStation();
		station3.stationName = "����";
		station3.longitude = 121.58423333;
		station3.latitude = 31.10226667;
		stations.add(station3);
		
		BusStation station4 = new BusStation();
		station4.stationName = "����·����·";
		station4.longitude = 121.57745;
		station4.latitude = 31.10686667;
		stations.add(station4);
		
		BusStation station5 = new BusStation();
		station5.stationName = "�ϰ���";
		station5.longitude = 121.57615;
		station5.latitude = 31.1115;
		stations.add(station5);
		
		BusStation station6 = new BusStation();
		station6.stationName = "����·����·";
		station6.longitude = 121.5742;
		station6.latitude = 31.11645;
		stations.add(station6);
		
		BusStation station7 = new BusStation();
		station7.stationName = "����";
		station7.longitude = 121.5719333;
		station7.latitude = 31.1191;
		stations.add(station7);
		
		BusStation station8 = new BusStation();
		station8.stationName = "������г�";
		station8.longitude = 121.56925;
		station8.latitude = 31.12093333;
		stations.add(station8);
		
		BusStation station9 = new BusStation();
		station9.stationName = "��һ";
		station9.longitude = 121.5647667;
		station9.latitude = 31.11993333;
		stations.add(station9);
		
		BusStation station10 = new BusStation();
		station10.stationName = "��Ѹ��ѧ";
		station10.longitude = 121.5582;
		station10.latitude = 31.12081667;
		stations.add(station10);

		BusStation station11 = new BusStation();
		station11.stationName = "�Լ�լ";
		station11.longitude = 121.5505333;
		station11.latitude = 31.12135;
		stations.add(station11);
		
		BusStation station12 = new BusStation();
		station12.stationName = "�ϻ���";
		station12.longitude = 121.5457167;
		station12.latitude = 31.12133333;
		stations.add(station12);
		
		BusStation station13 = new BusStation();
		station13.stationName = "����";
		station13.longitude = 121.5380833;
		station13.latitude = 31.12188333;
		stations.add(station13);
		
		BusStation station14 = new BusStation();
		station14.stationName = "�ɼ�լ";
		station14.longitude = 121.5305667;
		station14.latitude = 31.12555;
		stations.add(station14);
		
		BusStation station15 = new BusStation();
		station15.stationName = "����";
		station15.longitude = 121.5162667;
		station15.latitude = 31.12973333;
		stations.add(station15);

		BusStation station16 = new BusStation();
		station16.stationName = "����·�⻷��";
		station16.longitude = 121.5127667;
		station16.latitude = 31.13533333;
		stations.add(station16);
		
		BusStation station17 = new BusStation();
		station17.stationName = "����·��̩·";
		station17.longitude = 121.5092667;
		station17.latitude = 31.14118333;
		stations.add(station17);
		
		BusStation station18 = new BusStation();
		station18.stationName = "����·����·";
		station18.longitude = 121.5043333;
		station18.latitude = 31.1478;
		stations.add(station18);
		
		BusStation station19 = new BusStation();
		station19.stationName = "����·������·";
		station19.longitude = 121.5015333;
		station19.latitude = 31.1524;
		stations.add(station19);
		
		BusStation station20 = new BusStation();
		station20.stationName = "����·����·";
		station20.longitude = 121.4972;
		station20.latitude = 31.15705;
		stations.add(station20);
		
		BusStation station21 = new BusStation();
		station21.stationName = "����·��˼·";
		station21.longitude = 121.49335;
		station21.latitude = 31.16325;
		stations.add(station21);
		
		BusStation station22 = new BusStation();
		station22.stationName = "����·����·";
		station22.longitude = 121.4921167;
		station22.latitude = 31.17203333;
		stations.add(station22);
		
		BusStation station23 = new BusStation();
		station23.stationName = "����·����·";
		station23.longitude = 121.4912167;
		station23.latitude = 31.1751;
		stations.add(station23);
		
		BusStation station24 = new BusStation();
		station24.stationName = "ҫ��·����·";
		station24.longitude = 121.48655;
		station24.latitude = 31.17988333;
		stations.add(station24);
		
		BusStation station25 = new BusStation();
		station25.stationName = "��ɽ�϶�·��ľ��·";
		station25.longitude = 121.4595167;
		station25.latitude = 31.1935;
		stations.add(station25);
		
		BusStation station26 = new BusStation();
		station26.stationName = "��ɽ�϶�·����·";
		station26.longitude = 121.4529333;
		station26.latitude = 31.18933333;
		stations.add(station26);
		
		BusStation station27 = new BusStation();
		station27.stationName = "�Ϻ���Ӿ��";
		station27.longitude = 121.4375667;
		station27.latitude = 31.18078333;
		stations.add(station27);
		
		BusStation station28 = new BusStation();
		station28.stationName = "��Ϫ������Ŧ";
		station28.longitude = 121.4319833;
		station28.latitude = 31.18171667;
		stations.add(station28);
		
		return stations;
	}
	
	public static List<BusStation> getWanZhouDownBusLine(){
		List<BusStation> stations = new ArrayList<BusStation>();
		
		BusStation station1 = new BusStation();
		station1.stationName = "��Ϫ������Ŧ";
		station1.longitude = 121.4328167;
		station1.latitude = 31.18103333;
		stations.add(station1);
		
		BusStation station2 = new BusStation();
		station2.stationName = "�Ϻ���Ӿ��";
		station2.longitude = 121.4385833;
		station2.latitude = 31.18093333;
		stations.add(station2);
		
		BusStation station3 = new BusStation();
		station3.stationName = "��ɽ�϶�·����·";
		station3.longitude = 121.45515;
		station3.latitude = 31.19018333;
		stations.add(station3);
		
		BusStation station4 = new BusStation();
		station4.stationName = "��ɽ�϶�·��ľ��·";
		station4.longitude = 121.4619667;
		station4.latitude = 31.1946;
		stations.add(station4);
		
		BusStation station5 = new BusStation();
		station5.stationName = "ҫ��·����·";
		station5.longitude = 121.484;
		station5.latitude = 31.17795;
		stations.add(station5);
		
		BusStation station6 = new BusStation();
		station6.stationName = "����·�ֶ���·";
		station6.longitude = 121.4902333;
		station6.latitude = 31.17936667;
		stations.add(station6);
		
		BusStation station7 = new BusStation();
		station7.stationName = "����·����·";
		station7.longitude = 121.4908667;
		station7.latitude = 31.17563333;
		stations.add(station7);
		
		BusStation station8 = new BusStation();
		station8.stationName = "����·����·";
		station8.longitude = 121.4918833;
		station8.latitude = 31.17198333;
		stations.add(station8);
		
		BusStation station9 = new BusStation();
		station9.stationName = "����·��˼·";
		station9.longitude = 121.4931;
		station9.latitude = 31.16316667;
		stations.add(station9);
		
		BusStation station10 = new BusStation();
		station10.stationName = "����·����·";
		station10.longitude = 121.4967833;
		station10.latitude = 31.1573;
		stations.add(station10);
		
		BusStation station11 = new BusStation();
		station11.stationName = "����·������·";
		station11.longitude = 121.5010167;
		station11.latitude = 31.15285;
		stations.add(station11);
		
		BusStation station12 = new BusStation();
		station12.stationName = "����·����·";
		station12.longitude = 121.5041667;
		station12.latitude = 31.14768333;
		stations.add(station12);
		
		BusStation station13 = new BusStation();
		station13.stationName = "����·��̩·";
		station13.longitude = 121.5092;
		station13.latitude = 31.14085;
		stations.add(station13);
		
		BusStation station14 = new BusStation();
		station14.stationName = "����·�⻷��";
		station14.longitude = 121.51255;
		station14.latitude = 31.13603333;
		stations.add(station14);
		
		BusStation station15 = new BusStation();
		station15.stationName = "����";
		station15.longitude = 121.5183;
		station15.latitude = 31.12875;
		stations.add(station15);
		
		BusStation station16 = new BusStation();
		station16.stationName = "�ɼ�լ";
		station16.longitude = 121.5294;
		station16.latitude = 31.12616667;
		stations.add(station16);
		
		BusStation station17 = new BusStation();
		station17.stationName = "����";
		station17.longitude = 121.5375;
		station17.latitude = 31.12213333;
		stations.add(station17);
		
		BusStation station18 = new BusStation();
		station18.stationName = "�ϻ���";
		station18.longitude = 121.5417667;
		station18.latitude = 31.1212;
		stations.add(station18);
		
		BusStation station19 = new BusStation();
		station19.stationName = "�Լ�լ";
		station19.longitude = 121.5505333;
		station19.latitude = 31.12121667;
		stations.add(station19);
		
		BusStation station20 = new BusStation();
		station20.stationName = "��Ѹ��ѧ";
		station20.longitude = 121.5568;
		station20.latitude = 31.12083333;
		stations.add(station20);
		
		BusStation station21 = new BusStation();
		station21.stationName = "��һ";
		station21.longitude = 121.5650333;
		station21.latitude = 31.11988333;
		stations.add(station21);
		
		BusStation station22 = new BusStation();
		station22.stationName = "������г�";
		station22.longitude = 121.5682333;
		station22.latitude = 31.12063333;
		stations.add(station22);
		
		BusStation station23 = new BusStation();
		station23.stationName = "����";
		station23.longitude = 121.5712333;
		station23.latitude = 31.12001667;
		stations.add(station23);
		
		BusStation station24 = new BusStation();
		station24.stationName = "����·����·";
		station24.longitude = 121.5738833;
		station24.latitude = 31.11668333;
		stations.add(station24);
		
		BusStation station25 = new BusStation();
		station25.stationName = "�ϰ���";
		station25.longitude = 121.5758;
		station25.latitude = 31.11258333;
		stations.add(station25);
		
		BusStation station26 = new BusStation();
		station26.stationName = "����·����·";
		station26.longitude = 121.5777167;
		station26.latitude = 31.10591667;
		stations.add(station26);
		
		BusStation station27 = new BusStation();
		station27.stationName = "����";
		station27.longitude = 121.5845;
		station27.latitude = 31.10213333;
		stations.add(station27);
		
		BusStation station28 = new BusStation();
		station28.stationName = "ţ��";
		station28.longitude = 121.6042;
		station28.latitude = 31.10523333;
		stations.add(station28);
		
		BusStation station29 = new BusStation();
		station29.stationName = "��ף��·(�Ϻ�ҽҩ԰��)";
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
