/**
 * 
 */
package cn.salesuite.bus.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import cn.salesuite.bus.BaseActivity;
import cn.salesuite.bus.config.Constant;

/**
 * 用html5实现实时公交线路
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

		final String website = Constant.BUS_REAL_TIME_H5;
		
		webView = (WebView) findViewById(R.id.webview);
		
		addFootMenuView();
		
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
		dialog = ProgressDialog.show(this,"请稍后", "正在加载网页...",true);
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setInitialScale(100);
		webView.setVerticalScrollBarEnabled(false);                  //垂直不显示滚动条
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);//提高渲染的优先级
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
		webView.loadUrl(website);
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		return false;
    	}
    	return super.onKeyDown(keyCode, event);
    }
	
	protected void onDestroy() {
		super.onDestroy();
		webView.removeAllViews();
		webView.destroy();
	}
}
