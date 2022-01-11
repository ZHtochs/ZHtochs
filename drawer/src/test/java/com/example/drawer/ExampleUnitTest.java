package com.example.drawer;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void additionIsCorrect() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                System.out.println(System.currentTimeMillis());
                Response proceed = chain.proceed(chain.request());
                System.out.println(System.currentTimeMillis());
                return proceed;
            }
        }).build();
        Request request = new Request.Builder().url("http://wwww.baidu.com").build();
        Call call = okHttpClient.newCall(request);
        Response execute = call.execute();
    }
}