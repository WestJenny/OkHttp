package com.example.okhttp2;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main1_5_28Single {
    OkHttpClient okHttpClient;
    private static Main1_5_28Single single = new Main1_5_28Single();

    private Main1_5_28Single(){
        okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .build();
    }

    public static Main1_5_28Single Client(){
        return single;
    }

    public void getGet(String url, final MyCallBack myCallBack){
        Request build = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myCallBack.error(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myCallBack.success(response.body().string());
            }
        });
    }

    public void getPost(String url, final MyCallBack myCallBack,String[] keyName,String[] values){
        FormBody.Builder builder = new FormBody.Builder();
        for (int i=0; i<keyName.length; i++){
            builder.add(keyName[i],values[i]);
        }
        FormBody build1 = builder.build();
        Request build = new Request.Builder()
                .post(build1)
                .url(url)
                .build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myCallBack.error(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myCallBack.success(response.body().string());
            }
        });
    }

    public interface MyCallBack{
        void success(String response);
        void error(String message);
    }
}
