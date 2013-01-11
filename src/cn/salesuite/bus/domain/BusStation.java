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
	public double longitude;//����
	public double latitude; //γ��
	public boolean uplineHasBus = false;
	public boolean downLineHasBus = false;
	public boolean middleStatus = false; //�Ƿ���վ�м�״̬
}
