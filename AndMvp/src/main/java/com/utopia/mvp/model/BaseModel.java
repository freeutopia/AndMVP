package com.utopia.mvp.model;

import com.utopia.mvp.listener.DataChangeListener;

public abstract class BaseModel implements IModel {
    private DataChangeListener mListener = null;

    @Override
    public void setCallback(DataChangeListener listener) {
        this.mListener = listener;
    }

    protected void notifyError(Throwable e) {
        if (mListener != null) {
            mListener.onError(e);
        }
    }

    protected  <T> void notifySuccess(T result) {
        if (mListener != null) {
            mListener.onSuccess(result);
        }
    }


    @Override
    public void onDestory() {
        mListener = null;
    }
}
