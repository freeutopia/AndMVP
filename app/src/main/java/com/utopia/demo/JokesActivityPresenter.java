package com.utopia.demo;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.utopia.mvp.presenter.ActivityPresenter;

public class JokesActivityPresenter extends ActivityPresenter<JokesView, JokesModel> {
    private int indexPage = 1;

    @Override
    protected void inCreat(Bundle savedInstanceState) {
        modelRef().get().fetch(1,10);
    }

    @Override
    protected void inDestory() {

    }

    @Override
    public void onError(Throwable e) {
        viewRef().get().onError(e);
    }

    @Override
    public <T> void onSuccess(T result) {
        runOnUiThread(()-> viewRef().get().onSuccess(result));
    }

    public void refresh(View view) {
        modelRef().get().fetch(++indexPage,10);
    }
}