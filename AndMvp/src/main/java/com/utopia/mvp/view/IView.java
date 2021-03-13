package com.utopia.mvp.view;


/**
 * 视图层代理的接口协议
 */
interface IView {

    /**
     * 显示加载中页面
     */
    default void showLoading(){}

    /**
     * 隐藏加载
     */
    default void hideLoading() { }


    void onDestory();
}
