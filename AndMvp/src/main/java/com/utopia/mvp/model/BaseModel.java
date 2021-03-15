package com.utopia.mvp.model;

import com.utopia.mvp.listener.DataChangeListener;

public abstract class BaseModel implements IModel{
    private DataChangeListener mListener = null;

    /**
     * 监听数据回调
     */
    public void setCallback(DataChangeListener listener) {
        this.mListener = listener;
    }

    /**
     * 数据错误回调
     */
    protected void notifyError(Throwable e) {
        if (mListener != null) {
            mListener.onError(e);
        }
    }

    /**
     * 数据获取成功回调
     */
    protected  <T> void notifySuccess(T result) {
        if (mListener != null) {
            mListener.onSuccess(result);
        }
    }

    /**
     * 处理资源回收
     */
    @Override
    public void onDestory() {
        mListener = null;
    }
}
