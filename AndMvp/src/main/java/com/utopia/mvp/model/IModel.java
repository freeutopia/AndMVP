package com.utopia.mvp.model;


public interface IModel {
    default void fetch(){};//拉取数据

    default void fetch(int indexPage , int pageSize){};//拉取分页数据

    void onDestory();
}
