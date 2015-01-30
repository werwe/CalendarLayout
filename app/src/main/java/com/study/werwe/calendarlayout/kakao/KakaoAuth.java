package com.study.werwe.calendarlayout.kakao;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by starmark on 15. 1. 29..
 * Kakao 사용자 인증 api
 *
 * kauth.kakao.com
 */
public interface KakaoAuth {
    //host    Host: kauth.kakao.com
    //api key:4035a6507be2873095a981b1f71ad83b

    //auth:    GET/oauth/authorize?client_id={app_key}&redirect_uri={redirect_uri}&response_type=code HTTP/1.1

    //    HTTP/1.1 302 Found
    //    Content-Length: 0
    //    Location: {redirect_uri}?code={authorize_code}

    @GET("/oauth/authorize?response_type=code")
    public void authorize(
            @Query("client_id") String app_key ,
            @Query("redirect_uri") String redirect_uri
            , Callback<Response> cb
    );

}
