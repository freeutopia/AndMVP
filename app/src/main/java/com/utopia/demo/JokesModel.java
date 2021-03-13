package com.utopia.demo;

import com.utopia.demo.response.JokesResponse;
import com.utopia.demo.utils.GsonUtils;
import com.utopia.mvp.model.BaseModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class JokesModel extends BaseModel {
    private Call call;

    @Override
    public void fetch(int indexPage, int pageSize) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://v.juhe.cn/joke/content/list.php?sort=asc&page="+indexPage
                        +"&pagesize="+pageSize
                        +"&time=1418816972&key=7a6c088870cc2d8f88b8bfac4f966777")
                .get()
                .build();
        call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                notifyError(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //Log.e("test","response:"+response.body().string());
                JokesResponse object = GsonUtils.toObject(response.body().string(), JokesResponse.class);
                notifySuccess(object.result.data);
            }
        });
    }

    @Override
    public void onDestory() {
        super.onDestory();
        if (call != null){
            call.cancel();
            call = null;
        }
    }
}
