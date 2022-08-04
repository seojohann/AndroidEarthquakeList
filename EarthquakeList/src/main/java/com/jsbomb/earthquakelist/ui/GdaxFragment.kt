package com.jsbomb.earthquakelist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jsbomb.earthquakelist.databinding.FragmentGdaxBinding

class GdaxFragment : Fragment() {

    private lateinit var binding: FragmentGdaxBinding
    private var clearHistory = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGdaxBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWebView()

        binding.imageButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initWebView() {
        with(binding.webView) {

            settings.javaScriptEnabled = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.domStorageEnabled = true

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    view.loadUrl(request.url.toString())
                    return false
                }

                override fun onPageFinished(view: WebView, url: String) {
                    if (clearHistory) {
                        clearHistory = false
                        view.clearHistory()
                    }
                    super.onPageFinished(view, url)
                }
            }

            loadUrl("https://pro.coinbase.com/")
        }
    }

    private fun initAdView() {
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