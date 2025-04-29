package com.example.tootsay.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tootsay.R;

public class NewsFullActivity extends AppCompatActivity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_full);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String url = getIntent().getStringExtra("url");
        webview = findViewById(R.id.webview);
        if (webview != null) {
            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webview.setWebViewClient(new WebViewClient());
            webview.loadUrl(url);
        } else {
            // Handle the case where the WebView is null
            // For example, you might want to show an error message or log this incident
            Log.e("fullnews", "WebView is null. Unable to load the URL.");
        }


    }

    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        if (webview != null && webview.canGoBack()) {
            webview.goBack();
        }

        return super.getOnBackInvokedDispatcher();

    }
}