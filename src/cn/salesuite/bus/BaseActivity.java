/**
 * 
 */
package cn.salesuite.bus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import cn.salesuite.bus.activity.MainActivity;
import cn.salesuite.bus.activity.R;
import cn.salesuite.bus.activity.RealTimeItemListActivity;
import cn.salesuite.saf.app.SAFActivity;

/**
 * ���Ϲ��� ���̵Ļ���Activity,��Ŀ�����е�Activty���̳и�Activity
 * @author Tony Shen
 *
 */
public class BaseActivity extends SAFActivity{
	
	public View FootView;
	public LayoutInflater mInflater;
	private Button btn0,btn1,btn2; //����ײ�Bar��3�������л���ť
	
	public static int MENU_SELECT = 0;
	protected ProgressDialog mProgressDialog;
	
	public static String MAIN_ACTIVITY = "cn.salesuite.bus.activity.MainActivity";
	public static String REAL_TIME_ACTIVITY = "cn.salesuite.bus.activity.SearchRealTimeActivity";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
//		GlobalExceptionHandler.register(this);
	}
	
	/**
	 * ���ӵײ��˵���
	 */
	public void addFootMenuView() {
		FootView = mInflater.inflate(R.layout.footview, null);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		// ���õײ�
		params.gravity = Gravity.BOTTOM;

		// ��ӿؼ�
		addContentView(FootView, params);
		
		btn0 = (Button)FootView.findViewById(R.id.btn_menu0);
		btn1 = (Button)FootView.findViewById(R.id.btn_menu1);
		btn2 = (Button)FootView.findViewById(R.id.btn_menu2);
		
		// ��ҳ
		btn0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menuSelceted(0);
				if(!MAIN_ACTIVITY.equals(getCurrentActivityName())) {
					startActivity(new Intent(BaseActivity.this,MainActivity.class));
					finishActivity();
				}
			}
		});
		
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menuSelceted(1);
				if (REAL_TIME_ACTIVITY.equals(getCurrentActivityName())) {
					startActivity(new Intent(BaseActivity.this,RealTimeItemListActivity.class));
					finishActivity();
				}
			}
		});

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menuSelceted(2);
			}
		});
	}
	
	/**
	 * 
	 * @param index
	 */
	public void menuSelceted(int index) {
		switch (index) {
		case 0:
			MENU_SELECT = 0;
			break;
		case 1:
			MENU_SELECT = 1;
			break;
		case 2:
			MENU_SELECT = 2;
			break;
		default:
			break;
		}		
	}
	
	/**
	 * ����app��ҳ
	 */
	public void backToMainActivity() {
		for (final Activity act : app.activityManager) {
			if (act.getClass() == MainActivity.class) {
				continue;
			}
			act.finish();
		}
	}
	
	/**
	 * ��ǰ��Activity�Ƿ�ΪMainActivity
	 * @return
	 */
	public boolean checkMainActivity() {
		if(MAIN_ACTIVITY.equals(getCurrentActivityName())) {
			return true;
		} else 
			return false;
	}
	
	/**
	 * �������MainActivity,���˳���Activity
	 */
	public void finishActivity() {
		if (!checkMainActivity()) {
			finish();
		}
	}
	
	public ProgressDialog getProgressDialog(Context context) {
		return ProgressDialog.show(context,
				getString(R.string.load_title), getString(R.string.load_message));
	}
	
	public void closeProgressDialog(ProgressDialog mProgressDialog) {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
}