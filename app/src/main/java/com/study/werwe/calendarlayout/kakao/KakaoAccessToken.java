package com.study.werwe.calendarlayout.kakao;

import org.parceler.Parcel;

/**
 * Created by starmark on 15. 2. 2..
 */
@Parcel
public class KakaoAccessToken {
    public String access_token;
    public String token_type;
    public String refresh_token;
    public String expires_in;
    public String scope;

    public String expire_date;

    public KakaoAccessToken(){}

    public KakaoAccessToken(String access_token, String token_type, String refresh_token, String expires_in, String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
    }

}
