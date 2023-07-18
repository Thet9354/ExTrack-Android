package sp.example.extrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.extrack.R;

public class WebView_Activity extends AppCompatActivity {

    private WebView web;
    private String link;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        intent = getIntent();

        initWidget();

        getIntentData();
    }

    private void getIntentData() {

        link = intent.getStringExtra("Link");

        initUI();
    }

    private void initUI() {
        //Using webView to view webpages within an app
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new Callback());
        web.loadUrl(link);
    }

    private void initWidget() {
        web = findViewById(R.id.web);
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }
}