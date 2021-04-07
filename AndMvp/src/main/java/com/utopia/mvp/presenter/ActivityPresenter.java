package com.utopia.mvp.presenter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.utopia.mvp.listener.DataChangeListener;
import com.utopia.mvp.model.BaseModel;
import com.utopia.mvp.utils.ReflectUtils;
import com.utopia.mvp.view.BaseView;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import androidx.appcompat.app.AppCompatActivity;

public abstract class ActivityPresenter<V extends BaseView, M extends BaseModel>
        extends AppCompatActivity implements DataChangeListener {

    private V v;//View 层
    private M m;//Model 层

    @Override
    @SuppressWarnings("unchecked")
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterizedType parameterizedType = (ParameterizedType) Objects.requireNonNull(getClass().getGenericSuperclass());
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        try {
            //通过反射泛型参数，构造view实例
            v = (V) ReflectUtils.getClass(actualTypeArguments[0]).newInstance();

            //填充ContentView
            v.creatContentView(this, view->{
                setContentView(view);
                addEventListener();
            });

            //初始化Model，具体初始化方式由子类实现
            m = (M) ReflectUtils.getClass(actualTypeArguments[1]).newInstance();
            m.setCallback(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //view 和 model初始化成功后，开始处理onCreate代理方法inCreat
        inCreat(savedInstanceState);
    }

    protected void addEventListener(){}


    /**
     * 获取viwe的引用,若view 已被回收，则抛出异常
     */
    protected final V getViewRef() {
        return v;
    }

    /**
     * 获取model的引用,若model 已被回收，则抛出异常
     */
    protected final M getModelRef() {
        return m;
    }

    @Override
    protected final void onDestroy() {
        //销毁view
        if (v != null) {
            v.onDestory();
            v = null;
        }

        //销毁model
        if (m != null) {
            m.onDestory();
            m = null;
        }

        inDestory();
        super.onDestroy();
    }

    /**
     * 生命周期处理->对应onCreate()
     */
    protected abstract void inCreat(Bundle savedInstanceState);

    /**
     * 生命周期处理->对应onDestroy()
     */
    protected abstract void inDestory();
}