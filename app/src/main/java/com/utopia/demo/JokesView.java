package com.utopia.demo;

import com.utopia.demo.adapter.JokesAdapter;
import com.utopia.demo.bean.Jokes;
import com.utopia.mvp.view.BaseView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class JokesView extends BaseView<JokesActivityPresenter> {
    private JokesAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        RecyclerView recyclerView = bind(R.id.rv_content);

        recyclerView.setLayoutManager(new LinearLayoutManager(p));
        mAdapter = new JokesAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public <T> void onSuccess(T result) {
        mAdapter.addData((List<Jokes>) result);
    }
}
