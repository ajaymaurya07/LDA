package com.example.lda.houseTax.paymentModeActivity

import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lda.BaseActivity
import com.example.lda.R

class SbiTestActivity : BaseActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sbi_test)

        webView = findViewById(R.id.webView)

        setupWebView()
        loadSbiPayment()
    }

    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            allowContentAccess = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {

                val url = request.url.toString()
                Log.d("SBI_WEBVIEW", "Intercept URL = $url")

                // SBI return / response URL detect
                if (
                    url.contains("/api/sbi/return") ||      // aapka backend return url
                    url.contains("PaymentResponse") ||      // SBI hosted response
                    url.contains("merchantresponse")
                ) {
                    // Payment flow finished
                    handlePaymentFinished()
                    return true
                }
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                Log.d("SBI_WEBVIEW", "Page Finished = $url")
            }
        }
    }

    private fun loadSbiPayment() {

        val postUrl = "https://merchant.sbiuat.bank.in/merchant/merchantprelogin.htm"

        val postData = "encdata=YiFYlLjMqUlu0Se+AB3tmIosZvsKsqUnQCHjSder0e1T3+5DMe76tvdAZJWtc7VIyUPcpq" +
                    "ODD1p6/sHHKSAR6q0RX1k8syl9MCyfoxotm+ItGIgidfboWftiqnGtsS9ijGyTzqscsBKBsLPbQIKdsT" +
                    "P90oGPkrHMm2viBBiuRNZnf4vohSXmKu3H3ag9mzPLUh0nEDdOPf/m8ls8aDkP78BYF8iNNLEW" +
                    "CT91FcszOhRIvj0vnrlFIYFfJVV3YKgv636+MXYZNKbQTnvxLp8AaFYDFTH8ZG0V6m//II19D32CkE" +
                    "MyccZyjCX7FPvS0ZE7YF9Ia8v/18y+UD7wLIOCMbQmToJSvKNqpCs9PPHcHoQ=" +
                    "&merchant_code=TEST_MERCHANT"

        Log.d("SBI_POST", postData)

        webView.postUrl(
            postUrl,
            postData.toByteArray(Charsets.UTF_8)
        )
    }

    private fun handlePaymentFinished() {
        // WebView band karo
        webView.stopLoading()

        // Yahin backend ko double verification call karna hai
        // callBackendVerifyApi()

        // Abhi test ke liye bas close
        finish()
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        // Payment ke beech back disable
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // optional: cancel flow
        }
    }
}

