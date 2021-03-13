package com.utopia.demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utopia.demo.R;
import com.utopia.demo.bean.Jokes;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.ViewHolder> {

    private final List<Jokes> mData = new ArrayList<>();

    public void addData(List<Jokes> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.fillData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvDate;

        public ViewHolder(View view) {
            super(view);
            tvContent = view.findViewById(R.id.tv_content);
            tvDate = view.findViewById(R.id.tv_date);
        }

        public void fillData(Jokes jokes){
            tvContent.setText(jokes.content);
            tvDate.setText(jokes.updatetime);
        }
    }
}
