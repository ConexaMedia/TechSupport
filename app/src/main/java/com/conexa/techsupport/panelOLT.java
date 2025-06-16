package com.conexa.techsupport;

import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class panelOLT extends AppCompatActivity {

    private WebView webView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olt);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String oltUrl = "http://103.55.224.200:2634/";
        String username = "user";
        String password = "user123";

        webView.loadUrl(oltUrl);

        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                new Handler().postDelayed(() -> {
                    webView.loadUrl("javascript:(function() {" +
                            "var userField = document.querySelectorAll('input')[0];" +
                            "var passField = document.getElementById('userPwd');" +

                            "userField.value = '" + username + "';" +
                            "passField.value = '" + password + "';" +

                            "userField.dispatchEvent(new Event('input'));" +
                            "passField.dispatchEvent(new Event('input'));" +

                            "document.querySelector('.login-form-submit a').click();" +
                            "})()");
                }, 1000);

            }
        });
    }
}
