package com.study.werwe.calendarlayout.kakao;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by starmark on 15. 1. 29..
 */
public interface KakaoApi {

    /**
     * Request
     * GET /oauth/authorize?client_id={app_key}&redirect_uri={redirect_uri}&response_type=code HTTP/1.1
     * Host: kauth.kakao.com
     * <p/>
     * Response
     * HTTP/1.1 302 Found
     * Content-Length: 0
     * Location: {redirect_uri}?code={authorize_code}
     */
    @GET("/oauth/authorize?response_type=code")
    public void authorize(
            @Query("client_id") String app_key,
            @Query("redirect_uri") String redirect_uri
            , Callback<Response> cb
    );

    /**
     * Request
     * POST /oauth/token HTTP/1.1
     * Host: kauth.kakao.com
     * <p/>
     * 키	            |    설명	                                                    |    필수
     * ---------------------------------------------------------------------------------------------
     * grant_type	    |    authorization_code로 고정	                            |    O
     * ---------------------------------------------------------------------------------------------
     * client_id	    |    앱 생성시 발급 받은 REST API 키.	                            |    O
     * ---------------------------------------------------------------------------------------------
     * redirect_uri	    |    코드가 리다이렉트 된 URI.                                   |
     * |    설정 > 일반 > 웹 > 사이트 도메인에서 설정한 각각의 도메인에         |    O
     * |    설정 > 일반 > 웹 > Redirect Path 를 붙인 URI.	            |
     * ---------------------------------------------------------------------------------------------
     * code	            |    위 코드 받기에서 발급 받은 인증된 코드.	                        |    O
     *
     * Response
     * HTTP/1.1 200 OK
     * Content-Type: application/json;charset=UTF-8
     * {
     * "access_token":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
     * "token_type":"bearer",
     * "refresh_token":"yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
     * "expires_in":43199,
     * "scope":"Basic_Profile"
     * }
     */


    @POST("/oauth/token")
    @Headers("Content-Type: application/json;charset=UTF-8")
    public void getAccessToken(@Body KakaoTokenRequest body, Callback<KakaoAccessToken> callback);

    @GET("/oauth/token?grant_type=authorization_code")
    public void getAccessTokenGet(@Query("client_id") String api_key, @Query("redirect_uri") String uri, @Query("code") String authCode, Callback<KakaoAccessToken> callback);

    /**
     * Request
     * POST /oauth/token HTTP/1.1
     * Host: kauth.kakao.com
     *
     * Response
     * HTTP/1.1 200 OK
     * Content-Type: application/json;charset=UTF-8
     * {
     * "access_token":"wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
     * "token_type":"bearer",
     * "refresh_token":"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",  //optional
     * "expires_in":43199,
     * }
     */
    //refresh token


    /**
     * POST /v1/user/logout HTTP/1.1
     * Host: kapi.kakao.com
     * <p/>
     * HEADER
     * Authorization: Bearer {access_token}
     * <p/>
     * Response
     * HTTP/1.1 200 OK
     * Content-Type: application/json;charset=UTF-8
     * {
     * "id":123456789
     * }
     */
    @POST("/v1/user/logout")
    public void logout(@Header("Authorization") String access_token, Callback<KakaoID> callback);


    /**
     * POST/v1/user/signup HTTP/1.1
     * Host: kapi.kakao.com
     * <p/>
     * HEADER
     * Authorization: Bearer {access_token}
     * Content-Type: application/x-www-form-urlencoded;charset=utf-8
     */
    @POST("/v1/user/signup")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    public void signup(@Header("Authorization") String access_token, Callback<KakaoID> callback);

//    GET/POST /v1/user/me HTTP/1.1
//    Host: kapi.kakao.com
//    Authorization: Bearer {access_token}
//    Content-type: application/x-www-form-urlencoded;charset=utf-8

    @GET("/v1/user/me")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    public void meGet(@Header("Authorization") String access_token, Callback<KakaoProfile> callback);

    @POST("/v1/user/me")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    public void mePost(@Header("Authorization") String access_token, Callback<KakaoProfile> callback);
}


