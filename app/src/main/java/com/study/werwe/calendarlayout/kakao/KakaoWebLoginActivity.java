package com.study.werwe.calendarlayout.kakao;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.study.werwe.calendarlayout.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class KakaoWebLoginActivity extends ActionBarActivity {

    public static final String TAG = KakaoWebLoginActivity.class.getSimpleName();

    @InjectView(R.id.login_webview)
    WebView mLoginWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_web_login);
        ButterKnife.inject(this);
        String url = getIntent().getStringExtra("url");
        mLoginWebview.getSettings().setJavaScriptEnabled(true);
        mLoginWebview.setWebViewClient(new KakaoWebLoginClient());
        WebSettings settings = mLoginWebview.getSettings();


        mLoginWebview.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kakao_web_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"KakaoWebLoginActivity.onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG,"KakaoWebLoginActivity.onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"KakaoWebLoginActivity.onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"KakaoWebLoginActivity.onStop");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG,"KakaoWebLoginActivity.onNewIntent");
        Uri uri = intent.getData();
        Log.d(TAG,uri.toString());

    }

    class KakaoWebLoginClient extends WebViewClient {
        public final String TAG = KakaoWebLoginClient.class.getSimpleName();
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("http://cbtnew.starmark.co.kr"))
            {
                Uri authoUri = Uri.parse(url);
                String authCode = authoUri.getQueryParameter("code");
                if(KakaoPreference.putAuthCode(KakaoWebLoginActivity.this, authCode)) {
                    Log.d(TAG, "auth code saved");
                }
                finish();

                view.stopLoading();
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    class KakaoChromeClient extends WebChromeClient {

    }

}

