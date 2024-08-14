package com.fit2081.fit2081_a1_parkdarin;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class EventGoogleResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_google_result);
        WebView webView = findViewById(R.id.webView);

        String eventName = getIntent().getExtras().getString("eventName");

        String googlePage = "https://www.google.com/search?q=" + eventName;

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(googlePage);
    }
}

