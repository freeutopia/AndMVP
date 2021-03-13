package com.utopia.mvp.listener;

/**
 * 用于m-v-p框架之间的数据传递
 */
public interface DataChangeListener {
    /**
     * 处理错误回调
     */
    default void onError(Throwable e){};

    /**
     * 处理成功回调
     */
    default <T> void onSuccess(T result){};
}
