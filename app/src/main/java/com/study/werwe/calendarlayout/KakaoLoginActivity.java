package com.study.werwe.calendarlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.werwe.calendarlayout.kakao.KakaoAccessToken;
import com.study.werwe.calendarlayout.kakao.KakaoApi;
import com.study.werwe.calendarlayout.kakao.KakaoProfile;
import com.study.werwe.calendarlayout.kakao.KakaoServiceGenerator;
import com.study.werwe.calendarlayout.kakao.KakaoWebLoginActivity;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class KakaoLoginActivity extends ActionBarActivity {

    private static final String TAG = KakaoLoginActivity.class.getSimpleName();
    public static final int REQUEST_CODE_LOGIN = 100;

    private final String HOST = "https://kauth.kakao.com";
    private final String APIKEY = "5436966ccbb8382a0f9ceb81ece945d6";
    private final String REDIRECT_URI = "http://cbtnew.starmark.co.kr/oauth";

    private KakaoAccessToken mAccessToken;

    @InjectView(R.id.profile_picture)
    ImageView mProfilePicture;
    @InjectView(R.id.user_name)
    TextView mUserName;
    @InjectView(R.id.login)
    Button mLogin;

    KakaoApi mKakaoAuth;
    KakaoApi mKakaoApi;

    Callback callback = new Callback() {
        @Override
        public void success(Object o, Response response) {
//            Log.d(TAG, response.toString());
//            Log.d(TAG, response.getBody().toString());
            Log.d(TAG, response.getReason());
            Log.d(TAG, response.getUrl());
            Intent intent = new Intent(KakaoLoginActivity.this, KakaoWebLoginActivity.class);
            intent.putExtra("url", response.getUrl());
            startActivityForResult(intent, REQUEST_CODE_LOGIN);
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

        mKakaoAuth = KakaoServiceGenerator.createService(KakaoApi.class, HOST);

    }

    @OnClick(R.id.login)
    public void login() {
        mKakaoAuth.authorize(APIKEY, REDIRECT_URI, callback);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"KakaoLoginActivity.onActivityResult");
        if (requestCode == REQUEST_CODE_LOGIN) {
            if (resultCode == RESULT_OK) {
                mAccessToken = Parcels.unwrap(data.getParcelableExtra("access_token"));
                mKakaoApi = KakaoServiceGenerator.createService(KakaoApi.class, "https://kapi.kakao.com", mAccessToken);
                getProfile();
            }
        } else {

        }
    }

    private void getProfile() {
        mKakaoApi.meGet("Bearer "+mAccessToken.access_token,new Callback<KakaoProfile>() {
            @Override
            public void success(KakaoProfile kakaoProfile, Response response) {
                Log.d(TAG, "kakao id:" + kakaoProfile.id);
                Log.d(TAG, kakaoProfile.properties.get("nickname"));
//                Log.d(TAG, kakaoProfile.properties.get("thumbnail_image")+"");
//                Log.d(TAG, kakaoProfile.properties.get("profile_image")+"");
                mUserName.setText(kakaoProfile.properties.get("nickname"));
                Glide.with(KakaoLoginActivity.this)
                        .load(kakaoProfile.properties.get("profile_image"))
                        .into(mProfilePicture);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
