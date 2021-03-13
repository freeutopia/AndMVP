package com.utopia.mvp.presenter;


import android.os.Bundle;

public interface IPresenter {
    /**
     * 生命周期处理->对应onCreate()
     */
    void inCreat(Bundle savedInstanceState);

    /**
     * 生命周期处理->对应onDestroy()
     */
    void inDestory();
}
