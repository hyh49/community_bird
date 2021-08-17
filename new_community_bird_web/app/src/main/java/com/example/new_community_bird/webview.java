package com.example.new_community_bird;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author chenjie
 */
public class webview extends AppCompatActivity {
    private ProgressBar pbAd;
    private WebView wvInternetPage;
    private TextView tvWebTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN |
//                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        setContentView(R.layout.webview);
        String loadUrl = getIntent().getStringExtra("jurl");
//         loadUrl = "http://xueshu.baidu.com/usercenter/paper/show?paperid=4868d8906f725a33c4737d1dbc647660&site=xueshu_se";
        String title = getIntent().getStringExtra("title");
        title = "加载中...";
        wvInternetPage = findViewById(R.id.pb_webView);
        tvWebTitle = findViewById(R.id.tv_web_title);
        pbAd = findViewById(R.id.pb_ad);
        WebViewOption(loadUrl, title);
    }

    private void WebViewOption(String loadUrl, String title) {
        tvWebTitle.setText(title);
        WebSettings settings = wvInternetPage.getSettings();
        settings.setJavaScriptEnabled(true);
        //允许打开js新窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        wvInternetPage.setWebViewClient(new MyWebViewClient());
        wvInternetPage.setWebChromeClient(new MyChromeWebViewClient());
        wvInternetPage.loadUrl(loadUrl);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            tvWebTitle.setText(view.getTitle());
            super.onPageFinished(view, url);
        }
    }

    private class MyChromeWebViewClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                pbAd.setVisibility(View.INVISIBLE);
            } else {
                if (pbAd.getVisibility() == GONE) {
                    pbAd.setVisibility(VISIBLE);
                }
                pbAd.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
    @Override
    public void onBackPressed() {
        if (wvInternetPage.canGoBack()) {
            wvInternetPage.goBack();
            return;
        }
        super.onBackPressed();
    }
}