package com.utopia.mvp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utopia.mvp.listener.DataChangeListener;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 * 将view加载的过程写在抽象类，做到代码复用。
 * View delegate base class
 * 视图层代理的基类
 */
public abstract class BaseView implements IView , DataChangeListener {
    private View rootView;

    /**
     * 加载根视图
     */
    public final View creatContentView(LayoutInflater inflater, ViewGroup parent) {
        rootView = inflater.inflate(getLayoutId(), parent);
        init(rootView.getContext());
        return rootView;
    }

    /**
     * 返回根视图的id,提供给继承者使用
     */
    protected abstract @LayoutRes int getLayoutId();

    /**
     * 初始化工作
     */
    protected abstract void init(Context context);

    @SuppressWarnings("unchecked")
    protected final <T extends View> T bind(@IdRes int id) {
        return (T) rootView.findViewById(id);
    }

    /**
     * 资源回收工作
     */
    @Override
    public void onDestory() {
        rootView = null;
    }
}
