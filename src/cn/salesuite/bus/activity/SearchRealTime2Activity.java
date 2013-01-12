/**
 * 
 */
package cn.salesuite.bus.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import cn.salesuite.bus.BaseActivity;
import cn.salesuite.bus.config.Constant;
import cn.salesuite.saf.utils.SAFUtil;

/**
 * ��html5ʵ��ʵʱ������·
 * @author Tony Shen
 *
 */
public class SearchRealTime2Activity extends BaseActivity{

	private ProgressDialog dialog;
    private WebView webView;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_real_time2);
		
		if (!SAFUtil.checkNetworkStatus(this)) {
			showToast("��ǰ������,���Ժ�����!");
		} else {
			findViews();
			initViews();
			initData();
		}
		
		addFootMenuView();
	}
	
	private void findViews() {
		webView = (WebView) findViewById(R.id.webview);
	}

	private void initViews() {
		webView.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event){				
				
				return false;
			}
			
		});	
		
		webView.setOnKeyListener(new View.OnKeyListener(){

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					finish();
		    		return false;
		    	}
				
				return false;
			}
			
		});
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setInitialScale(100);
		webView.setVerticalScrollBarEnabled(false);                  //��ֱ����ʾ������
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);//�����Ⱦ�����ȼ�
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		});
	}

	private void initData() {
		String website = Constant.BUS_REAL_TIME_H5;
		dialog = ProgressDialog.show(this,"���Ժ�", "���ڼ�����ҳ...",true);
		webView.loadUrl(website);
	}

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		finish();
    		return false;
    	}
    	return super.onKeyDown(keyCode, event);
    }
	
	protected void onDestroy() {
		super.onDestroy();
		if (webView!=null) {
			webView.removeAllViews();
			webView.destroy();
		}
	}
}