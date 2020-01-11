package com.example.td5_partie1_webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView myWebView =  (WebView) findViewById(R.id.webview);
        //myWebView.loadUrl("https://news.ycombinator.com/");
        myWebView.loadUrl("file:///android_asset/www/index.html");
        myWebView.getSettings().setJavaScriptEnabled(true);

    }
}
