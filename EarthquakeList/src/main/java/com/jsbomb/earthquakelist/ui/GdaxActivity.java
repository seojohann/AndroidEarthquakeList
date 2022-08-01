package com.jsbomb.earthquakelist.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jsbomb.earthquakelist.R;

/**
 * Created by john.seo on 7/25/2017.
 */
public class GdaxActivity extends AppCompatActivity {

    WebView mWebView;
    boolean mClearHistory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gdax_layout);

        initWebView();

        initAdView();

        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GdaxActivity.this.finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mWebView.reload();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            SharedPreferences prefs = getApplicationContext().
                    getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            String url = prefs.getString("url", "");
            if (!url.isEmpty()) {
                mWebView.loadUrl(url);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getApplicationContext().
                getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("url", mWebView.getUrl());
        edit.commit();   // can use edit.apply() but in this case commit is better
    }


    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }

        if (mWebView != null && !mWebView.getUrl().equals("https://www.gdax.com/")
                && !mWebView.canGoBack()) {
            mWebView.loadUrl("https://www.gdax.com/");
            mClearHistory = true;
            return;
        }

        super.onBackPressed();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                if (mClearHistory) {
                    mClearHistory = false;
                    view.clearHistory();
                }
                super.onPageFinished(view, url);
            }
        });
        mWebView.loadUrl("https://www.gdax.com/");
    }

    private void initAdView() {
        //my id
//        MobileAds.initialize(this);
////        MobileAds.initialize(this, "ca-app-pub-7438807169301480~2950987624");
//        //test id
////        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
//
//        AdView adview = (AdView)findViewById(R.id.adView);
//        AdRequest adRequest;
//        adRequest = new AdRequest.Builder().build();
////        adRequest = new AdRequest.Builder().addTestDevice("81A442EFD7E2204CA5092B6AD6AE3029").build();
//        adview.loadAd(adRequest);
    }

}
