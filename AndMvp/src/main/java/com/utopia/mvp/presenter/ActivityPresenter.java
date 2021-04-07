package com.utopia.mvp.presenter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.utopia.mvp.listener.DataChangeListener;
import com.utopia.mvp.model.BaseModel;
import com.utopia.mvp.utils.ReflectUtils;
import com.utopia.mvp.view.BaseView;

import java.lang.ref.SoftReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ActivityPresenter<V extends BaseView, M extends BaseModel>
        extends AppCompatActivity implements DataChangeListener {

    private SoftReference<V> vRef;//View 层
    private SoftReference<M> mRef;//Model 层

    @Override
    @SuppressWarnings("unchecked")
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterizedType parameterizedType = (ParameterizedType) Objects.requireNonNull(getClass().getGenericSuperclass());
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        //通过反射泛型参数，构造view实例
        try {
            V view = (V) ReflectUtils.getClass(actualTypeArguments[0]).newInstance();
            //填充ContentView
            view.creatContentView(this, v->{
                setContentView(v);
                addEventListener();
            });
            //避免内存泄漏，将view加入到软引用队列
            vRef = new SoftReference<>((V) view);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            //初始化Model，具体初始化方式由子类实现
            M model = (M) ReflectUtils.getClass(actualTypeArguments[1]).newInstance();
            model.setCallback(this);
            //避免内存泄漏，将model加入到软引用队列
            mRef = new SoftReference<>((M) model);
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
    protected final SoftReference<V> viewRef() {
        return vRef;
    }

    /**
     * 获取model的引用,若model 已被回收，则抛出异常
     */
    protected final SoftReference<M> modelRef() {
        return mRef;
    }

    @Override
    protected final void onDestroy() {
        //销毁view
        if (vRef != null) {
            V view = vRef.get();
            if (view != null) {
                view.onDestory();
                vRef.clear();
            }
            vRef = null;
        }

        //销毁model
        if (mRef != null) {
            M model = mRef.get();
            if (model != null) {
                model.onDestory();
                mRef.clear();
            }
            mRef = null;
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