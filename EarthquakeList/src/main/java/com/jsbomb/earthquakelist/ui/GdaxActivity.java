package com.jsbomb.earthquakelist.ui;


import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jsbomb.earthquakelist.R;

/**
 * Created by john.seo on 7/25/2017.
 */
public class GdaxActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gdax_layout);

        WebView webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });
        webView.loadUrl("https://www.gdax.com");

        //my id
        MobileAds.initialize(this, "ca-app-pub-7438807169301480~2950987624");
        //test id
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        AdView adview = (AdView)findViewById(R.id.adView);
        AdRequest adRequest;
        adRequest = new AdRequest.Builder().build();
//        adRequest = new AdRequest.Builder().addTestDevice("81A442EFD7E2204CA5092B6AD6AE3029").build();
        adview.loadAd(adRequest);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
}
