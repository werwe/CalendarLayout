package com.study.werwe.calendarlayout.kakao;

import retrofit.RestAdapter;

/**
 * Created by starmark on 15. 2. 2..
 */
public class KakaoServiceGenerator {
    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        return createService(serviceClass, baseUrl, null);
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, final KakaoAccessToken accessToken) {

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(baseUrl);
//                .setClient(new OkClient(new OkHttpClient()));

//        if (accessToken != null) {
//            builder.setRequestInterceptor(new RequestInterceptor() {
//                @Override
//                public void intercept(RequestFacade request) {
//                    request.addHeader("Authorization", accessToken.token_type +
//                            " " + accessToken.access_token);
//                }
//            });
//        }

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}
