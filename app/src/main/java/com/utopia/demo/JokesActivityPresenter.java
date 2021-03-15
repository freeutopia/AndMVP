package com.utopia.demo;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.widget.Toast;

import com.utopia.mvp.presenter.ActivityPresenter;

public class JokesActivityPresenter extends ActivityPresenter<JokesView, JokesModel> {

    @Override
    protected void inCreat(Bundle savedInstanceState) {
        getModelRef().fetch(1,10);
    }

    @Override
    protected void inDestory() {

    }

    @Override
    public void onError(Throwable e) {
        getViewRef().onError(e);
    }

    @Override
    public <T> void onSuccess(T result) {
        runOnUiThread(()-> getViewRef().onSuccess(result));
    }
}