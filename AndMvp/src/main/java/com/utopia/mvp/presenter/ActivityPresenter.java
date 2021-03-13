package com.utopia.mvp.presenter;

import android.os.Bundle;

import com.utopia.mvp.listener.DataChangeListener;
import com.utopia.mvp.model.BaseModel;
import com.utopia.mvp.utils.ReflectUtils;
import com.utopia.mvp.view.BaseView;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import androidx.appcompat.app.AppCompatActivity;

public abstract class ActivityPresenter<V extends BaseView, M extends BaseModel>
        extends AppCompatActivity
        implements DataChangeListener, IPresenter{
    private V v;//View 层
    private M m;//Model 层

    @SuppressWarnings("unchecked")
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Type[] type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments();
        try {
            v = (V) ReflectUtils.getClass(type[0]).newInstance();
            v.setPresenter(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            m = (M) ReflectUtils.getClass(type[1]).newInstance();
            m.setCallback(this);
        }catch (Exception e){
            e.printStackTrace();
        }

        setContentView(v.creatContentView(getLayoutInflater(), null));

        inCreat(savedInstanceState);
    }


    @Override
    protected final void onDestroy() {
        inDestory();

        if (v != null) {
            v.onDestory();
            v = null;
        }

        if (m != null) {
            m.onDestory();
            m = null;
        }

        super.onDestroy();
    }

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

}