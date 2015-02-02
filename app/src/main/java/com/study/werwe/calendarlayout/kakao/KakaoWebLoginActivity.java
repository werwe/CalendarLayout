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
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.study.werwe.calendarlayout.R;

import org.apache.http.Header;
import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class KakaoWebLoginActivity extends ActionBarActivity {

    public static final String TAG = KakaoWebLoginActivity.class.getSimpleName();
    public final String HOST = "https://kauth.kakao.com";
    private final String APIKEY = "5436966ccbb8382a0f9ceb81ece945d6";
    private final String REDIRECT_URI = "http://cbtnew.starmark.co.kr/oauth";

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
                Log.d(TAG, "url:" + url);
                Log.d(TAG, "authcode:" + authCode);
                if(KakaoPreference.putAuthCode(KakaoWebLoginActivity.this, authCode)) {
                }

                getAccessToken3(authCode);
                view.stopLoading();
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    private void getAccessToken2(String authCode) {
            KakaoApi auth = KakaoServiceGenerator.createService(KakaoApi.class, HOST);
            auth.getAccessToken(
                    new KakaoTokenRequest("authorization_code",APIKEY,REDIRECT_URI,authCode) ,
                    new Callback<KakaoAccessToken>(){
                        @Override
                        public void success(KakaoAccessToken kakaoAccessToken, Response response) {
                            kakaoAccessToken.expire_date = (System.currentTimeMillis()+(Integer.parseInt(kakaoAccessToken.expires_in) * 1000))+"";
                            KakaoPreference.putAccessToken(KakaoWebLoginActivity.this, new Gson().toJson(kakaoAccessToken));
                            Intent intent = new Intent();
                            intent.putExtra("access_token", Parcels.wrap(kakaoAccessToken));
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //로그인 실패
                            Log.d(TAG, error.toString());
                            Log.d(TAG, error.getMessage());
                            Toast.makeText(KakaoWebLoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    }
            );
    }

    private void getAccessToken3(String authCode) {
        KakaoApi auth = KakaoServiceGenerator.createService(KakaoApi.class, HOST);
        auth.getAccessTokenGet(
                APIKEY,REDIRECT_URI,authCode ,
                new Callback<KakaoAccessToken>(){
                    @Override
                    public void success(KakaoAccessToken kakaoAccessToken, Response response) {
                        kakaoAccessToken.expire_date = (System.currentTimeMillis()+(Integer.parseInt(kakaoAccessToken.expires_in) * 1000))+"";
                        KakaoPreference.putAccessToken(KakaoWebLoginActivity.this, new Gson().toJson(kakaoAccessToken));
                        Intent intent = new Intent();
                        intent.putExtra("access_token", Parcels.wrap(kakaoAccessToken));
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //로그인 실패
                        Log.d(TAG, error.toString());
                        Log.d(TAG, error.getMessage());
                        Toast.makeText(KakaoWebLoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                }
        );
    }


    private void getAccessToken(String authCode) {
        RequestParams params = new RequestParams();
        params.put("grant_type", "authorization_code");
        params.put("client_id", APIKEY);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("code", authCode);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(HOST + "/oauth/token", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, responseString);
            }
        });

    }

    class KakaoChromeClient extends WebChromeClient {

    }

}

