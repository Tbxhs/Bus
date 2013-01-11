/**
 * 
 */
package cn.salesuite.bus.domain;

import java.io.Serializable;

/**
 * @author Tony Shen
 *
 */
public class BusStation implements Serializable{

	private static final long serialVersionUID = 6002219882248793874L;
	
	public String stationName;
	public double longitude;//经度
	public double latitude; //纬度
	public boolean uplineHasBus = false;
	public boolean downLineHasBus = false;
	public boolean middleStatus = false; //是否两站中间状态
}
