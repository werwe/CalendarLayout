package com.study.werwe.calendarlayout.kakao;

/**
 * Created by starmark on 15. 2. 2..
 */
public class KakaoTokenRequest {

    public String grant_type;
    public String client_id;
    public String redirect_uri;
    public String code;


    public KakaoTokenRequest(){}

    public KakaoTokenRequest(String grant_type, String client_id, String redirect_uri, String code) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
    }
}
