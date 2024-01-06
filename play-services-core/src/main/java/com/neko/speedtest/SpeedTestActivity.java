package com.neko.speedtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.R;

public class SpeedTestActivity extends Activity {
    WebView web;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.uwu_speedtest);
        this.web = (WebView) findViewById(R.id.uwu_WebView);
        this.web.getSettings().setJavaScriptEnabled(true);
        this.web.loadUrl("https://fusiontempest.speedtestcustom.com");
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && this.web.canGoBack()) {
            this.web.goBack();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}
