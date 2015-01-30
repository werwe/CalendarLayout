package com.study.werwe.calendarlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.werwe.calendarlayout.kakao.KakaoAuth;
import com.study.werwe.calendarlayout.kakao.KakaoWebLoginActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class KakaoLoginActivity extends ActionBarActivity {

    private static final String TAG = KakaoLoginActivity.class.getSimpleName();
    @InjectView(R.id.profile_picture)
    ImageView mProfilePicture;
    @InjectView(R.id.user_name)
    TextView mUserName;
    @InjectView(R.id.login)
    Button mLogin;

    KakaoAuth mKakaoAuth;

    String APIKEY = "4035a6507be2873095a981b1f71ad83b";

    Callback callback = new Callback() {
        @Override
        public void success(Object o, Response response) {
//          Log.d(TAG, response.toString());
//          Log.d(TAG, response.getBody().toString());
//          Log.d(TAG, response.getReason());
//            Log.d(TAG, response.getUrl());
            Intent intent = new Intent(KakaoLoginActivity.this, KakaoWebLoginActivity.class);
            intent.putExtra("url", response.getUrl());
            startActivity(intent);
        }

        @Override
        public void failure(RetrofitError retrofitError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);
        ButterKnife.inject(this);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("https://kauth.kakao.com")
                .build();

        mKakaoAuth = restAdapter.create(KakaoAuth.class);

    }

    @OnClick(R.id.login)
    public void login()
    {
        mKakaoAuth.authorize( APIKEY , "http://cbtnew.starmark.co.kr/oauth",callback);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kakao_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
